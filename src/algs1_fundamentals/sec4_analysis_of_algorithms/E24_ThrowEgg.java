package algs1_fundamentals.sec4_analysis_of_algorithms;

import org.junit.jupiter.api.Test;
import util.io.FormatPrint;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * <p>
 * 扔鸡蛋。假设有一栋 N 层的大楼和许多鸡蛋，假设将鸡蛋从F及更高层扔下才会碎，
 * 设计一种策略来确定 F 的值。
 * </p>
 * <p>
 * 其中扔 lgN 次鸡蛋后摔碎的鸡蛋数量为 lgN 次，然后想办法将成本降低到 2lgF。
 * </p>
 */
@SuppressWarnings("Duplicates")
public class E24_ThrowEgg {

    private static int brokenEggNum = 0;
    private static int throwEggNum = 0;


    public static int findF(boolean[] floor) {
        int low = 0, high = floor.length - 1;

        while (low < high) {
            int mid = (low + high) / 2;
            throwEggNum += 2;
            if (floor[mid]) {
                brokenEggNum++;
                if (floor[mid - 1]) {
                    brokenEggNum++;
                    high = mid - 1;
                } else {
                    return mid;
                }
            } else {
                if (floor[mid + 1]) {
                    brokenEggNum++;
                    return mid + 1;
                } else {
                    low = mid + 1;
                }
            }
        }

        return -1;
    }

    public static int findF2(boolean[] floor) {
        int low = 0, high = 1;

        // 迭代缩小查找范围
        while (!floor[high]) {
            throwEggNum++;
            low = high;
            high *= 2;
            if (high > floor.length - 1) {
                high = floor.length - 1;
                break;
            }
        }
        brokenEggNum++;

        while (low <= high) {
            int mid = (low + high) / 2;
            throwEggNum++;
            if (floor[mid]) {
                brokenEggNum++;
                high = mid - 1;
            } else {
                low = mid + 1;
            }
        }

        return high;
    }

    @Test
    public void testFindF() throws Exception {
        int N = 30;
        check(E24_ThrowEgg::findF, N, "findF");
        check(E24_ThrowEgg::findF2, N, "findF2");
    }


    @FunctionalInterface
    private static interface ThrowEgg {

        int findF(boolean[] floor);
    }

    private static class EggNum {
        int brokenNum;
        int throwNum;


        public EggNum(int brokenNum, int throwNum) {
            this.brokenNum = brokenNum;
            this.throwNum = throwNum;
        }
    }

    private static void check(ThrowEgg throwEgg, int N, String title) {
        boolean[] floor = new boolean[N];
        final EggNum[] eggNums = new EggNum[N - 1];

        double brokenSum = 0, throwSum = 0;
        for (int i = 1; i < N; i++) {
            Arrays.fill(floor, 0, i, false);
            Arrays.fill(floor, i, N, true);
            assertEquals(findF(floor), i);
            brokenSum += brokenEggNum;
            throwSum += throwEggNum;
            eggNums[i - 1] = new EggNum(brokenEggNum, throwEggNum);
            brokenEggNum = 0;
            throwEggNum = 0;
        }
        double finalThrowSum = throwSum;
        double finalBrokenSum = brokenSum;
        FormatPrint.group(() -> {
            System.out.printf("以下是 F 取值 [1, %d] 的鸡蛋情况：\n", N - 1);
            for (int i = 0; i < eggNums.length; i++) {
                System.out.printf(" - 层 %d: 扔出鸡蛋数 %d, 破碎鸡蛋数 %d\n",
                        i + 1, eggNums[i].throwNum, eggNums[i].brokenNum);
            }

            System.out.printf("\n\n在 %d 层的大楼，平均扔出鸡蛋数：%.3f，平均摔碎鸡蛋数为 %.3f\n",
                    N, finalThrowSum / (N - 1), finalBrokenSum / (N - 1));
        }, title);
    }
}
