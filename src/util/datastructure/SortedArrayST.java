package util.datastructure;

import java.util.Arrays;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * 基于数组的有序符号表
 */
@SuppressWarnings("unchecked")
public class SortedArrayST<K extends Comparable<K>, V> implements SortedSymbolTable<K, V> {

    private Entry<K, V>[] entries;
    private int size;
    private int maxSize;

    private int modCnt;


    public SortedArrayST(int initSize, int maxSize) {
        if (initSize > maxSize || initSize <= 0 || maxSize <= 0) {
            throw new IllegalArgumentException("大小参数不正确");
        }

        this.entries = new Entry[initSize];
        this.size = 0;
        this.maxSize = maxSize;
    }

    public SortedArrayST(int initSize) {
        this(initSize, Integer.MAX_VALUE);
    }

    public SortedArrayST() {
        this(16);
    }


    public int getMaxSize() {
        return maxSize;
    }

    public void setMaxSize(int maxSize) {
        if (maxSize >= size) {
            this.maxSize = maxSize;
        }
    }

    @Override
    public V put(K k, V v) {
        if (k == null || v == null)
            return null;

        int i = rank(k);
        if (entries[i] != null && entries[i].key().equals(k)) {
            V temp = entries[i].value();
            entries[i].setValue(v);

            return temp;
        } else {
            modCnt++;
            if (!ensureCapacity())
                return null;
            System.arraycopy(entries, i, entries, i + 1, size - i);
            entries[i] = new Entry<>(k, v);
            size++;

            return v;
        }
    }

    @Override
    public V get(K k) {
        if (k == null)
            return null;

        Entry<K, V> entry = entries[rank(k)];
        return entry != null ? (entry.key().equals(k) ? entry.value(): null) : null;
    }

    @Override
    public V delete(K k) {
        if (k == null)
            return null;

        int i = rank(k);
        Entry<K, V> entry = entries[i];
        if (entry != null && entry.key().equals(k)) {
            modCnt++;
            V tmp = entry.value();
            System.arraycopy(entries, i + 1, entries, i, size - i);
            size--;

            return tmp;
        }

        return null;
    }

    @Override
    public boolean contains(K k) {
        if (k == null)
            return false;

        Entry<K, V> entry = entries[rank(k)];
        return entry != null && entry.key().equals(k);
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
        for (int i = 0; i < size; i++) {
            entries[i] = null;
        }
        size = 0;
        modCnt++;
    }

    @Override
    public Entry<K, V> min() {
        return size > 0 ? entries[0] : null;
    }

    @Override
    public Entry<K, V> max() {
        return size > 0 ? entries[size - 1] : null;
    }

    @Override
    public Entry<K, V> floor(K k) {
        if (k == null)
            return null;

        int i = rank(k);
        if (i > 0)
            return entries[i - 1];
        else
            return null;
    }

    @Override
    public Entry<K, V> ceil(K k) {
        if (k == null)
            return null;

        int i = rank(k);

        return entries[i];
    }

    @Override
    public int rank(K k) {
        if (k == null)
            return 0;

        int lo = 0, hi = size - 1;

        while (lo <= hi) {
            int mid = (lo + hi) / 2;
            int cmp = k.compareTo(entries[mid].key());
            if (cmp < 0)
                hi = mid - 1;
            else if (cmp > 0)
                lo = mid + 1;
            else
                return mid;
        }

        return lo;
    }

    @Override
    public Entry<K, V> select(int k) {
        if (k < 0 || k >= size)
            return null;

        return entries[k];
    }

    @Override
    public Entry<K, V> deleteMin() {
        if (size == 0)
            return null;

        modCnt++;
        Entry<K, V> tmp = entries[0];
        System.arraycopy(entries, 1, entries, 0, size - 1);
        size--;

        return tmp;
    }

    @Override
    public Entry<K, V> deleteMax() {
        if (size == 0)
            return null;

        modCnt++;
        Entry<K, V> tmp = entries[size - 1];
        entries[--size] = null;

        return tmp;
    }

    @Override
    public int size(K lo, K hi) {
        if (lo == null || hi == null || size == 0)
            return 0;

        int i = rank(lo), j = rank(hi);
        return i >= j ? 0 : j - i;
    }

    @Override
    public Iterable<K> keys(K lo, K hi) {
        int minIndex = rank(lo);
        int maxIndex = rank(hi);

        if (minIndex > maxIndex - 1)
            throw new IllegalStateException("The position of parameter lo is larger than " +
                    "that of parameter hi.");

        return () -> new Iter(minIndex, maxIndex - 1);
    }

    @Override
    public Iterable<K> keys() {
        return Iter::new;
    }


    private class Iter implements Iterator<K> {

        private int currentIndex = 0;
        private int maxIndex = size - 1;
        private int expectedModCnt = modCnt;
        private boolean okToRemove = false;


        public Iter() {}

        public Iter(int minIndex, int maxIndex) {
            currentIndex = minIndex;
            this.maxIndex = maxIndex;
        }


        @Override
        public boolean hasNext() {
            return currentIndex <= maxIndex;
        }

        @Override
        public K next() {
            if (modCnt != expectedModCnt) {
                throw new ConcurrentModificationException("You cannot change the structure " +
                        "of the SortedArrayST being iterated!");
            }
            if (!hasNext()) {
                throw new NoSuchElementException("Iteration to last element!");
            }
            okToRemove = true;

            return entries[currentIndex++].key();
        }

        @Override
        public void remove() {
            if (modCnt != expectedModCnt) {
                throw new ConcurrentModificationException("You cannot change the structure " +
                        "of the SortedArrayST being iterated!");
            }
            if (!okToRemove) {
                throw new IllegalStateException("The remove operation must be performed after " +
                        "the next operation!");
            }
            okToRemove = false;
            expectedModCnt++;
            SortedArrayST.this.delete(entries[--currentIndex].key());
        }
    }


    private boolean ensureCapacity() {
        if (size == maxSize)
            return false;

        if (size == entries.length) {
            int newCapacity = size;
            if (newCapacity >= maxSize / 2) {
                newCapacity = maxSize;
            } else {
                newCapacity *= 2;
            }
            entries = Arrays.copyOf(entries, newCapacity);
        }

        return true;
    }
}
