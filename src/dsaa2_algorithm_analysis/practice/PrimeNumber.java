package dsaa2_algorithm_analysis.practice;

import java.util.ArrayList;
import java.util.List;

/**
 * 求解关于素数的问题 - 练习2.20、2.21
 *
 * @author wutao
 */
public class PrimeNumber {

    /**
     * 确定参数n是否是素数 - 练习2.20
     * 时间复杂度：O(sqrt(N))
     *
     * @param n 给定的一个整数
     * @return n为素数返回true，否则返回false
     */
    public static boolean isPrime(int n) {
        if (n % 2 == 0) {
            return false;
        }

        int sqrtN = (int) Math.sqrt(Math.abs(n));
        for (int i = 3; i <= sqrtN; i += 2) {
            if (n % i == 0) {
                return false;
            }
        }

        return true;
    }

    /**
     * 实现一：查找所有小于参数n的素数(n大于0) - 练习2.21
     * 时间复杂度：O(NlogNlogN)
     *
     * @param n 素数上限
     * @return 包含所有小于n的素数的ArrayList
     */
    public static List<Integer> findPrimes(int n) {
        List<Integer> primes = new ArrayList<>();

        int[] ints = new int[n + 1];
        for (int i = 2; i < ints.length; i++) {
            ints[i] = i;
        }

        for (int i = 2; i < n; i++) {
            if (ints[i] != 0) {
                primes.add(i);
                for (int j = 2; j * i < n; j++) {
                    ints[i * j] = 0;
                }
            }
        }


        return primes;
    }

    /**
     * 实现二：查找所有小于参数n的素数(n大于0) - 练习2.21
     * 时间复杂度：O(NlogNlogN)
     *
     * @param n 素数上限
     * @return 包含所有小于n的素数的ArrayList
     */
    public static List<Integer> findPrimes2(int n) {
        List<Integer> primes = new ArrayList<>();

        int[] ints = new int[n + 1];
        for (int i = 2; i < ints.length; i++) {
            ints[i] = i;
        }

        int smallPrime = 2;
        while (smallPrime < n) {
            primes.add(smallPrime);

            for (int i = smallPrime; i < n; i += smallPrime) {
                ints[i] = 0;
            }

            while (++smallPrime < n && ints[smallPrime] == 0) {
            }
        }

        return primes;
    }
}
