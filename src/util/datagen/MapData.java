package util.datagen;


import util.tuple.TwoTuple;

import java.util.LinkedHashMap;

/**
 * 使用对象生成器填充一个MapData对象,该对象可以用作容器类对象的测试数据
 *
 * @author wutao
 */
public class MapData<K, V> extends LinkedHashMap<K, V> {

    public MapData(IGenerator<TwoTuple<K, V>> gen, int quantity) {
        for (int i = 0; i < quantity; i++) {
            TwoTuple<K, V> tuple = gen.next();
            put(tuple.a, tuple.b);
        }
    }

    public MapData(IGenerator<K> genK, IGenerator<V> genV, int quantity) {
        for (int i = 0; i < quantity; i++) {
            put(genK.next(), genV.next());
        }
    }

    public MapData(IGenerator<K> genK, V value, int quantity) {
        for (int i = 0; i < quantity; i++) {
            put(genK.next(), value);
        }
    }

    public MapData(Iterable<K> genK, IGenerator<V> genV) {
        for (K key : genK) {
            put(key, genV.next());
        }
    }

    public MapData(Iterable<K> genK, V value) {
        for (K key : genK) {
            put(key, value);
        }
    }


    public static <K, V> MapData<K, V> map(IGenerator<TwoTuple<K, V>> gen, int quantity) {
        return new MapData<K, V>(gen, quantity);
    }

    public static <K, V> MapData<K, V> map(IGenerator<K> genK, IGenerator<V> genV, int quantity) {
        return new MapData<K, V>(genK, genV, quantity);
    }

    public static <K, V> MapData<K, V> map(IGenerator<K> genK, V value, int quantity) {
        return new MapData<K, V>(genK, value, quantity);
    }

    public static <K, V> MapData<K, V> map(Iterable<K> genK, IGenerator<V> genV) {
        return new MapData<K, V>(genK, genV);
    }

    public static <K, V> MapData<K, V> map(Iterable<K> genK, V value) {
        return new MapData<K, V>(genK, value);
    }
}
