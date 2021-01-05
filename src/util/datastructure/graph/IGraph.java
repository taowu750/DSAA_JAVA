package util.datastructure.graph;

import java.util.List;
import java.util.Objects;
import java.util.function.BiFunction;

// TODO: 增加 Stream 支持
/**
 * 图接口。
 *
 * 图是一个组顶点和至少能够将两个顶点相连的边组成的。一张图至少需要有两个顶点和连接它们的一条边。
 * 图中的每条边都必须连接着两个顶点，不允许图中的边只与一个顶点关联或没有顶点与之关联。
 *
 * {@link IGraphEdge}既可以是无向边也可以是有向边。子类应该在文档中说明它可以包含哪些边。
 * 只能包含无向边就是无向图；只能包含有向边就是有向图；否则是混合图。参见{@link GraphType}。
 */
public interface IGraph extends IProps {

    /**
     * 表示图的类型
     */
    public enum GraphType {
        /**
         * 有向图，只包含有向边
         */
        DIRECTED,
        /**
         * 无向图，只包含无向边
         */
        UNDIRECTED,
        /**
         * 混合图，既包含有向边，又包含无向边
         */
        MIXED
    }

    /*
     * 下面是一组表示迭代顺序的常量。子类也可以定义自己的迭代顺序，需要在文档中说明。
     * 子类定义的迭代顺序常量最好也是二进制位的形式。
     */
    /**
     * 表示默认迭代顺序，可能是随机或其他方式。这种方式应该是最快的迭代顺序。
     * 具体由子类实现，子类应在文档中说明此顺序。
     */
    public static final int ITER_DEFAULT = 1;
    /**
     * 表示按 id 从小到大的迭代顺序。
     */
    public static final int ITER_ASC_BY_ID = 1 << 1;
    /**
     * 表示按 id 从大到小的迭代顺序。
     */
    public static final int ITER_DESC_BY_ID = 1 << 2;
    /**
     * 表示随机顺序，每次迭代的随机顺序应不同。
     *
     * 注意，这种顺序是可选的。子类实现不了可以抛出{@link IllegalArgumentException}。
     */
    public static final int ITER_RANDOM = 1 << 3;

    /**
     * 返回此图的类型，类型参见{@link GraphType}。
     * 此方法遍历图的所有边判断图的类型。子类应该覆盖它以实现更好的性能。
     *
     * @return 此图的类型
     */
    default GraphType type() {
        GraphType graphType = GraphType.MIXED;
        LABEL_ITER_EDGE:
        for (IGraphEdge edge : edges()) {
            switch (edge.type()) {
                case DIRECTED:
                    if (graphType == GraphType.MIXED) {
                        graphType = GraphType.DIRECTED;
                    } else if (graphType == GraphType.UNDIRECTED) {
                        graphType = GraphType.MIXED;
                        break LABEL_ITER_EDGE;
                    }
                    break;

                case UNDIRECTED:
                    if (graphType == GraphType.MIXED) {
                        graphType = GraphType.UNDIRECTED;
                    } else if (graphType == GraphType.DIRECTED) {
                        graphType = GraphType.MIXED;
                        break LABEL_ITER_EDGE;
                    }
                    break;
            }
        }

        return graphType;
    }

    /**
     * 判断此图是否是无向图。
     *
     * @return 是无向图返回 true；否则返回 false
     */
    default boolean isDirected() {
        return type() == GraphType.DIRECTED;
    }

    /**
     * 判断此图是否是有向图。
     *
     * @return 是有向图返回 true；否则返回 false
     */
    default boolean isUndirected() {
        return type() == GraphType.UNDIRECTED;
    }

    /**
     * 判断此图是否是混合图。
     *
     * @return 是混合图返回 true；否则返回 false
     */
    default boolean isMixed() {
        return type() == GraphType.MIXED;
    }

    /**
     * 返回图中顶点的数量。
     *
     * @return 图中顶点的数量
     */
    int vertexNum();

    /**
     * 返回图中边的数量。
     *
     * @return 图中边的数量
     */
    int edgeNum();

    /**
     * 添加一个顶点，并返回这个顶点 id。
     *
     * 对顶点的处理策略如下：
     *  - 顶点不存在图中：无视顶点是否有 id，为它分配一个 id，然后添加到图中
     *  - 顶点存在于此图中：直接返回它的 id
     *
     * 需要注意，如果顶点存在于另一个图中，可以有以下几种策略：
     *  - 复制这个顶点
     *  - 将它从原来的图中删除
     *  - 抛出异常
     *  - 返回异常值
     * 子类需要实现上面的策略之一（或者根据不同情况进行组合），且需要在文档中说明。
     *
     * @param vertex 顶点
     * @return 添加的顶点的 id
     */
    int addVertex(IGraphVertex vertex);

    /**
     * 将顶点插入图中，并指定顶点 id。
     *
     * 对顶点的处理策略如下：
     *  - 顶点 id 已被分配：直接返回 false
     *  - 顶点不存在图中：将其 id 设为 vid，然后添加到图中
     *  - 顶点存在于此图中：直接返回 false
     *
     * 需要注意，如果顶点存在于另一个图中，可以有以下几种策略：
     *  - 复制这个顶点
     *  - 将它从原来的图中删除
     *  - 抛出异常
     *  - 返回异常值
     * 子类需要实现上面的策略之一（或者根据不同情况进行组合），且需要在文档中说明。
     *
     * @param vid 指定的顶点 id
     * @param vertex 顶点
     * @return 插入成功返回 true，否则返回 false
     */
    boolean insertVertex(int vid, IGraphVertex vertex);

    public static final class RemovedVertexWithEdge {
        public final IGraphVertex vertex;
        public final List<IGraphEdge> edges;

        public RemovedVertexWithEdge(IGraphVertex vertex, List<IGraphEdge> edges) {
            this.vertex = vertex;
            this.edges = edges;
        }
    }

    /**
     * 根据顶点 id 删除一个顶点并返回它和与它相关联的边。顶点 id 不存在返回 null。
     *
     * 被删除的顶点的{@link IGraphVertex#graph()}方法应该返回 null。
     * 它的边的{@link IGraphEdge#graph()}、{@link IGraphEdge#from()}和{@link IGraphEdge#to()}方法也应该返回 null。
     *
     * @param vid 顶点 id
     * @return 返回删除的顶点和它相关联的边；不存在顶点返回 null
     */
    RemovedVertexWithEdge removeVertex(int vid);

    /**
     * 根据顶点 id 返回对应的顶点。顶点 id 不存在返回 null。
     *
     * @param vid 顶点 id
     * @return 返回对应的顶点；不存在返回 null
     */
    IGraphVertex vertex(int vid);

    /**
     * 判断顶点 id 处是否已分配顶点。
     *
     * @param vid 顶点 id
     * @return 顶点 id 已分配顶点则返回 true，否则返回 false
     */
    default boolean containsVertex(int vid) {
        return vertex(vid) != null;
    }

    /**
     * 判断图中是否存在给定顶点。
     *
     * @param vertex 顶点
     * @return 存在返回 true；否则返回 false
     */
    default boolean containsVertex(IGraphVertex vertex) {
        Objects.requireNonNull(vertex);
        return vertex(vertex.id()) == vertex;
    }

    /**
     * vid 表示的顶点是否和 eid 表示的边相关联。
     *
     * @param vid 顶点 id
     * @param eid 边 id
     * @return 相关联返回 true；否则返回 false
     */
    boolean vIsAttachEdge(int vid, int eid);

    /**
     * 返回边是否是指定顶点的出边（无向边既是出边又是入边）
     *
     * @param vid 顶点 id
     * @param eid 边 id
     * @return 是的话返回 true；否则返回 false
     */
    boolean vIsOutEdge(int vid, int eid);

    /**
     * 返回边是否是指定顶点的入边（无向边既是出边又是入边）
     *
     * @param vid 顶点 id
     * @param eid 边 id
     * @return 是的话返回 true；否则返回 false
     */
    boolean vIsInEdge(int vid, int eid);

    /**
     * 从 id 代表的顶点指出的边。边的顺序由 order 指定。无向边既是出边也是入边。
     *
     * @param vid 顶点 id
     * @param order 返回的结点的迭代顺序
     * @return 从 vid 代表的顶点指出的边、无向边的迭代器
     */
    Iterable<IGraphEdge> vOutEdges(int vid, int order);

    /**
     * 参见{@link #vOutEdges(int, int)}，此方法以{@link #ITER_DEFAULT}迭代顺序返回。
     *
     * @param vid 顶点 id
     * @return 从 vid 代表的顶点指出的边、无向边的迭代器
     */
    default Iterable<IGraphEdge> vOutEdges(int vid) {
        return vOutEdges(vid, ITER_DEFAULT);
    }

    /**
     * 指向 vid 代表的顶点的边。边的顺序由 order 指定。无向边既是出边也是入边。
     *
     * @param vid 顶点 id
     * @param order 返回的结点的迭代顺序
     * @return 指向 vid 代表的顶点的边、无向边的迭代器
     */
    Iterable<IGraphEdge> vInEdges(int vid, int order);

    /**
     * 参见{@link #vInEdges(int, int)}，此方法以{@link #ITER_DEFAULT}迭代顺序返回。
     *
     * @param vid 顶点 id
     * @return 指向 vid 代表的顶点的边、无向边的迭代器
     */
    default Iterable<IGraphEdge> vInEdges(int vid) {
        return vInEdges(vid, ITER_DEFAULT);
    }

    /**
     * vid 代表的顶点的所有边。边的顺序由 order 指定。注意每条无向边只能被返回一次。
     *
     * @param vid 顶点 id
     * @param order 返回的结点的迭代顺序
     * @return vid 代表的顶点的所有边的迭代器
     */
    Iterable<IGraphEdge> vEdges(int vid, int order);

    /**
     * 参见{@link #vEdges(int, int)}，此方法以{@link #ITER_DEFAULT}迭代顺序返回。
     *
     * @param vid 顶点 id
     * @return vid 代表的顶点的所有边的迭代器
     */
    default Iterable<IGraphEdge> vEdges(int vid) {
        return vEdges(vid, ITER_DEFAULT);
    }

    /**
     * vid 代表的顶点的出度（从此顶点指出边的数量）。无向边既是出边也是入边。
     *
     * @param vid 顶点 id
     * @return 出度（包含无向边）
     */
    int vOutDegree(int vid);

    /**
     * vid 代表的顶点的入度（指向此顶点边的数量）。无向边既是出边也是入边。
     *
     * @param vid 顶点 id
     * @return 入度（包含无向边）
     */
    int vInDegree(int vid);

    /**
     * 返回 vid 代表的顶点所有边的数量。注意每条无向边只能记一次数量。
     *
     * @param vid 顶点 id
     * @return vid 代表的顶点所有边的数量
     */
    int vEdgeNum(int vid);

    /**
     * vid 代表的顶点有没有出边。无向边既是出边也是入边。
     *
     * @param vid 顶点 id
     * @return 有出边返回 true；否则返回 false
     */
    default boolean vHasOutEdge(int vid) {
        return vOutDegree(vid) != 0;
    }

    /**
     * vid 代表的顶点有没有入边。无向边既是出边也是入边。
     *
     * @param vid 顶点 id
     * @return 有入边返回 true；否则返回 false
     */
    default boolean vHasInEdge(int vid) {
        return vInDegree(vid) != 0;
    }

    /**
     * vid 代表的顶点有没有边。
     *
     * @param vid 顶点 id
     * @return 有边返回 true；否则返回 false
     */
    default boolean vHashEdge(int vid) {
        return vEdgeNum(vid) != 0;
    }

    /**
     * 添加一条边
     *  - 如果这条边的两个顶点都存在于此图中，则添加这条边
     *  - 如果这条边的某个顶点没有和图绑定，则将顶点插入图中，然后再添加边
     *  - 如果边的某个顶点为 null，则添加失败（抛出异常或返回特殊值由子类决定）
     *
     * 如果这条边的某个顶点存在于另一张图中，可以有以下几种策略：
     *  - 复制顶点和边
     *  - 将顶点和边从原来的图中删除
     *  - 抛出异常
     *  - 返回 false
     * 子类需要实现上面的策略之一（或者根据不同情况进行组合），且需要在文档中说明。
     *
     * 边可能是无向边或有向边，子类对它们的处理策略也需要在文档中说明。
     *
     * 需要注意，子类需要处理自环和平行边的情况。是以某种策略处理还是不允许（抛出异常），需要在文档中说明。
     *
     * @param edge 边
     * @return 边的 id
     */
    int addEdge(IGraphEdge edge);

    /**
     * 添加一条边。顶点是顶点 vid 为 from 和 to 的顶点。如果 from 或 to 不存在，则抛出异常
     * （子类可以覆盖这一行为返回异常值）。
     *
     * 边可能是无向边或有向边，子类对它们的处理策略也需要在文档中说明。
     *
     * 需要注意，子类需要处理自环和平行边的情况。是以某种策略处理还是不允许（抛出异常），需要在文档中说明。
     *
     * @param from 起始顶点 vid（对于有向图）
     * @param to 目的顶点 vid（对于有向图）
     * @param edgeSupplier 根据起始顶点和目的顶点返回{@link IGraphEdge}对象
     * @return 返回边的 id
     */
    default int addEdge(int from, int to, BiFunction<IGraphVertex, IGraphVertex, IGraphEdge> edgeSupplier) {
        if (containsVertex(from) && containsVertex(to)) {
            return addEdge(edgeSupplier.apply(vertex(from), vertex(to)));
        }

        throw new IllegalArgumentException("the vertex does not exist");
    }

    /**
     * 删除从 from 到 to 的所有边并返回。边的顺序由 order 指定。
     *
     * 如果 from 到 to 只有一条边或子类不允许平行边，则返回的列表中只会包含一条边。
     * 如果 from 到 to 没有边，返回空的列表。
     *
     * 无向边 from 和 to 颠倒也会被此方法返回。
     *
     * 删除的边的{@link IGraphEdge#graph()}方法应该返回 null。
     *
     * @param from 起始顶点 vid（对于有向图）
     * @param to 目的顶点 vid（对于有向图）
     * @param order 返回的边的迭代顺序
     * @return from 到 to 的所有边。
     */
    List<IGraphEdge> removeEdges(int from, int to, int order);

    /**
     * 参见{@link #removeEdges(int, int, int)}，此方法以{@link #ITER_DEFAULT}迭代顺序返回。
     *
     * @param from 起始顶点 id（对于有向图）
     * @param to 目的顶点 id（对于有向图）
     * @return from 到 to 的所有边。
     */
    default List<IGraphEdge> removeEdges(int from, int to) {
        return removeEdges(from, to, ITER_DEFAULT);
    }

    /**
     * 删除从 from 到 to 的一条边并返回。
     *
     * 如果 from 到 to 有多条边，则具体行为由子类决定。
     * 如果 from 到 to 没有边，返回 null。
     *
     * 无向边 from 和 to 颠倒也会被此方法返回。
     *
     * 删除的边的{@link IGraphEdge#graph()}方法应该返回 null。
     *
     * @param from 起始顶点 id（对于有向图）
     * @param to 目的顶点 id（对于有向图）
     * @return from 到 to 的一条边。
     */
    IGraphEdge removeEdge(int from, int to);

    /**
     * 返回从 from 到 to 的所有边。边的顺序由 order 指定。
     *
     * 如果 from 到 to 只有一条边或子类不允许平行边，则返回的列表中只会包含一条边。
     * 如果 from 到 to 没有边，返回空的列表。
     *
     * 无向边 from 和 to 颠倒也会被此方法返回。
     *
     * @param from 起始顶点 vid（对于有向图）
     * @param to 目的顶点 vid（对于有向图）
     * @param order 返回的边的迭代顺序
     * @return from 到 to 的所有边。
     */
    List<IGraphEdge> edges(int from, int to, int order);

    /**
     * 参见{@link #edges(int, int, int)}，此方法以{@link #ITER_DEFAULT}迭代顺序返回。
     *
     * @param from 起始顶点 vid（对于有向图）
     * @param to 目的顶点 vid（对于有向图）
     * @return from 到 to 的所有边。
     */
    default List<IGraphEdge> edges(int from, int to) {
        return removeEdges(from, to, ITER_DEFAULT);
    }

    /**
     * 返回从 from 到 to 的一条边。
     *
     * 如果 from 到 to 有多条边，则具体行为由子类决定。
     * 如果 from 到 to 没有边，返回 null。
     *
     * 无向边 from 和 to 颠倒也会被此方法返回。
     *
     * @param from 起始顶点 id（对于有向图）
     * @param to 目的顶点 id（对于有向图）
     * @return from 到 to 的一条边。
     */
    IGraphEdge edge(int from, int to);

    /**
     * 根据边 eid 返回对应的边。不存在返回 null。
     *
     * @param eid 边 id
     * @return 对应的边；不存在返回 null
     */
    IGraphEdge edge(int eid);

    /**
     * 返回 from 和 to 顶点直接是否存在边。
     *
     * @param from 起始顶点 id（对于有向图）
     * @param to 目的顶点 id（对于有向图）
     * @return 存在边返回 true；否则返回 false
     */
    default boolean containsEdge(int from, int to) {
        return edge(from, to) != null;
    }

    /**
     * 返回图中是否存在 eid 代表的边。
     *
     * @param eid 边 id
     * @return 存在返回 true；否则返回 false
     */
    default boolean containsEdge(int eid) {
        return edge(eid) != null;
    }

    /**
     * 返回图中是否存在指定的边。
     *
     * @param edge 边
     * @return 存在返回 true；否则返回 false
     */
    default boolean containsEdge(IGraphEdge edge) {
        Objects.requireNonNull(edge);
        return edge(edge.id()) == edge;
    }

    /**
     * 删除 vid 表示的顶点的所有指出的边并返回它们组成的列表。如果顶点没有出边，
     * 返回空的列表。边的顺序由 order 指定。
     *
     * 注意无向边既是出边也是入边。
     *
     * 删除的边的{@link IGraphEdge#graph()}方法应该返回 null。
     *
     * @param vid 顶点 id
     * @param order 返回的结点的迭代顺序
     * @return 顶点的所有出边（包含无向边）
     */
    List<IGraphEdge> removeVertexOutEdges(int vid, int order);

    /**
     * 参见{@link #removeVertexOutEdges(int, int)}, 此方法以{@link #ITER_DEFAULT}迭代顺序返回。
     *
     * @param vid 顶点 id
     * @return 顶点的所有出边（包含无向边）
     */
    default List<IGraphEdge> removeVertexOutEdges(int vid) {
        return removeVertexOutEdges(vid, ITER_DEFAULT);
    }

    /**
     * 删除指向 vid 表示的顶点的边并返回它们组成的列表。如果顶点没有入边，
     * 返回空的列表。边的顺序由 order 指定。
     *
     * 注意无向边既是出边也是入边。
     *
     * 删除的边的{@link IGraphEdge#graph()}方法应该返回 null。
     *
     * @param vid 顶点 id
     * @param order 返回的结点的迭代顺序
     * @return 顶点的所有入边（包含无向边）
     */
    List<IGraphEdge> removeVertexInEdges(int vid, int order);

    /**
     * 参见{@link #removeVertexInEdges(int, int)}, 此方法以{@link #ITER_DEFAULT}迭代顺序返回。
     *
     * @param vid 顶点 id
     * @return 顶点的所有入边（包含无向边）
     */
    default List<IGraphEdge> removeVertexInEdges(int vid) {
        return removeVertexInEdges(vid, ITER_DEFAULT);
    }

    /**
     * 删除指向 vid 表示的顶点所有的边（包括出边和入边）并返回它们组成的列表。如果顶点没有边，
     * 返回空的列表。边的顺序由 order 指定。
     *
     * 注意无向边既是出边也是入边。每个无向边在返回列表中只能出现一次。
     *
     * 删除的边的{@link IGraphEdge#graph()}方法应该返回 null。
     *
     * @param vid 顶点 id
     * @param order 返回的结点的迭代顺序
     * @return 顶点的所有边
     */
    List<IGraphEdge> removeVertexAllEdges(int vid, int order);

    /**
     * 参见{@link #removeVertexAllEdges(int, int)}, 此方法以{@link #ITER_DEFAULT}迭代顺序返回。
     *
     * @param vid 顶点 id
     * @return 顶点的所有边
     */
    default List<IGraphEdge> removeVertexAllEdges(int vid) {
        return removeVertexAllEdges(vid, ITER_DEFAULT);
    }

    /**
     * 返回图中所有顶点的迭代器。参数 order 表示迭代顺序。
     *
     * @param order 迭代顺序
     * @return 图中所有顶点的迭代器
     */
    Iterable<IGraphVertex> vertices(int order);

    /**
     * 参见{@link #vertices(int)}, 此方法以{@link #ITER_DEFAULT}迭代顺序返回。
     *
     * @return 图中所有顶点的迭代器
     */
    default Iterable<IGraphVertex> vertices() {
        return vertices(ITER_DEFAULT);
    }

    /**
     * 返回图中所有边的迭代器。参数 order 表示迭代顺序。
     *
     * 需要注意的是，每条边在迭代中只能出现一次。
     *
     * @param order 迭代顺序
     * @return 图中所有边的迭代器
     */
    Iterable<IGraphEdge> edges(int order);

    /**
     * 参见{@link #edges(int)}，此方法以{@link #ITER_DEFAULT}迭代顺序返回。
     *
     * @return 图中所有边的迭代器
     */
    default Iterable<IGraphEdge> edges() {
        return edges(ITER_DEFAULT);
    }
}
