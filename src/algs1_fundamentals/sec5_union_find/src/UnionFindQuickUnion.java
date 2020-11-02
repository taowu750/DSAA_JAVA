package algs1_fundamentals.sec5_union_find.src;

/**
 * <p>
 * quick-union 算法中每个触点所对应的 id[] 元素都是同一个分量中另一个触点
 * 的名称（也可能是它自己），我们将这种关系称为链接。
 * </p>
 * <p>
 * 在实现 find() 方法时，我们从给定的触点开始，由它的链接得到另一个触点，
 * 再由这个触点得到下一个触点，直到到达根触点。当且仅当两个触点的根触点相同时，
 * 我们称它们是相通的。
 * </p>
 * <p>
 * 根触点就是数组值等于下标的触点。
 * </p>
 */
public class UnionFindQuickUnion implements IUnionFind {

    private int[] id;
    private int count;


    public UnionFindQuickUnion(int N) {
        count = N;
        id = new int[count];
        for (int i = 0; i < count; i++) {
            id[i] = i;
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

    @Override
    public void union(int p, int q) {
        int pRoot = find(p);
        int qRoot = find(q);

        if (pRoot != qRoot) {
            id[pRoot] = qRoot;
            count--;
        }
    }

    @Override
    public boolean connected(int p, int q) {
        return find(p) == find(q);
    }
}
