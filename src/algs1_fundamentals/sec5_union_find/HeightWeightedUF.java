package algs1_fundamentals.sec5_union_find;

import algs1_fundamentals.sec5_union_find.src.IUnionFind;

/**
 * 根据高度加权的 union-find
 */
public class HeightWeightedUF implements IUnionFind {

    private int[] id;
    private int[] height;
    private int count;


    public HeightWeightedUF(int N) {
        count = N;
        id = new int[count];
        height = new int[count];

        for (int i = 0; i < count; i++) {
            id[i] = i;
            height[i] = 0;
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
            if (height[pRoot] < height[qRoot]) {
                if (height[qRoot] < height[pRoot] + 1) {
                    height[qRoot] = height[pRoot] + 1;
                }
                id[pRoot] = qRoot;
            } else {
                if (height[pRoot] < height[qRoot] + 1) {
                    height[pRoot] = height[qRoot] + 1;
                }
                id[qRoot] = pRoot;
            }
            count--;
        }
    }

    @Override
    public boolean connected(int p, int q) {
        return find(p) == find(q);
    }
}
