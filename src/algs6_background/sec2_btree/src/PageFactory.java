package algs6_background.sec2_btree.src;

import java.util.Comparator;

public interface PageFactory {

    /**
     * 构造一个外部页
     *
     * @param maxNumNodes 最大结点数
     * @param <K>
     * @return
     */
    <K> Page<K> newPage(int maxNumNodes);

    /**
     * 构造一个外部页
     *
     * @param maxNumNodes 最大结点数
     * @param comparator 比较器
     * @param <K>
     * @return
     */
    <K> Page<K> newPage(int maxNumNodes, Comparator<K> comparator);

    /**
     * 构造一个页，指定其是否为外部页。
     *
     * @param maxNumNodes 最大结点数
     * @param comparator 比较器
     * @param external 是否为外部页
     * @param <K>
     * @return
     */
    <K> Page<K> newPage(int maxNumNodes, Comparator<K> comparator, boolean external);

    /**
     * 构造一个 root 页，指定其是否为外部页。
     *
     * @param maxNumNodes
     * @param comparator
     * @param <K>
     * @return
     */
    <K> Page<K> newRootPage(int maxNumNodes, Comparator<K> comparator, boolean external);
}
