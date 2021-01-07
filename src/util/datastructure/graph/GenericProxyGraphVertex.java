package util.datastructure.graph;

import java.util.Objects;
import java.util.function.BiFunction;

/**
 * 此类是{@link IGraphVertex}的代理类，它可以适配任意的顶点实现，并增加类型约束。
 *
 * 通过继承此类，子类可以指定的图类型和边类型。
 * 通过这种方式，可以强制子类使用指定的图类型和边类型。此类提供了编译期和运行时的类型检查。
 *
 * @param <G> 指定的图类型
 * @param <GE> 指定的边类型。
 */
public class GenericProxyGraphVertex<G extends IGraph, GE extends IGraphEdge> implements IGraphVertex {

    private final IGraphVertex vertex;
    private final Class<G> graphClass;
    private final Class<GE> edgeClass;

    public GenericProxyGraphVertex(IGraphVertex vertex, Class<G> graphClass, Class<GE> edgeClass) {
        Objects.requireNonNull(vertex);
        Objects.requireNonNull(graphClass);
        Objects.requireNonNull(edgeClass);

        this.vertex = vertex;
        this.graphClass = graphClass;
        this.edgeClass = edgeClass;
    }

    @Override
    public int hashCode() {
        return vertex.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return vertex.equals(obj);
    }

    @Override
    public String toString() {
        return vertex.toString();
    }

    @Override
    public G graph() {
        //noinspection unchecked
        return (G) vertex.graph();
    }

    @Override
    public int id() {
        return vertex.id();
    }

    @Override
    public void unsafeSetGraph(IGraph graph) {
        checkGraph(graph);
        vertex.unsafeSetGraph(graph);
    }

    public void checkedUnsafeSetGraph(G graph) {
        vertex.unsafeSetGraph(graph);
    }

    @Override
    public void unsafeSetId(int id) {
        vertex.unsafeSetId(id);
    }

    @Override
    public boolean isAttachEdge(int eid) {
        return vertex.isAttachEdge(eid);
    }

    @Override
    public boolean isAttachEdge(IGraphEdge edge) {
        checkEdge(edge);
        return vertex.isAttachEdge(edge);
    }

    public boolean checkedIsAttachEdge(GE edge) {
        return vertex.isAttachEdge(edge);
    }

    @Override
    public boolean isOutEdge(int eid) {
        return vertex.isOutEdge(eid);
    }

    @Override
    public boolean isOutEdge(IGraphEdge edge) {
        checkEdge(edge);
        return vertex.isOutEdge(edge);
    }

    public boolean checkedIsOutEdge(GE edge) {
        return vertex.isOutEdge(edge);
    }

    @Override
    public boolean isInEdge(int eid) {
        return vertex.isInEdge(eid);
    }

    @Override
    public boolean isInEdge(IGraphEdge edge) {
        checkEdge(edge);
        return vertex.isInEdge(edge);
    }

    public boolean checkedIsInEdge(GE edge) {
        return vertex.isInEdge(edge);
    }

    @Override
    public Iterable<IGraphEdge> outEdges(int order) {
        return vertex.outEdges(order);
    }

    public Iterable<GE> checkedOutEdges(int order) {
        //noinspection unchecked
        return (Iterable<GE>) vertex.outEdges(order);
    }

    @Override
    public Iterable<IGraphEdge> outEdges() {
        return vertex.outEdges();
    }

    public Iterable<GE> checkedOutEdges() {
        //noinspection unchecked
        return (Iterable<GE>) vertex.outEdges();
    }

    @Override
    public Iterable<IGraphEdge> inEdges(int order) {
        return vertex.inEdges(order);
    }

    public Iterable<GE> checkedInEdges(int order) {
        //noinspection unchecked
        return (Iterable<GE>) vertex.inEdges(order);
    }

    @Override
    public Iterable<IGraphEdge> inEdges() {
        return vertex.inEdges();
    }

    public Iterable<GE> checkedInEdges() {
        //noinspection unchecked
        return (Iterable<GE>) vertex.inEdges();
    }

    @Override
    public Iterable<IGraphEdge> edges(int order) {
        return vertex.edges(order);
    }

    public Iterable<GE> checkedEdges(int order) {
        //noinspection unchecked
        return (Iterable<GE>) vertex.edges(order);
    }

    @Override
    public Iterable<IGraphEdge> edges() {
        return vertex.edges();
    }

    public Iterable<GE> checkedEdges() {
        //noinspection unchecked
        return (Iterable<GE>) vertex.edges();
    }

    @Override
    public int outDegree() {
        return vertex.outDegree();
    }

    @Override
    public int inDegree() {
        return vertex.inDegree();
    }

    @Override
    public int degree() {
        return vertex.degree();
    }

    @Override
    public boolean hasOutEdge() {
        return vertex.hasOutEdge();
    }

    @Override
    public boolean hasInEdge() {
        return vertex.hasInEdge();
    }

    @Override
    public boolean hasEdge() {
        return vertex.hasEdge();
    }

    @Override
    public <V> V putProp(Object key, V value) {
        return vertex.putProp(key, value);
    }

    @Override
    public <V> V removeProp(Object key) {
        return vertex.removeProp(key);
    }

    @Override
    public boolean containsProp(Object key) {
        return vertex.containsProp(key);
    }

    @Override
    public <V> V getProp(Object key) {
        return vertex.getProp(key);
    }

    @Override
    public <V> V mergeProp(Object key, V value, BiFunction<V, V, V> merge) {
        return vertex.mergeProp(key, value, merge);
    }

    @Override
    public <V> V removeProp(Object key, V defaultValue) {
        return vertex.removeProp(key, defaultValue);
    }

    @Override
    public <V> V getProp(Object key, V defaultValue) {
        return vertex.getProp(key, defaultValue);
    }

    @Override
    public Iterable<Object> propKeys() {
        return vertex.propKeys();
    }

    @Override
    public Iterable<Object> propValues() {
        return vertex.propValues();
    }

    @Override
    public int propSize() {
        return vertex.propSize();
    }

    @Override
    public int clearProps() {
        return vertex.clearProps();
    }

    private void checkGraph(IGraph graph) {
        if (!graphClass.isInstance(graph))
            throw new IllegalStateException("the parameter graph type does not match the specified graph type");
    }

    private void checkEdge(IGraphEdge edge) {
        if (!edgeClass.isInstance(edge))
            throw new IllegalStateException("the parameter edge type does not match the specified edge type");
    }
}
