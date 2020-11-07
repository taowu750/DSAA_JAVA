package algs4_graph.sec2_digraph;

import algs1_fundamentals.sec5_union_find.src.IUnionFind;
import algs1_fundamentals.sec5_union_find.src.UnionFindQuickUnion;
import algs4_graph.sec2_digraph.src.Degrees;
import algs4_graph.sec2_digraph.src.Digraph;
import util.tuple.Tuple2;

/**
 * 有向无环图中的LCA（最近共同祖先）。给定一幅有向无环图和两个顶点v和w，找出v和w的LCA。
 *
 * 关于求LCA算法，有很多类型，可参见 https://www.cnblogs.com/MakiseVon/p/10699322.html
 */
public class E21_LCA {
}

/**
 * Tarjan算法求树中结点的LCA
 */
class Tarjan {

    // 一棵树，每个结点只有一个父结点
    private Digraph g;
    private IUnionFind uf;
    private boolean[] marked;
    private Tuple2<Integer, Integer>[] vws;
    private int[] ancestor;
    private int root;

    public Tarjan(Digraph g) {
        this.g = g;

        Degrees degrees = new Degrees(g);
        root = -1;
        for (int v = 0; v < g.vertexNum(); v++) {
            if (degrees.inDegree(v) == 0) {
                root = v;
                break;
            }
        }
        assert root != -1;

        // 不可用加权UF，因为要保证一个节点合并到另一个结点上
        uf = new UnionFindQuickUnion(g.vertexNum());
        marked = new boolean[g.vertexNum()];
    }

    public int[] lca(Tuple2<Integer, Integer>... vws) {
        this.vws = vws;
        ancestor = new int[vws.length];
        tarjan(root);

        return ancestor;
    }

    private void tarjan(int u) {
        // 下面的循环是一个后序遍历
        // 访问u的所有子结点
        for (int v: g.adj(u)) {
            tarjan(v);
            // 将v合并到u上，也就是保存v的父结点信息
            uf.union(v, u);
            marked[v] = true;
        }

        // 对于每个和u有询问关系的结点e，如果e已被访问，则u、e的LCA为find(e)，即e的祖先结点
        for (int i = 0; i < vws.length; i++) {
            Tuple2<Integer, Integer> vw = vws[i];
            if (vw.a == u && marked[vw.b])
                ancestor[i] = uf.find(vw.b);
            if (vw.b == u && marked[vw.a])
                ancestor[i] = uf.find(vw.a);
        }
    }
}
