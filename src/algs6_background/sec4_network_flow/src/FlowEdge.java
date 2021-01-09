package algs6_background.sec4_network_flow.src;

import util.datastructure.graph.GenericProxyGraphEdge;
import util.datastructure.graph.GraphEdgeImpl;

import java.util.Objects;

/**
 * 流量网络中的边。准确地说是剩余网络中的边。参见 {@link FordFulkerson}。
 *
 * 使用这一条无向边可以表示剩余网络中的两条边。
 */
public class FlowEdge extends GenericProxyGraphEdge<FlowNetwork, FlowVertex> {

    public FlowEdge(FlowVertex from, FlowVertex to, double capacity) {
        super(new GraphEdgeImpl(EdgeType.UNDIRECTED), FlowVertex.class);

        checkedUnsafeSetFrom(from);
        checkedUnsafeSetTo(to);

        // 容量
        putProp("capacity", capacity);
        // 流量
        putProp("flow", 0.);
    }

    /**
     * 边的容量
     *
     * @return
     */
    public double capacity() {
        return getProp("capacity");
    }

    /**
     * 边的流量
     *
     * @return
     */
    public double flow() {
        return getProp("flow");
    }

    /**
     * 确认顶点 vertex 在这条边上的的剩余容量
     *
     * @param vertex 与这条边关联的顶点
     * @return
     */
    public double residualCapacityTo(FlowVertex vertex) {
        Objects.requireNonNull(vertex);
        if (vertex == from())
            return flow();
        else if (vertex == to())
            return capacity() - flow();

        throw new IllegalArgumentException("the vertex must be attached with this edge");
    }

    /**
     * 将顶点 vertex 在这条边上的流量增加 delta
     *
     * @param vertex 与这条边关联的顶点
     * @param delta
     */
    public void addResidualFlowTo(FlowVertex vertex, double delta) {
        if (vertex == from())
            mergeProp("flow", -delta, Double::sum);
        else if (vertex == to())
            mergeProp("flow", delta, Double::sum);
        else
            throw new IllegalArgumentException("the vertex must be attached with this edge");
    }

    public boolean isFeasible() {
        return flow() >= 0 && flow() <= capacity();
    }

    @Override
    public String toString() {
        FlowVertex from = from(), to = to();
        return "FlowEdge(" +
                "from=" + (from != null ? from.id() : "null") +
                ", to=" + (to != null ? to.id() : "null") +
                ", capacity=" + getProp("capacity") +
                ", flow=" + getProp("flow") +
                ')';
    }
}
