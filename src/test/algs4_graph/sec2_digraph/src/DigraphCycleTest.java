package test.algs4_graph.sec2_digraph.src;

import algs4_graph.sec2_digraph.src.Digraph;
import algs4_graph.sec2_digraph.src.DigraphCycle;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * DigraphCycle Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>十月 21, 2019</pre>
 */
public class DigraphCycleTest {

    @BeforeEach
    public void before() throws Exception {
    }

    @AfterEach
    public void after() throws Exception {
    }

    @Test
    public void testCycle() throws Exception {
        // 一个有环图，环为 5->4->3->5
        Digraph g = new Digraph(6);
        g.addEdge(0, 1);
        g.addEdge(0, 5);
        g.addEdge(2, 0);
        g.addEdge(2, 3);
        g.addEdge(3, 2);
        g.addEdge(3, 5);
        g.addEdge(4, 2);
        g.addEdge(4, 3);
        g.addEdge(5, 4);

        DigraphCycle cycle = new DigraphCycle(g);
        assertTrue(cycle.hasCycle());
        for (Integer v : cycle.cycle()) {
            System.out.printf("%d ", v);
        }
        System.out.println();
    }
} 
