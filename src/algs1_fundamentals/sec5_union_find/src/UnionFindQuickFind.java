package algs1_fundamentals.sec5_union_find.src;

import util.algs.In;
import util.algs.StdOut;
import util.io.AlgsDataIO;

/**
 * 此实现的 find() 非常快，它让同一个连通分量的 id 都相等。
 * 但它的 union() 需要遍历数组，这使得此实现无法处理大型问题。
 */
public class UnionFindQuickFind implements IUnionFind {

    // 分量，下标表示元素，值表示连通分量 id
    private int[] id;
    // 分量数量
    private int count;


    public UnionFindQuickFind(int N) {
        count = N;
        id = new int[count];
        for (int i = 0; i < count; i++) {
            id[i] = i;
        }
    }


    public int count() {
        return count;
    }

    public int find(int p) {
        return id[p];
    }

    public void union(int p, int q) {
        int pId = find(p);
        int qId = find(q);

        if (pId != qId) {
            // 将 p 的分量重命名为 q 的名称
            for (int i = 0; i < id.length; i++) {
                if (id[i] == pId) {
                    id[i] = qId;
                }
            }
            count--;
        }
    }

    public boolean connected(int p, int q) {
        return find(p) == find(q);
    }


    public static void main(String[] args) {
        In in = AlgsDataIO.openMediumUF();
        int N = in.readInt();
        UnionFindQuickFind uf = new UnionFindQuickFind(N);
        while (!in.isEmpty()) {
            int p = in.readInt();
            int q = in.readInt();

            if (!uf.connected(p, q)) {
                uf.union(p, q);
                StdOut.println(p + " - " + q);
            }
        }
        StdOut.println(uf.count() + " 分量");
    }
}
