package algs5_string.sec2_search_tree.src;

/**
 * 单词查找树 API
 */
public interface StringST<V> {

    boolean isEmpty();

    int size();

    void put(String k, V v);

    void delete(String k);

    V get(String k);

    boolean contains(String key);

    /**
     * s 的前缀中最长的键。
     *
     * @param s
     * @return
     */
    String longestPrefixOf(String s);

    Iterable<String> keys();

    /**
     * 所有以 pre 为前缀的键。
     *
     * @param pre
     * @return
     */
    Iterable<String> keysWithPrefix(String pre);

    /**
     * 所有与 pat 匹配的键。其中 "." 能匹配任何字符，例如 My.ame 可以匹配 MyName 和 MyXame。
     *
     * @param pat
     * @return
     */
    Iterable<String> keysThatMatch(String pat);
}
