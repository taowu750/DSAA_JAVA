package algs5_string.sec1_sort.src;


/**
 * 高位优先的字符串排序。
 * 首先使用键索引计数法将所有字符串按照首字母排序，然后递归地再将每个首字母对应的
 * 子数组排序。
 */
public class MSD extends AbstractStringSort {

    private String[] aux;


    public void sort(String[] a) {
        int N = a.length;
        aux = new String[a.length];
        sort(a, 0, N - 1, 0);
    }


    /**
     * lo 和 hi 是字符串的索引。
     * d 是字符在字符串中的索引，从0开始。
     */
    private void sort(String[] a, int lo, int hi, int d) {
        // 以第 d 个字符为键将 a[lo] 至 a[hi] 排序
        if (hi <= lo + M) {
            insertSort(a, lo, hi, d);
        } else {
            // count 组排列：空、-1(表示超过字符串末尾)组、0(表示代码点)组、1组、2组、...
            int[] count = new int[R + 2];

            // 计算频率，完成后count排列：未使用(r=0)、长度为d字符串数量(r=1)、第d个字符基数为r-2的字符串数量(r>=2)
            for (int i = lo; i <= hi; i++)
                count[charAt(a[i], d) + 2]++;
            // 将频率转换为索引，完成后count排列：长度为d字符串开始索引(r=0)、第d个字符基数为r-1的字符串开始索引(r>=1)、未使用(r=R+1)
            for (int r = 0; r < R + 1; r++)
                count[r + 1] += count[r];
            // 数据分类，完成后count排列：长度为d的字符串结束索引(r=0)，第d个字符基数为r-1的字符串结束索引(r>=1)、未使用(r=R+1)
            for (int i = lo; i <= hi; i++)
                aux[count[charAt(a[i], d) + 1]++] = a[i];
            // 回写
            for (int i = lo; i <= hi; i++)
                a[i] = aux[i - lo];
            // 递归的以每个字符为键进行排序
            for (int r = 0; r < R; r++)
                sort(a, lo + count[r], lo + count[r + 1] - 1, d + 1);
        }
    }
}
