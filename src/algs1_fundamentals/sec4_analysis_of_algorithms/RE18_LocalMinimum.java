package algs1_fundamentals.sec4_analysis_of_algorithms;

// TODO: 不行
/**
 * 找到一个比左右两个元素都小的元素下标，程序最坏比较次数为2lgN
 */
public class RE18_LocalMinimum {

    public static int findLocalMinimum(int[] arr) {
        int center = arr.length / 2;
        int left = center - 1, right = center + 1;
        if (arr[center] < arr[left] && arr[center] < arr[right])
            return center;

        while (left > 0 || right < arr.length - 1) {
            if (left > 0) {
                boolean ll = arr[left] < arr[left-1];
                boolean lr = arr[left] < arr[left + 1];
                if (ll && lr)
                    return left;
                else {
                    if (ll)
                        left -= 2;
                    else
                        left -= 1;
                }
            }
            if (right < arr.length - 1) {
                boolean rl = arr[right] < arr[right - 1];
                boolean rr = arr[right] < arr[right + 1];
                if (rl && rr)
                    return right;
                else {
                    if (rr)
                        right += 2;
                    else
                        right += 1;
                }
            }
        }

        return -1;
    }
}
