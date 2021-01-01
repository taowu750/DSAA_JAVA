package util.datastructure.graph;

import java.util.Collections;
import java.util.Objects;

/**
 * 图中的顶点。
 */
public interface IGraphVertex extends IProps {

    /**
     * 返回此顶点绑定的图；没有返回 null。
     *
     * @return 顶点绑定的图；没有返回 null
     */
    IGraph graph();

    /**
     * 此顶点在图中的唯一 id。此 id 的取值范围和规则由子类规定。
     *
     * @return 顶点 id
     */
    int id();

    /**
     * 设置此顶点的{@link IGraph}。
     *
     * @apiNote 注意，此方法应仅被{@link IGraph}中的方法调用。
     * 通过其他方式调用的后果是未定义的。
     *
     * @param graph 图
     */
    void unsafeSetGraph(IGraph graph);

    /**
     * 设置此顶点的 id。
     *
     * @apiNote 注意，此方法应仅被{@link IGraph}中的方法调用。
     * 通过其他方式调用的后果是未定义的。
     *
     * @param id 顶点 id
     */
    void unsafeSetId(int id);

    /**
     * 返回此顶点是否与 eid 代表的边相关联。
     *
     * @param eid 边 id
     * @return 相关联返回 true；否则返回 false
     */
    default boolean isAttachEdge(int eid) {
        IGraph graph = graph();
        return graph != null && graph.vIsAttachEdge(id(), eid);
    }

    /**
     * 返回此顶点是否与指定边相关联。
     *
     * @param edge 边
     * @return 相关联返回 true；否则返回 false
     */
    default boolean isAttachEdge(IGraphEdge edge) {
        Objects.requireNonNull(edge);

        return graph() == edge.graph() && isAttachEdge(edge.id());
    }

    /**
     * 从此顶点指出的边。边的顺序由 order 指定。无向边既是出边也是入边。
     *
     * @param order 边的迭代顺序，参见{@link IGraph#ITER_ASC_BY_ID}等常量
     * @return 顶点指出的边和无向边的迭代器
     */
    default Iterable<IGraphEdge> outEdges(int order) {
        IGraph graph = graph();
        return graph != null ? graph.vOutEdges(id(), order) : Collections.emptyList();
    }

    /**
     * 参见{@link #outEdges(int)}，此方法以{@link IGraph#ITER_DEFAULT}迭代顺序返回。
     *
     * @return 顶点指出的边和无向边的迭代器
     */
    default Iterable<IGraphEdge> outEdges() {
        return outEdges(IGraph.ITER_DEFAULT);
    }

    /**
     * 指向此顶点的边。边的顺序由 order 指定。无向边既是出边也是入边。
     *
     * @param order 边的迭代顺序，参见{@link IGraph#ITER_ASC_BY_ID}等常量
     * @return 指向此顶点的边和无向边的迭代器
     */
    default Iterable<IGraphEdge> inEdges(int order) {
        IGraph graph = graph();
        return graph != null ? graph.vInEdges(id(), order) : Collections.emptyList();
    }

    /**
     * 参见{@link #inEdges(int)}，此方法以{@link IGraph#ITER_DEFAULT}迭代顺序返回。
     *
     * @return 指向此顶点的边和无向边的迭代器
     */
    default Iterable<IGraphEdge> inEdges() {
        return inEdges(IGraph.ITER_DEFAULT);
    }

    /**
     * 此顶点的所有边。边的顺序由 order 指定。注意每条无向边只能被返回一次。
     *
     * @param order 边的迭代顺序，参见{@link IGraph#ITER_ASC_BY_ID}等常量
     * @return 此顶点的所有边的迭代器
     */
    default Iterable<IGraphEdge> edges(int order) {
        IGraph graph = graph();
        return graph != null ? graph.vEdges(id(), order) : Collections.emptyList();
    }

    /**
     * 参见{@link #edges(int)}，此方法以{@link IGraph#ITER_DEFAULT}迭代顺序返回。
     *
     * @return 此顶点的所有边的迭代器
     */
    default Iterable<IGraphEdge> edges() {
        return edges(IGraph.ITER_DEFAULT);
    }

    /**
     * 此顶点的出度（从此顶点指出边的数量）。无向边既是出边也是入边。
     *
     * @return 出度（包含无向边）
     */
    default int outDegree() {
        IGraph graph = graph();
        return graph != null ? graph.vOutDegree(id()) : 0;
    }

    /**
     * 此顶点的入度（指向此顶点边的数量）。无向边既是出边也是入边。
     *
     * @return 入度（包含无向边）
     */
    default int inDegree() {
        IGraph graph = graph();
        return graph != null ? graph.vInDegree(id()) : 0;
    }

    /**
     * 返回此顶点所有边的数量。注意每条无向边只能记一次数量。
     *
     * @return 此顶点所有边的数量
     */
    default int edgeNum() {
        IGraph graph = graph();
        return graph != null ? graph.vEdgeNum(id()) : 0;
    }

    /**
     * 此顶点有没有出边。无向边既是出边也是入边。
     *
     * @return 有出边返回 true；否则返回 false
     */
    default boolean hasOutEdge() {
        return outDegree() != 0;
    }

    /**
     * 此顶点有没有入边。无向边既是出边也是入边。
     *
     * @return 有入边返回 true；否则返回 false
     */
    default boolean hasInEdge() {
        return inDegree() != 0;
    }

    /**
     * 此顶点有没有边。无向边既是出边也是入边。
     *
     * @return 有边返回 true；否则返回 false
     */
    default boolean hasEdge() {
        return edgeNum() != 0;
    }
}
