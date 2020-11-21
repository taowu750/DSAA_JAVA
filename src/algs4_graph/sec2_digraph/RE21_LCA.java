package algs4_graph.sec2_digraph;

import algs4_graph.sec2_digraph.src.Degrees;
import algs4_graph.sec2_digraph.src.Digraph;
import algs4_graph.sec2_digraph.src.DigraphCycle;
import org.junit.jupiter.api.Test;
import util.datastructure.MyQueue;

import java.util.*;
import java.util.function.BinaryOperator;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * 有向无环图中的LCA（最近共同祖先）。给定一幅有向无环图和两个顶点v和w，找出v和w的LCA。
 * 这里假设有向无环图中结点可以有多个父结点。如果是一棵树的话参见{@link E21_LCA}
 */
public class RE21_LCA {

    public static Digraph lcaG() {
        // 这是一个层次的图，从0-12，(0,1,2)、(3,4)、(5,6,7,8)、(9,10)、(11,12)分别是不同的层
        Digraph g = new Digraph(13);
        g.addEdge(0, 3);
        g.addEdge(1, 3);
        g.addEdge(1, 4);
        g.addEdge(2, 4);
        g.addEdge(3, 5);
        g.addEdge(3, 6);
        g.addEdge(3, 7);
        g.addEdge(4, 6);
        g.addEdge(4, 7);
        g.addEdge(4, 8);
        g.addEdge(6, 9);
        g.addEdge(7, 9);
        g.addEdge(8, 7);
        g.addEdge(8, 10);
        g.addEdge(9, 11);
        g.addEdge(9, 12);
        g.addEdge(10, 12);

        return g;
    }

    public static void testLCA(BinaryOperator<Integer> lca) {
        assertEquals(lca.apply(5, 10), 1);
        assertEquals(lca.apply(3, 1), 1);
        assertEquals(lca.apply(3, 4), 1);
        assertEquals(lca.apply(0, 2), -1);
        assertEquals(lca.apply(6, 12), 6);
        assertEquals(lca.apply(6, 12), 6);
        assertEquals(lca.apply(11, 10), 8);
        assertEquals(lca.apply(5, 9), 3);
    }

    @Test
    public void test_LCA() {
        testLCA(new HeightLCA(lcaG())::lca);
    }
}

class HeightLCA {
    private Digraph g, inv;
    private int[] height;
    private boolean[] marked;
    private Queue<Integer> queue;

    public HeightLCA(Digraph g) {
        assert !new DigraphCycle(g).hasCycle();

        this.g = g;
        inv = g.reverse();
        height = new int[g.vertexNum()];
        marked = new boolean[g.vertexNum()];
        queue = new MyQueue<>();

        Degrees degrees = new Degrees(g);
        int[] roots = IntStream.range(0, g.vertexNum()).filter(v -> degrees.inDegree(v) == 0).toArray();
        // 求出所有顶点高度
        for (int root : roots) {
            calcHeight(root);
        }
    }

    public int lca(int v, int w) {
        Set<Integer> vAncestors = findAncestors(v);
        Set<Integer> wAncestors = findAncestors(w);
        // 求共同祖先
        vAncestors.retainAll(wAncestors);

        // 具有最大高度的共同祖先即为LCA
        return vAncestors.stream().parallel().max(Comparator.comparingInt(i -> height[i])).orElse(-1);
    }

    public int height(int v) {
        return height[v];
    }

    private void calcHeight(int root) {
        for (int w: g.adj(root))
            calcHeight(w, root);
    }

    private void calcHeight(int u, int p) {
        if (height[u] == 0 || height[u] > height[p] + 1) {
            height[u] = height[p] + 1;
            for (int e: g.adj(u))
                calcHeight(e, u);
        }
    }

    private Set<Integer> findAncestors(int v) {
        marked[v] = true;
        queue.offer(v);

        Set<Integer> ancestors = new HashSet<>();
        // 需要加上它自己，因为v可能是w的祖先
        ancestors.add(v);
        while (!queue.isEmpty()) {
            v = queue.poll();
            for (int w: inv.adj(v)) {
                if (!marked[w]) {
                    marked[w] = true;
                    queue.offer(w);
                    ancestors.add(w);
                }
            }
        }
        Arrays.fill(marked, false);

        return ancestors;
    }
}
