package algs1_fundamentals.sec5_union_find.src;

/**
 * <p>
 * 加权 quick-union 算法，与之前随意的将一棵树链接到了一棵树上不同的是，
 * 这次我们会记录树的大小并将较小的树连接到较大的树上。这种方式会降低
 * 树的深度。
 * </p>
 * <p>
 * 对于动态连通性问题，加权 quick-union 算法是唯一一种能够解决大型问题的。
 * </p>
 * <p>
 * 要是想要进一步改进性能，我们可以使用<strong>路径压缩</strong>方法，
 * 这种算法在 find() 里面添加一个循环，将路径上遇到的所有节点都直接连接到
 * 根节点，这样我们就会得到一颗扁平的树。
 * </p>
 */
public class UnionFindWeighted implements IUnionFind {

    private int[] id;
    private int[] weight;
    private int count;


    public UnionFindWeighted(int N) {
        count = N;
        id = new int[count];
        weight = new int[count];
        for (int i = 0; i < count; i++) {
            id[i] = i;
            weight[i] = 1;
        }
    }


    @Override
    public int count() {
        return count;
    }

    @Override
    public int find(int p) {
        while (p != id[p]) {
            p = id[p];
        }

        return p;
    }

    @SuppressWarnings("Duplicates")
    @Override
    public void union(int p, int q) {
        int pRoot = find(p);
        int qRoot = find(q);

        if (pRoot != qRoot) {
            if (weight[pRoot] < weight[qRoot]) {
                id[pRoot] = qRoot;
                weight[qRoot] += weight[pRoot];
            } else {
                id[qRoot] = pRoot;
                weight[pRoot] += weight[qRoot];
            }
            count--;
        }
    }

    @Override
    public boolean connected(int p, int q) {
        return find(p) == find(q);
    }
}
