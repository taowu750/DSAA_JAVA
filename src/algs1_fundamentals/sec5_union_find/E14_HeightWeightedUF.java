package algs1_fundamentals.sec5_union_find;


import util.algs.StdIn;
import util.algs.StdOut;
import util.algs.visual.VisualAccumulator;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * 1.5.14<br/>
 * 根据高度加权的 <em>quick-union<em/> 算法，将高度较矮的树链接到较高的数上，用算法证明树的高度不会超过 lgN。
 */
public class E14_HeightWeightedUF {

    private int[] id;
    private int[] height;
    private int   count;

    private VisualAccumulator va;


    public E14_HeightWeightedUF(int N, boolean isVisible) {
        count = N;
        id = new int[N];
        height = new int[N];
        for (int i = 0; i < N; i++) {
            id[i] = i;
            height[i] = 0;
        }

        va = new VisualAccumulator(650, 100, isVisible);
    }

    public E14_HeightWeightedUF(int N) {
        this(N, false);
    }


    public int count() {
        return count;
    }

    public int find(int p) {
        while (p != id[p]) {
            p = id[p];

            va.addDataValue(2);
        }

        return p;
    }

    public boolean connected(int p, int q) {
        return find(p) == find(q);
    }

    public void union(int p, int q) {
        int pRoot = find(p);
        int qRoot = find(q);

        if (pRoot != qRoot) {
            if (height[pRoot] >= height[qRoot]) {
                height[pRoot] += (height[pRoot] == height[qRoot] ? 1 : 0);
                id[qRoot] = pRoot;

                va.addDataValue(4);
            } else {
                id[pRoot] = qRoot;

                va.addDataValue(1);
            }
            count--;

            va.showCost();
        }
    }


    public static void main(String[] args) throws FileNotFoundException {
        BufferedInputStream bf = new BufferedInputStream(new FileInputStream
                ("src/algs1_fundamentals/sec5_union_find/src/mediumUF.txt"));
        System.setIn(bf);

        int                  N  = StdIn.readInt();
        E14_HeightWeightedUF uf = new E14_HeightWeightedUF(N, true);
        while (!StdIn.isEmpty()) {
            int p = StdIn.readInt();
            int q = StdIn.readInt();
            uf.union(p, q);
            StdOut.println(p + " " + q);
        }
        StdOut.println(uf.count + " components");
    }
}
