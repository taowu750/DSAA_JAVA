package algs5_string.sec1_sort.src;


/**
 * <p>
 * 三向字符串快速排序。
 * </p>
 * <p>
 * 每次根据首字符v将字符串分成<code>&lt;v、=v、&gt;v</code>三组，然后递归地对每一组继续排序，其中等于的那一组忽略掉首字符。
 * 这种排序方法能够很好的应对字符串中存在较长的公共部分的情况，而且不需要额外的空间。
 * </p>
 * <p>
 * 对于字符串类型的键，标准的快速排序以及其他排序方法都是高位优先类的排序算法，因为{@link String#compareTo(String)}方法
 * 是从左到右访问字符串中所有字符的。三向字符串排序的核心思想是对首字母相同的键采取特殊策略。
 * 你可以把三向字符串排序看作对标准快速排序的改进，使之能够记录一直相同的多个开头字母。
 * 标准的方法在每次比较时仍然需要扫描整个字符串，但三向字符串排序可以避免这一点。
 * </p>
 */
public class Quick3string extends AbstractStringSort {

    public void sort(String[] a) {
        sort(a, 0, a.length - 1, 0);
    }


    private void sort(String[] a, int lo, int hi, int d) {
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
}
