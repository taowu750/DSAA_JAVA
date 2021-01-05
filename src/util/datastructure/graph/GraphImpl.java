package util.datastructure.graph;

import java.util.*;
import java.util.function.BiFunction;

// TODO: 允许迭代器删除

/**
 * {@link IGraph}的默认实现。此实现需要在构造的时候指定图的类型（{@link #type()}）。
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

    private static class VertexEntry {
        IGraphVertex vertex;
        TreeMap<Integer, IGraphEdge> attachedEdges;
        int outDegree, inDegree, edgeNum;

        VertexEntry(IGraphVertex vertex) {
            this.vertex = vertex;
        }

        TreeMap<Integer, IGraphEdge> _attachedEdges() {
            if (attachedEdges == null) {
                attachedEdges = new TreeMap<>();
            }

            return attachedEdges;
        }

        boolean noEdge() {
            return attachedEdges == null || attachedEdges.isEmpty();
        }

        void attachEdge(IGraphEdge edge) {
            if (!_attachedEdges().containsKey(edge.id())) {
                attachedEdges.put(edge.id(), edge);
                if (!edge.isDirected()) {
                    outDegree++;
                    inDegree++;
                } else if (edge.isFrom(vertex)) {
                    outDegree++;
                } else {
                    inDegree++;
                }
                edgeNum++;
            }
        }

        void detachEdge(int eid) {
            IGraphEdge edge = _attachedEdges().get(eid);
            if (edge != null) {
                attachedEdges.remove(eid);
                if (!edge.isDirected()) {
                    outDegree--;
                    inDegree--;
                } else if (edge.isFrom(vertex)) {
                    outDegree--;
                } else {
                    inDegree--;
                }
                edgeNum--;
            }
        }

        void detachEdge(IGraphEdge edge) {
            detachEdge(edge.id());
        }
    }

    private GraphType type;
    private TreeMap<Integer, VertexEntry> vertexEntries;
    private TreeMap<Integer, IGraphEdge> edgeMap;

    private int modCount;

    public GraphImpl(GraphType type) {
        Objects.requireNonNull(type);
        this.type = type;
    }

    @Override
    public String toString() {
        return Graphs.simpleGraphString(this);
    }

    @Override
    public GraphType type() {
        return type;
    }

    @Override
    public int vertexNum() {
        return _vertexEntries().size();
    }

    @Override
    public int edgeNum() {
        return _edgeMap().size();
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
        if (vGraph != null) {
            // 如果顶点的图等于当前图，我们认为它存在于此图中
            if (vGraph == this)
                return vertex.id();
            else
                throw new IllegalArgumentException("this vertex already exists in another graph: " + vertex);
        }

        // 生成 id
        int nextId = maxVid() + 1;
        // 绑定顶点
        vertex.unsafeSetGraph(this);
        vertex.unsafeSetId(nextId);
        // 将 id 与顶点进行关联
        _vertexEntries().put(nextId, new VertexEntry(vertex));

        modCount++;

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
        if (vGraph != null) {
            if (vGraph == this)
                return false;
            else
                throw new IllegalArgumentException("this vertex already exists in another graph: " + vertex);
        }

        if (_vertexEntries().containsKey(vid))
            return false;

        vertex.unsafeSetGraph(this);
        vertex.unsafeSetId(vid);
        vertexEntries.put(vid, new VertexEntry(vertex));

        modCount++;

        return true;
    }

    @Override
    public RemovedVertexWithEdge removeVertex(int vid) {
        // 删除顶点
        VertexEntry deleted = _vertexEntries().remove(vid);
        if (deleted == null)
            return null;

        // 解绑顶点
        deleted.vertex.unsafeSetGraph(null);
        deleted.vertex.unsafeSetId(-1);
        RemovedVertexWithEdge removedVertexWithEdge;
        if (deleted.noEdge()) {
            // 没有关联的边，则返回此顶点和空边列表
            removedVertexWithEdge = new RemovedVertexWithEdge(deleted.vertex, Collections.emptyList());
        } else {
            // 删除顶点所有关联的边
            List<IGraphEdge> deletedEdges = new ArrayList<>(deleted.attachedEdges.values());
            for (IGraphEdge deletedEdge : deletedEdges) {
                // 删除这条边另一个顶点中的此边
                VertexEntry other = vertexEntries.get(deletedEdge.other(deleted.vertex).id());
                _detachEdge(null, other, deletedEdge);
            }

            removedVertexWithEdge = new RemovedVertexWithEdge(deleted.vertex, deletedEdges);
        }

        modCount++;

        return removedVertexWithEdge;
    }

    @Override
    public IGraphVertex vertex(int vid) {
        VertexEntry vertexEntry = _vertexEntries().get(vid);
        return vertexEntry != null ? vertexEntry.vertex : null;
    }

    @Override
    public boolean vIsAttachEdge(int vid, int eid) {
        VertexEntry vertexEntry = _vertexEntries().get(vid);
        return vertexEntry != null && !vertexEntry.noEdge()
                && vertexEntry.attachedEdges.containsKey(eid);
    }

    @Override
    public boolean vIsOutEdge(int vid, int eid) {
        VertexEntry vertexEntry = _vertexEntries().get(vid);
        IGraphEdge edge = vertexEntry != null && !vertexEntry.noEdge() ? vertexEntry.attachedEdges.get(eid) : null;

        return edge != null && (edge.type() == IGraphEdge.EdgeType.UNDIRECTED || edge.from() == vertexEntry.vertex);
    }

    @Override
    public boolean vIsInEdge(int vid, int eid) {
        VertexEntry vertexEntry = _vertexEntries().get(vid);
        IGraphEdge edge = vertexEntry != null && !vertexEntry.noEdge() ? vertexEntry.attachedEdges.get(eid) : null;

        return edge != null && (edge.type() == IGraphEdge.EdgeType.UNDIRECTED || edge.to() == vertexEntry.vertex);
    }

    @Override
    public Iterable<IGraphEdge> vOutEdges(int vid, int order) {
        return vChooseEdges(vid, order, CHOOSE_EDGE_OUT);
    }

    private Iterable<IGraphEdge> vChooseEdges(int vid, int order, int chooseEdge) {
        VertexEntry vertexEntry;
        if ((vertexEntry = _vertexEntries().get(vid)) != null && !vertexEntry.noEdge()) {
            switch (order) {
                default:
                case IGraph.ITER_DEFAULT:
                case IGraph.ITER_ASC_BY_ID:
                    return () -> new OrderByIdEdgeIter(vertexEntry.attachedEdges, chooseEdge,
                            vertexEntry.vertex, true);

                case IGraph.ITER_DESC_BY_ID:
                    return () -> new OrderByIdEdgeIter(vertexEntry.attachedEdges, chooseEdge,
                            vertexEntry.vertex, false);

                case IGraph.ITER_RANDOM:
                    return () -> new RandomEdgeIter(vertexEntry.attachedEdges, chooseEdge,
                            vertexEntry.vertex);
            }
        } else {
            return Collections.emptyList();
        }
    }

    // 选择出边（包括无向边）
    private static final int CHOOSE_EDGE_OUT = 1;
    // 选择入边（包括无向边）
    private static final int CHOOSE_EDGE_IN = 2;
    // 选择所有边
    private static final int CHOOSE_EDGE_ALL = 3;

    private class OrderByIdEdgeIter implements Iterator<IGraphEdge> {

        int expectedModCount = modCount;
        // 需要遍历的边的类型
        int iterEdgeType;
        // 边的类型是出边或入边时，和其相关的顶点
        IGraphVertex attachedVertex;
        // 是否是升序遍历
        boolean asc;
        // 边迭代器。
        Iterator<IGraphEdge> allEdgeIterator;
        // 边的类型是出边或入边时，next 方法应该返回的下一个顶点
        IGraphEdge nextMatchedEdge;

        OrderByIdEdgeIter(TreeMap<Integer, IGraphEdge> iterEdgeMap,
                          int iterEdgeType,
                          IGraphVertex attachedVertex,
                          boolean asc) {
            this.asc = asc;
            this.iterEdgeType = iterEdgeType;
            this.attachedVertex = attachedVertex;
            if (asc)
                allEdgeIterator = iterEdgeMap.values().iterator();
            else
                allEdgeIterator = iterEdgeMap.descendingMap().values().iterator();
        }

        @Override
        public boolean hasNext() {
            if (iterEdgeType == CHOOSE_EDGE_ALL)
                return allEdgeIterator.hasNext();
            else {
                return nextMatchedEdge();
            }
        }

        @Override
        public IGraphEdge next() {
            checkModCount();
            if (iterEdgeType == CHOOSE_EDGE_ALL)
                return allEdgeIterator.next();
            else {
                if (nextMatchedEdge()) {
                    IGraphEdge tmp = nextMatchedEdge;
                    nextMatchedEdge = null;

                    return tmp;
                } else
                    throw new NoSuchElementException();
            }
        }

        private void checkModCount() {
            if (expectedModCount != modCount) {
                throw new ConcurrentModificationException();
            }
        }

        /**
         * 找到下一个符合条件的顶点
         *
         * @return 是否有下一个符合条件的顶点
         */
        private boolean nextMatchedEdge() {
            if (nextMatchedEdge == null) {
                while (allEdgeIterator.hasNext()) {
                    nextMatchedEdge = allEdgeIterator.next();
                    if (matchEdge(nextMatchedEdge, iterEdgeType, attachedVertex))
                        return true;
                }
                nextMatchedEdge = null;

                return false;
            }

            return true;
        }
    }

    private class RandomEdgeIter implements Iterator<IGraphEdge> {

        int expectedModCount = modCount;
        // 需要遍历的边的类型
        int iterEdgeType;
        // 边的类型是出边或入边时，和其相关的顶点
        IGraphVertex attachedVertex;
        // 随机选取的边 id
        List<IGraphEdge> iterEdges;
        int cursor;
        // 边的类型是出边或入边时，next 方法应该返回的下一个顶点
        IGraphEdge nextMatchedEdge;

        RandomEdgeIter(Map<Integer, IGraphEdge> iterEdgeMap, int iterEdgeType, IGraphVertex attachedVertex) {
            this.iterEdgeType = iterEdgeType;
            this.attachedVertex = attachedVertex;
            iterEdges = new ArrayList<>(iterEdgeMap.values());
            // 随机打乱 id
            Collections.shuffle(iterEdges);
        }

        @Override
        public boolean hasNext() {
            checkModCount();
            if (iterEdgeType == CHOOSE_EDGE_ALL)
                return cursor < iterEdges.size();
            else
                return nextMatchedEdge();
        }

        @Override
        public IGraphEdge next() {
            checkModCount();
            if (iterEdgeType == CHOOSE_EDGE_ALL) {
                if (cursor < iterEdges.size())
                    return iterEdges.get(cursor++);
            } else {
                if (nextMatchedEdge()) {
                    IGraphEdge tmp = nextMatchedEdge;
                    nextMatchedEdge = null;

                    return tmp;
                }
            }

            throw new NoSuchElementException();
        }

        private void checkModCount() {
            if (expectedModCount != modCount) {
                throw new ConcurrentModificationException();
            }
        }

        private boolean nextMatchedEdge() {
            if (nextMatchedEdge == null) {
                while (cursor < iterEdges.size()) {
                    nextMatchedEdge = iterEdges.get(cursor++);
                    if (matchEdge(nextMatchedEdge, iterEdgeType, attachedVertex))
                        return true;
                }
                nextMatchedEdge = null;

                return false;
            }

            return true;
        }
    }

    @Override
    public Iterable<IGraphEdge> vInEdges(int vid, int order) {
        return vChooseEdges(vid, order, CHOOSE_EDGE_IN);
    }

    @Override
    public Iterable<IGraphEdge> vEdges(int vid, int order) {
        return vChooseEdges(vid, order, CHOOSE_EDGE_ALL);
    }

    @Override
    public int vOutDegree(int vid) {
        VertexEntry vertexEntry = _vertexEntries().get(vid);
        return vertexEntry != null ? vertexEntry.outDegree : 0;
    }

    @Override
    public int vInDegree(int vid) {
        VertexEntry vertexEntry = _vertexEntries().get(vid);
        return vertexEntry != null ? vertexEntry.inDegree : 0;
    }

    @Override
    public int vEdgeNum(int vid) {
        VertexEntry vertexEntry = _vertexEntries().get(vid);
        return vertexEntry != null ? vertexEntry.edgeNum : 0;
    }

    /**
     * {@inheritDoc}
     * <p>
     * 如果边或顶点存在于另一个图中，或边和图的类型不匹配，则抛出{@link IllegalArgumentException}异常。
     * <p>
     * 如果边的某个顶点为 null，直接返回负值。
     */
    @Override
    public int addEdge(IGraphEdge edge) {
        Objects.requireNonNull(edge);

        if ((type == GraphType.DIRECTED && edge.type() == IGraphEdge.EdgeType.UNDIRECTED)
                || (type == GraphType.UNDIRECTED && edge.type() == IGraphEdge.EdgeType.DIRECTED)) {
            throw new IllegalArgumentException("type mismatch between edge and graph");
        }

        // 检查边的结点是否为 null 或存在于其他图中
        IGraphVertex from = edge.from(), to = edge.to();
        if (from == null || to == null)
            return -1;
        if ((from.graph() != null && from.graph() != this) ||
                (to.graph() != null && to.graph() != this)) {
            throw new IllegalArgumentException("edge's vertices already exists in another graph");
        }

        if (_edgeMap().get(edge.id()) == edge) {
            return edge.id();
        }

        // 顶点不在图中就将它添加进来
        if (from.graph() == null)
            addVertex(from);
        if (to.graph() == null)
            addVertex(to);

        // 生成边 id
        int nextEid = maxEid() + 1;
        // 绑定边
        edge.unsafeSetId(nextEid);
        // 将 id 与边进行关联
        _edgeMap().put(nextEid, edge);
        // 将图中顶点和边进行关联
        vertexEntries.get(from.id()).attachEdge(edge);
        vertexEntries.get(to.id()).attachEdge(edge);

        modCount++;

        return nextEid;
    }

    /**
     * {@inheritDoc}
     * <p>
     * 当某个顶点不存在时返回 -1。
     */
    @Override
    public int addEdge(int from, int to, BiFunction<IGraphVertex, IGraphVertex, IGraphEdge> edgeSupplier) {
        if (containsVertex(from) && containsVertex(to)) {
            return addEdge(edgeSupplier.apply(vertex(from), vertex(to)));
        }

        return -1;
    }

    @Override
    public List<IGraphEdge> removeEdges(int from, int to, int order) {
        return removeOrGetEdges(from, to, order, true);
    }

    private List<IGraphEdge> removeOrGetEdges(int from, int to, int order, boolean isRemove) {
        VertexEntry fromEntry = _vertexEntries().get(from);
        if (fromEntry == null || fromEntry.noEdge())
            return Collections.emptyList();
        VertexEntry toEntry = vertexEntries.get(to);
        if (toEntry == null || toEntry.noEdge())
            return Collections.emptyList();

        // 求两个顶点的共同边
        TreeSet<Integer> commonEdgeIds = new TreeSet<>(fromEntry.attachedEdges.keySet());
        commonEdgeIds.retainAll(toEntry.attachedEdges.keySet());

        List<IGraphEdge> edges = new ArrayList<>();
        switch (order) {
            default:
            case ITER_DEFAULT:
            case ITER_ASC_BY_ID:
                if (isRemove) {
                    for (int commonEdgeId : commonEdgeIds)
                        edges.add(_detachEdge(fromEntry, toEntry, commonEdgeId));
                } else {
                    for (int commonEdgeId : commonEdgeIds)
                        edges.add(edgeMap.get(commonEdgeId));
                }
                break;

            case ITER_DESC_BY_ID:
                Iterator<Integer> descIterator = commonEdgeIds.descendingIterator();
                if (isRemove) {
                    while (descIterator.hasNext())
                        edges.add(_detachEdge(fromEntry, toEntry, descIterator.next()));
                } else {
                    while (descIterator.hasNext())
                        edges.add(edgeMap.get(descIterator.next()));
                }
                break;

            case ITER_RANDOM:
                List<Integer> randomIds = new ArrayList<>(commonEdgeIds);
                Collections.shuffle(randomIds);
                if (isRemove) {
                    for (int randomId : randomIds)
                        edges.add(_detachEdge(fromEntry, toEntry, randomId));
                } else {
                    for (int randomId : randomIds)
                        edges.add(edgeMap.get(randomId));
                }
                break;
        }
        modCount++;

        return edges;
    }

    @Override
    public IGraphEdge removeEdge(int from, int to) {
        VertexEntry fromEntry = _vertexEntries().get(from);
        if (fromEntry == null || fromEntry.noEdge())
            return null;
        VertexEntry toEntry = vertexEntries.get(to);
        if (toEntry == null || toEntry.noEdge())
            return null;

        // 求两个顶点的共同边
        TreeSet<Integer> commonEdgeIds = new TreeSet<>(fromEntry.attachedEdges.keySet());
        commonEdgeIds.retainAll(toEntry.attachedEdges.keySet());

        if (commonEdgeIds.size() == 0)
            return null;

        modCount++;

        return _detachEdge(fromEntry, toEntry, commonEdgeIds.first());
    }

    @Override
    public List<IGraphEdge> edges(int from, int to, int order) {
        return removeOrGetEdges(from, to, order, false);
    }

    @Override
    public IGraphEdge edge(int from, int to) {
        VertexEntry fromEntry = _vertexEntries().get(from);
        if (fromEntry == null || fromEntry.noEdge())
            return null;
        VertexEntry toEntry = vertexEntries.get(to);
        if (toEntry == null || toEntry.noEdge())
            return null;

        // 求两个顶点的共同边
        TreeSet<Integer> commonEdgeIds = new TreeSet<>(fromEntry.attachedEdges.keySet());
        commonEdgeIds.retainAll(toEntry.attachedEdges.keySet());

        if (commonEdgeIds.size() == 0)
            return null;

        return _edgeMap().get(commonEdgeIds.first());
    }

    @Override
    public IGraphEdge edge(int eid) {
        return _edgeMap().get(eid);
    }

    @Override
    public List<IGraphEdge> removeVertexOutEdges(int vid, int order) {
        return removeVertexEdges(vid, order, CHOOSE_EDGE_OUT);
    }

    @Override
    public List<IGraphEdge> removeVertexInEdges(int vid, int order) {
        return removeVertexEdges(vid, order, CHOOSE_EDGE_IN);
    }

    @Override
    public List<IGraphEdge> removeVertexAllEdges(int vid, int order) {
        return removeVertexEdges(vid, order, CHOOSE_EDGE_ALL);
    }

    private List<IGraphEdge> removeVertexEdges(int vid, int order, int chooseEdgeType) {
        VertexEntry vertexEntry = _vertexEntries().get(vid);
        if (vertexEntry == null || vertexEntry.noEdge())
            return Collections.emptyList();

        Collection<IGraphEdge> allEdges;
        switch (order) {
            default:
            case ITER_DEFAULT:
            case ITER_ASC_BY_ID:
                allEdges = vertexEntry.attachedEdges.values();
                break;

            case ITER_DESC_BY_ID:
                allEdges = vertexEntry.attachedEdges.descendingMap().values();
                break;

            case ITER_RANDOM:
                allEdges = new ArrayList<>(vertexEntry.attachedEdges.values());
                Collections.shuffle((List<IGraphEdge>) allEdges);
                break;
        }

        List<IGraphEdge> removedEdges;
        if (chooseEdgeType == CHOOSE_EDGE_ALL) {
            if (order == ITER_RANDOM)
                removedEdges = (List<IGraphEdge>) allEdges;
            else
                removedEdges = new ArrayList<>(allEdges);
        } else {
            removedEdges = new ArrayList<>();
            for (IGraphEdge edge : allEdges) {
                if (matchEdge(edge, chooseEdgeType, vertexEntry.vertex))
                    removedEdges.add(edge);
            }
        }
        for (IGraphEdge removedEdge : removedEdges) {
            VertexEntry other = vertexEntries.get(removedEdge.other(vertexEntry.vertex).id());
            _detachEdge(vertexEntry, other, removedEdge);
        }
        modCount++;

        return removedEdges;
    }

    @Override
    public Iterable<IGraphVertex> vertices(int order) {
        if (vertexEntries == null || vertexEntries.isEmpty())
            return Collections.emptyList();

        switch (order) {
            default:
            case ITER_DEFAULT:
            case ITER_ASC_BY_ID:
                return () -> new VertexIter(order, vertexEntries.values().iterator());

            case ITER_DESC_BY_ID:
                return () -> new VertexIter(order, vertexEntries.descendingMap().values().iterator());

            case ITER_RANDOM:
                return () -> new VertexIter(order, null);
        }
    }

    private class VertexIter implements Iterator<IGraphVertex> {

        int expectedModCount = modCount;
        int cursor = 0;
        Iterator<VertexEntry> vertexIterator;
        List<VertexEntry> randomVertices;

        public VertexIter(int order, Iterator<VertexEntry> vertexIterator) {
            this.vertexIterator = vertexIterator;
            if (order == ITER_RANDOM) {
                randomVertices = new ArrayList<>(_vertexEntries().values());
                Collections.shuffle(randomVertices);
            }
        }

        @Override
        public boolean hasNext() {
            if (randomVertices != null)
                return cursor < randomVertices.size();
            else
                return vertexIterator.hasNext();
        }

        @Override
        public IGraphVertex next() {
            checkModCount();
            if (randomVertices != null) {
                if (cursor < randomVertices.size()) {
                    return randomVertices.get(cursor++).vertex;
                } else
                    throw new NoSuchElementException();
            } else {
                return vertexIterator.next().vertex;
            }
        }

        private void checkModCount() {
            if (expectedModCount != modCount) {
                throw new ConcurrentModificationException();
            }
        }
    }

    @Override
    public Iterable<IGraphEdge> edges(int order) {
        if (_edgeMap().size() == 0) {
            return Collections.emptyList();
        } else {
            switch (order) {
                default:
                case IGraph.ITER_DEFAULT:
                case IGraph.ITER_ASC_BY_ID:
                    return () -> new OrderByIdEdgeIter(edgeMap, CHOOSE_EDGE_ALL,
                            null, true);

                case IGraph.ITER_DESC_BY_ID:
                    return () -> new OrderByIdEdgeIter(edgeMap, CHOOSE_EDGE_ALL,
                            null, false);

                case IGraph.ITER_RANDOM:
                    return () -> new RandomEdgeIter(edgeMap, CHOOSE_EDGE_ALL,
                            null);
            }
        }
    }

    private TreeMap<Integer, VertexEntry> _vertexEntries() {
        return vertexEntries != null ? vertexEntries : (vertexEntries = new TreeMap<>());
    }

    private TreeMap<Integer, IGraphEdge> _edgeMap() {
        return edgeMap != null ? edgeMap : (edgeMap = new TreeMap<>());
    }


    private int maxVid() {
        if (vertexEntries == null)
            vertexEntries = new TreeMap<>();

        return vertexEntries.isEmpty() ? -1 : vertexEntries.lastKey();
    }

    private int maxEid() {
        if (edgeMap == null)
            edgeMap = new TreeMap<>();

        return edgeMap.isEmpty() ? -1 : edgeMap.lastKey();
    }

    private void checkId(int id) {
        if (id < 0)
            throw new IllegalArgumentException("id cannot be less than 0");
    }

    private boolean matchEdge(IGraphEdge edge, int chooseEdgeType, IGraphVertex attachedVertex) {
        return !edge.isDirected()
                || (chooseEdgeType == CHOOSE_EDGE_OUT && edge.isFrom(attachedVertex))
                || chooseEdgeType == CHOOSE_EDGE_IN && edge.isTo(attachedVertex);
    }

    private IGraphEdge _detachEdge(VertexEntry either, VertexEntry other, int eid) {
        // 将顶点和边解绑
        if (either != null)
            either.detachEdge(eid);
        if (other != null)
            other.detachEdge(eid);
        // 将边和顶点、图解绑
        IGraphEdge edge = _edgeMap().remove(eid);
        edge.unsafeSetId(-1);
        edge.unsafeSetFrom(null);
        edge.unsafeSetTo(null);

        return edge;
    }

    private void _detachEdge(VertexEntry either, VertexEntry other, IGraphEdge edge) {
        // 将顶点和边解绑
        if (either != null)
            either.detachEdge(edge);
        if (other != null)
            other.detachEdge(edge);
        // 将边和顶点、图解绑
        _edgeMap().remove(edge.id());
        edge.unsafeSetId(-1);
        edge.unsafeSetFrom(null);
        edge.unsafeSetTo(null);
    }
}
