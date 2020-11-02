package algs4_graph.sec2_digraph;

import algs4_graph.sec2_digraph.src.Degrees;
import algs4_graph.sec2_digraph.src.Digraph;
import util.datastructure.MyStack;

import java.util.Iterator;

/**
 * <p>
 * 有向欧拉环。欧拉环是一条图中每条边只出现了一次的有向环。找出有向图中的
 * 欧拉环或说明它不存在。
 * </p>
 * <p>
 * 提示：当且仅当有向图 G 是连通的且每个顶点的出度和入度相同时 G 含有
 * 一条欧拉环。
 * </p>
 */
public class E20_Euler {

    public static void main(String[] args) {

    }
}


@SuppressWarnings("unchecked")
class Euler {

    private MyStack<Integer> eulerCycle;


    public Euler(Digraph g) {
        eulerCycle = new MyStack<>();

        if (g.edgeNum() == 0)
            return;
        Degrees degrees = new Degrees(g);
        for (int v = 0; v < g.vertexNum(); v++) {
            if (degrees.inDegree(v) != degrees.outDegree(v))
                return;
        }

        Iterator<Integer>[] adjs = new Iterator[g.vertexNum()];
        for (int v = 0; v < g.vertexNum(); v++) {
            adjs[v] = g.adj(v).iterator();
        }

        int s;
        // 找一个出度大于 0 的顶点作为起点
        for (s = 0; s < g.vertexNum(); s++) {
            if (degrees.outDegree(s) > 0)
                break;
        }

        if (s != g.vertexNum()) {
            MyStack<Integer> stack = new MyStack<>();
            stack.push(s);

            // 类似于dfs
            while (!stack.isEmpty()) {
                int v = stack.pop();
                while (adjs[v].hasNext()) {
                    stack.push(v);
                    // 注意，迭代器 next() 之后不会回去
                    v = adjs[v].next();
                }
                eulerCycle.push(v);
            }
        }
        // 这表明没有遍历全图，说明图不是连通的
        if (eulerCycle.size() != g.edgeNum() + 1)
            eulerCycle.clear();
    }


    public boolean hasEulerCycle() {
        return !eulerCycle.isEmpty();
    }

    public Iterable<Integer> eulerCycle() {
        return eulerCycle;
    }
}
