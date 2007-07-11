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

/**
 * Prints bitfields as binary strings, most significant bit first
 */
public class BitFieldPrinter
{
    public static void main(String[] args)
    {
        printByte(19);
        printByte(8);
        printByte(129);
    }

    private static void printByte(int b)
    {
        byte b0 = (byte)b;
        System.out.println("01234567");
        StringBuilder sb = new StringBuilder();
        for (int i = 7; i >= 0; i--) {
            sb.append(((b0 >> i) & 1) != 0 ? "1" : "0");
        }
        System.out.println(sb.toString());
        System.out.println("decimal: " + b0);
    }
}
