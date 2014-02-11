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
 * An MX record
 */
public class MXRecord implements Comparable
{
    private String exchange;
    private int preference;

    public String getExchange()
    {
        return exchange;
    }

    public void setExchange(String exchange)
    {
        this.exchange = exchange;
    }

    public int getPreference()
    {
        return preference;
    }

    public void setPreference(int preference)
    {
        this.preference = preference;
    }

    public int compareTo(Object o)
    {
        return preference - ((MXRecord)o).getPreference();
    }

    public String toString()
    {
        return "{" + getExchange() + ": " + getPreference() + "}";
    }
}
