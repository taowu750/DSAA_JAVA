package algs1_fundamentals.sec4_analysis_of_algorithms;


import util.algs.StdOut;

/**
 * 1.4.20<br/>
 * 如果一个数组中的元素是先递增后递减的，则称这个数组是双调的。编写一个程序，给定一个含有N个不同 int 值的<br/>
 * 双调数组，判断它是否含有给定的整数。程序在最坏情况下所需的比较次数为 ~3lgN。
 */
@SuppressWarnings("Duplicates")
public class E20_BitonicArray {

    /**
     * 在双调数组a中查找整数find，时间复杂度为 ~3lgN。
     *
     * @param a 双调数组
     * @param find 需要查找的整数
     * @return 如果存在，返回true，否则返回false
     */
    public static boolean isExists(int[] a, int find) {
//        首先找到双调数组的最大点
        int maxIndex = maxIndex(a);
//        在递增和递减的部分以二分查找算法分别查找整数
        if (binarySearchIncreasing(a, 0, maxIndex, find) != -1) {
            return true;
        } else if (binarySearchDecreasing(a,maxIndex + 1, a.length - 1, find) != -1) {
            return true;
        } else {
            return false;
        }
    }

    public static int binarySearchIncreasing(int[] a, int start, int end, int find) {
        int min = start;
        int max = end;
        int mid;

        while (min <= max) {
            mid = min + (max - min) / 2;
            if (find < a[mid]) {
                max = mid - 1;
            } else if (find > a[mid]) {
                min = mid + 1;
            } else {
                return mid;
            }
        }

        return -1;
    }

    public static int binarySearchDecreasing(int[] a, int start, int end, int find) {
        int min = start;
        int max = end;
        int mid;

        while (min <= max) {
            mid = min + (max - min) / 2;
            if (find < a[mid]) {
                min = mid + 1;
            } else if (find > a[mid]) {
                max = mid - 1;
            } else {
                return mid;
            }
        }

        return -1;
    }

    public static int maxIndex(int a[]) {
        return maxIndex(a, 0, a.length - 1);
    }

    public static int maxIndex(int a[], int left, int right) {
        int mid = (left + right) / 2;

        if (mid > 0 && a[mid] < a[mid - 1]) {
            return maxIndex(a, left, mid - 1);
        } else if (mid < a.length - 1 && a[mid] < a[mid + 1]) {
            return maxIndex(a, mid + 1, right);
        } else {
            return mid;
        }
    }


    public static void main(String[] args) {
        int[] a = {0, 3, 5, 6, 7, 8, 11, 23, 21, 20, 16, 14, 11, 11, 8, 3, 1};

        int i1 = 7;
        int i2 = 2;
        StdOut.println("findIn2lgN " + i1 + ", result=" + isExists(a, i1));
        StdOut.println("findIn2lgN " + i2 + ", result=" + isExists(a, i2));
    }
}
