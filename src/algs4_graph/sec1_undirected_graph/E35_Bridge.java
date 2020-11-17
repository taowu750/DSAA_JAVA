package algs4_graph.sec1_undirected_graph;

import algs4_graph.sec1_undirected_graph.src.Bridge;
import algs4_graph.sec1_undirected_graph.src.UndirectedGraph;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>
 * 在一副<strong>连通图</strong>中，如果一条边被删除后图会被分成两个独立的连通分量，
 * 这条边就被称为桥。没有桥的图称为边连通图。
 * </p>
 * <p>
 * 开发一种基于深度优先搜索算法的数据类型，判断一个图是否是边连通图。
 * </p>
 * <p>
 * 桥是这样一种构造：除了桥以外，桥两端的顶点之间没有其他任何路径。也可以说，
 * 桥两端的顶点是无环的。
 * </p>
 */
public class E35_Bridge {

    /*
    构造一个含有桥的连通图
     */
    public static UndirectedGraph newBridgeG() {
        UndirectedGraph g = new UndirectedGraph(15);
        // 6结点和7结点之间有桥
        g.addEdge(0, 1);
        g.addEdge(0, 2);
        g.addEdge(0, 3);
        g.addEdge(1, 2);
        g.addEdge(1, 4);
        g.addEdge(2, 3);
        g.addEdge(2, 4);
        g.addEdge(2, 5);
        g.addEdge(4, 6);
        g.addEdge(5, 6);
        g.addEdge(6, 7);
        g.addEdge(7, 8);
        g.addEdge(7, 9);
        g.addEdge(7, 10);
        g.addEdge(8, 11);
        g.addEdge(9, 10);
        g.addEdge(9, 12);
        g.addEdge(9, 14);
        g.addEdge(10, 14);
        g.addEdge(11, 12);
        g.addEdge(12, 13);
        g.addEdge(13, 14);

        return g;
    }

    /*
    构造一个没有桥的图。
     */
    public static UndirectedGraph newEdgeG() {
        UndirectedGraph g = new UndirectedGraph(15);
        g.addEdge(0, 1);
        g.addEdge(0, 2);
        g.addEdge(0, 3);
        g.addEdge(1, 2);
        g.addEdge(1, 4);
        g.addEdge(2, 3);
        g.addEdge(2, 4);
        g.addEdge(2, 5);
        g.addEdge(4, 6);
        g.addEdge(4, 7);
        g.addEdge(5, 6);
        g.addEdge(6, 7);
        g.addEdge(7, 8);
        g.addEdge(7, 9);
        g.addEdge(7, 10);
        g.addEdge(8, 11);
        g.addEdge(9, 10);
        g.addEdge(9, 12);
        g.addEdge(9, 14);
        g.addEdge(10, 14);
        g.addEdge(11, 12);
        g.addEdge(12, 13);
        g.addEdge(13, 14);

        return g;
    }

    @Test
    public void testBridge() throws Exception {
        Bridge b = new Bridge(newBridgeG());
        assertFalse(b.isEdgeConnected());
        assertEquals(b.bridges(), 1);

        assertTrue(new Bridge(newEdgeG()).isEdgeConnected());
    }
}


