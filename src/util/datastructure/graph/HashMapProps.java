package util.datastructure.graph;

import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("unchecked")
public class HashMapProps implements IProps {
    private Map<Object, Object> props;

    public HashMapProps() {
        this.props = new HashMap<>();
    }

    public HashMapProps(int initialCapacity) {
        this.props = new HashMap<>(initialCapacity);
    }

    @Override
    public <V> V putProp(Object key, V value) {
        return (V) props.put(key, value);
    }

    @Override
    public <V> V removeProp(Object key) {
        if (!props.containsKey(key))
            throw new IllegalArgumentException("key is not exist: " + key);

        return (V) props.remove(key);
    }

    @Override
    public boolean containsProp(Object key) {
        return props.containsKey(key);
    }

    @Override
    public <V> V getProp(Object key) {
        if (!props.containsKey(key))
            throw new IllegalArgumentException("key is not exist: " + key);

        return (V) props.get(key);
    }

    @Override
    public Iterable<Object> propKeys() {
        return props.keySet();
    }

    @Override
    public Iterable<Object> propValues() {
        return props.values();
    }

    @Override
    public int propSize() {
        return props.size();
    }

    @Override
    public int clearProps() {
        int size = props.size();
        props.clear();

        return size;
    }
}
