package algs4_graph.sec1_undirected_graph.src;

import algs4_graph.sec3_mst.src.Edge;

import java.util.HashSet;

/*
Tarjan 算法求桥。
从某个顶点开始进行 DFS，并进行标号。如果某个顶点的某条边的 DFS 中有某个孩子
有边指向这个顶点的祖先，那么这条边不为桥，否则为桥。
 */
public class Bridge {

    private int count;

    private int[] order;
    private int[] anchor;

    private HashSet<Edge> bridges;


    /*
    g 是连通图。
     */
    public Bridge(UndirectedGraph g) {
        order = new int[g.vertexNum()];
        anchor = new int[g.vertexNum()];
        bridges = new HashSet<>();

        for (int v = 0; v < g.vertexNum(); v++) {
            order[v] = -1;
            anchor[v] = -1;
        }

        for (int v = 0; v < g.vertexNum(); v++) {
            if (order[v] == -1)
                dfs(g, v, v);
        }
    }


    public boolean isEdgeConnected() {
        return bridges.size() == 0;
    }

    public int bridges() {
        return bridges.size();
    }

    public boolean isBridge(int v, int w) {
        return bridges.contains(new Edge(v, w, 0));
    }

    public boolean isBridge(Edge edge) {
        return bridges.contains(edge);
    }


    private void dfs(UndirectedGraph g, int v, int parent) {
        // order[i] 表示顶点 i 访问的顺序，访问顺序靠前的就是后面的祖先
        order[v] = count++;
        // anchor[i] 表示顶点 i 和它的孩子中指向的最早的祖先
        // 这里设置为自己，如果后序没有更新的话，表示没有指向祖先的路径，也就是有桥
        anchor[v] = order[v];

        for (Integer w : g.adj(v)) {
            if (order[w] == -1) {
                dfs(g, w, v);
                // 此时顶点 w dfs过程已经结束

                // 更新顶点 v 的祖先为最早的祖先
                anchor[v] = Math.min(anchor[v], anchor[w]);
                // w 最早祖先等于 w，表示没有指向祖先的路径，此时边 v-w 为桥
                if (anchor[w] == order[w]) {
                    bridges.add(new Edge(v, w, 0));
                }
            }
            // w != parent，表示顶点 v 遇到了它的一个祖先
            else if (w != parent) {
                // 标号越小，表示这个祖先越早
                anchor[v] = Math.min(anchor[v], order[w]);
            }
        }
    }
}
