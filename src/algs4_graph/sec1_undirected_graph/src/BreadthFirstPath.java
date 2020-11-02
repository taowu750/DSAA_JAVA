package algs4_graph.sec1_undirected_graph.src;

import util.algs.StdIn;
import util.datastructure.MyQueue;
import util.datastructure.MyStack;
import util.io.AlgsDataIO;

import java.util.Queue;

/**
 * 广度优先搜索。它可以用来寻找最短路径。它的算法复杂度为 O(V+E)
 */
public class BreadthFirstPath implements Search {

    private boolean[] marked;
    private int[] edgeTo;
    private int[] dist;
    private final int s;
    private SimpleGraph g;
    int count;


    public BreadthFirstPath(SimpleGraph g, int s) {
        this.g = g;
        this.s = s;
        marked = new boolean[g.vertexNum()];
        edgeTo = new int[g.vertexNum()];
        dist = new int[g.vertexNum()];
        bfs(g, s);

        for (int i = 0; i < dist.length; i++) {
            if (i != s && dist[i] == 0)
                dist[i] = -1;
        }
    }


    public static void main(String[] args) {
        testPath();
    }

    @SuppressWarnings("Duplicates")
    public static void testPath() {
        UndirectedGraph g = new UndirectedGraph(AlgsDataIO.openTinyCG());
        System.out.print("输入源结点：");
        int s = StdIn.readInt();
        BreadthFirstPath search = new BreadthFirstPath(g, s);

        Search.printAllPath(search);
    }


    @Override
    public int source() {
        return s;
    }

    @Override
    public SimpleGraph graph() {
        return g;
    }

    public boolean hasPathTo(int v) {
        return marked[v];
    }

    @SuppressWarnings("Duplicates")
    public Iterable<Integer> pathTo(int v) {
        MyStack<Integer> path = new MyStack<>();
        if (!hasPathTo(v)) {
            return path;
        }

        for (int x = v; x != s; x = edgeTo[x])
            path.push(x);
        path.push(s);

        return path;
    }

    /**
     * 源点到 v 的距离。-1 表示不连通。
     *
     * @param v
     * @return
     */
    public int distTo(int v) {
        return dist[v];
    }

    @Override
    public int count() {
        return count;
    }


    private void bfs(SimpleGraph g, int s) {
        Queue<Integer> queue = new MyQueue<>();
        marked[s] = true;
        count++;
        queue.offer(s);

        while (!queue.isEmpty()) {
            int v = queue.poll();
            for (Integer w : g.adj(v)) {
                if (!marked[w]) {
                    marked[w] = true;
                    count++;
                    edgeTo[w] = v;
                    dist[w] += dist[v] + 1;
                    queue.offer(w);
                }
            }
        }
    }
}
