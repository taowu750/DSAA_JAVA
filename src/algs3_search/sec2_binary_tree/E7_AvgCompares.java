package algs3_search.sec2_binary_tree;

import org.junit.jupiter.api.Test;
import util.datastructure.BinaryTreeST;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * 计算给定数中的一次随机查找平均所需的比较次数（即树的内部路径长除以树的大小再加 1）。
 */
public class E7_AvgCompares {

    @Test
    public void avgCompares() throws Exception {
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

        /*
               1
           /      \
          -3      6
            \    / \
            -2  4   7
                     \
                     11
                    /
                   9
         */
        assertEquals(bt.innerPathLength(), 7);

        System.out.printf("随机查找命中的比较次数: %.2f\n", bt.innerPathLength() / (double) bt.size() + 1);
    }
}
