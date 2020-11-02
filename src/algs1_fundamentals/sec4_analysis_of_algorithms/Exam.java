package algs1_fundamentals.sec4_analysis_of_algorithms;

import org.junit.jupiter.api.Test;

import util.base.ExMath;
import util.io.AlgsDataIO;
import util.test.SpeedTester;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class Exam {

    /**
     * ### 10. 查找匹配的最小索引
     * @param arr
     * @param in
     * @return
     */
    public int binarySearch(int[] arr, int in) {
        int low = 0, high = arr.length - 1;
        while (low <= high) {
            int center = (low + high) / 2;
            if (arr[low] == in)
                return low;
            else if (arr[center] == in)
                high = center;
            else if (arr[center] < in)
                low = center + 1;
            else
                high = center - 1;
        }

        return -1;
    }

    @Test
    public void test_binarySearch() {
        int[] arr = {1,2,3,4,5,6,7};
        assertEquals(binarySearch(arr, 4), 3);

        arr = new int[]{1,2,3,4,5,6,7,8,9,10,11};
        assertEquals(binarySearch(arr, 6), 5);

        arr = new int[]{1,1,1,2,2,3,4,5,5,6,6,7,7,7,8,9,10,10};
        assertEquals(binarySearch(arr, 1), 0);
        assertEquals(binarySearch(arr, 2), 3);
        assertEquals(binarySearch(arr, 3), 5);
        assertEquals(binarySearch(arr, 5), 7);
        assertEquals(binarySearch(arr, 7), 11);
        assertEquals(binarySearch(arr, 10), 16);
    }

    // TODO: 不行
    /**
     * ### 15. 在O(n^2)的时间复杂度下，在已排序数组中找出和为0的三元组的数量
     * @param arr
     * @return
     */
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

    /**
     * ### 17. 找出差绝对值最大的两个数，要求时间复杂度O(N)
     * @param arr
     * @return
     */
    public static double[] findFarthestPair(double[] arr) {
        double[] pair = {Double.MAX_VALUE, Double.MIN_VALUE};

        for (double v : arr) {
            if (v < pair[0])
                pair[0] = v;
            if (v > pair[1])
                pair[1] = v;
        }

        return pair;
    }

    @Test
    public void test_findFarthestPair() {
        double[] a = {0.1, 1.3, 4.5, -1.0, 0.0, 8.9, -0.5};
        assertArrayEquals(findFarthestPair(a), new double[]{-1., 8.9}, 1e-9);
    }

    // TODO: 不行
    /**
     * ### 18. 找到一个比左右两个元素都小的元素下标，程序最坏比较次数为2lgN
     * @param arr
     * @return
     */
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

    // TODO: 不行
    /**
     * ### 20. 在一个先增后减的双调数组里面查找是否包含指定值，要求最坏比较次数为3lgN
     * @param arr
     * @return
     */
    public boolean sdSearch(int[] arr, int key, int low, int high) {
        if (low <= high) {
            int center = (low + high) / 2;
            if (arr[center] == key)
                return true;
            if (center == 0 || center == arr.length - 1)
                return false;
            boolean moreLeft = arr[center] > arr[center - 1];
            boolean moreRight = arr[center] > arr[center + 1];
            if (arr[center] < key) {
                if (moreLeft && moreRight)
                    return sdSearch(arr, key, low, center - 1) ||
                            sdSearch(arr, key, center + 1, high);
                if (moreRight)
                    return sdSearch(arr, key, low, center - 1);
                else
                    return sdSearch(arr, key, center + 1, high);
            } else {
                return sdSearch(arr, key, low, center - 1) ||
                        sdSearch(arr, key, center + 1, high);
            }
        }

        return false;
    }

    @Test
    public void test_sdSearch() {
        int[] a = {0, 3, 5, 6, 7, 8, 11, 23, 21, 20, 16, 14, 11, 8, 3, 1};

        assertTrue(sdSearch(a, 3, 0, a.length - 1));
        assertTrue(sdSearch(a, 0, 0, a.length - 1));
        assertTrue(sdSearch(a, 1, 0, a.length - 1));
        assertTrue(sdSearch(a, 11, 0, a.length - 1));
        assertTrue(sdSearch(a, 23, 0, a.length - 1));
        assertTrue(sdSearch(a, 21, 0, a.length - 1));
        assertTrue(sdSearch(a, 14, 0, a.length - 1));
    }

    /**
     * ### 24. N层的大楼，将鸡蛋从F层或以上往下扔会摔碎，找出这个F，要求鸡蛋破碎最坏次数为2lgF。
     * @param N
     * @param F
     * @return
     */
    public static int throwEgg(int N, int F) {
        int floor = 1;
        int safeFloor = 1;
        int brokenEggs = 0, throwEggs = 0;
        boolean isReturn = false;
        while (floor < F) {
            throwEggs += 1;
            safeFloor = floor;
            floor *= 2;
        }
        brokenEggs += 1;
        if (floor == safeFloor || floor == safeFloor + 1)
            isReturn = true;
        if (!isReturn) {
            int low = floor / 2, high = floor;
            while (low <= high) {
                floor = (low + high) / 2;
                throwEggs += 1;
                if (floor < F) {
                    low = floor + 1;
                    safeFloor = floor;
                } else {
                    brokenEggs += 1;
                    if (floor == safeFloor + 1)
                        break;
                    high = floor;
                }
            }
        }
        System.out.printf("%d层楼，危险楼层是%d, 总共扔了%d次，扔坏了%d个鸡蛋\n\n", N, F, throwEggs, brokenEggs);

        return floor;
    }

    @Test
    public void test_throwEgg() {
        int N = 30;

        for (int i = 1; i <= N; i++) {
            assertEquals(throwEgg(N, i), i);
        }
    }

    // TODO: 不行
    /**
     * ### 25. 和上一题相同，但这次你只有两个鸡蛋，要求扔鸡蛋次数为 O(Sqrt(F))
     * @param N
     * @param F
     * @return
     */
    public static int[] throwEggsOnly2(int N, int F) {
        int floor = 1;
        int safeFloor = 1;
        int throwEggs = 0;
        boolean isReturn = false;

        while (floor < F) {
            throwEggs += 1;
            safeFloor = floor;
            floor += safeFloor;
        }
        if (floor == safeFloor || floor == safeFloor + 1)
            isReturn = true;

        if (!isReturn) {
            int brokenFloor = floor;
            for (floor = safeFloor + 1; floor < brokenFloor; floor++) {
                throwEggs++;
                if (floor >= F)
                    break;
            }
        }

        return new int[]{floor, throwEggs};
    }

    @Test
    public void test_throwEggsOnly2() {
        int N = 10000;
        float actualCost = 0.f, expectCost = 0;
        for (int i = 1; i <= N; i++) {
            int[] result = throwEggsOnly2(N, i);
            assertEquals(result[0], i);
            actualCost += result[1];
            expectCost += ExMath.ln(i);
        }
        System.out.printf("N=%d, 均摊成本是%.2f次, 期望成本是%.2f次\n", N, actualCost / N, expectCost / N);
    }

    static int sF;
    static int lastGuess = -1;

    /**
     * 0表示温，正值表示热，负值表示冷，null表示第一次猜或者中了。
     * @param guess
     * @return
     */
    static Integer coldOrHot(int guess) {
        if (lastGuess == -1 || guess == sF) {
            lastGuess = guess;

            return null;
        } else {
            int lastDiff = Math.abs(lastGuess - sF);
            int diff = Math.abs(guess - sF);
            lastGuess = guess;

            return lastDiff - diff;
        }
    }

    /**
     * ###34. 在1-N之间猜一个数。离上次猜测近的话则是热，远为冷，相等为温。要求猜测最坏次数为 lgN
     * @param N
     * @param F
     * @return
     */
    public static int[] guessColdHot(int N, int F) {
        sF = F;
        int guess = (1 + N) / 2;
        coldOrHot(guess);

        int low = 1, high = N;
        int last = guess, cnt = 2;
        guess = (1 + guess) / 2;
        Integer r = coldOrHot(guess);
        while (r != null) {
            if (r > 0) {
                if (guess < last) {
                    high = (guess + last) / 2;
                } else {
                    low = (guess + last) / 2;
                }
            } else if (r < 0) {
                if (guess < last) {
                    low = (guess + last) / 2;
                } else {
                    high = (guess + last) / 2;
                }
            } else {
                if (guess != last) {
                    guess = (guess + last) / 2;
                } else {
                    for (guess = high; guess >= low; guess--) {
                        cnt++;
                        if (coldOrHot(guess) == null)
                            break;
                    }
                }
                break;
            }
            last = guess;
            guess = (low + high) / 2;
            r = coldOrHot(guess);
            cnt++;
//            System.out.println("guess=" + guess + ", last=" + last + ", low=" + low + ", high=" + high);
        }

        return new int[]{guess, cnt};
    }

    @Test
    public void test_guessColdHot() {
        int N = 10000;
        float actualCost = 0.f;
        for (int i = 1; i <= N; i++) {
            int[] result = guessColdHot(N, i);
            assertEquals(result[0], i);
            actualCost += result[1];
        }
        System.out.printf("N=%d, 均摊成本是%.2f次, 期望成本是%.2f次\n", N, actualCost / N, ExMath.ln(N));
    }

    @Test
    public void test() {
        System.out.println(Arrays.toString(guessColdHot(10000, 7)));
    }
}
