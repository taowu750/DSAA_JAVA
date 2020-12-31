package util.datastructure.graph;

public abstract class AbstractGraphVertex implements IGraphVertex {

    protected IProps props;

    public AbstractGraphVertex() {
        this.props = new HashMapProps();
    }

    @Override
    public <V> V putProp(Object key, V value) {
        return props.putProp(key, value);
    }

    @Override
    public <V> V removeProp(Object key) {
        return props.removeProp(key);
    }

    @Override
    public <V> V removeProp(Object key, V defaultValue) {
        return props.removeProp(key, defaultValue);
    }

    @Override
    public <V> V getProp(Object key) {
        return props.removeProp(key);
    }

    @Override
    public <V> V getProp(Object key, V defaultValue) {
        return props.getProp(key, defaultValue);
    }

    @Override
    public Iterable<Object> propKeys() {
        return props.propKeys();
    }

    @Override
    public Iterable<Object> propValues() {
        return props.propValues();
    }

    @Override
    public int propSize() {
        return props.propSize();
    }

    @Override
    public int clearProps() {
        return props.clearProps();
    }
}
