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
 * Contains information about the state of an UDP query-response-cycle.
 *
 * @author Noa Resare (noa@voxbiblia.com)
 */
class ConversationState
{
    private byte[] response;
    private int id;
    private byte[] query;
    private Throwable exception;

    public byte[] getResponse()
    {
        return response;
    }

    public void setResponse(byte[] response)
    {
        this.response = response;
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public byte[] getQuery()
    {
        return query;
    }

    public void setQuery(byte[] query)
    {
        this.query = query;
    }

    public Throwable getException()
    {
        return exception;
    }

    public void setException(Throwable exception)
    {
        this.exception = exception;
    }
}
