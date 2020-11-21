package algs1_fundamentals.sec4_analysis_of_algorithms;

import org.junit.jupiter.api.Test;
import util.base.ExMath;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * 在1-N之间猜一个数。离上次猜测近的话则是热，远为冷，相等为温。要求猜测最坏次数为 lgN
 */
public class RE34_HotOrCold {

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
}
