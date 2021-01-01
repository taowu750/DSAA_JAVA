package util.datastructure.graph;

/**
 * 一张属性表。
 */
public interface IProps {

    /**
     * 添加一个属性，键为 key，值为 value。如果 key 已存在，
     * 则用新值替换原来的值并返回它；否则返回 null。
     *
     * 注意键不能为 null。
     *
     * @param key 属性的键
     * @param value 属性的值
     * @param <V> 属性值的类型
     * @return key 存在，返回旧值，否则返回 null
     */
    <V> V putProp(Object key, V value);

    /**
     * 删除一个属性，键为 key，返回对应的值。如果属性不存在抛出{@link IllegalArgumentException}。
     *
     * @param key 属性的键
     * @param <V> 属性值的类型
     * @return 属性存在，返回对应的的值
     * @throws IllegalArgumentException 属性不存在抛出此异常
     */
    <V> V removeProp(Object key);

    /**
     * 删除一个属性，键为 key，返回对应的值。如果属性不存在返回 defaultValue。
     *
     * @param key 属性的键
     * @param defaultValue 属性不存在返回的默认值
     * @param <V> 属性值的类型
     * @return 属性存在，返回对应的的值；否则返回 defaultValue
     */
    default <V> V removeProp(Object key, V defaultValue) {
        try {
            return removeProp(key);
        } catch (IllegalArgumentException e) {
            return defaultValue;
        }
    }

    /**
     * 判断属性是否存在。
     *
     * @param key 属性键
     * @return 属性是否存在
     */
    boolean containsProp(Object key);

    /**
     * 获取键为 key 的属性值。如果属性不存在抛出{@link IllegalArgumentException}。
     *
     * @param key 属性的键
     * @param <V> 属性值的类型
     * @return 属性存在，返回对应的的值
     * @throws IllegalArgumentException 属性不存在抛出此异常
     */
    <V> V getProp(Object key);

    /**
     * 获取键为 key 的属性值。如果属性不存在返回 defaultValue。
     *
     * @param key 属性的键
     * @param <V> 属性值的类型
     * @return 属性存在，返回对应的的值；否则返回 defaultValue
     */
    default <V> V getProp(Object key, V defaultValue) {
        try {
            return getProp(key);
        } catch (IllegalArgumentException e) {
            return defaultValue;
        }
    }

    /**
     * 返回所有属性键的迭代器。
     *
     * @return 所有属性键的迭代器
     */
    Iterable<Object> propKeys();

    /**
     * 返回所有属性键的迭代器。
     *
     * @return 所有属性值的迭代器
     */
    Iterable<Object> propValues();

    /**
     * 返回属性数量。
     *
     * @return 属性数量
     */
    int propSize();

    /**
     * 清除所有属性，返回清除的属性数量。
     *
     * @return 属性数量
     */
    int clearProps();
}
