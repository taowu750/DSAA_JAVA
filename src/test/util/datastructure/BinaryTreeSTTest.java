package test.util.datastructure;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import util.datastructure.BinaryTreeST;

import static org.junit.jupiter.api.Assertions.*;


/**
 * BinaryTreeST Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>十月 8, 2019</pre>
 */
public class BinaryTreeSTTest {

    @BeforeEach
    public void before() throws Exception {
    }

    @AfterEach
    public void after() throws Exception {
    }

    @Test
    public void testSimple() throws Exception {
        BinaryTreeST<Integer, String> bt = new BinaryTreeST<>();

        assertTrue(bt.isEmpty());
        assertTrue(bt.size() == 0);

        bt.put(1, "1");
        bt.put(6, "6");
        bt.put(-3, "-3");
        bt.put(7, "7");
        bt.put(11, "11");
        bt.put(1, "1-1");
        bt.put(9, "9");
        bt.put(4, "4");
        bt.put(-2, "-2");
        assertFalse(bt.isEmpty());
        assertEquals(bt.size(), 8);

        assertTrue(bt.contains(1));
        assertTrue(bt.contains(4));
        assertFalse(bt.contains(-90));
        assertEquals(bt.get(1), "1-1");
        assertEquals(bt.get(7), "7");
        assertEquals(bt.get(4), "4");
        assertEquals(bt.get(-3), "-3");
        assertEquals(bt.get(100), null);

        assertEquals(bt.min().value(), "-3");
        assertEquals(bt.max().value(), "11");

        assertEquals(bt.floor(5).value(), "4");
        assertEquals(bt.floor(2).value(), "1-1");
        assertEquals(bt.floor(-3).value(), "-3");
        assertEquals(bt.floor(-1).value(), "-2");
        assertEquals(bt.floor(5).value(), "4");
        assertEquals(bt.floor(7).value(), "7");
        assertEquals(bt.floor(10).value(), "9");
        assertEquals(bt.floor(12).value(), "11");

        assertEquals(bt.ceil(1).value(), "1-1");
        assertEquals(bt.ceil(2).value(), "4");
        assertEquals(bt.ceil(-1).value(), "1-1");
        assertEquals(bt.ceil(5).value(), "6");
        assertEquals(bt.ceil(8).value(), "9");

        assertEquals(bt.select(0).value(), "-3");
        assertEquals(bt.select(1).value(), "-2");
        assertEquals(bt.select(2).value(), "1-1");
        assertEquals(bt.select(3).value(), "4");
        assertEquals(bt.select(4).value(), "6");
        assertEquals(bt.select(5).value(), "7");
        assertEquals(bt.select(6).value(), "9");
        assertEquals(bt.select(7).value(), "11");

        assertEquals(bt.rank(1), 2);
        assertEquals(bt.rank(2), 3);
        assertEquals(bt.rank(-1), 2);
        assertEquals(bt.rank(-3), 0);
        assertEquals(bt.rank(-2), 1);
        assertEquals(bt.rank(5), 4);
        assertEquals(bt.rank(7), 5);
        assertEquals(bt.rank(10), 7);

        for (Integer k : bt.keys()) {
            System.out.print(k + " ");
        }
        System.out.println();
        for (Integer k : bt.keys(-3, 9)) {
            System.out.print(k + " ");
        }
        System.out.println();

        assertEquals(bt.delete(6), "6");
        assertEquals(bt.delete(-2), "-2");
        assertEquals(bt.delete(7), "7");
        assertEquals(bt.delete(1), "1-1");
        assertEquals(bt.size(), 4);
    }

    @Test
    public void testDeleteMinMax() throws Exception {
        BinaryTreeST<Integer, String> bt = new BinaryTreeST<>();

        bt.put(1, "1");
        bt.put(6, "6");
        bt.put(-3, "-3");
        bt.put(7, "7");
        bt.put(11, "11");
        bt.put(1, "1-1");
        bt.put(9, "9");
        bt.put(4, "4");
        bt.put(-2, "-2");

        assertEquals(bt.deleteMin().value(), "-3");
        assertEquals(bt.size(), 7);
        assertEquals(bt.deleteMin().value(), "-2");
        assertEquals(bt.size(), 6);
        assertEquals(bt.deleteMax().value(), "11");
        assertEquals(bt.size(), 5);
        assertEquals(bt.deleteMax().value(), "9");
        assertEquals(bt.size(), 4);
    }
}
