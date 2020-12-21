package algs5_string.sec2_search_tree.src;


import util.datastructure.MyQueue;

import java.util.Queue;

/**
 * 三向单词查找树。
 *
 * @param <V>
 */
public class TST<V> implements StringST<V> {

    private Node root;


    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public void put(String k, V v) {
        root = put(root, k, v, 0);
    }

    // 删除操作需要删除 size（它和它的子结点中所有值的数量）为 0 的结点。因此每个结点都需要记录 size
    @Override
    public void delete(String k) {
    }

    @Override
    public V get(String k) {
        Node x = get(root, k, 0);
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
        Queue<String> q = new MyQueue<>();
        collect(root, root != null ? root.c + "" : "", q);

        return q;
    }

    @Override
    public Iterable<String> keysWithPrefix(String pre) {
        Node x = get(root, pre, 0);
        Queue<String> q = new MyQueue<>();
        collect(x, pre, q);

        return q;
    }

    @Override
    public Iterable<String> keysThatMatch(String pat) {
        Queue<String> q = new MyQueue<>();
        collect(root, "", pat, q);

        return q;
    }


    private class Node {
        Node left, mid, right;
        char c;
        V v;


        Node() {}

        Node(char c) {
            this.c = c;
        }
    }

    private Node get(Node x, String key, int d) {
        if (x == null)
            return null;
        char c = key.charAt(d);
        if (c < x.c)
            return get(x.left, key, d);
        else if (c > x.c)
            return get(x.right, key, d);
        else {
            if (d == key.length() - 1)
                return x;
            else
                return get(x.mid, key, d + 1);
        }
    }

    private Node put(Node x, String key, V v, int d) {
        char c = key.charAt(d);
        if (x == null) {
            x = new Node(c);
        }

        if (c < x.c)
            x.left = put(x.left, key, v, d);
        else if (c > x.c)
            x.right = put(x.right, key, v, d);
        else {
            if (d == key.length() - 1)
                x.v = v;
            else
                x.mid = put(x.mid, key, v, d + 1);
        }

        return x;
    }

    private int search(Node x, String s, int d, int length) {
        if (x == null)
            return length;
        if (d == s.length())
            return length;

        char c = s.charAt(d);
        if (c == x.c) {
            if (x.v != null)
                length = d + 1;
            return search(x.mid, s, d + 1, length);
        } else if (c < x.c) {
            return search(x.left, s, d, length);
        } else {
            return search(x.right, s, d, length);
        }
    }

    private void collect(Node x, String pre, Queue<String> q) {
        if (x == null)
            return;
        if (x.v != null)
            q.offer(pre);
        if (x.left != null)
            collect(x.left, pre + x.left.c, q);
        if (x.mid != null)
            collect(x.mid, pre + x.mid.c, q);
        if (x.right != null)
            collect(x.right, pre + x.right.c, q);
    }

    private void collect(Node x, String pre, String pat, Queue<String> q) {
        int d = pre.length();
        if (x == null || d > pat.length())
            return;

        if (d == pat.length()) {
            if (x.v != null)
                q.offer(pre);
            else
                return;
        }

        char c = pat.charAt(d);
        if (c == x.c || c == '.') {
            collect(x.mid, pre + c, pat, q);
        } else if (c < x.c) {
            collect(x.left, pre, pat, q);
        } else {
            collect(x.right, pre, pat, q);
        }
    }
}
