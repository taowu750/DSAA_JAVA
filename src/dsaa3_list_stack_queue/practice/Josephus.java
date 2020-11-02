package dsaa3_list_stack_queue.practice;

import java.util.Arrays;

/**
 * 热土豆传递问题 -- 练习3.6
 *
 * @author wutao
 */
public class Josephus {

    /**
     * 找出Josephus游戏中的获胜者
     *
     * @param nums  人数
     * @param steps 步数
     * @return 获胜者编号
     */
    public static int winner(int nums, int steps) {
        boolean[] peoples = new boolean[nums];
        Arrays.fill(peoples, true);

        int transmitter = 0, remainder = nums;
        while (remainder > 1) {
            int clear = transmitter;
            for (int count = 0; count < steps;) {
                clear = (clear + 1) % nums;
                if (peoples[clear]) {
                    count++;
                }
            }
            peoples[clear] = false;

            transmitter = (clear + 1) % nums;
            while (!peoples[transmitter]) {
                transmitter = (transmitter + 1) % nums;
            }

            remainder--;
        }

        return transmitter + 1;
    }
}
