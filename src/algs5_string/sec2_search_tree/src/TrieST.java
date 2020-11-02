package algs5_string.sec2_search_tree.src;

import util.datastructure.MyQueue;

import java.util.Queue;


// TODO: 使用字母表改造

/**
 * 单词查找树实现。这是一个 R 向单词查找树。
 *
 * 此实现不适用于长键和单向分支的情况
 *
 * @param <V>
 */
@SuppressWarnings("ALL")
public class TrieST<V> implements StringST<V> {

    private int R;
    private Node<V> root;


    public TrieST(int R) {
        this.R = R;
    }

    public TrieST() {
        this(256);
    }


    @Override
    public boolean isEmpty() {
        return root != null;
    }

    @Override
    public int size() {
        return size(root);
    }

    @Override
    public void put(String k, V v) {
        root = put(root, k, v, 0);
    }

    @Override
    public void delete(String k) {
        delete(root, k, 0);
    }

    @Override
    public V get(String k) {
        Node<V> x = get(root, k, 0);
        return x != null ? x.v : null;
    }

    @Override
    public boolean contains(String key) {
        return get(key) != null;
    }

    @Override
    public String longestPrefixOf(String s) {
        int length = search(root, s, 0, 0);

        return s.substring(0, length);
    }

    @Override
    public Iterable<String> keys() {
        return keysWithPrefix("");
    }

    @Override
    public Iterable<String> keysWithPrefix(String pre) {
        Queue<String> q = new MyQueue<>();
        collect(get(root, pre, 0), pre, q);

        return q;
    }

    @Override
    public Iterable<String> keysThatMatch(String pat) {
        Queue<String> q = new MyQueue<>();
        collect(root, "", pat, q);

        return q;
    }


    private static class Node<V> {
        private V v;
        private Node<V>[] next;


        public Node(int R) {
            next = new Node[R];
        }

        public Node(int R, V v) {
            this(R);
            this.v = v;
        }
    }

    private int size(Node<V> x) {
        if (x == null)
            return 0;

        int cnt = 0;
        if (x.v != null)
            cnt++;
        for (char c = 0; c < R; c++)
            cnt += size(x.next[c]);

        return cnt;
    }

    private Node<V> get(Node<V> x, String k, int d) {
        if (x == null)
            return null;
        if (d == k.length())
            return x;

        char c = k.charAt(d);
        return get(x.next[c], k, d + 1);
    }

    private Node<V> put(Node<V> x, String k, V v, int d) {
        if (x == null)
            x = new Node(R);
        if (d == k.length()) {
            x.v = v;
            return x;
        }

        char c = k.charAt(d);
        x.next[c] = put(x.next[c], k, v, d + 1);

        return x;
    }

    private void collect(Node<V> x, String pre, Queue<String> q) {
        if (x == null)
            return;
        if (x.v != null)
            q.offer(pre);
        for (char c = 0; c < R; c++)
            collect(x.next[c], pre + c, q);
    }

    private void collect(Node<V> x, String pre, String pat, Queue<String> q) {
        int d = pre.length();
        if (x == null)
            return;
        if (d == pat.length()) {
            if (x.v != null)
                q.offer(pre);
            else
                return;
        }

        char next = pat.charAt(d);
        for (char c = 0; c < R; c++) {
            if (next == '.' || next == c)
                collect(x.next[c], pre + c, pat, q);
        }
    }

    private int search(Node<V> x, String s, int d, int length) {
        if (x == null)
            return length;
        if (x.v != null)
            length = d;
        if (d == s.length())
            return length;

        char c = s.charAt(d);
        return search(x.next[c], s, d + 1, length);
    }

    private Node<V> delete(Node<V> x, String key, int d) {
        if (x == null)
            return null;
        if (d == key.length())
            x.v = null;
        else {
            char c = key.charAt(d);
            x.next[c] = delete(x.next[c], key, d + 1);
        }

        // 如果 x 上有值或者还有子结点，就保留 x
        if (x.v != null)
            return x;
        for (char c = 0; c < R; c++) {
            if (x.next[c] != null)
                return x;
        }

        // 如果 x 没有值且没有子结点了，就删除 x
        return null;
    }
}
