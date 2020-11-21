package algs2_sort.sec4_heap_sort;

import util.datastructure.MyPriorityQueue;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

// TODO: 接近
/**
 * 找出0-N之间，满足 a^3+b^3=c^3+d^3 的不同整数对 a,b,c,d。
 */
public class RE25_CubeSum {

    public List<List<Cube>> findEqualCubeSum(int N) {
        List<List<Cube>> matchedInts = new ArrayList<>();

        MyPriorityQueue<Cube> heap = new MyPriorityQueue<>(MyPriorityQueue.Order.MIN,
                N);
        IntStream.range(1, N + 1).mapToObj(i -> new Cube(0, i)).forEach(heap::offer);

        // 这里没有对超过两个cube相等的情况进行处理，因为我太懒了
        Cube last = null;
        while (!heap.isEmpty()) {
            Cube cube = heap.poll();
            if (last != null) {
                if (cube.i < N && cube.j - cube.i != 1)
                    heap.offer(new Cube(cube.i + 1, cube.j));
                if (last.compareTo(cube) == 0)
                    matchedInts.add(Stream.of(last, cube).collect(Collectors.toList()));
            }
            last = cube;
        }

        return matchedInts;
    }
}

class Cube implements Comparable<Cube> {
    int i, j;

    public Cube(int i, int j) {
        // 保证 i 小于 j
        this.i = Math.min(i, j);
        this.j = Math.max(i, j);
    }

    @Override
    public int compareTo(Cube o) {
        return (int) ((Math.pow(j, 3) - Math.pow(o.j, 3)) +
                (Math.pow(i, 3) - Math.pow(o.i, 3)));
    }

    @Override
    public String toString() {
        return "(i=" + i + ", j=" + j +")";
    }
}
