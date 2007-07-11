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

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.List;
import java.util.LinkedList;
import java.util.NoSuchElementException;

/**
 * Tests multiple resolutions 
 */
public class MultiResolverTest
        extends TestCase
{
    int stopCount = 0;
    private static class ResolverThread extends Thread
    {
        final private LinkedList toResolve;
        private Resolver resolver;
        private MultiResolverTest parent;


        public ResolverThread(LinkedList toResolve, Resolver resolver, MultiResolverTest parent)
        {
            this.toResolve = toResolve;
            this.resolver = resolver;
            this.parent = parent;
        }


        public void run()
        {
            try {
                //noinspection InfiniteLoopStatement
                while (true) {
                    String s;
                    synchronized(toResolve) {
                        s = (String)toResolve.removeFirst();
                    }
                    List l = resolver.resolve(new MXQuery(s));
                    System.out.println("s: " + s + " count: " + l.size());
                    for (int i = 0; i < l.size(); i++) {
                        System.out.println("\t" + l.get(i));
                    }

                }
            } catch (NoSuchElementException e) {
                // just exit silently when the last element is processed
                parent.finished();
            }
        }
    }

    private static final int THREAD_COUNT = 10;

    public void testResolver()
            throws Exception
    {
        long before = System.currentTimeMillis();
        BufferedReader br = new BufferedReader(new FileReader("test/data/email-domains.txt"));
        Resolver r = new Resolver("127.0.0.1");
        String s = br.readLine();
        LinkedList toResolve = new LinkedList();
        while (s != null) {
            toResolve.add(s);
            s = br.readLine();
        }
        br.close();

        for (int i = 0 ; i < THREAD_COUNT; i++) {
            ResolverThread rt =  new ResolverThread(toResolve, r, this);
            rt.start();
        }

        while(stopCount < THREAD_COUNT) {
            Thread.sleep(100);
        }
        System.out.println("execution took " + (System.currentTimeMillis() - before) + "ms");
    }

    /**
     * called by a ResolverThread when it has finished.
     */
    void finished()
    {
        stopCount++;
    }
}
