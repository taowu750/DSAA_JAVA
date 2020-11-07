package util.datastructure;


import util.tuple.Tuple2;

/**
 * AVL 树
 */
public class AvlTree<T extends Comparable<? super T>> {

    private static final int ALLOWED_IMBALANCE = 1;

    private int size;
    private AvlNode<T> root;


    public AvlTree() {
        size = 0;
        root = null;
    }


    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    public boolean contains(T t) {
        if (t != null) {
            return contains(t, root);
        } else {
            throw new IllegalArgumentException("Parameter 't' cannot be null!");
        }
    }

    public T findMax() {
        AvlNode<T> max = findMaxNode(root);

        return max != null ? max.element : null;
    }

    public T findMin() {
        AvlNode<T> min = findMinNode(root);

        return min != null ? min.element : null;
    }

    public void insert(T t) {
        if (t != null) {
            root = insertNode(t, root);
        } else {
            throw new IllegalArgumentException("Parameter 't' cannot be null!");
        }
    }

    public void remove(T t) {
        if (t != null) {
            root = removeNode(t, root);
        } else {
            throw new IllegalArgumentException("Parameter 't' cannot be null!");
        }
    }

    public T removeMax() {
        Tuple2<AvlNode<T>, T> result = removeMaxNode(root);
        root = result.a;

        return result.b;
    }

    public T removeMin() {
        Tuple2<AvlNode<T>, T> result = removeMinNode(root);
        root = result.a;

        return result.b;
    }


    private boolean contains(T t, AvlNode<T> start) {
        if (start != null) {
            int compareResult = start.element.compareTo(t);
            if (compareResult < 0) {
                return contains(t, start.rightNode);
            } else if (compareResult > 0) {
                return contains(t, start.leftNode);
            } else {
                return true;
            }
        }

        return false;
    }

    private AvlNode<T> findMaxNode(AvlNode<T> start) {
        if (start != null) {
            AvlNode<T> max = start;
            while (max.rightNode != null) {
                max = max.rightNode;
            }

            return max;
        }

        return null;
    }

    private AvlNode<T> findMinNode(AvlNode<T> start) {
        if (start != null) {
            AvlNode<T> min = start;
            while (min.leftNode != null) {
                min = min.leftNode;
            }

            return min;
        }

        return null;
    }

    private AvlNode<T> insertNode(T t, AvlNode<T> start) {
        if (start == null) {
            size++;
            return new AvlNode<>(t);
        }

        int compareResult = start.element.compareTo(t);
        if (compareResult < 0) {
            start.rightNode = insertNode(t, start.rightNode);
        } else if (compareResult > 0) {
            start.leftNode = insertNode(t, start.leftNode);
        }

        return balance(start);
    }

    private AvlNode<T> removeNode(T t, AvlNode<T> start) {
        if (start != null) {
            int compareResult = start.element.compareTo(t);
            if (compareResult < 0) {
                start.rightNode = removeNode(t, start.rightNode);
            } else if (compareResult > 0) {
                start.leftNode = removeNode(t, start.leftNode);
            } else if (start.leftNode != null && start.rightNode != null) {

            } else {

            }
        }

        return null;
    }

    private Tuple2<AvlNode<T>, T> removeMaxNode(AvlNode<T> start) {
        if (start != null) {
            AvlNode<T> removed = start;
            AvlNode<T> removedParent = start;

            while (removed.rightNode != null) {
                removedParent = removed;
                removed = removed.rightNode;
            }
            if (removed == removedParent) {
                start = removed.leftNode;
            } else {
                removedParent.rightNode = removed.leftNode;
            }
            T removedResult = removed.element;
            freeAvlNode(removed);
            size--;

            return new Tuple2<>(balance(start), removedResult);
        }

        return new Tuple2<>(null, null);
    }

    private Tuple2<AvlNode<T>, T> removeMinNode(AvlNode<T> start) {
        if (start != null) {
            AvlNode<T> removed = start;
            AvlNode<T> removedParent = start;

            while (removed.leftNode != null) {
                removedParent = removed;
                removed = removed.leftNode;
            }
            if (removed == removedParent) {
                start = removed.rightNode;
            } else {
                removedParent.leftNode = removed.rightNode;
            }
            T removedResult = removed.element;
            freeAvlNode(removed);
            size--;

            return new Tuple2<>(balance(start), removedResult);
        }

        return new Tuple2<>(null, null);
    }

    private void freeAvlNode(AvlNode<T> freed) {
        if (freed != null) {
            freed.rightNode = null;
            freed.leftNode = null;
            freed.element = null;
        }
    }

    private int height(AvlNode<T> node) {
        return node == null ? -1 : node.height;
    }

    private AvlNode<T> singleRotateWithLeftChild(AvlNode<T> parent) {
        AvlNode<T> leftChild = parent.leftNode;
        parent.leftNode = leftChild.rightNode;
        leftChild.rightNode = parent;

        parent.height = Math.max(height(parent.leftNode), height(parent.rightNode)) + 1;
        leftChild.height = Math.max(height(leftChild.leftNode), height(parent)) + 1;

        return leftChild;
    }

    private AvlNode<T> singleRotateWithRightChild(AvlNode<T> parent) {
        AvlNode<T> rightChild = parent.rightNode;
        parent.rightNode = rightChild.leftNode;
        rightChild.leftNode = parent;

        parent.height = Math.max(height(parent.leftNode), height(parent.rightNode)) + 1;
        rightChild.height = Math.max(height(parent), height(rightChild.rightNode)) + 1;

        return rightChild;
    }

    private AvlNode<T> doubleRotateWithLeftChild(AvlNode<T> parent) {
        parent.leftNode = singleRotateWithRightChild(parent.leftNode);

        return singleRotateWithLeftChild(parent);
    }

    private AvlNode<T> doubleRotateWithRightChild(AvlNode<T> parent) {
        parent.rightNode = singleRotateWithLeftChild(parent.rightNode);

        return singleRotateWithRightChild(parent);
    }

    private AvlNode<T> balance(AvlNode<T> start) {
        if (start != null) {
            if (height(start.leftNode) - height(start.rightNode) > ALLOWED_IMBALANCE) {
                if (height(start.leftNode.leftNode) >= height(start.leftNode.rightNode)) {
                    start = singleRotateWithLeftChild(start);
                } else {
                    start = doubleRotateWithLeftChild(start);
                }
            } else if (height(start.rightNode) - height(start.leftNode) > ALLOWED_IMBALANCE) {
                if (height(start.rightNode.rightNode) >= height(start.rightNode.leftNode)) {
                    start = singleRotateWithRightChild(start);
                } else {
                    start = doubleRotateWithRightChild(start);
                }
            }
            start.height = Math.max(height(start.leftNode), height(start.rightNode)) + 1;

            return start;
        }

        return null;
    }


    private static class AvlNode<T> {
        T element;
        AvlNode<T> leftNode;
        AvlNode<T> rightNode;
        int height;


        AvlNode(T element, AvlNode<T> leftNode, AvlNode<T> rightNode) {
            this.element = element;
            this.leftNode = leftNode;
            this.rightNode = rightNode;
            height = 0;
        }

        AvlNode(T element) {
            this(element, null, null);
        }
    }
}
