package util.datastructure;

/**
 * 有序符号表
 */
public interface SortedSymbolTable<K extends Comparable<K>, V> extends SymbolTable<K, V> {

    Entry<K, V> min();

    Entry<K, V> max();

    /**
     * 小于等于 k 的最大键值对
     *
     * @param k
     * @return
     */
    Entry<K, V> floor(K k);

    /**
     * 大于等于 k 的最小键值对
     *
     * @param k
     * @return
     */
    Entry<K, V> ceil(K k);

    /**
     * 小于 k 的键的数量。
     *
     * @param k
     * @return
     */
    int rank(K k);

    /**
     * 排名为 k 的键值对
     *
     * @param k
     * @return
     */
    Entry<K, V> select(int k);

    Entry<K, V> deleteMin();

    Entry<K, V> deleteMax();

    /**
     * [lo..hi) 之间键的数量
     *
     * @param lo
     * @param hi
     * @return
     */
    int size(K lo, K hi);

    /**
     * [lo..hi) 之间的所有键，已排序。
     *
     * @param lo
     * @param hi
     * @return
     */
    Iterable<K> keys(K lo, K hi);

    /**
     * 所有键，已排序。
     *
     * @return
     */
    Iterable<K> keys();
}
