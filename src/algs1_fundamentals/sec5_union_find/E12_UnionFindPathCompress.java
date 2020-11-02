package algs1_fundamentals.sec5_union_find;

import algs1_fundamentals.sec5_union_find.src.IUnionFind;

/**
 * 使用路径压缩的加权 quick-union 算法。
 */
public class E12_UnionFindPathCompress {


}


class UnionFindPathCompress implements IUnionFind {

    private int[] id;
    private int[] weight;
    private int count;


    public UnionFindPathCompress(int N) {
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
        int root = p;
        while (root != id[root]) {
            root = id[root];
        }
        while (p != id[p]) {
            int temp = p;
            p = id[p];
            id[temp] = root;
        }

        return root;
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
