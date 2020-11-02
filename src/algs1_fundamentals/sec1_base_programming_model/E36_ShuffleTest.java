package algs1_fundamentals.sec1_base_programming_model;


import util.algs.StdIn;
import util.algs.StdOut;
import util.algs.StdRandom;

import java.util.Arrays;

/**
 * 1.1.36<br/>
 * 接受命令行参数M和N，将大小为M的数组打乱N次且在每次打乱之前都将数组重新初始化为a[i] = i。<br/>
 * 打印一个 MxM 的表格，对于所有的列 j, 行 i 表示的是 i 在打乱后落到 j 的位置的次数。数组中<br/>
 * 所有元素的值都应该接近于 N/M
 */
public class E36_ShuffleTest {

    public static void printShuffleResult(int m, int n) {
        int[] shuffled = new int[m];
        int[][] result = new int[m][m];

        for (int i = 0; i < n; i++) {
            initShuffledArray(shuffled);
            StdRandom.shuffle(shuffled);
            for (int j = 0; j < m; j++) {
                result[j][shuffled[j]] += 1;
            }
        }

        for (int i = 0; i < m; i++) {
            StdOut.println(Arrays.toString(result[i]));
        }
    }


    private static void initShuffledArray(int[] a) {
        for (int i = 0; i < a.length; i++) {
            a[i] = i;
        }
    }


    public static void main(String[] args) {
        int m = StdIn.readInt();
        int n = StdIn.readInt();

        printShuffleResult(m, n);
    }
}
