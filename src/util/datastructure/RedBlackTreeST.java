package util.datastructure;

import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * <p>
 * 红黑树。红黑树就是二叉树对 2-3 树的模拟。这里的 2 和 3 指的是链接数而非节点数。
 * 2-3 树的优点是局部变换不会影响全局的平衡性，因为 2-3 树是由下往上生长的，最终
 * 会将根结点变成 3-结点，或者变成临时的 4-结点然后分解成三个 2-结点，后一种情况
 * 会使得树高加 1。
 * </p>
 * <p>
 * 如果将红黑树的红链接画平，你会发现红黑树也就和 2-3 树一样，所有空链接到根结点的
 * 距离是相等的。
 * </p>
 * <p>
 * 对于 2-3 树的插入：<br>
 * - 当向 2-结点插入时，将其转化为 3-结点。<br>
 * - 当向父节点为 2-结点的 3-结点插入时，3-结点变为 4-结点然后分解为三个 2-节点，
 * 最高的 2-结点在并入父节点，使父节点变为 3-结点。<br>
 * - 当向父节点为 3-结点的 3-结点插入时，对此节点的变换与上面一样，但是父节点也将
 * 进行相同的变换。<br>
 * 用红黑树实现算法时，<strong>先插入，然后由下往上变换</strong>。
 * </p>
 * <p>
 * 红黑树定义：<br>
 * - 红链接均为左链接<br>
 * - 没有任何一个节点同时链接两条红链接<br>
 * - 没有连续的红链接
 * - 该数是<strong>完美黑色平衡</strong>的，即任意空链接到根节点的路径上的黑链接数量相同
 * </p>
 * <p>
 * 红黑树旋转操作：<br>
 * - 如果右子结点是红色的而左子结点是黑色的，进行左旋转<br>
 * - 如果左子结点是红色的且它的左子结点也是红色的，进行右旋转<br>
 * - 如果左右子节点都是红色，进行颜色转换<br>
 * 我们总是用红链接将新结点和它的父结点相连
 * </p>
 * <p>
 * <p>
 * 为了实现红黑树的删除操作，我们还要引入 2-3-4 树。因此我们不仅要在为了删除一个结点
 * 而构造临时 4-结点时沿着查找路径向下进行变换，还要在分解遗留的 4-结点时沿着
 * 查找路径向上进行变换。
 * </p>
 * <p>
 * 2-3-4 树允许4-结点的存在，它的插入算法沿查找路径向下进行变换是为了保证
 * 当前节点不是4-结点，这样树底才有空间来插入新的键。在 2-3-4 树的插入中：<br>
 * - 如果根结点是 4-结点，我们就将它分解为三个 2-结点<br>
 * - 如果遇到了父节点为 2-结点的 4-结点，就将它分解为两个 2-结点并将中间键传给父节点，
 * 父节点就变为了 3-结点<br>
 * - 如果遇到了父节点为 3-结点的 4-结点，就将它解为两个 2-结点并将中间键传给父节点，
 * 父节点就变成了 4-结点<br>
 * - 我们不用担心会遇到父节点为 4-节点的 4-节点，因为插入算法本身保证这种情况不会出现，
 * 到达树的底部后，我们也只会遇到2-结点和3-结点，所以我们可以插入新的键。
 * </p>
 * <p>
 * 为了用红黑树表示这个算法，我们需要:<br>
 * - 将 4-结点表示为由三个2-结点组成的一颗平衡的子树，根节点和两个子节点都用红链接相连<br>
 * - 在<strong>向下</strong>的过程中分解所有4-节点并进行颜色转换。
 * - 和插入操作一样，在向上的过程中用旋转将4-节点配平。
 * </p>
 * <p>
 * 在 2-3-4 树的删除最小键操作中，从树底的3-结点删除键是很简单的，但是删除2-结点会破坏树
 * 的完美平衡性。注意4-结点指的是两个子结点都是红色链接的结点。
 * 所以我们在删除最小键的时候，要沿着左链接向下变换，确保当前结点不是 2-结点。
 * 如果根结点是2-节点且两个子节点都是2-节点，就把它们变成4-节点。
 * 需要先执行以下遍历过程：<br>
 * - 如果当前结点的左子结点不是 2-结点，完成<br>
 * - 如果当前结点的左子结点是 2-节点而它的兄弟节点不是 2-结点，将兄弟结点一个键移到
 * 左子结点中<br>
 * - 如果当前结点的左子结点和它的兄弟节点都是 2-节点，将左子结点、父结点的最小键
 * 和左子结点最近的兄弟结点合并为一个4-结点，使父结点由3变为2或由4变为3<br>
 * 最后我们可以得到一个含有最小键的 3-结点或4-结点，然后我们就可以删除并向上分解
 * 所有的临时4-节点。
 * </p>
 * <p>
 * 在删除操作中，我们可以在查找路径上进行和删除最小键一样的操作。然后如果被查找的
 * 键在树的底部，我们可以直接删除；如果不在，我们可以使用和二叉树相同的操作。
 * 最后我们再进行向上回溯并分解余下的4-结点。
 * </p>
 */
@SuppressWarnings("Duplicates")
public class RedBlackTreeST<K extends Comparable<K>, V> implements SortedSymbolTable<K, V> {

    private static final boolean RED = true;
    private static final boolean BLACK = false;

    private Node root;
    private Entry<K, V> tmp;


    @Test
    public void test_delete() throws Exception {
        RedBlackTreeST<Integer, Integer> rbt = new RedBlackTreeST<>();
        int[] arr = new Random().ints(20, -50, 50)
                .distinct()
                .peek(i -> rbt.put(i, i))
                .toArray();

        Arrays.stream(arr).forEach(i -> assertEquals(rbt.delete(i), i));
    }

    @Test
    public void test_deleteMin() throws Exception {
        RedBlackTreeST<Integer, Integer> rbt = new RedBlackTreeST<>();
        List<Integer> list = new Random().ints(20, -50, 50)
                .distinct()
                .peek(i -> rbt.put(i, i))
                .sorted()
                .boxed()
                .collect(Collectors.toList());
        System.out.println();

        list.stream()
                .peek(i -> System.out.print(i + " "))
                .forEach(i -> assertEquals(rbt.deleteMin().value(), i));
    }

    @Test
    public void test_deleteMax() throws Exception {
        RedBlackTreeST<Integer, Integer> rbt = new RedBlackTreeST<>();
        List<Integer> list = new Random().ints(20, -50, 50)
                .distinct()
                .peek(i -> rbt.put(i, i))
                .boxed()
                .sorted(Comparator.reverseOrder())
                .peek(i -> System.out.print(i + " "))
                .collect(Collectors.toList());

        list.forEach(i -> assertEquals(rbt.deleteMax().value(), i));
    }

    public void levelTraversal() {
        MyQueue<Node> q = new MyQueue<>();
        q.offer(root);

        int levelSize = 1;
        while (!q.isEmpty()) {
            while (levelSize > 0) {
                Node n = q.poll();
                if (n != null) {
                    System.out.print(n + " ");
                    q.offer(n.left);
                    q.offer(n.right);
                } else {
                    System.out.print("{null} ");
                }
                levelSize--;
            }
            System.out.println();
            levelSize = q.size();
        }
    }

    public void inOrder() {
        inOrder(root);
        System.out.println();
    }

    public void inOrder(Node root) {
        if (root != null) {
            inOrder(root.left);
            System.out.print(root.key + " ");
            inOrder(root.right);
        }
    }


    private class Record {
        int state;
        Node n;
        // isLeft 表示 n 的下一层结点是不是左结点
        boolean isLeft;


        public Record(int state, Node n, boolean isLeft) {
            this.n = n;
            this.state = state;
            this.isLeft = isLeft;
        }
    }

    public V loopPut(K k, V v) {
        if (k == null || v == null)
            return null;

        V r = v;
        if (root == null)
            root = new Node(k, v, BLACK);
        else {
            int cmp = k.compareTo(root.key);
            if (cmp == 0) {
                r = root.val;
                root.val = v;
            } else {
                MyStack<Record> s = new MyStack<>();
                Record cur = new Record(0, root, cmp < 0);
                while (true) {
                    if (cur.n == null) {
                        if (s.isEmpty())
                            break;
                        cur = s.pop();
                        if (cur.isLeft)
                            cur.n.left = new Node(k, v, RED);
                        else
                            cur.n.right = new Node(k, v, RED);
                    } else if (cur.state == 0) {
                        if (isRed(cur.n.left) && isRed(cur.n.right))
                            flipColors(cur.n);

                        cmp = k.compareTo(cur.n.key);
                        if (cmp == 0) {
                            r = cur.n.val;
                            cur.n.val = v;
                            cur.state = 1;
                        } else if (cmp < 0) {
                            s.push(new Record(1, cur.n, true));
                            cur.n = cur.n.left;
                        } else {
                            s.push(new Record(1, cur.n, false));
                            cur.n = cur.n.right;
                        }
                    } else if (cur.state == 1) {
                        if (isRed(cur.n.right) && !isRed(cur.n.left)) {
                            cur.n = rotateLeft(cur.n);
                        }
                        if (isRed(cur.n.left) && isRed(cur.n.left.left)) {
                            cur.n = rotateRight(cur.n);
                        }
                        cur.n.size = size(cur.n.left) + size(cur.n.right) + 1;
                        Node n = cur.n;

                        // 当进入state1且栈为空，表示此时到了向上回溯的顶部，
                        // 此时需要将当前节点返回给 root
                        if (s.isEmpty()) {
                            root = n;
                            break;
                        }
                        cur = s.pop();
                        if (cur.isLeft)
                            cur.n.left = n;
                        else
                            cur.n.right = n;
                    }
                }
            }
        }

        return r;
    }


    //==================================== 分割线 ====================================

    @Override
    public V put(K k, V v) {
        if (k == null || v == null)
            return null;

        root = put(root, k, v);
        root.color = BLACK;

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
        if (k == null || root == null)
            return null;

        // 保证根节点不是2-结点
        if (!isRed(root.left) && !isRed(root.right))
            root.color = RED;
        root = delete(root, k);
        if (root != null)
            root.color = BLACK;

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
        if (root == null)
            return null;
        // 保证根节点不是2-节点。这样才能进行后面的moveRedLeft操作，因为左子要从根节点借一个
        if (!isRed(root.left) && !isRed(root.right))
            root.color = RED;
        root = deleteMin(root);
        if (root != null)
            root.color = BLACK;

        if (tmp != null) {
            Entry<K, V> r = tmp;
            tmp = null;

            return r;
        }

        return null;
    }

    @Override
    public Entry<K, V> deleteMax() {
        if (root == null)
            return null;
        // 保证根节点不是2-节点
        if (!isRed(root.left) && !isRed(root.right))
            root.color = RED;
        root = deleteMax(root);
        if (root != null)
            root.color = BLACK;

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

    public Iterable<Entry<K, V>> entries() {
        Entry<K, V> min = min(), max = max();
        Queue<Entry<K, V>> queue = new MyQueue<>();

        if (min != null && max != null) {
            entries(root, queue, min.key(), max.key());
            queue.offer(max);
        }

        return queue;
    }


    private class Node {
        K key;
        V val;
        Node left;
        Node right;
        int size;


        // 由其父结点指向它的链接的颜色
        boolean color;

        public Node(K key, V val, Node left, Node right, int size, boolean color) {
            this.key = key;
            this.val = val;
            this.left = left;
            this.right = right;
            this.size = size;
            this.color = color;
        }

        public Node(K key, V val, int size, boolean color) {
            this(key, val, null, null, size, color);
        }


        public Node(K key, V val, boolean color) {
            this(key, val, 1, color);
        }

        @Override
        public String toString() {
            return "{" +
                    "val:" + key +
                    ", val:" + val +
                    ", color:" + color +
                    '}';
        }

    }

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

    private void entries(Node root, Queue<Entry<K, V>> queue, K lo, K hi) {
        if (root == null)
            return;

        int cmpLo = lo.compareTo(root.key);
        int cmpHi = hi.compareTo(root.key);

        if (cmpLo < 0)
            entries(root.left, queue, lo, hi);
        if (cmpLo <= 0 && cmpHi > 0)
            queue.offer(new Entry<>(root.key, root.val));
        if (cmpHi > 0)
            entries(root.right, queue, lo, hi);
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
        if (root == null)
            // 我们总是用红链接将新结点和它的父结点相连
            return new Node(k, v, RED);

        int cmp = k.compareTo(root.key);
        if (cmp == 0) {
            tmp = new Entry<>(null, root.val);
            root.val = v;
        } else if (cmp < 0) {
            root.left = put(root.left, k, v);
        } else {
            root.right = put(root.right, k, v);
        }

        /*
        这两行语句在递归调用之前就是 2-3-4 树的插入操作（允许存在4-节点--一左一右为红--向下过程中分解4-节点），
        否则就是 2-3 树的插入操作。
        这里在递归调用前后都不影响后续的删除操作
         */
        // 进行颜色转换并将红链接在树中向上传递
        if (isRed(root.left) && isRed(root.right))
            flipColors(root);
        // 将任意含有红色右链接的 3-结点向左旋转
        if (isRed(root.right) && !isRed(root.left)) {
            root = rotateLeft(root);
        }
        // 将两条连续红链接中的结点向右旋转
        if (isRed(root.left) && isRed(root.left.left)) {
            root = rotateRight(root);
        }

        root.size = size(root.left) + size(root.right) + 1;

        return root;
    }

    /*
   总是删除最底部的键。当遇到待删除的结点时，使用右子树中的最小结点替换待删除结点。向左搜索时，使用moveRedLeft，
   向右搜索时使用moveRedRight。
    红黑树中不会只有右结点，因为每个结点被插入时都会与红链接相连，而单独的红右结点会被旋转
     */
    private Node delete(Node root, K k) {
        if (k.compareTo(root.key) < 0) {
            // 删除最小值中的操作，就是沿着左链接向下变换
            if (!isRed(root.left) && !isRed(root.left.left))
                root = moveRedLeft(root);
            root.left = delete(root.left, k);
        } else {
            // 删除最大值中的操作，也是沿着右链接向下变换
            // 相等的时候依然要保证右结点不是2-结点
            if (isRed(root.left))
                root = rotateRight(root);
            // 待删结点在底部，可以直接删除
            if (k.compareTo(root.key) == 0 && root.right == null) {
                tmp = new Entry<>(null, root.val);
                return null;
            }

            if (!isRed(root.right) && !isRed(root.right.left))
                root = moveRedRight(root);
            if (k.compareTo(root.key) == 0) {
                Entry<K, V> t = new Entry<>(null, root.val);
                root.right = deleteMin(root.right);
                root.key = tmp.key();
                root.val = tmp.value();
                tmp = t;
            } else {
                root.right = delete(root.right, k);
            }
        }

        // 向上回溯
        return balance(root);
    }

    /*
    树是左倾的。
    树中不会有单独的红右链接，也不会有单独的右结点。树中不会有连续两条黑色左链接（注意根节点是黑色链接），
    所以树的最底层左结点一定会有红色链接与之相连。

    我们在删除最小键的时候，沿着左链接向下变换，确保当前结点不是 2-结点。
    如果根结点是2-节点且两个子节点都是2-节点，就把它们变成4-节点。
    注意4-结点指的是两个子结点都是红色链接的结点。
    需要先执行以下遍历过程：
    - 如果当前结点的左子结点不是 2-结点（注意2-结点指自己没有红链接且左结点也没有红链接），完成
    - 如果当前结点的左子结点是 2-节点而它的兄弟节点不是 2-结点，将兄弟结点一个键移到
      左子结点中
    - 如果当前结点的左子结点和它的兄弟节点都是 2-节点，将左子结点、父结点的最小键
      和左子结点最近的兄弟结点合并为一个 4-结点，使父结点由3变为2或由4变为3
    最后我们可以得到一个含有最小键的 3-结点或 4-结点，然后我们就可以删除并向上分解
    所有的临时4-节点。
     */
    private Node deleteMin(Node root) {
        // 树的最底层左结点一定会有红色链接与之相连，它不会是2-结点，所以可以安全地删除，
        if (root.left == null) {
            tmp = new Entry<>(root.key, root.val);
            return null;
        }

        // 如果当前结点的左子结点是 2-节点
        // 由于没有单独的红右链接，所以只需要检查左链接就可以了
        if (!isRed(root.left) && !isRed(root.left.left))
            root = moveRedLeft(root);
        root.left = deleteMin(root.left);

        return balance(root);
    }

    private Node moveRedLeft(Node root) {
        // 将根节点和左右子结点连接起来
        flipColors(root);
        if (isRed(root.right.left)) {
            // 将兄弟结点一个键移到左子结点中
            // 注意，这会导致出现连续两条红色左链接
            root.right = rotateRight(root.right);
            root = rotateLeft(root);
            // 注意！！！《算法4》书中这一章的习题中的代码缺少这一行，
            // 这一行就表示向兄弟借一个结点
            // 《算法4》里的照样能work，就是不好理解
            flipColors(root);
        }

        return root;
    }

    private Node deleteMax(Node root) {
        /*
         这里比deleteMin多了一步操作，因为右子节点从父节点中获得节点的时候，我们需要将左边节点给于到右边节点，如果我们不移动的话，会破坏树的平衡
                   5,6
               1,2     9   对于所展示的这个红黑树，如果不把5从左边移到右边的话，我们会直接删除9，
                           这样会导致树的不平衡，因为红节点总是在左边的，我们进行删除操作的时候，
                           直接将结点给予，只需要改变颜色即可，不需要移动。
                           对于红黑树而言，6是黑结点，再删除的时候，是不需要移动的，我们移动的是5这样的红结点

         */
        if (isRed(root.left))
            root = rotateRight(root);
        if (root.right == null) {
            tmp = new Entry<>(root.key, root.val);
            return null;
        }

        // 这里和moveRedRight都指考察左结点是不是红的，因为不会有单独的红右链接
        // 右子结点是2-结点
        if (!isRed(root.right) && !isRed(root.right.left))
            root = moveRedRight(root);
        root.right = deleteMax(root.right);

        return balance(root);
    }

    private Node moveRedRight(Node root) {
        flipColors(root);
        // 《算法4》里面这里有"!"号，且没有下面的flipColors
        if (isRed(root.left.left)) {
            // 注意，这里不需要像moveRedLeft那样移动两次，因为左结点没有子红结点
            // 和moveRedLeft类似，此时会有连续的两条红右链接，并且左链接会是黑色
            root = rotateRight(root);
            // 注意！！！《算法4》书中这一章的习题中的代码缺少这一行，
            // 这一行代表向兄弟借一个结点。
            // 《算法4》里的照样能work，就是不好理解
            flipColors(root);
        }

        return root;
    }

    /*
     * 纠正红右结点；去除连续的红色链接；去除临时的 4-结点
     */
    private Node balance(Node root) {
        if (isRed(root.right))
            root = rotateLeft(root);
        // 去除连续两条红色链接
        if (isRed(root.left) && isRed(root.left.left))
            root = rotateRight(root);
        // 去除4-结点
        if (isRed(root.left) && isRed(root.right))
            flipColors(root);

        root.size = size(root.left) + size(root.right) + 1;

        return root;
    }

    private boolean isRed(Node root) {
        // 默认空节点是黑链接
        if (root == null)
            return BLACK;

        return root.color == RED;
    }

    // 红右变红左
    private Node rotateLeft(Node root) {
        Node x = root.right;
        root.right = x.left;
        x.left = root;
        x.color = root.color;
        root.color = RED;
        x.size = root.size;
        root.size = size(root.left) + size(root.right) + 1;

        return x;
    }

    // 红左变红右
    private Node rotateRight(Node root) {
        Node x = root.left;
        root.left = x.right;
        x.right = root;
        x.color = root.color;
        root.color = RED;
        x.size = root.size;
        root.size = size(root.left) + size(root.right) + 1;

        return x;
    }

    /*
    反转颜色
     */
    private void flipColors(Node root) {
        root.color = !root.color;
        root.left.color = !root.left.color;
        root.right.color = !root.right.color;
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
}
