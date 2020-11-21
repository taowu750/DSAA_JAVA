package algs1_fundamentals.sec4_analysis_of_algorithms;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * N层的大楼，将鸡蛋从F层或以上往下扔会摔碎，找出这个F，要求鸡蛋破碎最坏次数为2lgF。
 */
public class RE24_ThrowEgg {

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
}
