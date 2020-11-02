package util.datastructure;

/**
 * 符号表。
 */
public interface SymbolTable<K, V> {

    /**
     * 将键值对存入表中。值为空不作操作。
     *
     * @param k
     * @param v
     */
    V put(K k, V v);

    V get(K k);

    V delete(K k);

    boolean contains(K k);

    boolean isEmpty();

    int size();

    void clear();

    Iterable<K> keys();


    public class Entry<K, V> {
        private K key;
        private V value;


        public Entry(K key, V value) {
            this.key = key;
            this.value = value;
        }


        public K key() {
            return key;
        }

        public V value() {
            return value;
        }

        public V setValue(V value) {
            V tmp = this.value;
            this.value = value;

            return value;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == this)
                return true;
            if (!(obj instanceof Entry))
                return false;

            Entry o = (Entry) obj;

            return key.equals(o.key) && value.equals(o.value);
        }

        @Override
        public int hashCode() {
            return key.hashCode();
        }
    }
}
