package util.datastructure.graph;

import java.util.Objects;

/**
 * {@link IGraphEdge}的默认实现类。
 */
public class GraphEdgeImpl implements IGraphEdge {

    protected IProps props;
    protected int id;
    protected EdgeType type;
    protected IGraphVertex from, to;

    public GraphEdgeImpl(EdgeType type) {
        this.type = type;
    }

    public GraphEdgeImpl(EdgeType type, IGraphVertex from, IGraphVertex to) {
        this.id = -1;
        this.type = type;
        this.from = from;
        this.to = to;
    }

    public GraphEdgeImpl(IGraphVertex from, IGraphVertex to) {
        this(EdgeType.DIRECTED, from, to);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this)
            return true;

        if (!(obj instanceof IGraphEdge))
            return false;

        IGraphEdge other = (IGraphEdge) obj;

        return graph() == other.graph()
                && id == other.id();
    }

    @Override
    public int hashCode() {
        return Objects.hash(graph(), id);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() +
                "{graph=" + Util.identityString(graph()) +
                ", id=" + id +
                ", type=" + type +
                ", from=" + Util.vertexSimpleString(from) +
                ", to=" + Util.vertexSimpleString(to) +
                "}";
    }

    @Override
    public int id() {
        return id;
    }

    @Override
    public void unsafeSetId(int id) {
        this.id = id;
    }

    @Override
    public EdgeType type() {
        return type;
    }

    @Override
    public IGraphVertex from() {
        return from;
    }

    @Override
    public IGraphVertex to() {
        return to;
    }

    @Override
    public void unsafeSetFrom(IGraphVertex from) {
        this.from = from;
    }

    @Override
    public void unsafeSetTo(IGraphVertex to) {
        this.to = to;
    }


    @Override
    public <V> V putProp(Object key, V value) {
        return props().putProp(key, value);
    }

    @Override
    public <V> V removeProp(Object key) {
        return props().removeProp(key);
    }

    @Override
    public <V> V removeProp(Object key, V defaultValue) {
        return props().removeProp(key, defaultValue);
    }

    @Override
    public <V> V getProp(Object key) {
        return props().removeProp(key);
    }

    @Override
    public <V> V getProp(Object key, V defaultValue) {
        return props().getProp(key, defaultValue);
    }

    @Override
    public boolean containsProp(Object key) {
        return props().containsProp(key);
    }

    @Override
    public Iterable<Object> propKeys() {
        return props().propKeys();
    }

    @Override
    public Iterable<Object> propValues() {
        return props().propValues();
    }

    @Override
    public int propSize() {
        return props().propSize();
    }

    @Override
    public int clearProps() {
        return props().clearProps();
    }

    /**
     * 只在用到 props 时才创建它。
     */
    protected IProps props() {
        if (props == null)
            props = new HashMapProps();
        return props;
    }
}
