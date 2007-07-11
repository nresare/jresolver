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
package com.voxbiblia.jresolver;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Map;
import java.util.HashMap;
import java.util.logging.Logger;

/**
 * A transport service that can be used to test the resend implementation
 * in 
 *
 * @author Noa Resare (noa@voxbiblia.com)
 */
public class DummyTransportService implements TransportService
{
    private static final Logger log = Logger.getLogger(DummyTransportService.class.getName());

    private final byte[] answer;
    private final LinkedList ids = new LinkedList();
    private final Map dropped = new HashMap();

    private int mode = 0;
    static final int REPLY = 0;
    static final int DROP_FIRST = 1;

    public DummyTransportService()
    {
        File testDataFile = new File("test/data/answer.bin");
        answer = new byte[(int)testDataFile.length()];
        try {
            FileInputStream fis = new FileInputStream(testDataFile);
            if (fis.read(answer) != answer.length) {
                throw new Error("failed to read the the answer properly");
            }
            fis.close();
        } catch (IOException e) {
            throw new Error(e);
        }

    }


    public void send(byte[] data)
    {

        Integer i = new Integer(Buffer.parseInt16(data));
        log.fine("packet in with id " + i);
        if (mode == DROP_FIRST) {
            Object o = dropped.get(i);
            if (o == null) {
                dropped.put(i, Boolean.TRUE);
                log.info("dropping packet with id " + i);
                return;
            }
        }
        synchronized(ids) {
            ids.add(i);
        }
        synchronized(this) {
            this.notify();
        }
    }

    public int recv(byte[] buffer)
    {

        try {
            synchronized(this) {
                this.wait();
            }
        } catch (InterruptedException e) {
            throw new Error(e);
        }
        System.arraycopy(answer, 0, buffer, 0, answer.length);
        int id;
        synchronized(ids) {
            id = ((Integer)ids.removeFirst()).intValue();
        }
        buffer[0] = (byte)(id >> 8 & 0xff);
        buffer[1] = (byte)(id & 0xff);
        log.fine("packet out with id " + id);
        return answer.length;
    }


    public void setMode(int mode)
    {
        this.mode = mode;
    }
}
