package dsaa4_tree.src;

/**
 * 二叉查找树的实现
 *
 * @author wutao
 */
public class BinarySearchTree<T extends Comparable<? super T>> {
    private int size;
    private TreeNode root;


    public BinarySearchTree() {
        root = null;
        size = 0;
    }


    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void clear() {
        clear(root);
        root = null;
        size = 0;
    }

    public boolean contains(T t) {
        if (t == null) {
            throw new NullPointerException("The parameter \"t\" cannot be null!");
        }

        return contains(t, root);
    }

    public T findMin() {
        TreeNode find = root;

        if (find != null) {
            while (find.left != null) {
                find = find.left;
            }
        }

        return find != null ? find.item : null;
    }

    public T findMax() {
        TreeNode find = root;

        if (find != null) {
            while (find.right != null) {
                find = find.right;
            }
        }

        return find != null ? find.item : null;
    }

    public T removeMin() {
        TreeNode remove = root, father = root;

        if (size == 0) {
            return null;
        } else if (size == 1) {
            T t = root.item;
            root = null;
            size = 0;

            return t;
        } else {
            if (remove.left == null) {
                root = root.right;
            } else {
                while (remove.left != null) {
                    father = remove;
                    remove = remove.left;
                }
                father.left = remove.right;
            }
            size--;

            return  remove.item;
        }
    }

    public T removeMax() {
        TreeNode remove = root, father = root;

        if (size == 0) {
            return null;
        } else if (size == 1) {
            T t = root.item;

            root.left = null;
            root.right = null;
            root = null;
            size = 0;

            return t;
        } else {
            if (remove.right == null) {
                root = root.left;
            } else {
                while (remove.right != null) {
                    father = remove;
                    remove = remove.right;
                }
                father.right = remove.left;
            }
            size--;

            return  remove.item;
        }
    }

    public void insert(T t) {
        if (t == null) {
            throw new NullPointerException("The parameter \"t\" cannot be null!");
        }

        root = insert(t, root);
    }

    public void remove(T t) {
        if (t == null) {
            throw new NullPointerException("The parameter \"t\" cannot be null!");
        }

        root = remove(t, root);
    }


    private class TreeNode {
        public T item;
        public TreeNode left;
        public TreeNode right;


        public TreeNode(T item) {
            this.item = item;
            left = null;
            right = null;
        }

        public TreeNode(T item, TreeNode left, TreeNode right) {
            this.item = item;
            this.left = left;
            this.right = right;
        }
    }

    private class Tuple {
        public TreeNode node;
        public T item;
    }


    private void clear(TreeNode start) {
        if (start != null) {
            clear(start.left);
            clear(start.right);
            start.item = null;
            start.left = null;
            start.right = null;
        }
    }

    private boolean contains(T t, TreeNode start) {
        if (start != null) {
            int compareResult = t.compareTo(start.item);
            if (compareResult == 0) {
                return true;
            } else if (compareResult > 0) {
                contains(t, start.right);
            } else {
                contains(t, start.left);
            }
        }

        return false;
    }

    private TreeNode insert(T t, TreeNode start) {
        if (start == null) {
            start = new TreeNode(t);
            size++;
        } else {
            int compareResult = t.compareTo(start.item);

            if (compareResult > 0) {
                start.right = insert(t, start.right);
            } else if (compareResult < 0) {
                start.left = insert(t, start.left);
            }
        }

        return start;
    }

    private TreeNode remove(T t, TreeNode start) {
        if (start == null) {
            return null;
        }

        int compareResult = t.compareTo(start.item);
        if (compareResult > 0) {
            start = remove(t, start.right);
        } else if (compareResult < 0) {
            start = remove(t, start.left);
        } else if (start.left != null && start.right != null) {
            Tuple tuple = removeMinForRemove(start.right);
            start.item = tuple.item;
            start.right = tuple.node;
        } else {
            start = start.left != null ? start.left: start.right;
            size--;
        }

        return start;
    }

    private Tuple removeMinForRemove(TreeNode start) {
        Tuple t = new Tuple();

        if (start.left == null && start.right == null) {
            t.item = start.item;
            t.node = null;
        } else {
            TreeNode remove = start, father = start;
            if (remove.left == null) {
                t.item = remove.item;
                t.node = remove.right;
            } else {
                while (remove.left != null) {
                    father = remove;
                    remove = remove.left;
                }
                father.left = remove.right;
                t.item = remove.item;
                t.node = start;
            }
        }
        size--;

        return t;
    }
}
