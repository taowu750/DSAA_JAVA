package test.util.datastructure;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import util.datastructure.MyIndexPriorityQueue;

import static org.junit.jupiter.api.Assertions.*;

/**
 * MyIndexPriorityQueue Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>十月 2, 2019</pre>
 */
public class MyIndexPriorityQueueTest {

    @BeforeEach
    public void before() throws Exception {
    }

    @AfterEach
    public void after() throws Exception {
    }

    @Test
    public void testIndex() throws Exception {
        MyIndexPriorityQueue<Integer> ipq = new MyIndexPriorityQueue<>();
        assertTrue(ipq.isEmpty());

        ipq.offer(10, 20);
        assertFalse(ipq.isEmpty());
        assertEquals(ipq.size(), 1);
        ipq.offer(1, 3);
        ipq.offer(2, 5);
        ipq.offer(3, -1);
        ipq.offer(4, 7);
        ipq.offer(5, 9);
        ipq.offer(6, 3);
        assertEquals(ipq.size(), 7);
        assertEquals(ipq.peek(10), new Integer(20));
        assertEquals(ipq.peek(1), new Integer(3));
        assertEquals(ipq.peek(6), new Integer(3));
        assertEquals(ipq.peek(3), new Integer(-1));
        assertEquals(ipq.peek(4), new Integer(7));
        assertEquals(ipq.peek().val(), new Integer(20));

        assertEquals(ipq.poll(5), new Integer(9));
        assertEquals(ipq.size(), 6);
        assertEquals(ipq.peek().val(), new Integer(20));
        assertEquals(ipq.poll(10), new Integer(20));
        assertEquals(ipq.poll().val(), new Integer(7));
        assertEquals(ipq.peek().val(), new Integer(5));
        assertEquals(ipq.size(), 4);

        ipq.offer(5, 4);
        assertEquals(ipq.peek(5), new Integer(4));
        assertEquals(ipq.set(5, 31), new Integer(4));
        assertEquals(ipq.peek(5), new Integer(31));
        assertEquals(ipq.peek().val(), new Integer(31));
        assertEquals(ipq.poll().val(), new Integer(31));
        assertEquals(ipq.size(), 4);
    }
} 
