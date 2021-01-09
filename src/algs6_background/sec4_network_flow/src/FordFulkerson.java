package algs6_background.sec4_network_flow.src;

import util.datastructure.MyQueue;
import util.io.AlgsDataIO;

import java.util.Arrays;
import java.util.Objects;
import java.util.Queue;

/**
 * Ford-Fulkerson 算法，也被称为增广路径算法，是解决最大流量问题的一种有效算法。
 *
 * 考虑一条从起点到终点的有向路径。假设 x 为该路径上所有边中未使用容量的最小值，
 * 那么只需要将所有边的流量增大 x 即可将网络中的总流量至少增大 x。
 * 反复这个过程，就得到一种计算网络中的流量分配方法：找到另一条路径，增大路径中
 * 的流量，如此反复，直到所有从起点到终点的路径上至少有一条边是饱和的。
 * 这种方法在某些情况下可以计算出网络中的最大流量，但在有些情况下不行。
 *
 * 我们把依据变为网络所对应无向图中从起点到终点的路径。在这样的路径中，当沿着
 * 起点向终点前进时，经过某条边时的方向可能和流量的方向相同，那么这条边是正向边；
 * 也可能和流量的方向相反，那这条边是逆向边。
 *
 * 现在，我们可以通过增加非饱和正向边的流量和降低非空逆向边的流量来增加网络的总
 * 流量。流量的增量受路径上所有正向边的未使用容量的最小值和所有逆向边的流量的限制。
 * 这样一条路径被称为增广路径。
 *
 * Ford-Fulkerson 最大流量算法：网络中的初始流量为 0，沿着任意从起点到终点
 * （且不含有饱和的正向边或是空逆向边）的增广路径扩大容量，
 * 直到网络中不存在这样的路径为止。
 *
 * 证明此算法的正确性需要用到 最大流-最小切分 定理。
 *
 *
 *
 * 通用的 Ford-Fulkerson 最大流量算法并没有指定寻找增广路径的方法。为此，
 * 我们需要引入剩余网络。
 *
 * 给定某个 st-流量网络和其 st-流量配置，它的剩余网络顶点和原网络相同。对于
 * 原网络中每条从顶点 v 到 w 的边 e，令 fe 表示它的流量、ce 表示它的容量。
 * 如果 fe 为正，将边 w->v 加入剩余网络且容量为 fe；如果 fe 小于 ce，
 * 将边 v->w 加入剩余网络且容量为 ce-fe。
 *
 * 如果从 v 到 w 的流量为 0，那么剩余网络中就只有容量为 ce 的边 v->w 与之对应；
 * 如果从 v 到 w 的流量饱和，那么剩余网络中就只有容量为 fe 的边 w->v 与之对应；
 * 如果它既不为空，也不饱和，那么剩余网络中就将含有相应容量的 v->w 和 w->v。
 *
 * 剩余网络中，与流量对应的边的方向和流量本身相反。它的正向边表示的是剩余的容量
 * （即选择从这条边通行所能增长的流量）；逆向边表示了实际流量（即选择从这条边通行
 * 将会减少的流量）。
 *
 * 剩余网络使得我们可以使用图搜索算法寻找增广路径，这是因为在剩余网络中所有从起点
 * 到终点的路径都是原流量网络中的一条增广路径。沿着增广路径增大流量可能会修改剩余网络。
 * 例如，至少有一条路径上的边变得饱和或变为空，因此在剩余网络中至少有一条边将会改变
 * 方向或消失。
 *
 * 不过因为我们使用的是抽象的剩余网络，只会检查正的容量，不需要实际的插入或删除边。
 *
 *
 *
 * 对 Ford-Fulkerson 算法的最简单实现就是最短增广路径算法：这里的最短指的是路径长度最小。
 */
public class FordFulkerson {

    public static void main(String[] args) {
        FlowNetwork flowNetwork = new FlowNetwork(AlgsDataIO.openTinyFN());
        System.out.println(flowNetwork + "\n");

        FordFulkerson fordFulkerson = new FordFulkerson(flowNetwork);
        System.out.println("Max flow from " + flowNetwork.src().id() + " to " + flowNetwork.dst().id());
        for (FlowVertex vertex : flowNetwork.checkedVertices()) {
            for (FlowEdge flowEdge : vertex.checkedEdges()) {
                if ((vertex == flowEdge.from()) && flowEdge.flow() > 0)
                    System.out.println("\t" + flowEdge);
            }
        }
        System.out.println("Max flow value = " + fordFulkerson.maxFlow());
    }

    private FlowNetwork network;
    private boolean[] marked;  // 标记路径上已知的顶点
    private FlowEdge[] edgeTo;  // 从 s 到 v 的最短路径上的最后一条边
    private double maxFlow;  // 当前最大流量

    public FordFulkerson(FlowNetwork network) {
        Objects.requireNonNull(network);

        this.network = network;
        marked = new boolean[network.vertexNum()];
        edgeTo = new FlowEdge[network.vertexNum()];

        // 找出从 st-流量网络的最大流量配置
        while (hasAugmentingPath(network.src(), network.dst())) {
            // 计算当前的瓶颈容量
            double bottle = Double.POSITIVE_INFINITY;
            for (FlowVertex v = network.dst(); v != network.src() ; v = edgeTo[v.id()].other(v)) {
                bottle = Math.min(bottle, edgeTo[v.id()].residualCapacityTo(v));
            }
            // 增大流量
            for (FlowVertex v = network.dst(); v != network.src() ; v = edgeTo[v.id()].other(v)) {
                edgeTo[v.id()].addResidualFlowTo(v, bottle);
            }

            maxFlow += bottle;
        }
    }

    public double maxFlow() {
        return maxFlow;
    }

    public boolean inCut(int v) {
        return marked[v];
    }

    /**
     * 通过广度优先搜索查找是否还存在增广路径。
     *
     * @param s 剩余网络起点
     * @param t 剩余网络终点
     * @return
     */
    private boolean hasAugmentingPath(FlowVertex s, FlowVertex t) {
        Arrays.fill(marked, false);
        Arrays.fill(edgeTo, null);

        Queue<FlowVertex> queue = new MyQueue<>();
        marked[s.id()] = true;
        queue.offer(s);
        while (!queue.isEmpty()) {
            FlowVertex v = queue.poll();
            for (FlowEdge e : v.checkedEdges()) {
                FlowVertex w = e.other(v);
                // 剩余网络中，对于任意一条连接到未标记顶点的边，且 w->v 未饱和，则可通过这条边
                if (e.residualCapacityTo(w) > 0 && !marked[w.id()]) {
                    edgeTo[w.id()] = e;
                    marked[w.id()] = true;
                    queue.offer(w);
                }
            }
        }

        return marked[t.id()];
    }
}
