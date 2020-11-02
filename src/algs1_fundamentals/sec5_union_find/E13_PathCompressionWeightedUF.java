package algs1_fundamentals.sec5_union_find;


import util.algs.In;
import util.algs.StdOut;
import util.algs.visual.VisualAccumulator;
import util.io.AlgsDataIO;

import java.io.FileNotFoundException;

/**
 * 1.5.13<br/>
 * 使用路径压缩的加权 quick-union 算法
 */
public class E13_PathCompressionWeightedUF {

    private int[] id;
    private int[] size;
    private int count;

    private VisualAccumulator va;


    public E13_PathCompressionWeightedUF(int N, boolean isVisible) {
        count = N;
        id = new int[N];
        size = new int[N];
        for (int i = 0; i < N; i++) {
            id[i] = i;
            size[i] = 1;
        }

        va = new VisualAccumulator(650, 100, isVisible);
    }

    public E13_PathCompressionWeightedUF(int N) {
        this(N, false);
    }


    public int count() {
        return count;
    }

    public int find(int p) {
        int pp = p;
        while (p != id[p]) {
            p = id[p];

            va.addDataValue(2);
        }
        while (pp != id[p]) {
            pp = id[p];
            id[p] = p;

            va.addDataValue(3);
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
            if (size[pRoot] >= size[qRoot]) {
                size[pRoot] += size[qRoot];
                id[qRoot] = pRoot;

                va.addDataValue(5);
            } else {
                size[qRoot] += size[pRoot];
                id[pRoot] = qRoot;

                va.addDataValue(3);
            }
            count--;
        }
        va.showCost();
    }


    public static void main(String[] args) throws FileNotFoundException {
        In in = AlgsDataIO.openMediumUF();
        int N = in.readInt();
        E13_PathCompressionWeightedUF uf = new E13_PathCompressionWeightedUF(N, true);
        while (!in.isEmpty()) {
            int p = in.readInt();
            int q = in.readInt();
            uf.union(p, q);
            StdOut.println(p + " " + q);
        }
        StdOut.println(uf.count + " 连通分量");
    }
}
