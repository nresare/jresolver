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
