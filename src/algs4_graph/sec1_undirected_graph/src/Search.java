package algs4_graph.sec1_undirected_graph.src;

/**
 * 图搜索算法接口。
 */
public interface Search {

    int source();

    SimpleGraph graph();

    boolean hasPathTo(int v);

    Iterable<Integer> pathTo(int v);

    int distTo(int v);

    int count();


    static void printAllPath(Search search) {
        int s = search.source();
        int vertexNum = search.graph().vertexNum();
        for (int v = 0; v < vertexNum; v++) {
            System.out.print(s + " to " + v + ": ");
            if (search.hasPathTo(v)) {
                for (Integer w : search.pathTo(v)) {
                    if (w == s)
                        System.out.print(w);
                    else
                        System.out.printf("-%d", w);
                }
            }
            System.out.println();
        }
    }
}
