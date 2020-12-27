package algs6_background.sec2_btree.src;

/**
 * 参见 {@link BTreeSet}。
 * <p>
 * Page 接口表示页的抽象表示。它可以关联键与指向 Page 对象的链接，支持检测页是否溢出、
 * 分裂页并区分内部页和外部页的操作。
 *
 * @param <K>
 */
public interface Page<K> {

    /**
     * 刷新页，将内存中的内容写回外部页（如果需要的话）。
     */
    void flush();

    /**
     * 将键插入当前（外部）页中。
     *
     * @param key
     */
    void add(K key);

    /**
     * 将 p 中最小键插入到当前（内部）页中，将插入的条目和 p 关联起来。
     *
     * @param p
     */
    void add(Page<K> p);

    /**
     * 当前页是否是外部页。
     *
     * @return
     */
    boolean isExternal();

    /**
     * 当前页是否包含指定键
     *
     * @param key
     * @return
     */
    boolean contains(K key);

    /**
     * 返回可能包含 key 的子树（页）
     *
     * @param key
     * @return
     */
    Page<K> next(K key);

    /**
     * 当前页是否已经溢出。
     *
     * @return
     */
    boolean isFull();

    /**
     * 分割当前页，将右半边移动到一个新的页中并返回。
     *
     * @return
     */
    Page<K> split();

    /**
     * 页中所有键的迭代器。
     *
     * @return
     */
    Iterable<K> keys();
}
