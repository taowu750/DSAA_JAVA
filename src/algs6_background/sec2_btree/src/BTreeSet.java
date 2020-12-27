package algs6_background.sec2_btree.src;


import java.util.Objects;

/**
 * 我们用页表示一块连续的数据，用探查表示访问一个页。一个页可能是本地计算机上的一个文件，
 * 也可能是远程计算机上的一张网页，也可能是服务器上某个文件的一部分，等等。
 * <p>
 * 我们的目标是实现仅使用极少次数的探查就能找到任意给定键的查找算法。我们对页的具体大小、
 * 一次探查需要的具体时间与访问页中内容所需时间等不感兴趣。我们使用页的访问次数（无论读写）
 * 作为外部查找算法的成本模型。
 * <p>
 * B-树是一颗多向树，具体来说是一颗 M 向树。M 一般是一个偶数。每个结点最多含有 M - 1 个
 * 键和链接，最少含有 M/2 个键和链接。根结点是个例外，它可以有最少 2 个键和链接。例如，
 * 在一棵 4 阶 B-树中，每个结点含有 2 到 3 个键和链接；6 阶 B-树每个结点含有 3 到 5 个
 * 键和链接。
 * <p>
 * 外部查找的应用常常会将索引和数据隔离。我们使用两种不同类型的结点做到这点：
 * - 内部节点：含有与页相关联的键的副本
 * - 外部节点：含有指向实际数据的引用
 * <p>
 * 内部结点 N 中每个键都和一个结点相关联，在以此结点为根的子树中，所有的键都大于等于与此
 * 结点关联的键，而小于 N 中更大的键（如果存在的话）。为了方便表示使用一个特殊的哨兵键“*”，
 * 它小于所有的键。
 * <p>
 * 根结点在初始化时仅含有哨兵键，我们会在内部结点中使用键的多个副本来引导查找。
 * <p>
 * 在查找时，从根结点开始，根据被查找的键选择当前结点中的适当区间并根据适当的链接从一个
 * 结点移动到下一个结点。最终，查找过程会到达树底的一个页。如果被查找的键在页中，查找命中；
 * 否则查找未命中。参见 btree-search.png。
 * <p>
 * 要在插入一个新键，可以使用递归代码。如果空间不足，可以允许被插入结点暂时溢出
 * （变成一个 M-结点），并在递归向上后不断分裂 M-结点。如果根结点也变成了 M-结点，则将它
 * 分裂成连接了两个 M/2-结点的 2-结点。对于树的其他位置，我们将 M-结点的父 k-结点变成连接
 * 两个 M/2-结点的 (k+1)-结点。参见 btree-add.png。
 *
 * @param <K>
 */
public class BTreeSet<K> {

    private Page<K> root;
    private int maxNumNodes;

    public BTreeSet(PageFactory pageFactory, int maxNumNodes) {
        Objects.requireNonNull(pageFactory);
        root = pageFactory.newPage(maxNumNodes);
        this.maxNumNodes = maxNumNodes;
    }

    public boolean contains(K key) {
        Objects.requireNonNull(key);
        return contains(root, key);
    }

    private boolean contains(Page<K> page, K key) {
        if (page.isExternal())
            return page.contains(key);
        else
            return contains(page.next(key), key);
    }

    public void add(K key) {
        add(root, key);
    }

    private void add(Page<K> page, K key) {
        if (page.isExternal()) {
            page.add(key);
            return;
        }

        Page<K> nextPage = page.next(key);
        add(nextPage, key);
        if (nextPage.isFull()) {
            page.add(nextPage.split());
        }
        nextPage.flush();
    }
}
