package util.datastructure.graph;

import java.util.Objects;
import java.util.function.BiFunction;

/**
 * 此类是{@link IGraphEdge}的代理类，它可以适配任意的边实现，并增加类型约束。
 *
 * 通过继承此类，子类可以指定的图类型和顶点类型。
 * 通过这种方式，可以强制子类使用指定的图类型和顶点类型。此类提供了编译期和运行时的类型检查。
 *
 * @param <G> 指定的图类型
 * @param <GV> 指定的顶点类型
 */
public class GenericProxyGraphEdge<G extends IGraph, GV extends IGraphVertex> implements IGraphEdge {

    private final IGraphEdge edge;
    private final Class<GV> vertexClass;

    public GenericProxyGraphEdge(IGraphEdge edge, Class<GV> vertexClass) {
        Objects.requireNonNull(edge);
        Objects.requireNonNull(vertexClass);

        this.edge = edge;
        this.vertexClass = vertexClass;
    }

    @Override
    public int hashCode() {
        return edge.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return edge.equals(obj);
    }

    @Override
    public String toString() {
        return edge.toString();
    }

    @Override
    public G graph() {
        //noinspection unchecked
        return (G) edge.graph();
    }

    @Override
    public int id() {
        return edge.id();
    }

    @Override
    public void unsafeSetId(int id) {
        edge.unsafeSetId(id);
    }

    @Override
    public EdgeType type() {
        return edge.type();
    }

    @Override
    public boolean isDirected() {
        return edge.isDirected();
    }

    @Override
    public GV from() {
        //noinspection unchecked
        return (GV) edge.from();
    }

    @Override
    public GV to() {
        //noinspection unchecked
        return (GV) edge.to();
    }

    @Override
    public void unsafeSetFrom(IGraphVertex from) {
        checkVertex(from);
        edge.unsafeSetFrom(from);
    }

    public void checkedUnsafeSetFrom(GV from) {
        edge.unsafeSetFrom(from);
    }

    @Override
    public void unsafeSetTo(IGraphVertex to) {
        checkVertex(to);
        edge.unsafeSetTo(to);
    }

    public void checkedUnsafeSetTo(GV to) {
        edge.unsafeSetTo(to);
    }

    @Override
    public GV other(IGraphVertex vertex) {
        checkVertex(vertex);
        //noinspection unchecked
        return (GV) edge.other(vertex);
    }

    public GV checkedOther(GV vertex) {
        //noinspection unchecked
        return (GV) edge.other(vertex);
    }

    @Override
    public GV other(int vid) {
        //noinspection unchecked
        return (GV) edge.other(vid);
    }

    @Override
    public boolean isFrom(IGraphVertex vertex) {
        checkVertex(vertex);
        return edge.isFrom(vertex);
    }

    public boolean checkIsFrom(GV vertex) {
        return edge.isFrom(vertex);
    }

    @Override
    public boolean isFrom(int vid) {
        return edge.isFrom(vid);
    }

    @Override
    public boolean isTo(IGraphVertex vertex) {
        checkVertex(vertex);
        return edge.isTo(vertex);
    }

    @Override
    public boolean isTo(int vid) {
        return edge.isTo(vid);
    }

    @Override
    public boolean isAttachVertex(int vid) {
        return edge.isAttachVertex(vid);
    }

    @Override
    public boolean isAttachVertex(IGraphVertex vertex) {
        checkVertex(vertex);
        return edge.isAttachVertex(vertex);
    }

    public boolean checkedIsAttachVertex(GV vertex) {
        return edge.isAttachVertex(vertex);
    }

    @Override
    public boolean isSelfCircle() {
        return edge.isSelfCircle();
    }

    @Override
    public <V> V putProp(Object key, V value) {
        return edge.putProp(key, value);
    }

    @Override
    public <V> V mergeProp(Object key, V value, BiFunction<V, V, V> merge) {
        return edge.mergeProp(key, value, merge);
    }

    @Override
    public <V> V removeProp(Object key) {
        return edge.removeProp(key);
    }

    @Override
    public <V> V removeProp(Object key, V defaultValue) {
        return edge.removeProp(key, defaultValue);
    }

    @Override
    public boolean containsProp(Object key) {
        return edge.containsProp(key);
    }

    @Override
    public <V> V getProp(Object key) {
        return edge.getProp(key);
    }

    @Override
    public <V> V getProp(Object key, V defaultValue) {
        return edge.getProp(key, defaultValue);
    }

    @Override
    public Iterable<Object> propKeys() {
        return edge.propKeys();
    }

    @Override
    public Iterable<Object> propValues() {
        return edge.propValues();
    }

    @Override
    public int propSize() {
        return edge.propSize();
    }

    @Override
    public int clearProps() {
        return edge.clearProps();
    }

    private void checkVertex(IGraphVertex vertex) {
        if (vertex != null && !vertexClass.isInstance(vertex)) {
            throw new IllegalStateException("the parameter vertex type does not match the specified vertex type");
        }
    }
}
