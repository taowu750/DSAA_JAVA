package algs5_string.sec1_sort.src;

/**
 * 低位优先的字符串排序。如果字符串的长度都是 w，就从右至左以每个位置的
 * 字符为键，用键索引计数法将字符串排序 w 遍。
 * 这种算法是一种线性时间的排序算法。无论 N 有多大，它都只遍历 w 次数据
 */
public class LSD {

    /**
     * 通过前 W 个字符将 a[] 排序
     *
     * @param a
     * @param W
     */
    public static void sort(String[] a, int W) {
        int N = a.length;
        int R = 256; // 假设字符都是 ASCII 码
        String[] aux = new String[N];

        for (int d = W - 1; d >= 0; d--) {
            // 根据第 d 个字符用键索引计数法排序
            int[] count = new int[R + 1];
            for (int i = 0; i < N; i++)
                count[a[i].charAt(d) + 1]++;
            for (int r = 0; r < R; r++)
                count[r + 1] += count[r];
            for (int i = 0; i < N; i++)
                aux[count[a[i].charAt(d)]++] = a[i];
            for (int i = 0; i < N; i++)
                a[i] = aux[i];
        }
    }
}
