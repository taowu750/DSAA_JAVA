package util.datastructure;

import java.util.Queue;

/**
 * Created by wutao on 2019/10/5.
 */
@SuppressWarnings("Duplicates")
public class BinaryTreeST<K extends Comparable<K>, V> implements SortedSymbolTable<K, V> {

    private Node root;
    // 利用缓存变量解决 put、deleteMin、deleteMax 方法返回旧值的问题
    private Entry<K, V> tmp;


    public Iterable<K> iter(K lo, K hi) {
        Queue<K> queue = new MyQueue<>();
        iter(root, queue, lo, hi);

        return queue;
    }

    private void iter(Node root, Queue<K> queue, K lo, K hi) {
        if (root != null) {
            if (root.key.compareTo(lo) < 0)
                iter(root.right, queue, lo, hi);
            else if (root.key.compareTo(hi) > 0)
                iter(root.left, queue, lo, hi);
            else {
                iter(root.left, queue, lo, hi);
                queue.add(root.key);
                iter(root.right, queue, lo, hi);
            }
        }
    }

    public void levelTraversal() {
        MyQueue<Node> q = new MyQueue<>();
        q.offer(root);

        int levelSize = 1;
        while (!q.isEmpty()) {
            while (levelSize > 0) {
                Node n = q.poll();
                if (n != null) {
                    System.out.print("{k:" + n.key + ", v:" + n.val + "} ");
                    q.offer(n.left);
                    q.offer(n.right);
                } else {
                    System.out.print("{null, null} ");
                }
                levelSize--;
            }
            System.out.println();
            levelSize = q.size();
        }
    }

    public void inOrder() {
        inOrder(root);
    }

    private void inOrder(Node root) {
        if (root != null) {
            inOrder(root.left);
            System.out.println(root + ":" + root.size);
            inOrder(root.right);
        }
    }

    public int innerPathLength() {
        return innerPathLength(root, 0);
    }

    /*
    内部路径长要除掉叶节点
     */
    private int innerPathLength(Node root, int length) {
        if (root == null || (root.left == null && root.right == null))
            return 0;

        // length 表示 root 的内部路径长
        return innerPathLength(root.left, length + 1) +
                innerPathLength(root.right, length + 1) + length;
    }

    public V loopPut(K k, V v) {
        if (root == null) {
            root = new Node(k, v);
            return v;
        }

        MyStack<Node> s = new MyStack<>();
        Node cur = root;
        V r = v;
        while (cur != null) {
            int cmp = k.compareTo(cur.key);
            if (cmp == 0) {
                r = cur.val;
                cur.val = v;
                break;
            } else if (cmp < 0) {
                s.push(cur);
                cur = cur.left;
            } else {
                s.push(cur);
                cur = cur.right;
            }
        }
        if (cur == null) {
            Node parent = s.pop();
            parent.size++;
            while (!s.isEmpty()) {
                s.pop().size++;
            }
            int cmp = k.compareTo(parent.key);
            if (cmp < 0)
                parent.left = new Node(k, v);
            else
                parent.right = new Node(k, v);
        }

        return r;
    }

    private class Record {
        int state;
        Node node;
        int cmpLo;
        int cmpHi;

        public Record(int state, Node node, int cmpLo, int cmpHi) {
            this.state = state;
            this.node = node;
            this.cmpLo = cmpLo;
            this.cmpHi = cmpHi;
        }
    }

    public Iterable<K> loopKeys() {
        Entry<K, V> min = min(), max = max();
        Queue<K> queue = new MyQueue<>();

        if (min != null && max != null) {
            K lo = min.key(), hi = max.key();
            MyStack<Record> s = new MyStack<>();
            Record cur = new Record(0, root, lo.compareTo(root.key),
                    hi.compareTo(root.key));
            while (true) {
                if (cur.node == null || cur.state == 2) {
                    if (s.isEmpty())
                        break;
                    cur = s.pop();
                } else if (cur.state == 0) {
                    int cmpLo = lo.compareTo(cur.node.key);
                    int cmpHi = hi.compareTo(cur.node.key);
                    if (cmpLo < 0) {
                        s.push(new Record(1, cur.node, cmpLo, cmpHi));
                        cur.node = cur.node.left;
                    } else {
                        cur.state = 1;
                    }
                } else if (cur.state == 1) {
                    if (cur.cmpLo <= 0 && cur.cmpHi >= 0) {
                        queue.offer(cur.node.key);
                    }
                    if (cur.cmpHi > 0) {
                        s.push(new Record(2, cur.node, cur.cmpLo, cur.cmpHi));
                        cur.node = cur.node.right;
                        cur.state = 0;
                    } else {
                        cur.state = 2;
                    }
                }
            }
        }

        return queue;
    }

    // ========================== 上面是练习 ============================


    @Override
    public V put(K k, V v) {
        if (k == null || v == null)
            return null;

        root = put(root, k, v);
        if (tmp != null) {
            V r = tmp.value();
            tmp = null;

            return r;
        }

        return v;
    }

    @Override
    public V get(K k) {
        if (size(root) == 0 || k == null)
            return null;

        Node node = get(root, k);

        return node != null ? node.val : null;
    }

    @Override
    public V delete(K k) {
        if (k == null)
            return null;

        root = delete(root, k);
        if (tmp != null) {
            V r = tmp.value();
            tmp = null;

            return r;
        }

        return null;
    }

    @Override
    public boolean contains(K k) {
        return k != null && contains(root, k);
    }

    @Override
    public boolean isEmpty() {
        return root == null;
    }

    @Override
    public int size() {
        return size(root);
    }

    @Override
    public void clear() {
        clear(root);
        root = null;
    }

    @Override
    public Entry<K, V> min() {
        Node node = min(root);

        return node != null ? new Entry<>(node.key, node.val) : null;
    }

    @Override
    public Entry<K, V> max() {
        Node node = max(root);

        return node != null ? new Entry<>(node.key, node.val) : null;
    }

    @Override
    public Entry<K, V> floor(K k) {
        if (k == null)
            return null;

        Node node = floor(root, k);
        return node != null ? new Entry<>(node.key, node.val) : null;
    }

    @Override
    public Entry<K, V> ceil(K k) {
        if (k == null)
            return null;

        Node node = ceil(root, k);
        return node != null ? new Entry<>(node.key, node.val) : null;
    }

    @Override
    public int rank(K k) {
        if (k == null)
            return 0;

        return rank(root, k);
    }

    @Override
    public Entry<K, V> select(int k) {
        if (k < 0 || k >= size(root))
            return null;

        Node node = select(root, k);
        return new Entry<>(node.key, node.val);
    }

    @Override
    public Entry<K, V> deleteMin() {
        root = deleteMin(root);
        if (tmp != null) {
            Entry<K, V> r = tmp;
            tmp = null;

            return r;
        }

        return null;
    }

    @Override
    public Entry<K, V> deleteMax() {
        root = deleteMax(root);
        if (tmp != null) {
            Entry<K, V> r = tmp;
            tmp = null;

            return r;
        }

        return null;
    }

    @Override
    public int size(K lo, K hi) {
        Queue<K> queue = new MyQueue<>();
        keys(root, queue, lo, hi);

        return queue.size();
    }

    /**
     * {@inheritDoc}
     * 此迭代器不支持删除操作
     *
     * @param lo
     * @param hi
     * @return
     */
    @Override
    public Iterable<K> keys(K lo, K hi) {
        Queue<K> queue = new MyQueue<>();
        keys(root, queue, lo, hi);

        return queue;
    }

    /**
     * {@inheritDoc}
     * 此迭代器不支持删除操作
     *
     * @return
     */
    @Override
    public Iterable<K> keys() {
        Entry<K, V> min = min(), max = max();
        Queue<K> queue = new MyQueue<>();

        if (min != null && max != null) {
            keys(root, queue, min.key(), max.key());
            queue.offer(max.key());
        }

        return queue;
    }


    private class Node {
        private K key;
        private V val;
        private Node left, right;
        // 以该节点为根的子树中的节点数
        private int size;

        public Node(K key, V val, Node left, Node right, int size) {
            this.key = key;
            this.val = val;
            this.left = left;
            this.right = right;
            this.size = size;
        }


        public Node(K key, V val) {
            this(key, val, null, null, 1);
        }
        @Override
        public String toString() {
            return "{k:" + key + ", v:" + val + "}";
        }

    }

    /*
    相当于中序遍历。
     */
    private void keys(Node root, Queue<K> queue, K lo, K hi) {
        if (root == null)
            return;

        int cmpLo = lo.compareTo(root.key);
        int cmpHi = hi.compareTo(root.key);

        if (cmpLo < 0)
            keys(root.left, queue, lo, hi);
        if (cmpLo <= 0 && cmpHi > 0)
            queue.offer(root.key);
        if (cmpHi > 0)
            keys(root.right, queue, lo, hi);
    }

    private int size(Node root) {
        if (root == null)
            return 0;
        else
            return root.size;
    }

    private boolean contains(Node root, K k) {
        while (root != null) {
            int cmp = k.compareTo(root.key);
            if (cmp == 0)
                return true;
            else if (cmp < 0)
                root = root.left;
            else
                root = root.right;
        }

        return false;
    }

    private Node get(Node root, K k) {
        while (root != null) {
            int cmp = k.compareTo(root.key);
            if (cmp == 0)
                return root;
            else if (cmp < 0)
                root = root.left;
            else
                root = root.right;
        }

        return null;
    }

    private Node put(Node root, K k, V v) {
        if (root == null) {
            return new Node(k, v);
        }

        int cmp = k.compareTo(root.key);
        if (cmp == 0) {
            tmp = new Entry<>(null, root.val);
            root.val = v;
        } else if (cmp < 0)
            root.left = put(root.left, k, v);
        else
            root.right = put(root.right, k, v);
        // 更新大小
        root.size = size(root.left) + size(root.right) + 1;

        return root;
    }

    private Node delete(Node root, K k) {
        if (root == null)
            return null;

        int cmp = k.compareTo(root.key);
        if (cmp == 0) {
            Entry<K, V> deleteTmp = new Entry<>(null, root.val);
            // 两个子树都存在
            if (root.left != null && root.right != null) {
                root.right = deleteMin(root.right);
                root.key = tmp.key();
                root.val = tmp.value();
            } else {
                // deleteMin 会增加 modCnt，所以上面的不需要在增加 modCnt 了
                if (root.left != null) {
                    root = root.left;
                } else {
                    root = root.right;
                }
            }
            tmp = deleteTmp;
        } else if (cmp < 0) {
            root.left = delete(root.left, k);
        } else {
            root.right = delete(root.right, k);
        }

        if (root != null) {
            root.size = size(root.left) + size(root.right) + 1;
        }

        return root;
    }

    private void clear(Node root) {
        if (root != null) {
            clear(root.left);
            clear(root.right);
            root.key = null;
            root.val = null;
            root.left = null;
            root.right = null;
            root.size = 0;
        }
    }

    private Node min(Node root) {
        if (root == null)
            return null;

        Node node = root;
        while (node.left != null)
            node = node.left;

        return node;
    }

    private Node max(Node root) {
        if (root == null)
            return null;

        Node node = root;
        while (node.right != null)
            node = node.right;

        return node;
    }

    private Node floor(Node root, K k) {
        Node tmp = root;
        while (root != null) {
            int cmp = k.compareTo(root.key);
            if (cmp == 0)
                return root;
            else if (cmp < 0) {
                if (root.left != null && k.compareTo(root.left.key) > 0)
                    // 只是记录，因为有可能更接近的数值在 root 左子树的右子树中
                    tmp = root.left;

                root = root.left;
            } else {
                if (root.right != null) {
                    if (k.compareTo(root.right.key) < 0)
                        tmp = root;
                    else
                        tmp = root.right;
                }

                root = root.right;
            }
        }

        return tmp;
    }

    private Node ceil(Node root, K k) {
        Node tmp = root;
        while (root != null) {
            int cmp = k.compareTo(root.key);
            if (cmp == 0)
                return root;
            else if (cmp < 0) {
                if (root.left != null) {
                    if (k.compareTo(root.left.key) > 0)
                        tmp = root;
                    else
                        tmp = root.left;
                }

                root = root.left;
            } else {
                if (root.right != null && k.compareTo(root.right.key) < 0)
                    tmp = root.right;

                root = root.right;
            }
        }

        return tmp;
    }

    private Node select(Node root, int k) {
        k++;
        if (k == 1)
            return min(root);
        while (k != 0) {
            if (size(root) == k)
                return max(root);
            else if (size(root.left) + 1 == k)
                break;
            else if (size(root.left) == k)
                return max(root.left);
            else if (size(root.left) > k)
                root = root.left;
            else {
                k -= size(root.left) + 1;
                root = root.right;
            }
        }

        return root;
    }

    private int rank(Node root, K k) {
        int size = 0;
        while (root != null) {
            int cmp = k.compareTo(root.key);
            if (cmp == 0)
                return size + size(root.left);
            else if (cmp < 0) {
                root = root.left;
            } else {
                size += size(root.left) + 1;
                root = root.right;
            }
        }

        return size;
    }

    /**
     * 返回新的 root，将删除值存在 tmp 里面。
     *
     * @param root
     * @return
     */
    private Node deleteMin(Node root) {
        if (root == null)
            return null;
        if (root.left == null) {
            tmp = new Entry<>(root.key, root.val);
            return root.right;
        }

        Node parent = root, min = root.left;
        parent.size--;
        while (min.left != null) {
            parent = min;
            parent.size--;
            min = min.left;
        }
        parent.left = min.right;
        tmp = new Entry<>(min.key, min.val);

        return root;
    }

    private Node deleteMax(Node root) {
        if (root == null)
            return null;
        if (root.right == null) {
            tmp = new Entry<>(root.key, root.val);

            return root.left;
        }

        Node parent = root, max = root.right;
        parent.size--;
        while (max.right != null) {
            parent = max;
            parent.size--;
            max = max.right;
        }
        parent.right = max.left;
        tmp = new Entry<>(max.key, max.val);

        return root;
    }
}
