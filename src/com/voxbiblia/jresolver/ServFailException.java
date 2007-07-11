package com.voxbiblia.jresolver;

/**
 * This exception is thrown when recieving a response packet from the
 * resolving nameserver with the return code Servef Failure as defined in
 * RFC1035 4.1.1.
 *
 * @author Noa Resare (noa@voxbiblia.com)
 */
public class ServFailException extends RuntimeException
{
}
