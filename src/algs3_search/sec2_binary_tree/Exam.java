package algs3_search.sec2_binary_tree;

import org.junit.jupiter.api.Test;
import util.datastructure.BinaryTreeST;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class Exam {

    /**
     * keys() 方法实现于{@link BinaryTreeST#iter(Comparable, Comparable)}方法
     * @throws Exception
     */
    @Test
    public void test_iter() throws Exception {
        BinaryTreeST<Integer, Integer> st = new BinaryTreeST<>();
        new Random().ints(30, -100, 100)
                .forEach(i -> st.put(i, i));

        int last = -1000;
        for (Integer k : st.iter(-50, 50)) {
            assertTrue(k >= -50 && k < 50);
            assertTrue(k >= last);
            last = k;
        }
    }
}
