package com.voxbiblia.jresolver;

import junit.framework.TestCase;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

import com.voxbiblia.jresolver.*;

/**
 * Tests Resolver
 */
public class ResolverTest
    extends TestCase
{
 
    public void testResolve()
    {
        Resolver r = new Resolver("johanna.resare.com");
        r.setTimeout(100);
        List l = r.resolve(new MXQuery("dn.se"));
        assertEquals(1, l.size());
        assertEquals("mail-gw.dn.se", ((MXRecord)l.get(0)).getExchange());
        assertEquals(10, ((MXRecord)l.get(0)).getPreference());
    }

    public void testParseResponse()
            throws Exception
    {
        List l = Resolver.parseResponse(readFile("test/data/answer.bin"));
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
            Resolver.parseResponse(readFile("test/data/servfail.bin"));
            fail("should have caught ServfailException");
        } catch (ServFailException e) {

        }
    }

    public void testParseNXDomain()
            throws Exception
    {
        try {
            Resolver.parseResponse(readFile("test/data/nxdomain.bin"));
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
