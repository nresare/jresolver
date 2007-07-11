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

/**
 * An abstraction of some kind of low level transportation mechanism, such
 * as an UDP socket connected to a specific service.
 *
 * @author Noa Resare (noa@voxbiblia.com)
 */
interface TransportService
{
    /**
     * Sends the specified answer to the transport.
     *
     * @param data the data to send.
     */
    void send(byte[] data);

    /**
     * Blocks until answer is recieved from the transport, then returns
     * the number of bytes written to the given buffer.
     *
     * @param buffer the buffer to write the answer to
     * @return the number of bytes recieved
     */
    int recv(byte[] buffer);
}
