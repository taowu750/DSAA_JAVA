package test.util.datastructure;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import util.datastructure.MyStack;

import static org.junit.jupiter.api.Assertions.*;

/**
 * MyStack Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>九月 18, 2019</pre>
 */
public class MyStackTest {

    @BeforeEach
    public void before() throws Exception {
    }

    @AfterEach
    public void after() throws Exception {
    }

    @Test
    public void testStack() throws Exception {
        MyStack<Integer> stack = new MyStack<>(1);

        assertTrue(stack.isEmpty());
        assertEquals(stack.pop(), null);

        stack.push(1);
        assertEquals(stack.top(), new Integer(1));
        assertEquals(stack.top(), new Integer(1));
        assertFalse(stack.isEmpty());

        stack.push(2);
        stack.push(3);
        assertEquals(stack.pop(), new Integer(3));
        assertEquals(stack.pop(), new Integer(2));
        assertEquals(stack.pop(), new Integer(1));
        assertEquals(stack.pop(), null);
        assertTrue(stack.isEmpty());
    }
} 
