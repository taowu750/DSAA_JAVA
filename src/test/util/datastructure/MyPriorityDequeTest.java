package test.util.datastructure;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import util.datastructure.MyPriorityDeque;

import static org.junit.jupiter.api.Assertions.*;

/**
 * MyPriorityDeque Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>九月 28, 2019</pre>
 */
public class MyPriorityDequeTest {

    @BeforeEach
    public void before() throws Exception {
    }

    @AfterEach
    public void after() throws Exception {
    }


    @Test
    public void testDeque() throws Exception {
        MyPriorityDeque<Integer> dq = new MyPriorityDeque<>();

        assertTrue(dq.isEmpty());
        dq.offer(3);
        assertFalse(dq.isEmpty());
        assertEquals(dq.peekFirst(), new Integer(3));
        assertEquals(dq.peekLast(), new Integer(3));
        assertEquals(dq.pollLast(), new Integer(3));
        assertEquals(dq.pollFirst(), null);
        assertEquals(dq.size(), 0);

        dq.offer(7);
        dq.offer(9);
        dq.offer(-1);
        dq.offer(4);
        dq.offer(3);
        dq.offer(6);
        dq.offer(3);
        dq.offer(2);
        assertEquals(dq.size(), 8);
        assertEquals(dq.peekFirst(), new Integer(9));
        assertEquals(dq.peekLast(), new Integer(-1));
        assertEquals(dq.pollFirst(), new Integer(9));
        assertEquals(dq.pollFirst(), new Integer(7));
        assertEquals(dq.pollLast(), new Integer(-1));
        assertEquals(dq.pollLast(), new Integer(2));
        assertEquals(dq.size(), 4);
    }
} 
