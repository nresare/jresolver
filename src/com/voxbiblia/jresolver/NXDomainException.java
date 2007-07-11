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
