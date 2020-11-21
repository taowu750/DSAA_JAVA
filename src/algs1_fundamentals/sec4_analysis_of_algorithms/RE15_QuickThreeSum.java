package algs1_fundamentals.sec4_analysis_of_algorithms;

import org.junit.jupiter.api.Test;
import util.io.AlgsDataIO;
import util.test.SpeedTester;

import java.util.Arrays;

// TODO: 不行
/**
 * 在O(n^2)的时间复杂度下，在已排序数组中找出和为0的三元组的数量
 */
public class RE15_QuickThreeSum {

    public int quick3sum(int[] arr) {
        if (arr[0] > 0 || arr[arr.length - 1] < 0)
            return 0;

        int left0 = -1, right0 = -1, firstPositive = -1, lastNegative = 0;
        boolean isSearch0 = true;
        // 查找0的下标 => O(N)
        for (int i = 0; i < arr.length; i++) {
            if (isSearch0) {
                if (arr[i] == 0) {
                    left0 = i;
                    for (int j = i + 1; j < arr.length; j++) {
                        if (arr[j] != 0) {
                            right0 = j - 1;
                            isSearch0 = false;
                        }
                    }
                }
            }
            if (arr[i] > 0 && firstPositive == -1) {
                firstPositive = i;
            }
            if (arr[i] < 0) {
                lastNegative = i;
            }
        }
        if (left0 == 0 || right0 == arr.length - 1) {
            if (right0 - left0 < 2)
                return 0;
            else
                return 1;
        }

        int result = right0 - left0 > 2 ? 1 : 0;
        // 统计一个正数、一个负数和一个0的元组数量 => O(N*logN)
        if (!isSearch0) {
            int numPos = arr.length - right0 - 1;
            if (left0 >= numPos) {
                for (int i = 0; i < left0; i++) {
                    if (Arrays.binarySearch(arr, right0 + 1, arr.length, -arr[i]) != -1)
                        result++;
                }
            } else {
                for (int i = right0 + 1; i < arr.length; i++) {
                    if (Arrays.binarySearch(arr, 0, left0, -arr[i]) != -1)
                        result++;
                }
            }
        }
        // 统计两负一正的元组数量
        if (lastNegative >= 1) {
            for (int i = 0; i < lastNegative; i++) {
                for (int j = i + 1; j <= lastNegative; j++) {
                    if (arr[i] + arr[j] + arr[firstPositive] > 0)
                        break;
                    if (Arrays.binarySearch(arr, firstPositive, arr.length, -arr[i] - arr[j]) >= 0)
                        result++;
                }
            }
        }
        // 统计两正一负的元组数量
        if (firstPositive < arr.length - 1) {
            for (int i = firstPositive; i < arr.length - 1; i++) {
                for (int j = i + 1; j < arr.length; j++) {
                    if (arr[i] + arr[j] + arr[0] > 0)
                        break;
                    if (Arrays.binarySearch(arr, 0, lastNegative + 1, -arr[i] - arr[j]) >= 0)
                        result++;
                }
            }
        }

        return result;
    }

    @Test
    public void test_quick3sum() {
        int[] ints = AlgsDataIO.get2Kints();
        Arrays.sort(ints);
        SpeedTester.printRunTime("quick3sum: 2000", () ->
                System.out.println(quick3sum(ints)));
    }
}
