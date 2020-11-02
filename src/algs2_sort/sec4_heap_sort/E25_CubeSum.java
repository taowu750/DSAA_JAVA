package algs2_sort.sec4_heap_sort;

import org.junit.jupiter.api.Test;
import util.datastructure.MyPriorityQueue;

/**
 * <p>
 * 计算数论。在不使用额外空间的条件下，按大小顺序打印所有 a**3 + b**3 的
 * 结果，其中 a 和 b 为 0 至 N 之间的整数。用这段程序找出 0 到 10**6 之间
 * 所有满足 a**3 + b**3 = c**3 + d**3 的不同整数 a,b,c,d
 * </p>
 */
public class E25_CubeSum {

    public void findCubeSum() {
        int N = (int) Math.pow(10, 3);
        MyPriorityQueue<Record> pq = new MyPriorityQueue<>(N * N - N);

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (i != j) {
                    pq.offer(new Record(i, j, (long) Math.pow(i, 3) +
                            (long) Math.pow(j, 3)));
                }
            }
        }

        int NN = pq.size();
        boolean same = false;
        Record lastRecord = pq.poll();
        Record r;
        for (int i = 1; i < NN; i++) {
            r = pq.poll();
            if (r.satisfy(lastRecord)) {
                same = true;
                System.out.print(lastRecord + " ");
                if (i == NN - 1) {
                    System.out.println(r);
                }
            } else {
                if (same) {
                    System.out.println(lastRecord);
                }
                same = false;
            }
            lastRecord = r;
        }
    }

    @Test
    public void testFindCubeSum() throws Exception {
        findCubeSum();
    }
}

class Record implements Comparable<Record> {
    int a, b;
    long cubeSum;


    public Record(int a, int b, long cubeSum) {
        this.a = a;
        this.b = b;
        this.cubeSum = cubeSum;
    }

    @Override
    public int compareTo(Record o) {
        return cubeSum > o.cubeSum ? 1 : cubeSum == o.cubeSum ? 0 : -1;
    }

    public boolean satisfy(Record r) {
        return cubeSum == r.cubeSum &&
                a != r.a && a != r.b && b != r.a && b != r.b;
    }

    @Override
    public String toString() {
        return "(" + a + "," + b + "," + cubeSum + ")";
    }
}