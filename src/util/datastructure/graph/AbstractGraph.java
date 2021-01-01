package util.datastructure.graph;

/**
 * {@link IGraph}的抽象类，实现了其中{@link IProps}的方法。
 */
public abstract class AbstractGraph implements IGraph {

    protected IProps props;

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
