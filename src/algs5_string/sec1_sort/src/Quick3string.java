package algs5_string.sec1_sort.src;


/**
 * 三向字符串快速排序。
 * 每次根据首字符v将字符串分成&lt;v、=v、&gt;v三组，然后递归地对每一组继续排序，
 * 其中等于的那一组忽略掉首字符。
 * 这种排序方法能够很好的应对字符串中存在较长的公共部分的情况，而且不需要额外的空间。
 */
public class Quick3string {

    public static void sort(String[] a) {
        sort(a, 0, a.length - 1, 0);
    }


    private static void sort(String[] a, int lo, int hi, int d) {
        if (hi <= lo)
            return;

        int lt = lo, gt = hi;
        int v = MSD.charAt(a[lo], d);
        int i = lo + 1;
        while (i <= gt) {
            int t = MSD.charAt(a[i], d);
            if (t < v)
                exch(a, lt++, i++);
            else if (t > v)
                exch(a, i, gt--);
            else
                i++;
        }
        sort(a, lo, lt - 1, d);
        if (v >= 0)
            sort(a, lt, gt, d + 1);
        sort(a, gt + 1, hi, d);
    }


    private static void exch(String[] a, int i, int j) {
        String tmp = a[i];
        a[i] = a[j];
        a[j] = tmp;
    }
}
