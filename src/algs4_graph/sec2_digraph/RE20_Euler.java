package algs4_graph.sec2_digraph;

import algs4_graph.sec2_digraph.src.DFSOrder;
import algs4_graph.sec2_digraph.src.Degrees;
import algs4_graph.sec2_digraph.src.Digraph;

import java.util.ArrayList;

// TODO: 不行
/**
 * 欧拉环是图中经过每条边一次且仅一次的环。找出有向图中的欧拉环或者说明它不存在。
 */
public class RE20_Euler {

    public static class Euler {

        private Digraph g;
        private Degrees degrees;
        private boolean hasEuler;
        private Iterable<Integer> euler = new ArrayList<>(1);

        public Euler(Digraph g) {
            this.g = g;
            degrees = new Degrees(g);

            hasEuler = true;
            for (int v = 0; v < g.vertexNum(); v++) {
                if (degrees.inDegree(v) != degrees.outDegree(v)) {
                    hasEuler = false;
                    break;
                }
            }
            if (hasEuler)
                euler = new DFSOrder(g).pre();
        }

        public boolean hasEuler() {
            return hasEuler;
        }

        public Iterable<Integer> euler() {
            return euler;
        }
    }
}
