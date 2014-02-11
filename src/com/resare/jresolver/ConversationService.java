/*
jresolver - The java DNS resolver library
Copyright (C) 2007  Noa Resare (noa@voxbiblia.com)

This program is free software: you can redistribute it and/or modify it under
the terms of the GNU General Public License as published by the Free Software
Foundation, either version 3 of the License, or (at your option) any later
version.

This program is distributed in the hope that it will be useful, but WITHOUT
ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more details.

The GNU General Public License is available from <http://gnu.org/licenses/>.
*/
package com.resare.jresolver;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Low level multithreaded UDP Service that sends and recieves UDP messages,
 * pairing requests and responses based on the id value in the first two
 * packet bytes.
 *
 * @author Noa Resare (noa@resare.com)
 */
class ConversationService
{
    private static Logger log = Logger.getLogger(ConversationService.class.getName());

    // default timeout value is 20 seconds.
    private int timeout = 20;
    private Map queryMap = Collections.synchronizedMap(new HashMap());

    private TransportService transportService;
    private ConversationScheduler scheduler;

    /**
     * Constructs a new ConversationService and spins up the Reciever and Scheduler
     * daemon threads.
     *
     * @param transportService the underlying transport to use
     */
    public ConversationService(TransportService transportService)
    {
        this.transportService = transportService;
        scheduler = new ConversationScheduler(transportService, queryMap);
        scheduler.setDaemon(true);
        scheduler.setName("Scheduler");
        scheduler.start();


        Thread t = new Reciever();
        t.setDaemon(true);
        t.setName("Reciever");
        t.start();
    }

    /**
     * Delegates responsibility for sending the specified query packet to
     * the udp service.
     *
     * @param query query answer
     *
     * @return a DatagramPacket recieved from the server with matching id field
     *
     * @throws TimeoutException if the specified timeout has been reached
     * without an answer.
     */
    public byte[] sendRecv(byte[] query)
    {
        ConversationState state = new ConversationState();

        state.setQuery(query);
        int id = Buffer.parseInt16(query);
        state.setId(id);
        Integer key = new Integer(id);
        queryMap.put(key, state);
        try {
            synchronized(state) {
                scheduler.enqueue(state);
                state.wait(timeout * 1000);
            }
        } catch (InterruptedException e) {
            throw new Error("someone interruped this thread");
        }
        queryMap.remove(key);
        byte[] response = state.getResponse();
        if (response == null) {
            Throwable t = state.getException();
            if (t != null) {
                throw new ServFailException(t);
            }
            throw new TimeoutException("timeout " +
                    "after " + timeout + " seconds.");
        }
        return response;

    }

    public void setTimeout(int seconds)
    {
        this.timeout = seconds;
    }

    private class Reciever extends Thread
    {
        public void run()
        {
            //noinspection InfiniteLoopStatement
            while (true) {

                // the size limit for UDP packets according to RFC1035 2.3.4 is 512 octets.
                byte[] buffer = new byte[512];

                int count = transportService.recv(buffer);
                int id = Buffer.parseInt16(buffer);
                scheduler.remove(id);
                ConversationState state = (ConversationState)queryMap.get(new Integer(id));
                if (state != null) {
                    byte[] data = new byte[count];
                    System.arraycopy(buffer, 0, data, 0, count);
                    state.setResponse(data);
                    synchronized(state) {
                        state.notify();
                    }
                } else {
                    log.info("no query for packet with id " + id + " ignoring");
                }
            }
        }

    }


}
