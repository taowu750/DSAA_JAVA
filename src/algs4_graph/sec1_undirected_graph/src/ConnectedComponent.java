package algs4_graph.sec1_undirected_graph.src;

import util.datastructure.MyArrayList;
import util.io.AlgsDataIO;

import java.util.List;

/**
 * 连通分量。
 */
@SuppressWarnings("unchecked")
public class ConnectedComponent {

    private boolean[] marked;
    private int[] id;
    private int count;


    public ConnectedComponent(UndirectedGraph g) {
        marked = new boolean[g.vertexNum()];
        id = new int[g.vertexNum()];

        for (int v = 0; v < g.vertexNum(); v++) {
            if (!marked[v]) {
                dfs(g, v);
                count++;
            }
        }
    }


    public static void main(String[] args) {
        UndirectedGraph g = new UndirectedGraph(AlgsDataIO.openTinyG());
        ConnectedComponent cc = new ConnectedComponent(g);

        int M = cc.count;
        System.out.println(M + " components");

        List<Integer>[] coms = new List[M];
        for (int i = 0; i < M; i++) {
            coms[i] = new MyArrayList<>();
        }

        for (int v = 0; v < g.vertexNum(); v++) {
            coms[cc.id(v)].add(v);
        }

        for (int i = 0; i < M; i++) {
            for (Integer v : coms[i]) {
                System.out.printf(v + " ");
            }
            System.out.println();
        }
    }


    public boolean connected(int v, int w) {
        return id[v] == id[w];
    }

    public int id(int v) {
        return id[v];
    }

    public int count() {
        return count;
    }


    private void dfs(UndirectedGraph g, int v) {
        marked[v] = true;
        id[v] = count;
        for (Integer w : g.adj(v)) {
            if (!marked[w])
                dfs(g, w);
        }
    }
}
