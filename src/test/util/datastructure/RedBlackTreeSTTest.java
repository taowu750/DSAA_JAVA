package test.util.datastructure;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import util.datastructure.RedBlackTreeST;

import static org.junit.jupiter.api.Assertions.*;

/**
 * RedBlackTreeST Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>十月 12, 2019</pre>
 */
public class RedBlackTreeSTTest {

    @BeforeEach
    public void before() throws Exception {
    }

    @AfterEach
    public void after() throws Exception {
    }

    @Test
    public void testPut() throws Exception {
        RedBlackTreeST<Integer, String> rbt = new RedBlackTreeST<>();

        rbt.put(1, "1");
        rbt.put(6, "6");
        rbt.put(-3, "-3");
        rbt.put(7, "7");
        rbt.put(11, "11");
        rbt.put(1, "1-1");
        rbt.put(9, "9");
        rbt.put(4, "4");
        rbt.put(-2, "-2");
        assertFalse(rbt.isEmpty());
        assertEquals(rbt.size(), 8);

        assertTrue(rbt.contains(1));
        assertTrue(rbt.contains(4));
        assertFalse(rbt.contains(-90));
        assertEquals(rbt.get(1), "1-1");
        assertEquals(rbt.rank(7), 5);
        assertEquals(rbt.min().value(), "-3");
        assertEquals(rbt.max().value(), "11");
        assertEquals(rbt.floor(-1).value(), "-2");
        assertEquals(rbt.ceil(5).value(), "6");
        assertEquals(rbt.select(2).value(), "1-1");

        for (Integer k : rbt.keys()) {
            System.out.print(k + " ");
        }
        System.out.println();
        for (Integer k : rbt.keys(-3, 9)) {
            System.out.print(k + " ");
        }
        System.out.println();

        rbt.inOrder();
        System.out.println();
        rbt.levelTraversal();
    }

    @Test
    public void testDeleteMin() throws Exception {
        RedBlackTreeST<Integer, String> rbt = new RedBlackTreeST<>();

        rbt.put(1, "1");
        rbt.put(6, "6");
        rbt.put(-3, "-3");
        rbt.put(7, "7");
        rbt.put(11, "11");
        rbt.put(1, "1-1");
        rbt.put(9, "9");
        rbt.put(4, "4");
        rbt.put(-2, "-2");

        assertEquals(rbt.deleteMin().value(), "-3");
        assertEquals(rbt.size(), 7);
        assertEquals(rbt.deleteMin().value(), "-2");
        assertEquals(rbt.size(), 6);

        rbt.levelTraversal();
    }

    @Test
    public void testDeleteMax() throws Exception {
        RedBlackTreeST<Integer, String> rbt = new RedBlackTreeST<>();

        rbt.put(1, "1");
        rbt.put(6, "6");
        rbt.put(-3, "-3");
        rbt.put(7, "7");
        rbt.put(11, "11");
        rbt.put(1, "1-1");
        rbt.put(9, "9");
        rbt.put(4, "4");
        rbt.put(-2, "-2");

        assertEquals(rbt.deleteMax().value(), "11");
        assertEquals(rbt.size(), 7);
        assertEquals(rbt.deleteMax().value(), "9");
        assertEquals(rbt.size(), 6);

        rbt.levelTraversal();
    }

    @Test
    public void testDelete() throws Exception {
        RedBlackTreeST<Integer, String> rbt = new RedBlackTreeST<>();

        rbt.put(1, "1");
        rbt.put(6, "6");
        rbt.put(-3, "-3");
        rbt.put(7, "7");
        rbt.put(11, "11");
        rbt.put(1, "1-1");
        rbt.put(9, "9");
        rbt.put(4, "4");
        rbt.put(-2, "-2");

        rbt.levelTraversal();
        System.out.println();

        assertEquals(rbt.delete(6), "6");
        assertEquals(rbt.size(), 7);
        assertEquals(rbt.delete(1), "1-1");
        assertEquals(rbt.size(), 6);

        rbt.levelTraversal();
    }
}
