package util.datastructure;

import java.util.Queue;

/**
 * 散列表。
 */
public class HashST<K extends Comparable<K>, V> implements SymbolTable<K, V> {

    private int size;
    private int capacity;
    private float loadFactor;
    private int threshold;

    private RedBlackTreeST<K, V>[] tables;



    public HashST(int initCapacity, float loadFactor) {
        if (initCapacity < 2)
            throw new IllegalArgumentException("parameter \"initCapacity\" must greater Greater than or equal to 2.");
        if (Float.compare(loadFactor, 0) <= 0 || Float.compare(loadFactor, 1.0f) > 0)
            throw new IllegalArgumentException("parameter \"loadFactor\" must be between 0 and 1.");

        int cap = 1;
        while (cap < initCapacity)
            cap <<= 1;
        capacity = cap;
        this.loadFactor = loadFactor;
        threshold = (int) (capacity * loadFactor);
        tables = (RedBlackTreeST<K, V>[]) new RedBlackTreeST[capacity];
    }

    public HashST(int initCapacity) {
        this(initCapacity, 0.75f);
    }

    public HashST(float loadFactor) {
        this(16, loadFactor);
    }

    public HashST() {
        this(0.75f);
    }


    @Override
    public V put(K k, V v) {
        if (k == null || v == null)
            return null;
        if (!rehash())
            return null;

        RedBlackTreeST<K, V> t = tableOf(k);
        int oldSize = t.size();
        V r = t.put(k, v);
        if (t.size() > oldSize)
            size++;

        return r;
    }

    @Override
    public V get(K k) {
        if (k == null)
            return null;

        return tableOf(k).get(k);
    }

    @Override
    public V delete(K k) {
        if (k == null)
            return null;

        RedBlackTreeST<K, V> t = tableOf(k);
        int oldSize = t.size();
        V r = t.delete(k);
        if (oldSize > t.size())
            size--;

        return r;
    }

    @Override
    public boolean contains(K k) {
        return k != null && tableOf(k).contains(k);

    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void clear() {
        for (int i = 0; i < tables.length; i++) {
            if (tables[i] != null) {
                tables[i].clear();
                tables[i] = null;
            }
        }
        size = 0;
    }

    @Override
    public Iterable<K> keys() {
        Queue<K> r = new MyQueue<>(size + 1);
        for (RedBlackTreeST<K, V> table : tables) {
            if (table != null) {
                for (K k : table.keys()) {
                    r.offer(k);
                }
            }
        }

        return r;
    }


    // 可以选择存储哈希值加快速度
    private int indexOf(K k) {
        return k.hashCode() & (capacity - 1);
    }

    private RedBlackTreeST<K, V> tableOf(K k) {
        int idx = indexOf(k);
        return tables[idx] != null ? tables[idx] : (tables[idx] = new RedBlackTreeST<>());
    }

    private boolean rehash() {
        if (threshold == size) {
            if (capacity > Integer.MAX_VALUE >>> 1)
                return false;
            int newCap = capacity << 1;
            int newThreshold = (int) (capacity * loadFactor);
            RedBlackTreeST<K, V>[] newTables = new RedBlackTreeST[newCap];

            capacity = newCap;
            threshold = newThreshold;
            for (RedBlackTreeST<K, V> table : tables) {
                if (table != null) {
                    for (Entry<K, V> entry : table.entries()) {
                        int idx = indexOf(entry.key());
                        if (newTables[idx] == null)
                            newTables[idx] = new RedBlackTreeST<>();
                        newTables[idx].put(entry.key(), entry.value());
                    }
                    // 这里可以 clear table，也可以让垃圾收集来做
                }
            }
            tables = newTables;
        }

        return true;
    }
}
