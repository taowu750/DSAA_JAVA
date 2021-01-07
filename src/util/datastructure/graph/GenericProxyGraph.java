package util.datastructure.graph;

import java.util.List;
import java.util.Objects;
import java.util.function.BiFunction;

/**
 * 此类是{@link IGraph}的代理类，它可以适配任意的图实现，并增加类型约束。
 *
 * 通过继承此类，子类可以指定的顶点类型和边类型。
 * 通过这种方式，可以强制子类使用指定的顶点类型和边类型。此类提供了编译期和运行时的类型检查。
 *
 * @param <GV> 指定的顶点类型
 * @param <GE> 指定的边类型
 */
public class GenericProxyGraph<GV extends IGraphVertex, GE extends IGraphEdge> implements IGraph {

    private final IGraph graph;
    private final Class<GV> vertexClass;
    private final Class<GE> edgeClass;

    public GenericProxyGraph(IGraph graph, Class<GV> vertexClass, Class<GE> edgeClass) {
        Objects.requireNonNull(graph);
        Objects.requireNonNull(vertexClass);
        Objects.requireNonNull(edgeClass);

        this.vertexClass = vertexClass;
        this.edgeClass = edgeClass;
        this.graph = graph;
    }

    @Override
    public GraphType type() {
        return graph.type();
    }

    @Override
    public boolean isDirected() {
        return graph.isDirected();
    }

    @Override
    public boolean isUndirected() {
        return graph.isUndirected();
    }

    @Override
    public boolean isMixed() {
        return graph.isMixed();
    }

    @Override
    public int vertexNum() {
        return graph.vertexNum();
    }

    @Override
    public int edgeNum() {
        return graph.edgeNum();
    }

    @Override
    public int addVertex(IGraphVertex vertex) {
        checkVertex(vertex);
        return graph.addVertex(vertex);
    }

    public int checkedAddVertex(GV vertex) {
        return graph.addVertex(vertex);
    }

    @Override
    public boolean insertVertex(int vid, IGraphVertex vertex) {
        checkVertex(vertex);
        return graph.insertVertex(vid, vertex);
    }

    public boolean checkedInsertVertex(int vid, GV vertex) {
        return graph.insertVertex(vid, vertex);
    }

    public static class CheckedRemovedVertexWithEdge<GV, GE> {

        public final GV vertex;
        public final List<GE> edges;

        public CheckedRemovedVertexWithEdge(GV vertex, List<GE> edges) {
            this.vertex = vertex;
            this.edges = edges;
        }
    }

    @Override
    public RemovedVertexWithEdge removeVertex(int vid) {
        return graph.removeVertex(vid);
    }

    public CheckedRemovedVertexWithEdge<GV, GE> checkedRemoveVertex(int vid) {
        RemovedVertexWithEdge removed = graph.removeVertex(vid);
        //noinspection unchecked
        return new CheckedRemovedVertexWithEdge<GV, GE>((GV) removed.vertex, (List<GE>) removed.edges);
    }

    @Override
    public GV vertex(int vid) {
        //noinspection unchecked
        return (GV) graph.vertex(vid);
    }

    @Override
    public boolean containsVertex(int vid) {
        return graph.containsVertex(vid);
    }

    @Override
    public boolean containsVertex(IGraphVertex vertex) {
        checkVertex(vertex);
        return graph.containsVertex(vertex);
    }

    public boolean checkedContainsVertex(GV vertex) {
        return graph.containsVertex(vertex);
    }

    @Override
    public boolean vIsAttachEdge(int vid, int eid) {
        return graph.vIsAttachEdge(vid, eid);
    }

    @Override
    public boolean vIsOutEdge(int vid, int eid) {
        return graph.vIsOutEdge(vid, eid);
    }

    @Override
    public boolean vIsInEdge(int vid, int eid) {
        return graph.vIsInEdge(vid, eid);
    }

    @Override
    public Iterable<IGraphEdge> vOutEdges(int vid, int order) {
        return graph.vOutEdges(vid, order);
    }

    public Iterable<GE> checkedVOutEdges(int vid, int order) {
        //noinspection unchecked
        return (Iterable<GE>) graph.vOutEdges(vid, order);
    }

    @Override
    public Iterable<IGraphEdge> vOutEdges(int vid) {
        return graph.vOutEdges(vid);
    }

    public Iterable<GE> checkedVOutEdges(int vid) {
        //noinspection unchecked
        return (Iterable<GE>) graph.vOutEdges(vid);
    }

    @Override
    public Iterable<IGraphEdge> vInEdges(int vid, int order) {
        return graph.vInEdges(vid, order);
    }

    public Iterable<GE> checkedVInEdges(int vid, int order) {
        //noinspection unchecked
        return (Iterable<GE>) graph.vInEdges(vid, order);
    }

    @Override
    public Iterable<IGraphEdge> vInEdges(int vid) {
        return graph.vInEdges(vid);
    }

    public Iterable<GE> checkedVInEdges(int vid) {
        //noinspection unchecked
        return (Iterable<GE>) graph.vInEdges(vid);
    }

    @Override
    public Iterable<IGraphEdge> vEdges(int vid, int order) {
        return graph.vEdges(vid, order);
    }

    public Iterable<GE> checkedVEdges(int vid, int order) {
        //noinspection unchecked
        return (Iterable<GE>) graph.vEdges(vid, order);
    }

    @Override
    public Iterable<IGraphEdge> vEdges(int vid) {
        return graph.vEdges(vid);
    }

    public Iterable<GE> checkedVEdges(int vid) {
        //noinspection unchecked
        return (Iterable<GE>) graph.vEdges(vid);
    }

    @Override
    public int vOutDegree(int vid) {
        return graph.vOutDegree(vid);
    }

    @Override
    public int vInDegree(int vid) {
        return graph.vInDegree(vid);
    }

    @Override
    public int vDegree(int vid) {
        return graph.vDegree(vid);
    }

    @Override
    public boolean vHasOutEdge(int vid) {
        return graph.vHasOutEdge(vid);
    }

    @Override
    public boolean vHasInEdge(int vid) {
        return graph.vHasInEdge(vid);
    }

    @Override
    public boolean vHashEdge(int vid) {
        return graph.vHashEdge(vid);
    }

    @Override
    public int addEdge(IGraphEdge edge) {
        checkEdge(edge);
        return graph.addEdge(edge);
    }

    public int checkedAddEdge(GE edge) {
        return graph.addEdge(edge);
    }

    @Override
    public int addEdge(int from, int to, BiFunction<IGraphVertex, IGraphVertex, IGraphEdge> edgeSupplier) {
        BiFunction<IGraphVertex, IGraphVertex, IGraphEdge> checkedEdgeSupplier = (f, t) -> {
            checkVertex(f);
            checkVertex(t);

            IGraphEdge result = edgeSupplier.apply(f, t);
            checkEdge(result);

            return result;
        };

        return graph.addEdge(from, to, checkedEdgeSupplier);
    }

    public int checkedAddEdge(int from, int to, BiFunction<GV, GV, GE> edgeSupplier) {
        //noinspection unchecked
        return graph.addEdge(from, to, (BiFunction<IGraphVertex, IGraphVertex, IGraphEdge>) edgeSupplier);
    }

    @Override
    public List<IGraphEdge> removeEdges(int from, int to, int order) {
        return graph.removeEdges(from, to, order);
    }

    public List<GE> checkedRemoveEdges(int from, int to, int order) {
        //noinspection unchecked
        return (List<GE>) graph.removeEdges(from, to, order);
    }

    @Override
    public List<IGraphEdge> removeEdges(int from, int to) {
        return graph.removeEdges(from, to);
    }

    public List<GE> checkedRemoveEdges(int from, int to) {
        //noinspection unchecked
        return (List<GE>) graph.removeEdges(from, to);
    }

    @Override
    public GE removeEdge(int from, int to) {
        //noinspection unchecked
        return (GE) graph.removeEdge(from, to);
    }

    @Override
    public List<IGraphEdge> edges(int from, int to, int order) {
        return graph.edges(from, to, order);
    }

    public List<GE> checkedEdges(int from, int to, int order) {
        //noinspection unchecked
        return (List<GE>) graph.edges(from, to, order);
    }

    @Override
    public List<IGraphEdge> edges(int from, int to) {
        return graph.edges(from, to);
    }

    public List<GE> checkedEdges(int from, int to) {
        //noinspection unchecked
        return (List<GE>) graph.edges(from, to);
    }

    @Override
    public GE edge(int from, int to) {
        //noinspection unchecked
        return (GE) graph.edge(from, to);
    }

    @Override
    public GE edge(int eid) {
        //noinspection unchecked
        return (GE) graph.edge(eid);
    }

    @Override
    public boolean containsEdge(int from, int to) {
        return graph.containsEdge(from, to);
    }

    @Override
    public boolean containsEdge(int eid) {
        return graph.containsEdge(eid);
    }

    @Override
    public boolean containsEdge(IGraphEdge edge) {
        checkEdge(edge);
        return graph.containsEdge(edge);
    }

    public boolean checkedContainsEdge(GE edge) {
        return graph.containsEdge(edge);
    }

    @Override
    public List<IGraphEdge> removeVertexOutEdges(int vid, int order) {
        return graph.removeVertexOutEdges(vid, order);
    }

    public List<GE> checkedRemoveVertexOutEdges(int vid, int order) {
        //noinspection unchecked
        return (List<GE>) graph.removeVertexOutEdges(vid, order);
    }

    @Override
    public List<IGraphEdge> removeVertexOutEdges(int vid) {
        return graph.removeVertexOutEdges(vid);
    }

    public List<GE> checkedRemoveVertexOutEdges(int vid) {
        //noinspection unchecked
        return (List<GE>) graph.removeVertexOutEdges(vid);
    }

    @Override
    public List<IGraphEdge> removeVertexInEdges(int vid, int order) {
        return graph.removeVertexInEdges(vid, order);
    }

    public List<GE> checkedRemoveVertexInEdges(int vid, int order) {
        //noinspection unchecked
        return (List<GE>) graph.removeVertexInEdges(vid, order);
    }

    @Override
    public List<IGraphEdge> removeVertexInEdges(int vid) {
        return graph.removeVertexInEdges(vid);
    }

    public List<GE> checkedRemoveVertexInEdges(int vid) {
        //noinspection unchecked
        return (List<GE>) graph.removeVertexInEdges(vid);
    }

    @Override
    public List<IGraphEdge> removeVertexAllEdges(int vid, int order) {
        return graph.removeVertexAllEdges(vid, order);
    }

    public List<GE> checkedRemoveVertexAllEdges(int vid, int order) {
        //noinspection unchecked
        return (List<GE>) graph.removeVertexAllEdges(vid, order);
    }

    @Override
    public List<IGraphEdge> removeVertexAllEdges(int vid) {
        return graph.removeVertexAllEdges(vid);
    }

    public List<GE> checkedRemoveVertexAllEdges(int vid) {
        //noinspection unchecked
        return (List<GE>) graph.removeVertexAllEdges(vid);
    }

    @Override
    public Iterable<IGraphVertex> vertices(int order) {
        return graph.vertices();
    }

    public Iterable<GV> checkedVertices(int order) {
        //noinspection unchecked
        return (Iterable<GV>) graph.vertices();
    }

    @Override
    public Iterable<IGraphVertex> vertices() {
        return graph.vertices();
    }

    public Iterable<GV> checkedVertices() {
        //noinspection unchecked
        return (Iterable<GV>) graph.vertices();
    }

    @Override
    public Iterable<IGraphEdge> edges(int order) {
        return graph.edges(order);
    }

    public Iterable<GE> checkedEdges(int order) {
        //noinspection unchecked
        return (Iterable<GE>) graph.edges(order);
    }

    @Override
    public Iterable<IGraphEdge> edges() {
        return graph.edges();
    }

    public Iterable<GE> checkedEdges() {
        //noinspection unchecked
        return (Iterable<GE>) graph.edges();
    }

    @Override
    public <V> V putProp(Object key, V value) {
        return graph.putProp(key, value);
    }

    @Override
    public <V> V mergeProp(Object key, V value, BiFunction<V, V, V> merge) {
        return graph.mergeProp(key, value, merge);
    }

    @Override
    public <V> V removeProp(Object key) {
        return graph.removeProp(key);
    }

    @Override
    public <V> V removeProp(Object key, V defaultValue) {
        return graph.removeProp(key, defaultValue);
    }

    @Override
    public boolean containsProp(Object key) {
        return graph.containsProp(key);
    }

    @Override
    public <V> V getProp(Object key) {
        return graph.getProp(key);
    }

    @Override
    public <V> V getProp(Object key, V defaultValue) {
        return graph.getProp(key, defaultValue);
    }

    @Override
    public Iterable<Object> propKeys() {
        return graph.propKeys();
    }

    @Override
    public Iterable<Object> propValues() {
        return graph.propValues();
    }

    @Override
    public int propSize() {
        return graph.propSize();
    }

    @Override
    public int clearProps() {
        return graph.clearProps();
    }

    private void checkEdge(IGraphEdge edge) {
        if (!edgeClass.isInstance(edge))
            throw new IllegalStateException("the parameter edge type does not match the specified edge type");
    }

    private void checkVertex(IGraphVertex vertex) {
        if (!vertexClass.isInstance(vertex)) {
            throw new IllegalStateException("the parameter vertex type does not match the specified vertex type");
        }
    }
}
