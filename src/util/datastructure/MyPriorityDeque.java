package util.datastructure;

import util.base.ExMath;

import java.util.Arrays;

/**
 * 支持双向操作的优先队列。队头最大值，队尾最小值。
 */
@SuppressWarnings({"unchecked", "Duplicates"})
public class MyPriorityDeque<T extends Comparable<T>> {

    private int size;
    private int maxSize;
    private T[] maxPq;
    private T[] minPq;

    private Less maxPqLess = (i, j) -> i.compareTo(j) < 0;
    private Less minPqLess = (i, j) -> j.compareTo(i) < 0;


    public MyPriorityDeque(int initSize, int maxSize) {
        if (initSize > maxSize ||
                (initSize <= 0 || initSize > Integer.MAX_VALUE - 1) ||
                (maxSize <= 0 || maxSize > Integer.MAX_VALUE - 1)) {
            throw new IllegalArgumentException("大小参数不正确");
        }

        this.maxSize = maxSize;
        size = 0;
        maxPq = (T[]) new Comparable[initSize + 1];
        minPq = (T[]) new Comparable[initSize + 1];
    }

    public MyPriorityDeque(int initSize) {
        this(initSize, Integer.MAX_VALUE - 1);
    }

    public MyPriorityDeque() {
        this(16);
    }


    public boolean offer(T t) {
        if (!ensureCapacity())
            return false;

        maxPq[size + 1] = t;
        swim(size + 1, maxPq, maxPqLess);
        minPq[size + 1] = t;
        swim(size + 1, minPq, minPqLess);
        size++;

        return true;
    }

    public T peekFirst() {
        return peek(maxPq);
    }

    public T peekLast() {
        return peek(minPq);
    }

    public T pollFirst() {
        return poll(maxPq, maxPqLess);
    }

    public T pollLast() {
        return poll(minPq, minPqLess);
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


    private T peek(T[] pq) {
        return size > 0 ? pq[1] : null;
    }

    private T poll(T[] pq, Less less) {
        if (size < 1)
            return null;

        T temp = pq[1];
        exch(pq, 1, size);
        pq[size] = null;
        sink(1, pq, less, size - 1);
        if (pq == maxPq)
            deleteTail(minPq, minPqLess);
        else
            deleteTail(maxPq, maxPqLess);
        size--;

        return temp;
    }

    /**
     * 也可以在插入时复制成两个孪生结点，分别插入到最大堆和最小堆中。
     * 孪生结点中存有元素的值，自身在数组的下标以及指向另一个孪生结点的指针。
     * 删除结点时需要去另一个堆删除对应的孪生结点。方法是令待删除结点与最后一个结点交换，
     * 然后删除最后一个结点，随后对交换后的结点做一次 Swim 和 Sink，维持堆的状态。
     */
    private void deleteTail(T[] pq, Less less) {
        if (size < 1)
            return;
        if (size == 1) {
            pq[1] = null;
            return;
        }
        if (size == 2) {
            pq[2] = null;
            return;
        }

        // 利用完全树的数学性质，但是这样最坏时间为 N/2
        int h = (int) ExMath.ln(size) + 1; // 堆高
        int t = (int) Math.pow(2, h - 1) - 1; // 堆中完全树节点总数
        int r = Math.round((size - t) / 2.f); // 完全树最底层有子节点的节点数
        int n = Math.round((float) Math.pow(2, h - 2)); // 完全树最底层节点数
        int i = t - n + r + 1; // 开始搜寻最小/大值的位置

        int min = i++;
        for(; i <= size; i++) {
            if (less.less(pq[i], pq[min])) {
                min = i;
            }
        }
        if (min == size) {
            pq[min] = null;
        } else {
            System.arraycopy(pq, min + 1, pq, min, size - min);
            pq[size] = null;
        }
    }

    /**
     * 将 k 处的元素上浮。
     *
     * @param k
     */
    private void swim(int k, T[] pq, Less less) {
        T tmp = pq[k];
        while (k > 1 && less.less(pq[k / 2], tmp)) {
            pq[k] = pq[k / 2];
            k /= 2;
        }
        pq[k] = tmp;
    }

    /**
     * 将 k 处的元素下沉。
     *
     * @param k
     */
    private void sink(int k, T[] pq, Less less, int size) {
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

    private boolean ensureCapacity() {
        // size 是元素数量，maxSize 是最大元素数量
        if (size == maxSize)
            return false;

        if (size == maxPq.length - 1) {
            int newCapacity = size;
            if (newCapacity >= maxSize / 2) {
                newCapacity = maxSize;
            } else {
                newCapacity *= 2;
            }
            maxPq = Arrays.copyOf(maxPq, newCapacity + 1);
            minPq = Arrays.copyOf(minPq, newCapacity + 1);
        }

        return true;
    }

    private void exch(T[] pq, int i, int j) {
        T temp = pq[i];
        pq[i] = pq[j];
        pq[j] = temp;
    }
}
