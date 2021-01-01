package util.datastructure.graph;

import java.util.*;

// TODO: 删除时需要不要缩减内部实现的空间
// TODO: 实现结点、边的 Object 方法
// TODO: 快速添加结点的方法

/**
 * {@link IGraph}的默认实现。此时需要在构造的时候指定图的类型（{@link #type()}）。
 * <p>
 * 此实现的顶点 id 和边 id 均从 0 开始，且不能为负数。id 的增长策略和数据库的自增主键类似，
 * 会从已分配的最大 id 处不断加 1。
 * <p>
 * 此实现管理所有顶点和边。因此顶点和边的子类不应该再试图管理资源，而应都交由此实现管理，
 * 否则会发生未定义的行为。
 * <p>
 * 此类不是线程安全的。
 */
public class GraphImpl extends AbstractGraph {

    private static class VertexEdges {
        IGraphVertex vertex;
        /* 分别保存出边、入边、无向边；每个 Map 中的边不重复 */
        Map<Integer, IGraphEdge> outEdges;
        Map<Integer, IGraphEdge> inEdges;
        Map<Integer, IGraphEdge> undirectedEdges;

        VertexEdges(IGraphVertex vertex) {
            this.vertex = vertex;
        }

        Map<Integer, IGraphEdge> outs() {
            if (outEdges == null)
                outEdges = new HashMap<>(3);
            return outEdges;
        }

        Map<Integer, IGraphEdge> ins() {
            if (inEdges == null)
                inEdges = new HashMap<>(3);
            return inEdges;
        }

        Map<Integer, IGraphEdge> undirected() {
            if (undirectedEdges == null)
                undirectedEdges = new HashMap<>(3);
            return undirectedEdges;
        }

        boolean noEdge() {
            return outEdges == null && inEdges == null && undirectedEdges == null;
        }
    }

    private GraphType type;
    private TreeSet<Integer> vertexIds;
    private TreeSet<Integer> edgeIds;
    private Map<Integer, VertexEdges> vertexEdges;
    private Map<Integer, IGraphEdge> edges;

    public GraphImpl(GraphType type) {
        this.type = type;
    }

    @Override
    public GraphType type() {
        return type;
    }

    @Override
    public int vertexNum() {
        return _vertexEdges().size();
    }

    @Override
    public int edgeNum() {
        return _edges().size();
    }

    /**
     * {@inheritDoc}
     * <p>
     * 如果顶点存在于另一个图中，抛出{@link IllegalArgumentException}异常。
     */
    @Override
    public int addVertex(IGraphVertex vertex) {
        Objects.requireNonNull(vertex);

        IGraph vGraph = vertex.graph();
        if (vertex.graph() != null) {
            if (vGraph == this)
                return vertex.id();
            else
                throw new IllegalArgumentException("this vertex already exists in another graph");
        }

        // 生成 id
        int nextId = nextVid();
        // 绑定顶点
        vertex.unsafeSetGraph(this);
        vertex.unsafeSetId(nextId);
        // 将 id 与顶点进行映射
        _vertexEdges().put(nextId, new VertexEdges(vertex));

        return nextId;
    }

    /**
     * {@inheritDoc}
     * <p>
     * 如果顶点存在于另一个图中，抛出{@link IllegalArgumentException}异常。
     */
    @Override
    public boolean insertVertex(int vid, IGraphVertex vertex) {
        checkId(vid);
        Objects.requireNonNull(vertex);

        IGraph vGraph = vertex.graph();
        if (vertex.graph() != null) {
            if (vGraph == this)
                return false;
            else
                throw new IllegalArgumentException("this vertex already exists in another graph");
        }

        if (_vertexEdges().containsKey(vid))
            return false;

        vertex.unsafeSetGraph(this);
        vertex.unsafeSetId(vid);
        vertexEdges.put(vid, new VertexEdges(vertex));
        _vids().add(vid);

        return false;
    }

    @Override
    public DeletedVertexWithEdge removeVertex(int vid) {
        checkId(vid);

        VertexEdges deleted = _vertexEdges().remove(vid);
        if (deleted == null)
            return null;

        // 解绑顶点
        deleted.vertex.unsafeSetGraph(null);
        DeletedVertexWithEdge deletedVertexWithEdge;
        if (deleted.noEdge()) {
            // 没有关联的边，则返回此顶点和空边列表
            deletedVertexWithEdge = new DeletedVertexWithEdge(deleted.vertex, Collections.emptyList());
        } else {
            TreeSet<Integer> eids = _eids();
            List<IGraphEdge> deletedEdges = new ArrayList<>();
            // 删除入边
            if (deleted.inEdges != null) {
                Collection<IGraphEdge> edges = deleted.inEdges.values();
                for (IGraphEdge deletedEdge : edges) {
                    // 删除关联边的 id
                    eids.remove(deletedEdge.id());
                    // 删除这条边另一个顶点中，对应的出边
                    VertexEdges other = vertexEdges.get(deletedEdge.other(deleted.vertex).id());
                    other.outEdges.remove(deletedEdge.id());
                    // 解绑边
                    deletedEdge.unsafeSetFrom(null);
                    deletedEdge.unsafeSetTo(null);
                }
                // 添加到删除边的列表中
                deletedEdges.addAll(edges);
            }
            // 删除出边
            if (deleted.outEdges != null) {
                Collection<IGraphEdge> edges = deleted.outEdges.values();
                for (IGraphEdge deletedEdge : edges) {
                    // 删除关联边的 id
                    eids.remove(deletedEdge.id());
                    // 删除这条边另一个顶点中，对应的入边
                    VertexEdges other = vertexEdges.get(deletedEdge.other(deleted.vertex).id());
                    other.inEdges.remove(deletedEdge.id());
                    // 解绑边
                    deletedEdge.unsafeSetFrom(null);
                    deletedEdge.unsafeSetTo(null);
                }
                // 添加到删除边的列表中
                deletedEdges.addAll(edges);
            }
            // 删除无向边
            if (deleted.undirectedEdges != null) {
                Collection<IGraphEdge> edges = deleted.undirectedEdges.values();
                for (IGraphEdge deletedEdge : edges) {
                    // 删除关联边的 id
                    eids.remove(deletedEdge.id());
                    // 删除这条边另一个顶点中，对应的无向边
                    VertexEdges other = vertexEdges.get(deletedEdge.other(deleted.vertex).id());
                    other.undirectedEdges.remove(deletedEdge.id());
                    // 解绑边
                    deletedEdge.unsafeSetFrom(null);
                    deletedEdge.unsafeSetTo(null);
                }
                // 添加到删除边的列表中
                deletedEdges.addAll(edges);
            }

            deletedVertexWithEdge = new DeletedVertexWithEdge(deleted.vertex, deletedEdges);
        }
        // 删除 vid
        _vids().remove(vid);

        return deletedVertexWithEdge;
    }

    @Override
    public IGraphVertex vertex(int vid) {
        VertexEdges vertexEdges = _vertexEdges().get(vid);
        return vertexEdges != null ? vertexEdges.vertex : null;
    }

    @Override
    public Iterable<IGraphEdge> vOutEdges(int vid, int order) {
        return null;
    }

    @Override
    public boolean vIsAttachEdge(int vid, int eid) {
        return false;
    }

    @Override
    public Iterable<IGraphEdge> vInEdges(int vid, int order) {
        return null;
    }

    @Override
    public Iterable<IGraphEdge> vEdges(int vid, int order) {
        return null;
    }

    @Override
    public int vOutDegree(int vid) {
        return 0;
    }

    @Override
    public int vInDegree(int vid) {
        return 0;
    }

    @Override
    public int vEdgeNum(int vid) {
        return 0;
    }

    /**
     * {@inheritDoc}
     * <p>
     * 如果边或顶点存在于另一个图中，抛出{@link IllegalArgumentException}异常
     */
    @Override
    public boolean addEdge(IGraphEdge edge) {
        return false;
    }

    @Override
    public List<IGraphEdge> removeEdges(int from, int to, int order) {
        return null;
    }

    @Override
    public IGraphEdge removeEdge(int from, int to) {
        return null;
    }

    @Override
    public List<IGraphEdge> edges(int from, int to, int order) {
        return null;
    }

    @Override
    public IGraphEdge edge(int from, int to) {
        return null;
    }

    @Override
    public IGraphEdge edge(int eid) {
        return null;
    }

    @Override
    public List<IGraphEdge> removeVertexOutEdges(int vid, int order) {
        return null;
    }

    @Override
    public List<IGraphEdge> removeVertexInEdges(int vid, int order) {
        return null;
    }

    @Override
    public List<IGraphEdge> removeVertexAllEdges(int vid, int order) {
        return null;
    }

    @Override
    public Iterable<IGraphVertex> vertices(int order) {
        return null;
    }

    @Override
    public Iterable<IGraphEdge> edges(int order) {
        return null;
    }

    private TreeSet<Integer> _vids() {
        return vertexIds != null ? vertexIds : (vertexIds = new TreeSet<>());
    }

    private TreeSet<Integer> _eids() {
        return edgeIds != null ? edgeIds : (edgeIds = new TreeSet<>());
    }

    private Map<Integer, VertexEdges> _vertexEdges() {
        return vertexEdges != null ? vertexEdges : (vertexEdges = new HashMap<>());
    }

    private Map<Integer, IGraphEdge> _edges() {
        return edges != null ? edges : (edges = new HashMap<>());
    }


    private int maxVid() {
        if (vertexIds == null) {
            vertexIds = new TreeSet<>();
            vertexIds.add(0);
        }

        return vertexIds.last();
    }

    @SuppressWarnings("DuplicatedCode")
    private int nextVid() {
        if (vertexIds == null) {
            vertexIds = new TreeSet<>();
            vertexIds.add(0);

            return 0;
        }

        int max = vertexIds.last();
        vertexIds.add(max + 1);

        return max;
    }

    private int maxEid() {
        if (edgeIds == null) {
            edgeIds = new TreeSet<>();
            edgeIds.add(0);
        }

        return edgeIds.last();
    }

    @SuppressWarnings("DuplicatedCode")
    private int nextEid() {
        if (edgeIds == null) {
            edgeIds = new TreeSet<>();
            edgeIds.add(0);

            return 0;
        }

        int max = edgeIds.last();
        edgeIds.add(max + 1);

        return max;
    }

    private void checkId(int id) {
        if (id < 0)
            throw new IllegalArgumentException("id cannot be less than 1");
    }
}
