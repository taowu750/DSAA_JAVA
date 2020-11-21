package algs1_fundamentals.sec3_bag_queue_stack;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RE37_Josephus {

    /**
     * 循环报数
     * @param N N个人
     * @param M 数M次
     */
    public List<Integer> josephus(int N, int M) {
        boolean[] arr = new boolean[N];
        Arrays.fill(arr, true);
        List<Integer> killed = new ArrayList<>();

        int killNum = 0;
        int idx = 0;
        while (killNum != N) {
            int step = 0;
            while (true) {
                if (arr[idx]) {
                    step++;
                    if (step == M)
                        break;
                }
                idx++;
                if (idx == N)
                    idx = 0;
            }
            arr[idx] = false;
            killed.add(idx);
            killNum++;
        }

        return killed;
    }

    @Test
    public void test_josephus() {
        assertEquals(josephus(7, 2), Arrays.asList(1,3,5,0,4,2,6));
    }
}
