package algs3_search.sec2_binary_tree;

import org.junit.jupiter.api.Test;
import util.datastructure.BinaryTreeST;

/**
 * <p>
 * 迭代器。实现非递归版本的 keys() 方法。
 * </p>
 */
public class E36_LoopIterator {

    @Test
    public void testLoopKeys() throws Exception {
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

        for (Integer k : bt.loopKeys()) {
            System.out.printf(k + " ");
        }
        System.out.println();
    }
}
