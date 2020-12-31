package util.datastructure.graph;

import java.util.List;

/**
 * 图接口。
 *
 * 图是一个组顶点和至少能够将两个顶点相连的边组成的。一张图至少需要有两个顶点和连接它们的边。
 */
public interface IGraph extends IProps {

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
     * 添加一个顶点，并返回这个顶点的 id。
     *
     * 需要注意，如果顶点存在于另一个图中，可以有以下几种策略：
     *  - 复制这个顶点
     *  - 将它从原来的图中删除
     *  - 抛出异常
     *  - 返回负值
     * 子类需要实现上面的策略之一（或者根据不同情况进行组合），且需要在文档中说明。
     *
     * @param vertex 顶点
     * @return 添加的顶点的 id
     */
    int addVertex(IGraphVertex vertex);

    /**
     * 根据顶点 vid（从 0 开始）删除一个顶点并返回它。vid 不存在返回 null。
     * 删除顶点后，顶点的 vid 应该是个负值。
     *
     * @param vid 顶点 vid
     * @return 返回删除的顶点；不存在返回 null
     */
    IGraphVertex removeVertex(int vid);

    /**
     * 根据顶点 vid（从 0 开始）返回对应的顶点。vid 不存在返回 null。
     *
     * @param vid 顶点 vid
     * @return 返回对应的顶点；不存在返回 null
     */
    IGraphVertex getVertex(int vid);

    /**
     * 添加一条边。如果这条边的顶点不存在于这个图中，可以有以下几种策略：
     *  - 复制顶点和边
     *  - 将顶点和边从原来的图中删除
     *  - 抛出异常
     *  - 返回 false
     * 子类需要实现上面的策略之一（或者根据不同情况进行组合），且需要在文档中说明。
     *
     * 需要注意，子类需要处理自环和平行边的情况。是以某种策略处理还是不允许（抛出异常），需要在文档中说明。
     *
     * @param edge
     */
    boolean addEdge(IGraphEdge edge);

    /**
     * 删除从 from 到 to 的所有边并返回。边的顺序由子类决定。
     *
     * 如果 from 到 to 只有一条边或子类不允许平行边，则返回的列表中只会包含一条边。
     * 如果 from 到 to 没有边，返回空的列表。
     *
     * 删除的边的{@link IGraphEdge#graph()}方法应该返回 null。
     *
     * @param from 起始顶点 id
     * @param to 目的顶点 id
     * @return from 到 to 的所有边。
     */
    List<IGraphEdge> removeEdges(int from, int to);

    /**
     * 删除从 from 到 to 的一条边并返回。
     *
     * 如果 from 到 to 有多条边，则具体行为由子类决定。
     * 如果 from 到 to 没有边，返回 null。
     *
     * 删除的边的{@link IGraphEdge#graph()}方法应该返回 null。
     *
     * @param from 起始顶点 id
     * @param to 目的顶点 id
     * @return from 到 to 的所有边。
     */
    IGraphEdge removeEdge(int from, int to);

    /**
     * 删除 vid 表示的顶点的所有指出的边并返回它们组成的列表。如果顶点没有出边，
     * 返回空的列表。边的顺序由子类决定。
     *
     * 删除的边的{@link IGraphEdge#graph()}方法应该返回 null。
     *
     * @param vid 顶点 vid
     * @return 顶点的所有出边
     */
    List<IGraphEdge> removeVertexOutEdges(int vid);

    /**
     * 删除指向 vid 表示的顶点的边并返回它们组成的列表。如果顶点没有入边，
     * 返回空的列表。边的顺序由子类决定。
     *
     * 删除的边的{@link IGraphEdge#graph()}方法应该返回 null。
     *
     * @param vid 顶点 vid
     * @return 顶点的所有入边
     */
    List<IGraphEdge> removeVertexInEdges(int vid);

    /**
     * 删除指向 vid 表示的顶点所有的边（包括出边和入边）并返回它们组成的列表。如果顶点没有边，
     * 返回空的列表。边的顺序由子类决定。
     *
     * 删除的边的{@link IGraphEdge#graph()}方法应该返回 null。
     *
     * @param vid 顶点 vid
     * @return 顶点的所有边
     */
    List<IGraphEdge> removeVertexAllEdges(int vid);

    /*
     下面是一组表示迭代顺序的常量。
     */
    /**
     * 用在{@link #vertices(int)}方法中，表示默认迭代顺序，可能是随机或其他方式。
     * 具体由子类实现，子类应在文档中说明此顺序。
     */
    public static final int ITER_DEFAULT = 0;
    /**
     * 用在{@link #vertices(int)}方法中，表示按顶点 id 从小到大的迭代顺序。
     */
    public static final int ITER_ASC_BY_VID = 1;
    /**
     * 用在{@link #vertices(int)}方法中，表示按顶点 id 从大到小的迭代顺序。
     */
    public static final int ITER_DESC_BY_VID = 2;
    /**
     * 用在{@link #vertices(int)}方法中，表示随机顺序。
     *
     * 注意，这种顺序是可选的。子类实现不了可以抛出{@link IllegalArgumentException}。
     */
    public static final int ITER_RANDOM = 3;

    /**
     * 返回图中所有顶点的迭代器。参数 order 表示迭代顺序，参见{@link #ITER_DEFAULT}、
     * {@link #ITER_ASC_BY_VID}、{@link #ITER_DESC_BY_VID}、{@link #ITER_RANDOM}。
     *
     * 子类也可以定义自己的迭代顺序，需要在文档中说明。
     *
     * @param order 迭代顺序
     * @return 图中所有顶点的迭代器
     */
    Iterable<IGraphVertex> vertices(int order);

    /**
     * 返回图中所有边的迭代器。参数 order 表示迭代顺序，参见{@link #ITER_DEFAULT}、
     * {@link #ITER_ASC_BY_VID}、{@link #ITER_DESC_BY_VID}、{@link #ITER_RANDOM}。
     *
     * 注意，对于 {@link #ITER_ASC_BY_VID}、{@link #ITER_DESC_BY_VID} 来说，
     * 是按顶点的出边还是入边、多个边之间怎么排序等实现细节交由子类实现。
     * 子类需要在文档中记录策略。
     *
     * 子类也可以定义自己的迭代顺序，需要在文档中说明。
     *
     * @param order 迭代顺序
     * @return 图中所有边的迭代器
     */
    Iterable<IGraphEdge> edges(int order);
}
