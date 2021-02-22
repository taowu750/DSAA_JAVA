package util.datastructure;

import java.util.Objects;

/**
 * 《算法导论》中的红黑树。
 *
 * 《算法导论》导论中的红黑树使用红结点和黑结点（《算法4》中是红链接、黑链接）。
 * 并且满足如下红黑性质：
 * 1. 每个结点或是红色，或是黑色。
 * 2. 根结点是黑色的。
 * 3. 每个叶结点（又叫外部结点）是黑色的。注意，这里的叶结点指的是最底层的 NULL 结点。
 * 4. 如果一个结点是红色的，则它的两个子结点都是黑色的。这保证了不会有连续的红结点。
 * 5. 对于每个结点，从该结点到其所有后代叶结点的简单路径上，均包含相同数目的黑色结点。
 *
 * 从某个结点出发到达一个叶结点的任意一条简单路径上的黑色结点个数称为该结点的黑高。
 * 一颗有 n 个内部结点的红黑树高度至多是 2lg(n+1)（证明见书）。
 *
 * Java 的 HashMap 的 TreeNode 是以《算法导论》方式实现的红黑树。
 */
public class RBTree<K extends Comparable<K>, V> {

    private class Node {
        boolean color;
        Node parent;
        Node left, right;
        K key;
        V value;

        public Node(Node parent, K key, V value) {
            this.color = RED;
            this.parent = parent;
            this.key = key;
            this.value = value;
        }
    }

    private static final boolean RED = true;
    private static final boolean BLACK = false;

    private Node root;

    public V get(K key) {
        Objects.requireNonNull(key);
        Node x = getNode(key);
        return x != null ? x.value : null;
    }

    private Node getNode(K key) {
        Node x = root;
        while (x != null) {
            int cmp = key.compareTo(x.key);
            if (cmp == 0)
                break;
            else if (cmp < 0)
                x = x.left;
            else
                x = x.right;
        }
        return x;
    }

    public V put(K key, V value) {
        Objects.requireNonNull(key);

        Node x = root, parent = x;
        while (x != null) {
            parent = x;
            int cmp = key.compareTo(x.key);
            if (cmp == 0)
                break;
            else if (cmp < 0)
                x = x.left;
            else
                x = x.right;
        }
        if (x != null) {
            V result = x.value;
            x.value = value;

            return result;
        } else {
            x = new Node(parent, key, value);
            if (parent == null)
                root = x;
            else if (parent.key.compareTo(key) < 0)
                parent.left = x;
            else
                parent.right = x;
            insertFixUp(x);

            return null;
        }
    }

    /**
     * 删除操作后，从下往上平衡红黑树，保持红黑树的性质。
     *
     * @param x 开始平衡操作的起始结点，颜色为红色
     */
    private void insertFixUp(Node x) {
        Node xp = x.parent;
        // 如果 x 的父结点 xp 颜色不为红色，则平衡完毕
        while (isRed(xp)) {
            // x 的爷爷结点 xpp 的颜色肯定是黑色。根据性质 4 可得
            Node xpp = xp.parent;
            // 如果 x 的父结点 xp 是 xpp 的左子结点
            if (xp == xpp.left) {
                // x 的叔叔结点 xppr
                Node xppr = xpp.right;
                // 如果 xppr 也是红色
                if (isRed(xppr)) {
                    // 将两个子结点变成黑色，并将红色向上传播。这保持了路径上的黑结点数量不变，
                    // 并且修复了对性质 4 的破坏，仍然保持性质 1、2、3、5
                    xp.color = BLACK;
                    xppr.color = BLACK;
                    xpp.color = RED;
                    // x 向上走
                    x = xpp;
                } else {
                    // 如果 x 是 xp 的右子结点，则进行左旋转。
                    // 这一步是为了统一处理 x 是左子、右子结点的两种情况
                    if (x == xp.right) {
                        leftRotate(x = xp);
                        xp = x.parent;
                        xpp = xp.parent;
                    }
                    // 处理连续两个红色结点。这修复了对性质 4 的破坏，并仍然保持性质 1、2、3、5。
                    // 此操作完成后，x 的父结点颜色为黑色，会退出循环
                    xp.color = BLACK;
                    xpp.color = RED;
                    rightRotate(xpp);
                }
            } else {
                // 否则如果 x 的父结点 xp 是 xpp 的右子结点。这时上面情况的镜像

                Node xppl = xpp.left;
                if (isRed(xppl)) {
                    xp.color = BLACK;
                    xppl.color = BLACK;
                    xpp.color = RED;
                    x = xpp;
                } else {
                    if (x == xp.left) {
                        rightRotate(x = xp);
                        xp = x.parent;
                        xpp = xp.parent;
                    }
                    xp.color = BLACK;
                    xpp.color = RED;
                    leftRotate(xpp);
                }
            }
        }
        // 要满足性质 2
        root.color = BLACK;
    }

    public V remove(K key) {
        Objects.requireNonNull(key);

        // 查找待删结点 x
        Node x = getNode(key);
        if (x == null) {
            return null;
        }

        V result = x.value;
        Node replacement, reParent;
        // 下面的代码中，y 是被删除或移至树内的结点。
        // 如果 y 初始的颜色是黑色，则删除 y 或移动 y 将引起红黑性质的破坏。
        // replacement 表示用来替换 y 的结点。另外，replacement 可能为 null，
        // 因此需要记录它的父结点。
        boolean yInitialColor = x.color;
        // 如果 x 左子结点不存在
        if (x.left == null) {
            // 用 x 的右子结点替换 x
            replacement = x.right;
            transplant(x, replacement);
            reParent = replacement.parent;
        } else if (x.right == null) {
            // 否则如果 x 右子结点不存在

            // 用 x 的左子结点替换 x
            replacement = x.left;
            transplant(x, replacement);
            reParent = replacement.parent;
        } else {
            // 否则 x 的左右结点都存在

            // 找到 x 右子树中的最小结点 y
            Node y = minNode(x.right);
            yInitialColor = y.color;
            replacement = y.right;
            if (replacement == null)
                reParent = y;
            else
                reParent = replacement.parent;
            // 如果 y 不是 x 的孩子
            if (y != x.right) {
                // 用 y 的右子结点替换 y，此时 y 的右子结点(如果存在)的父结点是 y 的父结点
                transplant(y, y.right);
                // 将 y 和 x 的右子结点连接起来
                y.right = x.right;
                y.right.parent = x.right;
            }
            // 用 y 替换 x
            transplant(x, y);
            // 将 y 和 x 的左子结点连接起来
            y.left = x.left;
            y.left.parent = y;
            // y 要完全替换 x，所以颜色也要变
            y.color = x.color;
        }
        if (yInitialColor == BLACK)
            removeFixUp(replacement, reParent);

        return result;
    }

    /**
     * 在删除后平衡红黑树。
     *
     * @param x 开始进行平衡的结点，可能为 null
     * @param xp x 的父结点
     */
    private void removeFixUp(Node x, Node xp) {
        // 初始时，x 是替换 y 的结点。我们假设在删除或移动 y 后，
        // 将黑色推给了 x，x 就有了一重额外的黑色。于是任意包含 x 的路径上的黑色结点数加 1。
        // 如果 x 的颜色是红色，那么现在它的颜色是红黑色；否则 x 的颜色是双重黑色。
        // 这样，就能继续保持性质 5。而现在需要修正性质 1、2、4。

        // 我们的目的是希望将额外的黑色沿树上移，修正性质 1。并在过程中，
        // 也修复性质 2、4。

        // 如果 x 是红黑结点，那么可以简单地将 x 着为单个黑色；
        // 或者 x 是根结点，此时可以简单地移除额外的黑色。
        // 否则，需要在循环中执行适当的旋转和重新着色。
        while (x != root && !isRed(x)) {
            // 下面的代码图示见《算法导论》书。

            if (x == xp.left) {
                Node xpr = xp.right;
                // 情况 1。下面的代码将情况 1 转为情况 2、3 或 4
                if (isRed(xpr)) {
                    xpr.color = BLACK;
                    xp.color = RED;
                    leftRotate(xp);
                    xpr = xp.right;
                }
                // 情况 2。如果是由情况 1 进入的情况 2，则 while 循环结束。
                // 因为新的 x 是红黑色的。
                if (!isRed(xpr.left) && !isRed(xpr.right)) {
                    xpr.color = RED;
                    x = xp;
                    xp = xp.parent;
                } else {
                    // 情况 3。将其转化为情况 4
                    if (isRed(xpr.left)) {
                        xpr.left.color = BLACK;
                        xpr.color = RED;
                        rightRotate(xpr);
                        xpr = xp.right;
                    }
                    // 情况 4。通过改变结点颜色和旋转，将 x 的额外黑色去掉，然后循环终止。
                    xpr.color = xp.color;
                    xpr.right.color = BLACK;
                    xp.color = BLACK;
                    leftRotate(xp);
                    x = root;
                }
            } else {
                // 镜像

                Node xpl = xp.left;
                if (isRed(xpl)) {
                    xpl.color = BLACK;
                    xp.color = RED;
                    rightRotate(xp);
                    xpl = xp.left;
                }
                // 因为新的 x 是红黑色的。
                if (!isRed(xpl.left) && !isRed(xpl.right)) {
                    xpl.color = RED;
                    x = xp;
                    xp = xp.parent;
                } else {
                    if (isRed(xpl.right)) {
                        xpl.right.color = BLACK;
                        xpl.color = RED;
                        leftRotate(xpl);
                        xpl = xp.left;
                    }
                    xpl.color = xp.color;
                    xpl.left.color = BLACK;
                    xp.color = BLACK;
                    rightRotate(xp);
                    x = root;
                }
            }
        }
        if (x != null)
            x.color = BLACK;
    }

    private Node minNode(Node x) {
        while (x.left != null)
            x = x.left;
        return x;
    }

    /**
     * 将结点 u 替换为 v。
     */
    private void transplant(Node u, Node v) {
        if (u.parent == null)
            root = v;
        else if (u == u.parent.left)
            u.parent.left = v;
        else
            u.parent.right = v;
        if (v != null)
            v.parent = u.parent;
    }

    /**
     * 左旋转
     */
    private void leftRotate(Node x) {
        Node r = x.right;
        x.right = r.left;
        if (x.right != null)
            x.right.parent = x;
        r.parent = x.parent;
        if (r.parent == null)
            root = r;
        else if (r.parent.left == x)
            r.parent.left = r;
        else
            r.parent.right = r;
        x.parent = r;
        r.left = x;
    }

    /**
     * 右旋转
     */
    private void rightRotate(Node x) {
        Node l = x.left;
        x.left = l.right;
        if (x.left != null)
            x.left.parent = x;
        l.parent = x.parent;
        if (l.parent == null)
            root = l;
        else if (l.parent.left == x)
            l.parent.left = l;
        else
            l.parent.right = l;
        x.parent = l;
        l.right = x;
    }

    private boolean isRed(Node x) {
        return x == null ? BLACK : x.color;
    }
}
