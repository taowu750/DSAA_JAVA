package algs4_graph.sec1_undirected_graph.src;

import util.algs.StdIn;
import util.datastructure.MyStack;
import util.io.AlgsDataIO;

/**
 * <p>
 * 深度优先搜索。算法复杂度和顶点的度数之和成正比。
 * </p>
 * <p>
 * 深度优先搜索主要解决一个节点到另一个节点是否连通的问题，以及该节点与所有
 * 连通节点的路径。
 * </p>
 */
public class DepthFirstSearch implements Search {

    // 每个结点是否与起点 s 连通
    private boolean[] marked;
    // 存储路径当前结点的上一个结点
    private int[] edgeTo;
    // 存储路径到当前结点为止的结点数
    private int[] dist;
    // 起点
    private final int s;
    private SimpleGraph g;
    private int count;


    /**
     * s 是源结点。
     *
     * @param g
     * @param s
     */
    public DepthFirstSearch(SimpleGraph g, int s) {
        this.g = g;
        this.s = s;
        marked = new boolean[g.vertexNum()];
        edgeTo = new int[g.vertexNum()];
        dist = new int[g.vertexNum()];
        dfs(g, s);

        for (int i = 0; i < dist.length; i++) {
            if (i != s && dist[i] == 0)
                dist[i] = -1;
        }
    }


    public static void main(String[] args) {
//        testMarked();
        testPath();
    }

    public static void testMarked() {
        UndirectedGraph g = new UndirectedGraph(AlgsDataIO.openTinyCG());
        System.out.print("输入源结点：");
        int s = StdIn.readInt();
        DepthFirstSearch search = new DepthFirstSearch(g, s);

        for (int v = 0; v < g.vertexNum(); v++) {
            if (search.hasPathTo(v))
                System.out.print(v + " ");
        }
        System.out.println();

        if (search.count() != g.vertexNum())
            System.out.print("NOT ");
        System.out.println("connected");
    }

    @SuppressWarnings("Duplicates")
    public static void testPath() {
        UndirectedGraph g = new UndirectedGraph(AlgsDataIO.openTinyCG());
        System.out.print("输入源结点：");
        int s = StdIn.readInt();
        DepthFirstSearch search = new DepthFirstSearch(g, s);

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

    /**
     * 检查 s 和 w 是否连通
     *
     * @param w
     * @return
     */
    public boolean hasPathTo(int w) {
        return marked[w];
    }

    /**
     * 检查和 s 连通的所有节点数量
     *
     * @return
     */
    public int count() {
        return count;
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

    @Override
    public int distTo(int v) {
        return dist[v];
    }


    private void dfs(SimpleGraph g, int v) {
        marked[v] = true;
        count++;
        for (Integer w : g.adj(v)) {
            if (!marked[w]) {
                edgeTo[w] = v;
                dist[w] += dist[v] + 1;
                dfs(g, w);
            }
        }
    }
}
