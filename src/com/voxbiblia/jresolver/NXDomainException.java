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
 * Instances of this class is thrown when name server response
 * is recieved that has the RCODE 3, Name Error as defined in
 * RFC1035 4.4.1. This is what is commonly known as the No Such
 * Domain response, or NXDOMAIN
 *
 * @author Noa Resare (noa@voxbiblia.com)
 */
public class NXDomainException extends RuntimeException
{
}
