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

/**
 * This exception is thrown when recieving a response packet from the
 * resolving nameserver with the return code Servef Failure as defined in
 * RFC1035 4.1.1.
 *
 * @author Noa Resare (noa@resare.com)
 */
public class ServFailException extends RuntimeException
{
    public ServFailException()
    {
        super();
    }

    public ServFailException(Throwable root)
    {
        super(root);
    }

    public ServFailException(String msg, Throwable root)
    {
        super(msg,root);
    }

    public ServFailException(String msg)
    {
        super(msg);
    }
}
