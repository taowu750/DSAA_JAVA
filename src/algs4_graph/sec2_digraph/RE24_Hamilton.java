package algs4_graph.sec2_digraph;

import algs4_graph.sec2_digraph.src.Digraph;
import algs4_graph.sec2_digraph.src.Topological;
import org.junit.jupiter.api.Test;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * 设计一个线性时间的算法寻找有向无环图中的汉密尔顿路径，即一条正好每个顶点访问一次的路径。
 * 汉密尔顿路径可以判断图中是否有唯一的拓扑排序
 */
public class RE24_Hamilton {

    public static Iterable<Integer> hamiltonPath(Digraph g) {
        Topological topic = new Topological(g);

        Iterator<Integer> iter = topic.order().iterator();
        if (!iter.hasNext())
            return null;

        int v = iter.next();
        while (iter.hasNext()) {
            int next = iter.next();
            boolean adjToNext = false;
            for (int w: g.adj(v)) {
                if (w == next) {
                    adjToNext = true;
                    break;
                }
            }
            if (!adjToNext)
                return null;
            v = next;
        }

        return topic.order();
    }

    @Test
    public void test_hamiltonPath() {
        Digraph digraph1 = new Digraph(5);
        digraph1.addEdge(0, 1);
        digraph1.addEdge(0, 2);
        digraph1.addEdge(1, 2);
        digraph1.addEdge(2, 3);
        digraph1.addEdge(3, 4);
        assertNotNull(hamiltonPath(digraph1));

        Digraph digraph2 = new Digraph(6);
        digraph2.addEdge(0, 1);
        digraph2.addEdge(1, 2);
        digraph2.addEdge(3, 4);
        digraph2.addEdge(4, 5);
        assertNull(hamiltonPath(digraph2));

        Digraph digraph3 = new Digraph(9);
        digraph3.addEdge(0, 1);
        digraph3.addEdge(1, 2);
        digraph3.addEdge(1, 3);
        digraph3.addEdge(4, 5);
        digraph3.addEdge(5, 6);
        digraph3.addEdge(6, 8);
        digraph3.addEdge(6, 7);
        digraph3.addEdge(7, 2);
        digraph3.addEdge(8, 3);
        assertNull(hamiltonPath(digraph3));

        Digraph digraph4 = new Digraph(5);
        digraph4.addEdge(0, 2);
        digraph4.addEdge(1, 2);
        digraph4.addEdge(1, 3);
        digraph4.addEdge(2, 4);
        digraph4.addEdge(3, 4);
        assertNull(hamiltonPath(digraph4));
    }
}
