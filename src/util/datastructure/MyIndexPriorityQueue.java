package util.datastructure;

import java.util.Arrays;
import java.util.Iterator;

/**
 * TODO: 添加最小/最大值缓存功能和 toggleOrder
 * <p>
 * 索引优先队列。索引优先队列允许引用优先队列中的任意元素。
 * </p>
 */
@SuppressWarnings({"unchecked", "Duplicates"})
public class MyIndexPriorityQueue<T extends Comparable<T>> implements Iterable<T> {



    public enum Order {
        MAX, MIN;
    }
    public class Entry {

        int index;
        T val;
        public Entry(int index, T val) {
            this.index = index;
            this.val = val;
        }


        public int index() {
            return index;
        }


        public T val() {
            return val;
        }

    }
    private static final Less MAX_LESS = (i, j) -> i.compareTo(j) < 0;


    private static final Less MIN_LESS = (i, j) -> j.compareTo(i) < 0;
    /*
    索引数组，pq[i] 存储的是与索引 i 相关的 keys 数组中的元素在 keys 数组中的下标。
    索引从 0 开始。这个索引数组就相当于一个 map。
     */
    private int[] pq;


    /*
    索引数组的逆序。如果 pq[j] = i，那么 qp[i] = j。
    如果 i 不在队列中，则总是令 qp[i] = 0
     */
    private int[] qp;
    private T[] keys;
    private int size;
    private int maxSize;
    private Order order;

    private Less less = MAX_LESS;
    public MyIndexPriorityQueue(Order order, int initSize, int maxSize) {
        if (initSize > maxSize ||
                (initSize <= 0 || initSize > Integer.MAX_VALUE - 1) ||
                (maxSize <= 0 || maxSize > Integer.MAX_VALUE - 1)) {
            throw new IllegalArgumentException("大小参数不正确");
        }

        if (order == Order.MIN) {
            less = MIN_LESS;
        }
        this.order = order;
        this.maxSize = maxSize;
        this.pq = new int[initSize + 1];
        this.qp = new int[initSize + 1];
        this.keys = (T[]) new Comparable[initSize + 1];
    }


    public MyIndexPriorityQueue(Order order, int initSize) {
        this(order, initSize, Integer.MAX_VALUE - 1);
    }

    public MyIndexPriorityQueue(int initSize) {
        this(Order.MAX, initSize);
    }

    public MyIndexPriorityQueue(Order order) {
        this(Order.MAX, 16);
    }

    public MyIndexPriorityQueue() {
        this(16);
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

    public int maxIndex() {
        return pq.length - 1;
    }

    public Order order() {
        return order;
    }

    public boolean contains(int k) {
        return pq[k] != 0;
    }

    /**
     * 插入具有索引 k 的元素。如果索引不正确、索引处已有元素或元素数
     * 达到最大值，插入失败并返回 false。
     *
     * @param k
     * @param item
     * @return
     */
    public boolean offer(int k, T item) {
        if (!checkIndex(k) || pq[k] != 0)
            return false;
        if (!ensureCapacity())
            return false;

        keys[++size] = item;
        swim(size, k);

        return true;
    }

    /**
     * 将索引 k 处的元素更改为 newItem。如果索引不正确或索引 k 处没有
     * 元素，设置失败并返回 null；否则返回原来的元素。
     *
     * @param k
     * @param newItem
     * @return
     */
    public T set(int k, T newItem) {
        if (!checkIndex(k) || pq[k] == 0)
            return null;

        T temp = keys[pq[k]];
        keys[pq[k]] = newItem;
        swim(pq[k], k);
        sink(pq[k], k);

        return temp;
    }

    public T peek(int k) {
        if (!checkIndex(k) || pq[k] == 0)
            return null;

        return keys[pq[k]];
    }

    public T poll(int k) {
        if (!checkIndex(k) || pq[k] == 0)
            return null;

        T temp = keys[pq[k]];
        keys[pq[k]] = keys[size];
        keys[size--] = null;

        sink(pq[k], k);
        pq[k] = 0;

        return temp;
    }

    public Entry peek() {
        return size > 0 ? new Entry(qp[1], keys[1]) : null;
    }

    public Entry poll() {
        if (size <= 0)
            return null;

        // 保存删除的值
        T temp = keys[1];
        // 末尾元素的索引
        int k = qp[size];
        // 删除值的索引
        int delIdx = qp[1];
        Entry r = new Entry(delIdx, temp);

        // 删除值并将末尾元素调到开头
        keys[1] = keys[size];
        keys[size--] = null;

        sink(1, k);
        pq[delIdx] = 0;

        return r;
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {

            private int index;


            @Override
            public boolean hasNext() {
                return index < size;
            }

            @Override
            public T next() {
                return keys[index++];
            }
        };
    }


    /**
     * 将 i 处的元素上浮。
     *
     * @param i
     */
    private void swim(int i, int k) {
        T tmp = keys[i];
        while (i > 1 && less.less(keys[i / 2], tmp)) {
            keys[i] = keys[i / 2];
            // 需要注意的是，在上浮/下沉的操作中，也会改变其他元素的位置。
            // pq[j]=i, qp[i]=j。下面的 j = qp[i / 2]
            pq[qp[i / 2]] = i;
            qp[i] = qp[i / 2];
            i /= 2;
        }
        keys[i] = tmp;

        pq[k] = i;
        qp[i] = k;
    }

    /**
     * 将 i 处的元素下沉。
     *
     * @param i
     */
    private void sink(int i, int k) {
        T tmp = keys[i];
        while (2 * i <= size) {
            int j = 2 * i;
            if (j < size && less.less(keys[j], keys[j + 1]))
                j++;
            if (!less.less(tmp, keys[j]))
                break;
            keys[i] = keys[j];
            pq[qp[j]] = i;
            qp[i] = qp[j];
            i = j;
        }
        keys[i] = tmp;

        pq[k] = i;
        qp[i] = k;
    }

    private boolean checkIndex(int k) {
        return k >= 0 && k < pq.length;
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
            qp = Arrays.copyOf(qp, newCapacity + 1);
            keys = Arrays.copyOf(keys, newCapacity + 1);
        }

        return true;
    }
}
