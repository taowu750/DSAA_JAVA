package algs6_background.sec4_network_flow.src;

import util.algs.In;
import util.datastructure.graph.*;

/**
 * 流量网络。一张流量网络是一张边的权重（这里称为容量）为正的加权有向图。
 * 一个 st-流量网络有两个已知的顶点，即起点 s 和终点 t。
 *
 * 有时我们认为某些边的容量是无限的。我们将流入一个顶点的总流量称为该顶点的流入量；
 * 留出一个顶点的总流量称为该顶点的流出量；而两者之差称为净流量。
 *
 * st-流量配置是由一组和每条边关联的值组成的集合，这个值称为边的流量。
 * 如果所有边的流量均小于边的容量且满足每个顶点的局部平衡（即净流量均为 0，s 和 t 除外），
 * 那么就称这种流量配置是可行的。
 *
 * 最大 st-流量问题：给定一个 st-流量网络，找到一种 st-流量配置，使得从 s 到 t 的流量最大化。
 *
 *
 * 此网络是剩余网络，参见{@link FordFulkerson}。
 */
public class FlowNetwork extends GenericProxyGraph<FlowVertex, FlowEdge> {

    public FlowNetwork(int vertexNum) {
        super(new GraphImpl(GraphType.UNDIRECTED), FlowVertex.class, FlowEdge.class);
        Graphs.addAll(this, vertexNum, i -> new FlowVertex());
    }

    public FlowNetwork(In in) {
        super(new GraphImpl(GraphType.UNDIRECTED), FlowVertex.class, FlowEdge.class);

        int vertexNum = in.readInt(), edgeNum = in.readInt();
        Graphs.addAll(this, vertexNum, i -> new FlowVertex());

        for (int i = 0; i < edgeNum; i++) {
            checkedAddEdge(in.readInt(), in.readInt(), (from, to) -> new FlowEdge(from, to, in.readDouble()));
        }
    }

    /**
     * 流量网络起点
     *
     * @return
     */
    public FlowVertex src() {
        return vertex(0);
    }

    /**
     * 流量网络终点
     *
     * @return
     */
    public FlowVertex dst() {
        return vertex(vertexNum() - 1);
    }

    /**
     * 检查网络流量配置是否可行。
     */
    public boolean isFeasible() {
        // 确认每条边的流量非负且小于等于容量
        for (FlowEdge edge : checkedEdges()) {
            if (!edge.isFeasible())
                return false;
        }

        // 确认每个顶点是否达到局部平衡状态
        for (FlowVertex vertex : checkedVertices()) {
            if (!vertex.localEq())
                return false;
        }

        return true;
    }

    @Override
    public String toString() {
        return Graphs.graphString(this, (g, sb) -> sb.append("FlowNetwork"));
    }
}
