package util.datastructure;

import util.base.ExMath;

import java.util.Arrays;

/**
 * 优先队列，实现为二叉堆。默认元素越大，优先级越大，即 {@link Order#MAX}。也可以
 * 反过来，即 {@link Order#MIN}。
 */
@SuppressWarnings({"unchecked", "Duplicates"})
public class MyPriorityQueue<T extends Comparable<T>> {

    public enum Order {
        MAX, MIN
    }


    private int size;
    private int maxSize;
    private T[] pq;

    private Order order;
    private Less less = (i, j) -> i.compareTo(j) < 0;

    private T tail;


    public MyPriorityQueue(Order order, int initSize, int maxSize) {
        if (initSize > maxSize ||
                (initSize <= 0 || initSize > Integer.MAX_VALUE - 1) ||
                (maxSize <= 0 || maxSize > Integer.MAX_VALUE - 1)) {
            throw new IllegalArgumentException("大小参数不正确");
        }

        if (order == Order.MIN) {
            less = (i, j) -> j.compareTo(i) < 0;
        }
        this.order = order;
        // 数组大小为 initSize + 1，最大大小为 maxSize + 1
        this.maxSize = maxSize;
        size = 0;
        pq = (T[]) new Comparable[initSize + 1];
    }

    public MyPriorityQueue(Order order, int initSize) {
        this(order, initSize, Integer.MAX_VALUE - 1);
    }

    public MyPriorityQueue(Order order) {
        this(order, 16);
    }

    public MyPriorityQueue(int initSize) {
        this(Order.MAX, initSize);
    }

    public MyPriorityQueue() {
        this(16);
    }

    public MyPriorityQueue(T[] a, Order order, int maxSize) {
        if (a.length < 1) {
            throw new IllegalArgumentException("数组大小不对");
        }
        if (maxSize < a.length || maxSize > Integer.MAX_VALUE - 1) {
            throw new IllegalArgumentException("大小参数不正确");
        }

        if (order == Order.MIN) {
            less = (i, j) -> j.compareTo(i) < 0;
        }
        this.order = order;
        size = 0;
        this.maxSize = maxSize;
        pq = (T[]) new Comparable[a.length + 1];
        for (T t : a) {
            offer(t);
        }
    }

    public MyPriorityQueue(T[] a, Order order) {
        this(a, order, Integer.MAX_VALUE - 1);
    }

    public MyPriorityQueue(T[] a, int maxSize) {
        this(a, Order.MAX, maxSize);
    }

    public MyPriorityQueue(T[] a) {
        this(a, Order.MAX);
    }


    public boolean offer(T t) {
        if (!ensureCapacity())
            return false;
        if (size == 0)
            tail = t;
        else if (less.less(t, tail)) {
            tail = t;
        }

        pq[++size] = t;
        swim(size);

        return true;
    }

    public T peek() {
        return size > 0 ? pq[1] : null;
    }

    public T poll() {
        if (size < 1)
            return null;

        T temp = pq[1];
        exch(1, size);
        pq[size--] = null;
        sink(1);

        return temp;
    }

    /**
     *
     * @return
     */
    public T tail() {
        return tail;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public int maxSize() {
        return maxSize;
    }

    public Order order() {
        return order;
    }

    public void toggleOrder() {
        if (size <= 1)
            return;

        if (order == Order.MAX) {
            order = Order.MIN;
            less = (i, j) -> j.compareTo(i) < 0;
        } else {
            order = Order.MAX;
            less = (i, j) -> i.compareTo(j) < 0;
        }

        tail = pq[1];
        T[] tmp = pq;
        int tmpSize = size;
        pq = (T[]) new Comparable[tmp.length];
        size = 0;
        // 倒过来减少比较和交换次数
        for (int i = tmpSize; i >= 1; i--) {
            pq[++size] = tmp[i];
            swim(size);
        }
    }


    /**
     * 将 k 处的元素上浮。
     *
     * @param k
     */
    private void swim(int k) {
        /*T tmp = pq[k];
        while (k > 1 && less.less(pq[k / 2], tmp)) {
            pq[k] = pq[k / 2];
            k /= 2;
        }
        pq[k] = tmp;*/

        // 二分查找优化比较次数
        T tmp = pq[k];
        int lo = 1, hi = k;
        while (lo < hi) {
            int loDeep = (int) ExMath.ln(lo), hiDeep = (int) ExMath.ln(hi);
            int divide = (int) Math.pow(2, Math.round((hiDeep - loDeep) / 2.));
            int mid = hi / divide;
            if (less.less(tmp, pq[mid])) {
                lo = hi / (divide / 2);
            } else if (less.less(pq[mid], tmp)) {
                // hi 不能等于 mid / 2，因为最终结果是从lo开始往后退的
                hi = mid;
            } else {
                lo = hi / (divide / 2);
                break;
            }
        }
        for (int i = k; i > lo; i /= 2) {
            pq[i] = pq[i / 2];
        }
        pq[lo] = tmp;
    }

    /**
     * 将 k 处的元素下沉。
     *
     * @param k
     */
    private void sink(int k) {
        T tmp = pq[k];
        while (2 * k <= size) {
            int j = 2 * k;
            if (j < size && less.less(pq[j], pq[j + 1]))
                j++;
            if (!less.less(tmp, pq[j]))
                break;
            pq[k] = pq[j];
            k = j;
        }
        pq[k] = tmp;
    }


    private void exch(int i, int j) {
        T temp = pq[i];
        pq[i] = pq[j];
        pq[j] = temp;
    }


    private boolean ensureCapacity() {
        // size 是元素数量，maxSize 是最大元素数量
        if (size == maxSize)
            return false;

        if (size == pq.length - 1) {
            int newCapacity = size;
            if (newCapacity >= maxSize / 2) {
                newCapacity = maxSize;
            } else {
                newCapacity *= 2;
            }
            pq = Arrays.copyOf(pq, newCapacity + 1);
        }

        return true;
    }
}
