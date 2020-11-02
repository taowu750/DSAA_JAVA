package algs1_fundamentals.sec1_base_programming_model;


import util.algs.StdOut;

import java.util.Arrays;
import java.util.Random;

/**
 * 1.1.15<br/>
 * 编写一个方法histogram(), 接受一个整型数组a[]和一个整数M作为参数并返回一个大小为M的数组，<br/>
 * 其中第i个元素的值为整数i在参数数组中出现的次数。如果a[]中的值均在0到M-1之间，返回数组中的和应该和a.length相同。
 */
public class E15_IntArrayCheck {

    public static int[] histogram(int[] a, int m) {
        int[] intCount = new int[m];
        for (int i : a) {
            if (i < intCount.length && i >= 0) {
                intCount[i] += 1;
            }
        }

        return intCount;
    }


    public static void main(String[] args) {
        Random random = new Random();
        int[] a1 = new int[10];
        for (int i = 0; i < a1.length; i++) {
            a1[i] = random.nextInt(a1.length);
        }
        int[] result = histogram(a1, a1.length);
        System.out.print("数组a1：");
        StdOut.println(Arrays.toString(a1));
        StdOut.print("结果：");
        StdOut.println(Arrays.toString(result));
    }
}
