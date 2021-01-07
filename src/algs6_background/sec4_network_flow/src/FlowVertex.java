package algs6_background.sec4_network_flow.src;

import util.datastructure.graph.GenericProxyGraphVertex;
import util.datastructure.graph.GraphVertexImpl;

/**
 * 流量网络中的顶点。
 */
public class FlowVertex extends GenericProxyGraphVertex<FlowNetwork, FlowEdge> {

    public FlowVertex() {
        super(new GraphVertexImpl(), FlowNetwork.class, FlowEdge.class);
    }

    /**
     * 检查顶点是否局部平衡。
     */
    public boolean localEq() {
        double netFlow = 0.;
        for (FlowEdge edge : checkedInEdges()) {
            netFlow += edge.flow();
        }
        for (FlowEdge edge : checkedOutEdges()) {
            netFlow -= edge.flow();
        }

        return Math.abs(netFlow) < 1e-11;
    }

    @Override
    public String toString() {
        return "FlowVertex(id=" + id() + ")";
    }
}
