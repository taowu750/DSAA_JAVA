package algs4_graph.sec2_digraph.src;

import algs4_graph.sec1_undirected_graph.src.SimpleGraph;
import util.datastructure.MyQueue;
import util.datastructure.MyStack;

import java.util.Queue;

/**
 * 有向图中基于深度优先搜索的顶点排序。
 */
public class DFSOrder {

    private boolean[] marked;
    /*
    前序遍历：在递归调用之前将顶点加入队列。这就是 dfs 的调用顺序
     */
    private Queue<Integer> pre;
    /*
    后序遍历：在递归调用之后将顶点加入队列。这是顶点遍历完成的顺序
     */
    private Queue<Integer> post;
    /*
    逆后序：在递归调用之后将顶点加入栈。这是拓扑排序将会用到的遍历
     */
    private MyStack<Integer> reversePost;


    public DFSOrder(SimpleGraph g) {
        pre = new MyQueue<>();
        post = new MyQueue<>();
        reversePost = new MyStack<>();
        marked = new boolean[g.vertexNum()];

        for (int v = 0; v < g.vertexNum(); v++) {
            if (!marked[v])
                dfs(g, v);
        }
    }


    public Iterable<Integer> pre() {
        return pre;
    }

    public Iterable<Integer> post() {
        return post;
    }

    public Iterable<Integer> reversePost() {
        return reversePost;
    }


    private void dfs(SimpleGraph g, int v) {
        pre.offer(v);

        marked[v] = true;
        for (Integer w : g.adj(v)) {
            if (!marked[w])
                dfs(g, w);
        }
        post.offer(v);
        reversePost.push(v);
    }
}
