package algs4_graph.sec2_digraph.src;

import algs4_graph.sec1_undirected_graph.src.SimpleGraph;
import algs4_graph.sec1_undirected_graph.src.SymbolGraph;
import util.io.AlgsDataIO;

/**
 * <p>
 * 拓扑排序。顶点表示任务，有向边表示优先级顺序。拓扑排序就是对所有的
 * 顶点排序，使得所有的有向边均从排在前面的元素指向排在后面的元素。
 * </p>
 * <p>
 * 如果图中有环，则无法进行排序。
 * </p>
 * <p>
 * 拓扑排序就相当于对图进行逆后序遍历。对于任意边 v->w，在调用 dfs(v)
 * 时，下面的三种情况必有其一成立：
 * - dfs(w) 已经被调用过且已经返回了（w已被标记）
 * - dfs(w) 还没有被调用（w还未被标记），因此 v->w 会直接或间接调用并返回dfs(w)，
 * 且 dfs(w) 会在 dfs(v) 返回前返回。
 * - dfs(w) 已经被调用但还未返回。这是由于递归调用链存在从w到v的路径，此时也就有环
 * 的存在，这种情况在拓扑排序中不会出现。
 * <br>
 * 在两种可能的情况中，dfs(w) 都会在 dfs(v) 之前完成，因此在后序排列中 w 排在 v
 * 之前而在逆后序中 w 排在 v 之后，因此任意一条边 v->w 都如我们所愿地从排名
 * 较前顶点指向排名较后的顶点。
 * <br>
 * 之所以用逆后序而不用前序，这是因为逆后序会等 dfs(v) 的所有后序节点 w 遍历完后
 * 才会继续。而不用BFS是因为需要用DFS搜索路径。
 * </p>
 * <p>
 *     严谨的证明：
 *     按拓扑顺序记录DAG的节点，任一个节点必须在它的所有父节点都被记录后才可以被记录。
 *     后序遍历任何一个子节点的位置皆先于其所有父节点，取后序遍历的逆序必有任何一个子节点
 *     必位于其所有父节点之后，即符合拓扑顺序的定义。
 *     反之，先序遍历任何一个节点都跟随其第一个父节点被记录，即任一节点仅需第一个父节点被记录后，
 *     就必然先于其第二个父节点被记录，从而不能导出其“晚于所有父节点被记录”的拓扑顺序，即充分性不成立。
 *     同样可以证明，任一拓扑顺序要求先（所有）父后子，当且仅当每子仅一父时先序遍历才满足
 *     （此时DAG成为一棵树），即必要性不成立。
 * </p>
 */
public class Topological {

    private Iterable<Integer> order;


    public Topological(SimpleGraph g) {
        DigraphCycle cycleFinder = new DigraphCycle(g);
        if (!cycleFinder.hasCycle()) {
            DFSOrder dfs = new DFSOrder(g);
            order = dfs.reversePost();
        }
    }


    public static void main(String[] args) {
        SymbolGraph sg = new SymbolGraph(AlgsDataIO.fileJobs(), "/", Digraph.class);
        Topological top = new Topological(sg.g());

        for (Integer v : top.order()) {
            System.out.println(sg.name(v));
        }
    }


    public Iterable<Integer> order() {
        return order;
    }

    public boolean isDAG() {
        return order != null;
    }
}
