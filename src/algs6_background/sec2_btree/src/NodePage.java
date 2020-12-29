package algs6_background.sec2_btree.src;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Objects;

/**
 * {@link Page}的内存实现。表示内存中的结点。
 *
 * @param <K> 键
 */
public class NodePage<K> implements Page<K> {

    // 一个哨兵对象，表示小于任何键（除了它自己）的值
    private static final Comparable SENTINEL = new Comparable() {
        @Override
        public int compareTo(Object o) {
            if (o == this)
                return 0;
            return -1;
        }
    };

    private Object[] keys;
    private Page<K>[] subPages;
    private int maxNumNodes;
    private int size;
    private boolean external;
    private Comparator comparator;

    // 构造页并指定是否是外部页，并且是否用哨兵初始化
    public NodePage(int maxNumNodes, Comparator<K> comparator, boolean external, boolean initialWithSentinel) {
        if (maxNumNodes < 4)
            throw new IllegalArgumentException("maxNumNodes must be greater than or equal to 2");
        if (maxNumNodes % 2 != 0)
            throw new IllegalArgumentException("maxNumNodes must be an even");

        // 一开始不分配 maxNumNodes 大小，节省空间。
        keys = new Object[maxNumNodes / 2];
        this.maxNumNodes = maxNumNodes;
        this.comparator = comparator;
        this.external = external;

        if (initialWithSentinel)
            keys[0] = SENTINEL;
    }

    // 构造页并指定是否是外部页
    public NodePage(int maxNumNodes, Comparator<K> comparator, boolean external) {
        this(maxNumNodes, comparator, external, false);
    }

    // 构造一个外部页，并提供 Comparator
    public NodePage(int maxNumNodes, Comparator<K> comparator) {
        this(maxNumNodes, comparator, true);
    }

    // 构造一个外部页，使用自然排序
    public NodePage(int maxNumNodes) {
        this(maxNumNodes, null);
    }

    @Override
    public void flush() {
    }

    @SuppressWarnings("unchecked")
    @Override
    public NodePage<K> add(K key) {
        Objects.requireNonNull(key);

        if (external) {
            // 扩容
            if (size == keys.length)
                keys = Arrays.copyOf(keys, maxNumNodes);

            if (size == 0) {
                keys[size++] = key;
            } else if (size == 1 && compare(keys[0], key) != 0) {
                keys[size++] = key;
                if (compare(0, 1) > 0)
                    exch(0, 1);
            } else {
                int insertedIdx = Arrays.binarySearch(keys, 0, size, key, comparator);
                if (insertedIdx < 0) {
                    insertedIdx = -insertedIdx - 1;
                    System.arraycopy(keys, insertedIdx, keys, insertedIdx + 1, size - insertedIdx);
                    keys[insertedIdx] = key;
                    size++;
                }
            }

            // 已满，需要分裂
            if (isFull()) {
                size = maxNumNodes / 2;
                return new NodePage<K>(Arrays.copyOfRange(keys, maxNumNodes / 2, maxNumNodes),
                        maxNumNodes, comparator);
            }

            return null;
        } else {
            throw new IllegalStateException("Only external pages can add key");
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public NodePage<K> attach(Page<K> p) {
        Objects.requireNonNull(p);

        if (external) {
            throw new IllegalStateException("Only inner pages can attach sub page");
        } else {
            if (p.size() == 0)
                throw new IllegalArgumentException("p cannot be an empty page");

            Object minKey = p.minKey();
            // 扩容
            if (size == keys.length)
                keys = Arrays.copyOf(keys, maxNumNodes);
            if (size == 0) {
                keys[size] = minKey;
                sub()[size] = p;
                size++;
            } else {
                int insertedIdx = Arrays.binarySearch(keys, 0, size, minKey, comparator);
                if (insertedIdx < 0) {
                    insertedIdx = -insertedIdx - 1;
                    System.arraycopy(keys, insertedIdx, keys, insertedIdx + 1, size - insertedIdx);
                    keys[insertedIdx] = minKey;
                    size++;
                }
                sub()[insertedIdx] = p;
            }

            // 已满，需要分裂
            if (isFull()) {
                size = maxNumNodes / 2;
                return new NodePage<K>(Arrays.copyOfRange(keys, maxNumNodes / 2, maxNumNodes),
                        Arrays.copyOfRange(sub(), maxNumNodes / 2, maxNumNodes),
                        maxNumNodes, comparator, false);
            }

            return null;
        }
    }

    @Override
    public Object minKey() {
        return keys[0];
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isExternal() {
        return external;
    }

    @Override
    public boolean contains(K key) {
        return Arrays.binarySearch(keys, 0, size, key) >= 0;
    }

    @Override
    public Page<K> next(K key) {
        if (external) {
            throw new IllegalStateException("Only inner pages can attach sub page");
        }

        if (size == 0)
            return null;
        int idx = Arrays.binarySearch(keys, 0, size, key);
        if (idx < 0)
            idx = -idx - 2;
        return subPages[idx];
    }

    @Override
    public boolean isFull() {
        return size == maxNumNodes - 1;
    }

    @Override
    public Iterable<K> keys() {
        return null;
    }

    private NodePage(Object[] halfKeys, Page<K>[] halfSubPages,
                     int maxNumNodes, Comparator<K> comparator, boolean external) {
        keys = halfKeys;
        subPages = halfSubPages;
        this.maxNumNodes = maxNumNodes;
        this.comparator = comparator;
        this.external = external;
        size = maxNumNodes / 2;
    }

    private NodePage(Object[] halfKeys, int maxNumNodes, Comparator<K> comparator) {
        this(halfKeys, null, maxNumNodes, comparator, true);
    }

    @SuppressWarnings("unchecked")
    private Page<K>[] sub() {
        if (subPages == null) {
            subPages = (Page<K>[]) new Page[keys.length];
        }
        return subPages;
    }

    private void exch(int i, int j) {
        Object tmp = keys[i];
        keys[i] = keys[j];
        keys[j] = tmp;

        if (subPages != null) {
            Page<K> tmpPage = subPages[i];
            subPages[i] = subPages[j];
            subPages[j] = tmpPage;
        }
    }

    @SuppressWarnings("unchecked")
    private int compare(Object k1, Object k2) {
        if (comparator != null) {
            return comparator.compare(k1, k2);
        } else {
            Comparable ci = (Comparable) k1;
            return ci.compareTo(k2);
        }
    }

    @SuppressWarnings("unchecked")
    private int compare(int i, int j) {
        return compare(keys[i], keys[j]);
    }
}
