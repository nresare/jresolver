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

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

/**
 * Tests Resolver
 */
public class ResolverTest
    extends TestCase
{
 
    public void testResolve()
    {
        Resolver r = new Resolver("ns.resare.com");
        r.setTimeout(10);
        List l = r.resolve(new MXQuery("jresolvertest.resare.com"));
        assertEquals(1, l.size());
        assertEquals("mail.resare.com", ((MXRecord)l.get(0)).getExchange());
        assertEquals(10, ((MXRecord)l.get(0)).getPreference());
    }

    public void testRefused()
    {
        Resolver r = new Resolver("ns2.spotify.com");
        r.setTimeout(10);
        try {
            r.resolve(new MXQuery("resare.com"));
            fail("should have thrown QRE");
        } catch (QueryRefusedException e) {
            // ignore
        }

    }

    public void testParseResponse()
            throws Exception
    {
        List l = Resolver.parseResponse(readFile("src/test/data/answer.bin"));
        assertNotNull(l);
        assertEquals(3, l.size());

        MXRecord r = (MXRecord)l.get(0);
        assertEquals(5, r.getPreference());
        assertEquals("johanna.resare.com", r.getExchange());
        r = (MXRecord)l.get(1);
        assertEquals(10, r.getPreference());
        assertEquals("evert.evolvator.se", r.getExchange());
        r = (MXRecord)l.get(2);
        assertEquals(20, r.getPreference());
        assertEquals("ulla.resare.com", r.getExchange());
    }

    public void testParseServfail()
            throws Exception
    {
        try {
            Resolver.parseResponse(readFile("src/test/data/servfail.bin"));
            fail("should have caught ServfailException");
        } catch (ServFailException e) {

        }
    }

    public void testParseNXDomain()
            throws Exception
    {
        try {
            Resolver.parseResponse(readFile("src/test/data/nxdomain.bin"));
            fail("should have caught NXDomainException");
        } catch (NXDomainException e) {

        }
    }


    private byte[] readFile(String fn)
            throws IOException
    {
        File testDataFile = new File(fn);
        byte[] data = new byte[(int)testDataFile.length()];
        FileInputStream fis = new FileInputStream(testDataFile);
        assertEquals(data.length, fis.read(data));
        fis.close();
        return data;
    }
}
