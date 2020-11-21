package algs1_fundamentals.sec4_analysis_of_algorithms;

import org.junit.jupiter.api.Test;
import util.base.ExMath;

import static org.junit.jupiter.api.Assertions.assertEquals;

// TODO: 不行
/**
 * 和上一题相同，但这次你只有两个鸡蛋，要求扔鸡蛋次数为 O(Sqrt(F))
 */
public class RE25_ThrowTwoEgg {

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
}
