package algs1_fundamentals.sec4_analysis_of_algorithms;

import org.junit.jupiter.api.Test;
import util.algs.StdRandom;
import util.base.ExMath;
import util.io.FormatPrint;

import static org.junit.jupiter.api.Assertions.assertEquals;


/**
 * <p>
 * 热还是冷。你的目标是猜出 1 到 N 之间（包含 1 和 N）的一个秘密的整数。每次猜完
 * 一个整数后，你会知道你的猜测和这个秘密的整数是否相等（如果是游戏结束）。如果
 * 不相等，你会知道你的猜测比上一次猜测距离该秘密整数是比较热（接近）还是比较冷（远离）。
 * </p>
 * <p>
 * 设计一个算法在 2lgN 之内找到这个整数，然后在设计一个算法在 lgN 之内找到
 * 这个整数。
 * </p>
 */
public class E34_HotOrCold {

    private static int magic = 0;
    private static int N = 200000;
    private static int last = 0;
    private static int cnt = 0;


    public enum State {
        COMPLETE, HOT, COLD, SAME, STATELESS
    }


    public static int findIn2lgN() {
        int low = 0, high = 1;

        /*
        需要注意 hotOrCold 具有副作用，每次都会把 last 更新为参数，
        所以不能直接在循环判断中使用两次
         */
        State state = hotOrCold(high);
        while (state == State.STATELESS || state == State.HOT) {
            cnt++;
            low = high;
            high *= 2;
            if (high >= N) {
                high = N;
                state = hotOrCold(high);
                break;
            }
            state = hotOrCold(high);
        }
        // 之所以这样做是为了避免 magic = 7 这种情况
        if (state == State.COLD) {
            low /= 2;
            hotOrCold(low);
            state = hotOrCold(high);
        }

        while (low < high) {
            cnt++;
            switch (state) {
                case COMPLETE:
                    return high;

                case SAME:
                    // 不能将 low 替换为 last
                    return (high + low) / 2;

                case HOT:
                    low = (high + low) / 2;
                    break;

                case COLD:
                    high = (high + low) / 2;
                    break;
            }
            /*
            需要注意，我们的 high 始终要和 low 的状态进行比较。
            这条语句即检查了 low，又将 low 更新为 last
             */
            if (hotOrCold(low) == State.COMPLETE) {
                return low;
            }
            state = hotOrCold(high);
        }

        // 这里的 return 是为了防止编译出错，实际上在循环中就会解决问题
        return high;
    }

    public static State hotOrCold(int number) {
        if (number == magic) {
            // 不能在这里让 last 等于 0，总之函数造成的副作用越小越好。
            return State.COMPLETE;
        } else {
            if (last == 0) {
                last = number;
                return State.STATELESS;
            } else {
                int diff = Math.abs(number - magic) - Math.abs(last - magic);
                last = number;
                if (diff < 0) {
                    return State.HOT;
                } else if (diff > 0) {
                    return State.COLD;
                } else {
                    return State.SAME;
                }
            }
        }
    }

    @Test
    public void testFind_random() throws Exception {
        FormatPrint.group(() -> {
            magic = StdRandom.uniform(1, N + 1);
            System.out.println("magic = " + magic);

            assertEquals(findIn2lgN(), magic);
            System.out.printf("在[1, %d]中找到魔法数字 %d 共迭代了 %d 次\n",
                    N, magic, cnt);

            last = 0;
            magic = 0;
            cnt = 0;
        }, "findIn2lgN");
    }

    @Test
    public void testFind_all() throws Exception {
        double sumCnt = 0;
        for (int i = 0; i < N; i++) {
            magic = i + 1;
            assertEquals(findIn2lgN(), magic);
//            System.out.printf("在[1, %d]中找到魔法数字 %d 共迭代了 %d 次\n",
//                    N, magic, cnt);
            sumCnt += cnt;

            last = 0;
            magic = 0;
            cnt = 0;
        }
        System.out.printf("\n\n魔法数字范围为[1, %d]，迭代平均次数为：%.3f，lg%d = %.3f\n",
                N, sumCnt / N, N, ExMath.ln(N));
    }

    @Test
    public void testCost() {
        N = 10000;
        float actualCost = 0.f;
        for (int i = 1; i <= N; i++) {
            magic = i;
            int guess = findIn2lgN();
            assertEquals(guess, magic);
            actualCost += cnt;

            last = 0;
            magic = 0;
            cnt = 0;
        }
        System.out.printf("N=%d, 均摊成本是%.2f次, 期望成本是%.2f次\n", N, actualCost / N, ExMath.ln(N));
    }
}
