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

import junit.framework.TestCase;

import java.util.List;

/**
 * Tests the resend implementation.
 */
public class ResendTest
    extends TestCase
{
    public void testResend()
    {
        Resolver r = new Resolver(new DummyTransportService());
        List l = r.resolve(new MXQuery("svd.se"));
        assertNotNull(l);
    }

    public void testWithDrop()
    {
        DummyTransportService dts = new DummyTransportService();
        dts.setMode(DummyTransportService.DROP_FIRST);

        Resolver r = new Resolver(dts);
        List l = r.resolve(new MXQuery("svd.se"));
        assertNotNull(l);
        
    }
}
