package algs6_background.sec4_network_flow.src;

import util.datastructure.graph.GraphEdgeImpl;

/**
 * 流量网络中的边。
 */
public class FlowEdge extends GraphEdgeImpl {

    public FlowEdge(double capacity) {
        super(EdgeType.DIRECTED);
        // 容量
        putProp("capacity", capacity);
        // 流量
        putProp("flow", 0);
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
     * 边剩余容量。
     *
     * @return
     */
    public double residualCapacity() {
        return (double) getProp("capacity") - (double) getProp("flow");
    }

    /**
     * 判断边的流量非负且小于等于容量
     *
     * @return
     */
    public boolean isFeasible() {
        return (double) getProp("flow") >= 0 && residualCapacity() >= 0;
    }

    /**
     * 将边的流量增加 delta。返回增加后的流量。
     *
     * @param delta
     * @return
     */
    public double addFlow(double delta) {
       return mergeProp("flow", delta, Double::sum);
    }

    @Override
    public String toString() {
        return "FlowEdge(" +
                "from=" + (from != null ? from.id() : "null") +
                ", to=" + (to != null ? to.id() : "null") +
                ", capacity=" + getProp("capacity") +
                ", flow=" + getProp("flow") +
                ')';
    }
}
