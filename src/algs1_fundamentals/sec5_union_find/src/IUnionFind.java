package algs1_fundamentals.sec5_union_find.src;

/**
 * Union-Find 算法接口。Union-Find 的成本模型，在研究实现 union-find 的 API 的各种
 * 算法时，我们统计的是<strong>数组的访问次数</strong>，无论读写。
 */
public interface IUnionFind {

    /**
     * 连通分量的数量。
     *
     * @return
     */
    int count();

    /**
     * p 所在在分量的标识符
     *
     * @param p
     * @return
     */
    int find(int p);

    /**
     * 在 p 和 q 之间添加一条连接
     *
     * @param p
     * @param q
     */
    void union(int p, int q);

    /**
     * 如果 p 和 q 存在于同一个分量中则返回 true
     *
     * @param p
     * @param q
     * @return
     */
    boolean connected(int p, int q);
}
