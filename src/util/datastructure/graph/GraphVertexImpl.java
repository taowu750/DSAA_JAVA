package util.datastructure.graph;

/**
 * {@link IGraphVertex}的默认实现类。
 */
public class GraphVertexImpl implements IGraphVertex {

    protected IProps props;
    protected IGraph graph;
    protected int id;

    public GraphVertexImpl() {
    }

    public GraphVertexImpl(IGraph graph, int id) {
        this.graph = graph;
        this.id = id;
    }

    @Override
    public IGraph graph() {
        return graph;
    }

    @Override
    public int id() {
        return id;
    }

    @Override
    public void unsafeSetGraph(IGraph graph) {
        this.graph = graph;
    }

    @Override
    public void unsafeSetId(int id) {
        this.id = id;
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
