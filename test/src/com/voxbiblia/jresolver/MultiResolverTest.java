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
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.zip.GZIPInputStream;

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

        private List exceptions = new ArrayList();
        private int resolveCount = 0;

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
                    try {
                        resolver.resolve(new MXQuery(s));
                    } catch (Throwable t) {
                        exceptions.add(t);
                    }

                    resolveCount++;
                    if (resolveCount % 10 == 0) {
                        System.out.println("Thread " + getName() +
                                " has resolved "+ resolveCount);
                    }
                    /**
                    System.out.println("s: " + s + " count: " + l.size());
                    for (int i = 0; i < l.size(); i++) {
                        System.out.println("\t" + l.get(i));
                    }
                    */

                }
            } catch (NoSuchElementException e) {
                // just exit silently when the last element is processed
                parent.finished();
            }
        }

        public int getResolveCount()
        {
            return resolveCount;
        }

        public List getExceptions()
        {
            return exceptions;
        }
    }

    private static final int THREAD_COUNT = 10;

    public void testResolver()
            throws Exception
    {
        long before = System.currentTimeMillis();


        Resolver r = new Resolver("127.0.0.1");

        LinkedList toResolve = getDomains("test/data/domains.txt.gz");
        List threads = new ArrayList();
        for (int i = 0 ; i < THREAD_COUNT; i++) {
            ResolverThread rt =  new ResolverThread(toResolve, r, this);
            rt.start();
            threads.add(rt);
        }

        while(stopCount < THREAD_COUNT) {
            synchronized (this) {
                wait();
            }

        }
        int sum = 0;
        List exceptions =  new ArrayList();

        for (int i = 0; i < threads.size(); i++) {
            ResolverThread rt = (ResolverThread)threads.get(i);
            sum += rt.getResolveCount();
            exceptions.addAll(rt.getExceptions());
        }
        System.out.println("number of resolutions: "+ sum);
        System.out.println("number of exceptions: "+ exceptions.size());
        for (int i = 0; i < exceptions.size(); i++) {
            Throwable t = (Throwable)exceptions.get(i);
            System.out.println(t.getClass().getName() + " " + t.getMessage());
        }

        System.out.println("execution took " + (System.currentTimeMillis() - before) + "ms");
    }

    private LinkedList getDomains(String fileName)
            throws Exception
    {
        InputStream is;
        if (fileName.endsWith(".gz")) {
            is = new GZIPInputStream(new FileInputStream(fileName));
        } else {
            is = new FileInputStream(fileName);
        }
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        LinkedList ll = new LinkedList();
        String domain = br.readLine();
        while (domain != null) {
            ll.add(domain);
            domain = br.readLine();
        }
        return ll;
    }


    /**
     * called by a ResolverThread when it has finished.
     */
    void finished()
    {
        synchronized (this) {
            notify();
        }
        stopCount++;
    }
}
