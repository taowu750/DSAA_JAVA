package algs3_search.sec3_red_black_tree;

import org.junit.jupiter.api.Test;
import util.datastructure.RedBlackTreeST;

/**
 * <p>
 * 编写非递归的红黑树插入方法。
 * </p>
 */
public class E26_LoopPut {

    @Test
    public void testLoopPut() throws Exception {
        RedBlackTreeST<Integer, String> rbt = new RedBlackTreeST<>();

        rbt.loopPut(1, "1");
        rbt.loopPut(6, "6");
        rbt.loopPut(-3, "-3");
        rbt.loopPut(7, "7");
        rbt.loopPut(11, "11");
        rbt.loopPut(1, "1-1");
        rbt.loopPut(9, "9");
        rbt.loopPut(4, "4");
        rbt.loopPut(-2, "-2");

        rbt.inOrder();
        System.out.println();
        rbt.levelTraversal();
    }
}
