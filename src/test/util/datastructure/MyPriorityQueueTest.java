package test.util.datastructure;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import util.datastructure.MyPriorityQueue;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

/**
 * MyPriorityQueue Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>九月 27, 2019</pre>
 */
public class MyPriorityQueueTest {

    @BeforeEach
    public void before() throws Exception {
    }

    @AfterEach
    public void after() throws Exception {
    }

    @Test
    public void test_insert() throws Exception {
        MyPriorityQueue<Integer> pq = new MyPriorityQueue<>(MyPriorityQueue.Order.MAX);
        int len = 20;
        Integer[] arr = new Random().ints(len, -15, 15)
                .boxed()
                .peek(i -> {
                    System.out.print(i + " ");
                    if (i == len)
                        System.out.println();
                })
                .peek(pq::offer)
                .toArray(Integer[]::new);
        System.out.println();
        Integer[] sorted = Arrays.stream(arr)
                .sorted(Comparator.reverseOrder())
                .peek(i -> {
                    System.out.print(i + " ");
                    if (i == len)
                        System.out.println();
                })
                .toArray(Integer[]::new);

        Arrays.stream(sorted)
                .forEach(i -> assertEquals(pq.poll(), i));
    }

    @Test
    public void testPQ() throws Exception {
        MyPriorityQueue<Integer> pq = new MyPriorityQueue<>();

        assertTrue(pq.isEmpty());
        pq.offer(4);
        assertFalse(pq.isEmpty());

        pq.offer(10);
        pq.offer(1);
        pq.offer(-1);
        pq.offer(0);
        pq.offer(9);
        pq.offer(20);
        assertEquals(pq.size(), 7);
        assertEquals(pq.peek(), new Integer(20));
        assertEquals(pq.poll(), new Integer(20));
        assertEquals(pq.poll(), new Integer(10));
        assertEquals(pq.size(), 5);

        pq.offer(34);
        pq.offer(21);
        pq.offer(15);
        pq.offer(-3);
        pq.offer(23);
        pq.offer(31);
        pq.offer(34);
        pq.offer(22);
        pq.offer(221);
        pq.offer(1);
        pq.offer(13);
        pq.offer(-8);
        assertEquals(pq.size(), 17);
        assertEquals(pq.maxSize(), Integer.MAX_VALUE - 1);
        assertEquals(pq.poll(), new Integer(221));

        pq.toggleOrder();
        assertEquals(pq.size(), 16);
        assertEquals(pq.peek(), new Integer(-8));
    }

    @Test
    public void testArray() throws Exception {
        MyPriorityQueue<Integer> pq = new MyPriorityQueue<>(new Integer[]{7, 9, -1, 4, 3});
        assertFalse(pq.isEmpty());
        assertEquals(pq.size(), 5);
        assertEquals(pq.peek(), new Integer(9));
        assertEquals(pq.poll(), new Integer(9));
        assertEquals(pq.size(), 4);

        pq.toggleOrder();
        assertEquals(pq.poll(), new Integer(-1));
        assertEquals(pq.poll(), new Integer(3));
    }

    @Test
    public void testTail() throws Exception {
        MyPriorityQueue<Integer> pq = new MyPriorityQueue<>(new Integer[]
                {7, 9, -1, 4, 3, -9, 100, -1, -2, 3, 67, 25, 44, 35, 1});
        assertEquals(pq.tail(), new Integer(-9));

        pq.toggleOrder();
        assertEquals(pq.tail(), new Integer(100));
    }
}
