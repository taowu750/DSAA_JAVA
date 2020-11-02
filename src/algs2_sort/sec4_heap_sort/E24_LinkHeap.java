package algs2_sort.sec4_heap_sort;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;
import java.util.function.BiPredicate;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

/**
 * 链接堆，要求操作仍然为O(lgN)
 */
public class E24_LinkHeap<T extends Comparable<T>> {

    private class Node {
        T item;
        Node parent;
        Node left;
        Node right;

        Node(T item) {
            this.item = item;
        }

        @Override
        public String toString() {
            return "" + item;
        }
    }


    private Node root;
    private int size;
    private Node last;

    private BiPredicate<Node, Node> less = (c1, c2) -> c1.item.compareTo(c2.item) < 0;


    public int size() {
        return size;
    }

    public T poll() {
        if (root == null)
            return null;
        else {
            T result = root.item;
            root.item = last.item;
            if (size == 1) {
                root = null;
                last = null;
            } else {
                Node newLast = last;
                if (newLast == last.parent.right)
                    newLast = last.parent.left;
                else {
                    // 找到包含删除节点子树的的右子树根部
                    while (newLast != root && newLast == newLast.parent.left)
                        newLast = newLast.parent;

                    // 已经是满二叉树
                    if (newLast == root) {
                        // 一路向右
                        while (newLast.right != null)
                            newLast = newLast.right;
                    } else {
                        // 先向左一步，再一路向右
                        newLast = newLast.parent.left;
                        while (newLast.right != null)
                            newLast = newLast.right;
                    }
                }

                if (last.parent.left == last)
                    last.parent.left = null;
                else
                    last.parent.right = null;
                last = newLast;
                sink(root);
            }
            size--;

            return result;
        }
    }

    public void offer(T t) {
        if (root == null) {
            root = new Node(t);
            last = root;
        } else {
            Node node = new Node(t);

            if (size == 1) {
                root.left = node;
                node.parent = root;
            } else {
                if (last == last.parent.left) {
                    last.parent.right = node;
                    node.parent = last.parent;
                } else {
                    // 找到包含插入点子树的的左子树根部
                    Node prev = last;
                    while (prev != root && prev == prev.parent.right)
                        prev = prev.parent;

                    // 如果是完全树，一路向左
                    if (prev == root) {
                        while (prev.left != null)
                            prev = prev.left;
                    } else {
                        // 先向右一步再一路向左
                        prev = prev.parent.right;
                        while (prev.left != null)
                            prev = prev.left;
                    }

                    prev.left = node;
                    node.parent = prev;
                }
            }
            last = node;
            swim(last);
        }
        size++;
    }


    private void sink(Node start) {
        if (start == null)
            return;

        T temp = start.item;
        while (start.left != null) {
            Node t = start.left;
            if (start.right != null && less.test(t, start.right))
                t = start.right;

            if (temp.compareTo(t.item) >= 0)
                break;
            start.item = t.item;
            start = t;
        }
        start.item = temp;
    }

    private void swim(Node start) {
        T temp = start.item;
        while (start != root && temp.compareTo(start.parent.item) > 0) {
            start.item = start.parent.item;
            start = start.parent;
        }
        start.item = temp;
    }

    public static void main(String[] args) {
        E24_LinkHeap<Integer> heap = new E24_LinkHeap<>();
        int[] a = new Random().ints(0, 100)
                .limit(15)
                .peek(i -> System.out.print(i + " "))
                .peek(heap::offer)
                .toArray();
        System.out.println();
        int[] sorted = Arrays.stream(a)
                .boxed()
                .sorted(Comparator.reverseOrder())
                .mapToInt(i -> i)
                .peek(i -> System.out.print(i + " "))
                .toArray();
        System.out.println("\n");

        assertArrayEquals(Stream.generate(heap::poll)
                        .mapToInt(i -> i)
                        .limit(sorted.length)
                        .peek(i -> System.out.print(i + " "))
                        .toArray(),
                sorted);
    }
}
