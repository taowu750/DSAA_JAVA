package util.datastructure.graph;

import java.util.Objects;

public interface IGraphEdge extends IProps {

    /**
     * 表示边是有向还是无向的。
     */
    enum EdgeType {
        /**
         * 有向边
         */
        DIRECTED,
        /**
         * 无向边
         */
        UNDIRECTED
    }

    /**
     * 如果这条边存在于某个图中，返回这个图，否则返回 null。
     *
     * @return 存在图返回这个图；否则返回 null
     */
    default IGraph graph() {
        IGraph graph = null;
        IGraphVertex vertex;
        if ((vertex = from()) != null)
            graph = vertex.graph();
        if (graph == null && (vertex = to()) != null)
            graph = vertex.graph();

        return graph;
    }

    /**
     * 此边在图中的唯一 id。此 id 的取值范围和规则由子类规定。
     *
     * @return 边 id
     */
    int id();

    /**
     * 设置此边的 id。
     *
     * @apiNote 注意，此方法应仅被{@link IGraph}中的方法调用。
     * 通过其他方式调用的后果是未定义的。
     *
     * @param id 边 id
     */
    void unsafeSetId(int id);

    /**
     * 此边的类型，参见{@link EdgeType}。
     *
     * @return 边的类型
     */
    EdgeType type();

    /**
     * 判断此边是不是有向边。
     *
     * @return 是有向边返回 true，否则返回 false
     */
    default boolean isDirected() {
        return type() == EdgeType.DIRECTED;
    }

    /**
     * 如果此边是有向边，返回这条边的起始顶点；如果是无向边，返回其中一个顶点。
     * 没有的话返回 null
     *
     * @return 起始顶点（有向边）或其中一个顶点（无向边）；没有的话返回 null。
     */
    IGraphVertex from();

    /**
     * 如果此边是有向边，返回这条边的目的顶点；如果是无向边，返回另一个顶点。
     * 没有的话返回 null
     *
     * @return 目的顶点（有向边）或另一个顶点（无向边）；没有的话返回 null。
     */
    IGraphVertex to();

    /**
     * 设置此边的起始顶点（对于有向图）。
     *
     * @apiNote 注意，此方法应仅被{@link IGraph}中的方法调用。
     * 通过其他方式调用的后果是未定义的。
     *
     * @param from 起始顶点（对于有向图）
     */
    void unsafeSetFrom(IGraphVertex from);

    /**
     * 设置此边的目的顶点（对于有向图）。
     *
     * @apiNote 注意，此方法应仅被{@link IGraph}中的方法调用。
     * 通过其他方式调用的后果是未定义的。
     *
     * @param to 目的顶点（对于有向图）
     */
    void unsafeSetTo(IGraphVertex to);

    /**
     * 给定这条边的一个顶点，返回另一个顶点。如果 vertex 不是这条边的顶点，抛出{@link IllegalArgumentException}。
     *
     * @param vertex 这条边的一个顶点
     * @return 另一个顶点
     * @throws IllegalArgumentException 如果 vertex 不是这条边的顶点
     */
    default IGraphVertex other(IGraphVertex vertex) {
        Objects.requireNonNull(vertex);
        if (vertex == from()) {
            return to();
        } else if (vertex == to()) {
            return from();
        } else {
            throw new IllegalArgumentException("the parameter is not the vertex of the edge");
        }
    }

    /**
     * 给定这条边的一个顶点，返回另一个顶点。如果 vertex 不是这条边的顶点，抛出{@link IllegalArgumentException}。
     *
     * @param vid 这条边的一个顶点的 id
     * @return 另一个顶点
     * @throws IllegalArgumentException 如果 vertex 不是这条边的顶点
     */
    default IGraphVertex other(int vid) {
        return other(graph().vertex(vid));
    }

    /**
     * 返回指定顶点是否是边的起始顶点（有向图中）。
     *
     * @param vertex 顶点
     * @return 是起始顶点（有向图中）返回 true；否则返回 false。
     */
    default boolean isFrom(IGraphVertex vertex) {
        return vertex != null && vertex == from();
    }

    /**
     * 返回指定顶点是否是边的起始顶点（有向图中）。
     *
     * @param vid 顶点 id
     * @return 是起始顶点（有向图中）返回 true；否则返回 false。
     */
    default boolean isFrom(int vid) {
        return isFrom(graph().vertex(vid));
    }

    /**
     * 返回指定顶点是否是边的目的顶点（有向图中）。
     *
     * @param vertex 顶点
     * @return 是目的顶点（有向图中）返回 true；否则返回 false。
     */
    default boolean isTo(IGraphVertex vertex) {
        return vertex != null && vertex == to();
    }

    /**
     * 返回指定顶点是否是边的目的顶点（有向图中）。
     *
     * @param vid 顶点 id
     * @return 是目的顶点（有向图中）返回 true；否则返回 false。
     */
    default boolean isTo(int vid) {
        return isTo(graph().vertex(vid));
    }

    /**
     * 判断此边是否与 vid 代表的顶点相关联。
     *
     * @param vid 顶点 id
     * @return 关联返回 true；否则返回 false
     */
    default boolean isAttachVertex(int vid) {
        IGraphVertex from = from(), to = to();
        return (from != null && from.id() == vid) || (to != null && to.id() == vid);
    }

    /**
     * 判断此边是否与指定的顶点相关联。
     *
     * @param vertex 顶点
     * @return 关联返回 true；否则返回 false
     */
    default boolean isAttachVertex(IGraphVertex vertex) {
        return vertex != null && (from() == vertex || to() == vertex);
    }

    /**
     * 这条边是不是个自环（即 from 和 to 相等）。
     *
     * @return 如果是自环返回 true；不是或未设置边返回 false
     */
    default boolean isSelfCircle() {
        IGraphVertex from = from(), to = to();
        return (from != null && to != null) && from == to;
    }
}
