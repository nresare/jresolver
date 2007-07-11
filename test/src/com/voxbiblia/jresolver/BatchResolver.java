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

import java.util.List;

/**
 * Resolves the MX records of a couple of domains.
 */
public class BatchResolver
{
    public static void main(String[] args)
    {
        Resolver r = new Resolver("johanna.resare.com");
        long before = System.currentTimeMillis();
        List l = r.resolve(new MXQuery("svd.se"));
        for (int i = 0; i < l.size(); i++) {
            MXRecord mx = (MXRecord)l.get(i);
            System.out.println("mx: " + mx.getExchange() + " p: "+ mx.getPreference());
        }
        System.out.println("elapsed time: " + (System.currentTimeMillis() - before) / 1000.0);
    }
}
