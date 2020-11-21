package algs4_graph.sec2_digraph;

import algs4_graph.sec2_digraph.src.Digraph;
import algs4_graph.sec2_digraph.src.DigraphCycle;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.stream.IntStream;

/**
 * 基于队列的拓扑排序
 */
public class RE30_QueueTop {

    public static Iterable<Integer> queueBasedTopic(Digraph g) {
        assert !new DigraphCycle(g).hasCycle(): "必须是无环图";

        int[] inDegrees = new int[g.vertexNum()];
        for (int v = 0; v < g.vertexNum(); v++) {
            for (int w : g.adj(v)) {
                inDegrees[w]++;
            }
        }

        List<Integer> topic = new ArrayList<>(g.vertexNum());
        // 入度小的优先级高
        PriorityQueue<Integer> pq = new PriorityQueue<>(g.vertexNum(),
                (v, w) -> inDegrees[w] - inDegrees[v]);
        // 将入度为0的顶点加入到优先队列中
        IntStream.range(0, g.vertexNum()).filter(i -> inDegrees[i] == 0).forEach(pq::offer);
        while (!pq.isEmpty()) {
            int v = pq.poll();
            if (inDegrees[v] != 0)
                return null;
            topic.add(v);
            for (int w : g.adj(v)) {
                inDegrees[w]--;
                pq.offer(w);
            }
        }

        return topic;
    }
}
