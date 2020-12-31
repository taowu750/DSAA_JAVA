package util.datastructure.graph;

/**
 * 图中的顶点。
 */
public interface IGraphVertex extends IProps {

    /**
     * 此顶点在图中的唯一 id，需要大于等于 0。为负值表示它没有绑定到一张图上。
     *
     * @return 顶点 id
     */
    int id();

    /**
     * 返回此顶点绑定的图；没有返回 null。
     *
     * @return 顶点绑定的图；没有返回 null
     */
    IGraph graph();

    /**
     * 从此顶点指出的边。顺序由子类决定。
     *
     * @return 顶点指出的边的迭代器
     */
    Iterable<IGraphEdge> outEdges();

    /**
     * 指向此顶点的边。顺序由子类决定。
     *
     * @return 指向此顶点的边的迭代器
     */
    Iterable<IGraphEdge> inEdges();

    /**
     * 此顶点的所有边。顺序由子类决定。
     *
     * @return 此顶点的所有边的迭代器
     */
    Iterable<IGraphEdge> edges();

    /**
     * 此顶点的出度（从此顶点指出边的数量）
     *
     * @return 出度
     */
    int outDegree();

    /**
     * 此顶点的入度（指向此顶点边的数量）
     *
     * @return 入度
     */
    int inDegree();

    /**
     * 返回此顶点所有边的数量。
     *
     * @return 此顶点所有边的数量
     */
    int edgeNum();
}
