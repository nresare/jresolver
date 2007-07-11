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


import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.DatagramPacket;
import java.io.IOException;
import java.util.logging.Logger;
import java.util.logging.Level;

/**
 * Implements the TransportService as an UDP datagram socket connected to
 * a specified server on port 53.
 * 
 * @author Noa Resare (noa@voxbiblia.com)
 */
class UDPTransportService implements TransportService
{
    private DatagramSocket socket;
    private static final int PORT = 53;
    private static final Logger log = Logger.getLogger(UDPTransportService.class.getName());

    public UDPTransportService(String serverName)
    {
        try {
            socket = new DatagramSocket();
            socket.connect(InetAddress.getByName(serverName), PORT);
        } catch (Exception e) {
            throw new Error(e);
        }
    }

    public void send(byte[] data)
    {
        DatagramPacket dp = new DatagramPacket(data, data.length);
        if (log.isLoggable(Level.FINE)) {
            log.fine("sending packet with id " + Buffer.parseInt16(data));
        }
        try {
            socket.send(dp);
        } catch (IOException e) {
            throw new Error(e);
        }
    }

    public int recv(byte[] buffer)
    {
        try {
            DatagramPacket dp = new DatagramPacket(buffer, buffer.length);
            socket.receive(dp);
            if (log.isLoggable(Level.FINE)) {
                log.fine("recieving packet with id " + Buffer.parseInt16(buffer));
            }
            return dp.getLength();
        } catch (IOException e) {
            throw new Error(e);
        }
    }
}
