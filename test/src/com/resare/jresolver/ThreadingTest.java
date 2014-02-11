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

/**
 * Some code to test threading behaviour
 *
 * @author Noa Resare (noa@resare.com)
 */
public class ThreadingTest
{
    private static class Waiter extends Thread
    {
        public void run()
        {
            //try {
                System.out.println("Waiter: sleeping");
                //Thread.sleep(1000);
                System.out.println("Waiter: done sleeping");
            //} catch (InterruptedException e) {
            //}
            System.out.println("Waiter: waiting");
            try {
                synchronized(this) {
                    this.wait();
                }
                System.out.println("Waiter: wait returned");
            } catch (InterruptedException e) {
                throw new Error(e);
            }
        }
    }

    public static void main(String[] args) throws InterruptedException
    {
        Waiter w = new Waiter();
        w.start();
        Thread.sleep(1000);
        System.out.println("main: sending notify");
        synchronized(w) {
            w.notify();
        }
    }

}
