package algs6_background.sec4_network_flow.src;

import util.datastructure.graph.GraphVertexImpl;
import util.datastructure.graph.IGraphEdge;

/**
 * 流量网络中的顶点。
 */
public class FlowVertex extends GraphVertexImpl {

    /**
     * 检查顶点是否局部平衡。
     */
    public boolean localEq() {
        double netFlow = 0.;
        for (IGraphEdge edge : inEdges()) {
            FlowEdge flowEdge = (FlowEdge) edge;
            netFlow += flowEdge.flow();
        }
        for (IGraphEdge edge : outEdges()) {
            FlowEdge flowEdge = (FlowEdge) edge;
            netFlow -= flowEdge.flow();
        }

        return Math.abs(netFlow) < 1e-11;
    }

    @Override
    public String toString() {
        return "FlowVertex(id=" + id + ")";
    }
}
