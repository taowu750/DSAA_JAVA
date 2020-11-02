package algs2_sort.sec4_heap_sort;

import util.datastructure.MyPriorityQueue;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * 设计一种数据类型，支持在对数时间内插入元素、常数时间内找到中位数、对数时间内删除中位数。
 * 有奇数个数，则取最中间的数作为中位数；偶数个数，取中间偏左的数作为中位数
 * @param <T>
 */
public class E30_MedianHeap<T extends Comparable<T>> {
    /*
    让最小堆存大半边的数，最大堆存小半边的数。不能思维定势
     */

    private int size;
    private MyPriorityQueue<T> maxHeap = new MyPriorityQueue<>(MyPriorityQueue.Order.MAX);
    private MyPriorityQueue<T> minHeap = new MyPriorityQueue<>(MyPriorityQueue.Order.MIN);

    public void offer(T t) {
        if (size == 0) {
            minHeap.offer(t);
        } else {
            if (minHeap.size() > maxHeap.size()) {
                if (t.compareTo(minHeap.peek()) > 0) {
                    maxHeap.offer(minHeap.poll());
                    minHeap.offer(t);
                } else {
                    maxHeap.offer(t);
                }
            } else {
                if (t.compareTo(maxHeap.peek()) < 0) {
                    minHeap.offer(maxHeap.poll());
                    maxHeap.offer(t);
                } else {
                    minHeap.offer(t);
                }
            }
        }
        size++;
    }

    public T poll() {
        if (size == 0)
            return null;
        else {
            size--;
            if (minHeap.size() > maxHeap.size()) {
                return minHeap.poll();
            } else {
                return maxHeap.poll();
            }
        }
    }

    public T median() {
        if (minHeap.size() > maxHeap.size()) {
            return minHeap.peek();
        } else {
            return maxHeap.peek();
        }
    }

    public static void main(String[] args) {
        E30_MedianHeap<Integer> medianHeap = new E30_MedianHeap<>();
        List<Integer> list = new Random().ints(20, 0, 100)
                .boxed()
                .peek(medianHeap::offer)
                .sorted()
                .peek(i -> System.out.print(i + " "))
                .collect(Collectors.toList());
        System.out.println();

        int len = list.size();
        for (int i = 0; i < len; i++) {
            assertEquals(medianHeap.poll(), list.remove(Math.round(list.size() / 2.f) - 1));
        }
    }
}
