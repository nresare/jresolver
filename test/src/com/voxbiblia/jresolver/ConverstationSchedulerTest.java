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

import java.util.LinkedList;


/**
 * Tests ConversationScheduler
 */
public class ConverstationSchedulerTest
        extends TestCase
{
    public void testPutSorted()
    {
        LinkedList l = new LinkedList();
        l.add(new ConversationScheduler.SendTask(100L, null));
        l.add(new ConversationScheduler.SendTask(200L, null));
        l.add(new ConversationScheduler.SendTask(300L, null));

        ConversationScheduler.putSorted(l, new ConversationScheduler.SendTask(150L, null));
        assertEquals(4, l.size());
        assertEquals(150L, ((ConversationScheduler.SendTask)l.get(1)).getTime());

        l.remove(1);
        ConversationScheduler.putSorted(l, new ConversationScheduler.SendTask(50L, null));
        assertEquals(50L, ((ConversationScheduler.SendTask)l.get(0)).getTime());

        ConversationScheduler.putSorted(l, new ConversationScheduler.SendTask(400L, null));
        assertEquals(5, l.size());
        assertEquals(400L, ((ConversationScheduler.SendTask)l.get(4)).getTime());
    }
}
