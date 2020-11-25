package algs5_string.sec1_sort.src;

import java.util.Arrays;

public abstract class AbstractStringSort {

    // 基数
    protected static int R = 256;
    // 小数组的切换阈值
    protected static final int M = 15;

    public abstract void sort(String[] a);

    public static void check(AbstractStringSort stringSort) {
        String[] a = {"Rene", "Argento", "Arg", "Alg", "Algorithms", "LSD", "MSD", "3WayStringQuickSort",
                "Dijkstra", "Floyd", "Warshall", "Johnson", "Sedgewick", "Wayne", "Bellman", "Ford", "BFS", "DFS"};
        stringSort.sort(a);
        for (int i = 1; i < a.length; i++) {
            if (a[i].compareTo(a[i - 1]) < 0) {
                throw new IllegalStateException(Arrays.deepToString(a));
            }
        }
    }

    /**
     * 当指定位置超过字符串末尾时返回-1
     */
    protected static int charAt(String s, int d) {
        if (d < s.length())
            return s.charAt(d);
        else
            return -1;
    }
    /*
    对字符串的[index,)部分进行排序
     */

    protected static void insertSort(String[] a, int lo, int hi, int index) {
        for (int i = lo + 1; i <= hi; i++) {
            String tmp = a[i];
            int j = i - 1;
            for (; j >= lo && a[j].substring(index).compareTo(tmp.substring(index)) > 0; j--)
                a[j + 1] = a[j];
            if (j != i - 1) {
                a[j] = tmp;
            }
        }
    }

    protected static void exch(String[] a, int i, int j) {
        String tmp = a[i];
        a[i] = a[j];
        a[j] = tmp;
    }
}
