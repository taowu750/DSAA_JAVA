package algs2_sort.sec4_heap_sort;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.Random;
import java.util.function.BiPredicate;
import java.util.stream.Stream;

import static java.util.stream.Collectors.joining;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;

// TODO: 接近
/**
 * 使用链接的优先队列。保证操作时间为对数级别
 */
public class RE24_LinkHeap {

    public static class LinkHeap<T extends Comparable<T>> {

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

        private BiPredicate<Node, Node> less = (c1, c2) -> c1.item.compareTo(c2.item) < 0;

        // 完全数最底端的父节点组
        private LinkedList<Node> bottomNodes = new LinkedList<>();
        private int inserted = 0;


        public void offer(T t) {
            if (root == null) {
                root = new Node(t);
                inserted = 0;
                bottomNodes.addLast(root);
            } else {
                Node node = new Node(t);
                Node parent = bottomNodes.get(inserted);
                if (parent.left == null) {
                    parent.left = node;
                } else if (parent.right == null) {
                    parent.right = node;
                } else if (inserted < bottomNodes.size() - 1) {
                    parent = bottomNodes.get(++inserted);
                    parent.left = node;
                } else {
                    // 移除上一层节点，添加下一层节点
                    int len = bottomNodes.size();
                    for (int i = 0; i < len; i++) {
                        Node n = bottomNodes.removeFirst();
                        bottomNodes.addLast(n.left);
                        bottomNodes.addLast(n.right);
                    }
                    inserted = 0;
                    parent = bottomNodes.get(inserted);
                    parent.left = node;
                }
                node.parent = parent;

                swim(node);
            }
            size++;
        }

        public T poll() {
            if (root == null)
                return null;
            else {
                T result = root.item;
                Node last;
                Node parent = bottomNodes.get(inserted);
                if (parent.right != null)
                    last = parent.right;
                else if (parent.left != null)
                    last = parent.left;
                else {
                    if (inserted > 0) {
                        parent = bottomNodes.get(--inserted);
                        last = parent.right;
                    } else {
                        // 退回到上一层
                        int len = bottomNodes.size();
                        if (len > 1) {
                            for (int i = 0; i < len; i += 2) {
                                bottomNodes.addLast(bottomNodes.get(0).parent);
                                bottomNodes.removeFirst();
                                bottomNodes.removeFirst();
                            }
                            inserted = bottomNodes.size() - 1;
                            parent = bottomNodes.getLast();
                            last = parent.right;
                        } else {
                            inserted = 0;
                            bottomNodes.removeFirst();
                            last = root;
                        }
                    }
                }

                if (last == root) {
                    root = null;
                } else {
                    if (last == last.parent.left)
                        last.parent.left = null;
                    else
                        last.parent.right = null;
                    root.item = last.item;
                    sink(root);
                }
                size--;

                return result;
            }
        }

        public T peek() {
            return root != null ? root.item : null;
        }

        public int size() {
            return size;
        }

        public void printTree() {
            printTree(root, 0);
        }


        private void printTree(Node start, int deep) {
            if (start != null) {
                System.out.println(Stream.generate(() -> "-")
                        .limit(deep)
                        .collect(joining()) + start.item);
                printTree(start.left, deep + 1);
                printTree(start.right, deep + 1);
            }
        }

        private void sink(Node start) {
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
    }

    @Test
    public void test_LinkHeap() throws Exception {
        LinkHeap<Integer> heap = new LinkHeap<>();
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

        heap.printTree();
        System.out.println();

        assertArrayEquals(Stream.generate(heap::poll)
                .mapToInt(i -> i)
                .limit(sorted.length)
                .peek(i -> System.out.print(i + " "))
                .toArray(), sorted);
    }
}
