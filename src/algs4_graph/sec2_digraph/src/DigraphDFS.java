package algs4_graph.sec2_digraph.src;

import util.algs.StdIn;
import util.datagen.ArrayConverter;
import util.datastructure.MyLinkedList;
import util.io.AlgsDataIO;

import java.util.Collections;
import java.util.List;

/**
 * 有向图深度优先遍历。它解决的是有向图可达性的问题。
 */
public class DigraphDFS {

    private boolean[] marked;


    public DigraphDFS(Digraph g, int s) {
        marked = new boolean[g.vertexNum()];
        dfs(g, s);
    }

    public DigraphDFS(Digraph g, Iterable<Integer> sources) {
        marked = new boolean[g.vertexNum()];
        for (Integer source : sources) {
            if (!marked[source])
                dfs(g, source);
        }
    }


    public static void main(String[] args) {
        Digraph g = new Digraph(AlgsDataIO.openTinyDG());
        List<Integer> sources = new MyLinkedList<>();

        System.out.println("请输入源点：");
        Collections.addAll(sources, ArrayConverter.to(StdIn.readLineInts()));

        DigraphDFS dfs = new DigraphDFS(g, sources);
        for (int v = 0; v < g.vertexNum(); v++) {
            if (dfs.marked(v))
                System.out.printf("%d ", v);
        }
        System.out.println();
    }


    public boolean marked(int v) {
        return marked[v];
    }


    private void dfs(Digraph g, int v) {
        marked[v] = true;
        for (Integer w : g.adj(v)) {
            if (!marked[w])
                dfs(g, w);
        }
    }
}
