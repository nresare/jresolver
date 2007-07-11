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

import junit.framework.TestCase;

import java.io.ByteArrayOutputStream;

/**
 * Tests the query class. 
 */
public class QueryTest
    extends TestCase
{
    public void testGetHeader()
    {
        for (int i = 0 ; i < 1; i++) {
            MXQuery q = new MXQuery("test");
            byte[] bytes = q.toWire();
            assertNotNull(bytes);
            int id = q.getId();
            assertEquals((byte)(id >> 8 & 0xff), bytes[0]);
            assertEquals((byte)(id & 0xff), bytes[1]);
            assertEquals((byte)1, bytes[2]);
            assertEquals(0, bytes[3]);
            assertEquals(0, bytes[4]);
            assertEquals((byte)1, bytes[5]);
            assertEquals(0, bytes[6]);
            assertEquals(0, bytes[7]);
            assertEquals(0, bytes[8]);
            assertEquals(0, bytes[9]);
            assertEquals(0, bytes[10]);
            assertEquals(0, bytes[11]);
        }

    }

    public void testNameToWire()
            throws Exception
    {
        MXQuery q = new MXQuery("resare.com");
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        q.nameToWire(baos);
        compareArrays(new byte[]{6,'r','e','s','a','r','e',3,'c','o','m',0},baos.toByteArray());
        baos = new ByteArrayOutputStream();
        q.nameToWire(baos);
        compareArrays(new byte[]{6,'r','e','s','a','r','e',3,'c','o','m',0},baos.toByteArray());
    }

    private void compareArrays(byte[] b0, byte[] b1)
    {
        assertEquals(b0.length, b1.length);
        for(int i = 0; i < b0.length; i++) {
            assertEquals("array differs at offset " + i, b0[i], b1[i]);
        }
    }


}
