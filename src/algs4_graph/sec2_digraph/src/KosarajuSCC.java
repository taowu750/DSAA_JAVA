package algs4_graph.sec2_digraph.src;

import algs4_graph.sec1_undirected_graph.src.SimpleGraph;
import util.io.AlgsDataIO;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * Kosaraju 强连通分量算法。
 * </p>
 * <p>
 * 它会完成以下任务：
 * - 在有向图 G 中，使用 DFSOrder 来计算它的反向图的逆后序排列（也就是对反向图拓扑排序）。
 * - 在 G 中进行标准的深度优先搜索，但是要按照刚才得到的顺序访问所有未标记的顶点。
 * - 在构造函数中，所有在同一个递归 dfs() 调用中被访问到的顶点都在同一个
 * 强连通分量中，将它们按照和连通分量相同的方式识别出来。
 * </p>
 * <p>
 * 想要证明这个算法的正确性，我们需要证明下面两个命题的正确性：<br/>
 * - 每个和 s 强连通的顶点 v 都会在构造函数中调用的 dfs(g, s) 中被访问到。这是显而易见的。
 * - 构造函数中调用的 dfs(g, s) 所到达的任意顶点都必然是和 s 强连通的。
 * 设v为 dfs(g, s) 到达的某个顶点。那么 g 中必然存在一条从 s 到 v 的路径，因此
 * 要证明 g 中还存在一条从 v 到 s 的路径。这也等价于在反向 g 中存在一条从
 * s 到 v 的路径，因此只需要证明在反向 g 中存在一条从 s 到 v 的路径即可。
 * 按照反向 g 的逆后序进行的dfs意味着，dfs(g,v) 必然在 dfs(g,s) 之前就结束了（因为反向 g 有 v 到 s 的路径，
 * 它的逆后序 v 会在 s 之前），这样 dfs(g,v) 的调用就只会出现两种情况：
 * - 调用在 dfs(g,s) 的调用之前，并且结束
 * - 调用在 dfs(g,s) 的调用之后，并在 dfs(g,s) 结束前结束
 * 第一种情况是不会出现的，因为在反向 g 中存在一条从 v 到 s 的路径；而第二种
 * 情况则说明反向 g 中存在一条从 s 到 v 的路径。证毕。
 * </p>
 * <p>
 * 更加易懂的解释参见：https://www.zhihu.com/question/58926821/answer/583402591。
 * 该算法旨在让每次递归探索中的所有顶点属于同一强连通分量。所以可以这么理解，当递归进入一个
 * 强连通分量中时，把它锁死在这个强连通分量中。
 * <br>
 * 需要注意，逆后序会把拓扑排序中后面的顶点先放到栈底（1）。
 * <br>
 * 一个图的反向图有着和原图相同的强连通分量划分情况，但反向图的连通分量之间的指向关系和原图相反。
 * 例如两个连通分量s和v，原图中s->v，在反向图中就是v->s（2）。
 * <br>
 * 我们的目的就是先dfs v，然后dfs s，在dfs的过程中做好标记，这样就能区分这两个联通分量。（3）<br>
 * 在反向图的逆后序中，s的结点在栈中的位置肯定会在v的下面（参见（1）、（2））。所以利用这个
 * 顺序，我们就能先访问到v联通分量中的结点，也就是先dfs v，这就满足了（3）。
 * </p>
 */
@SuppressWarnings("unchecked")
public class KosarajuSCC {

    private boolean[] marked;
    private int[] id;
    private int count;


    public KosarajuSCC(Digraph g) {
        marked = new boolean[g.vertexNum()];
        id = new int[g.vertexNum()];
        DFSOrder order = new DFSOrder(g.reverse());

        for (Integer s : order.reversePost()) {
            if (!marked[s]) {
                dfs(g, s);
                count++;
            }
        }
    }


    public static void main(String[] args) {
        Digraph g = new Digraph(AlgsDataIO.openTinyDG());
        KosarajuSCC scc = new KosarajuSCC(g);

        int m = scc.count;
        System.out.println(m + " components");

        List<Integer>[] coms = new List[m];
        for (int i = 0; i < m; i++) {
            coms[i] = new ArrayList<>();
        }

        for (int v = 0; v < g.vertexNum(); v++) {
            coms[scc.id(v)].add(v);
        }
        for (int i = 0; i < m; i++) {
            for (Integer v : coms[i]) {
                System.out.printf(v + " ");
            }
            System.out.println();
        }
    }


    public boolean stronglyConnected(int v, int w) {
        return id[v] == id[w];
    }

    public int id(int v) {
        return id[v];
    }


    private void dfs(SimpleGraph g, int v) {
        marked[v] = true;
        id[v] = count;
        for (Integer w : g.adj(v)) {
            if (!marked[w])
                dfs(g, w);
        }
    }
}
