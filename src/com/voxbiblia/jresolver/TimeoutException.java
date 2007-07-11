package com.voxbiblia.jresolver;

/**
 * An exception of this class is thrown the timeout set in the
 * Resolver expires before an answer was recieved. {@see Resolver.setTimeout()}
 *
 * @author Noa Resare (noa@resare.com)
 */
public class TimeoutException extends RuntimeException
{
    public TimeoutException()
    {
        super();
    }

    public TimeoutException(String msg)
    {
        super(msg);
    }
}
