package algs3_search.sec2_binary_tree;

import org.junit.jupiter.api.Test;
import util.datastructure.BinaryTreeST;

/**
 * 为二叉树实现非递归的 put() 方法。
 */
public class E13_LoopPut {

    @Test
    public void testLoopPut() throws Exception {
        BinaryTreeST<Integer, String> bt = new BinaryTreeST<>();

        bt.loopPut(1, "1");
        bt.loopPut(6, "6");
        bt.loopPut(-3, "-3");
        bt.loopPut(7, "7");
        bt.loopPut(11, "11");
        bt.loopPut(1, "1-1");
        bt.loopPut(9, "9");
        bt.loopPut(4, "4");
        bt.loopPut(-2, "-2");

        bt.inOrder();
    }
}
