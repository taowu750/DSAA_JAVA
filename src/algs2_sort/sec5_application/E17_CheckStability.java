package algs2_sort.sec5_application;

import algs2_sort.sec1_primary_sort.src.InsertionSort;
import algs2_sort.src.AbstractSort;

import java.util.Arrays;

/**
 * <p>
 * 检测稳定性。对指定数组调用 sort()，如果排序是稳定的返回 true，否则返回 false。
 * 不要假设排序只会使用 exch() 移动数据。
 * </p>
 */
public class E17_CheckStability {

    /*
    TODO: 另一种较好的做法是使用一个包装器数组，每个包装器含有原来数组对应元素的值和下标，
    TODO: 对包装器数组进行排序，然后检查相同元素的下标是否按顺序排列
     */
    public static boolean check(Comparable[] a, AbstractSort sort) {
        // 顺序扫描排序后的数组，遇到相同的元素就查看它们在原来数组中的位置
        // 如果位置变了说明不是稳定的，反之则是稳定的

        // 对数组使用稳定的排序方法进行排序，然后和给定的排序方法进行比较

        Comparable[] copy = Arrays.copyOf(a, a.length);
        new InsertionSort().sort(copy);
        sort.sort(a);

        for (int i = 0; i < a.length; i++) {
            if (copy[i] != a[i])
                return false;
        }

        return true;
    }
}
