package algs2_sort.sec4_heap_sort;

import org.junit.jupiter.api.Test;
import util.algs.StdRandom;
import util.datastructure.MyPriorityQueue;

import java.util.*;
import java.util.function.BiPredicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.stream.Collectors.joining;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;

public class Exam {

    // TODO: 接近
    /**
     * ### 24. 使用链接的优先队列。保证操作时间为对数级别
     *
     * @param <T>
     */
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

    public static class Cube implements Comparable<Cube> {
        int i, j;

        public Cube(int i, int j) {
            // 保证 i 小于 j
            this.i = Math.min(i, j);
            this.j = Math.max(i, j);
        }

        @Override
        public int compareTo(Cube o) {
            return (int) ((Math.pow(j, 3) - Math.pow(o.j, 3)) +
                    (Math.pow(i, 3) - Math.pow(o.i, 3)));
        }

        @Override
        public String toString() {
            return "(i=" + i + ", j=" + j +")";
        }
    }

    // TODO: 接近
    /**
     * ### 25. 找出0-N之间，满足 a^3+b^3=c^3+d^3 的不同整数对 a,b,c,d。
     *
     * @param N
     * @return
     */
    public List<List<Cube>> findEqualCubeSum(int N) {
        List<List<Cube>> matchedInts = new ArrayList<>();

        MyPriorityQueue<Cube> heap = new MyPriorityQueue<>(MyPriorityQueue.Order.MIN,
                N);
        IntStream.range(1, N + 1).mapToObj(i -> new Cube(0, i)).forEach(heap::offer);

        // 这里没有对超过两个cube相等的情况进行处理，因为我太懒了
        Cube last = null;
        while (!heap.isEmpty()) {
            Cube cube = heap.poll();
            if (last != null) {
                if (cube.i < N && cube.j - cube.i != 1)
                    heap.offer(new Cube(cube.i + 1, cube.j));
                if (last.compareTo(cube) == 0)
                    matchedInts.add(Stream.of(last, cube).collect(Collectors.toList()));
            }
            last = cube;
        }

        return matchedInts;
    }

    // TODO: 不行 - 未能真正认识到堆近似排序的特性，此外思维定势
    /**
     * ### 30. 设计一种数据类型，支持在对数时间内插入元素、常数时间内找到中位数、对数时间内删除中位数。
     * 有奇数个数，则取最中间的数作为中位数；偶数个数，取中间偏左的数作为中位数
     * @param <T>
     */
    public static class MedianHeap<T extends Comparable<T>> {
    }

    /**
     * ### 31. 使用二分查找改善swim()操作的比较次数，实现已写在{@link MyPriorityQueue}。
     */
    public static void binarySwim() {}

    // TODO: 接近
    /**
     * ### 35. 离散概率分布的采样。编写一个Sample类，它的构造函数接受一个double数组p。
     * random() 以概率p[i]/T返回下标i，其中T是p的元素之和。
     * change(i, v) 将i处的值改变为v。
     */
    public static class Sample {

        private static class Node {
            double weight;
            double sumSubTreeWeights;

            Node(double weight) {
                this.weight = weight;
            }

            double sum() {
                return weight + sumSubTreeWeights;
            }
        }

        private Node[] tree;

        public Sample(double[] p) {
            int size = p.length;
            tree = new Node[size + 1];
            for (int i = size; i >= 1; i++) {
                tree[i] = new Node(p[i - 1]);
                tree[i].sumSubTreeWeights += i * 2 <= size ? tree[i * 2].weight : 0;
                tree[i].sumSubTreeWeights += i * 2 + 1 <= size ? tree[i * 2 + 1].weight : 0;
            }
        }

        public int random() {
            double t = StdRandom.uniform(0, tree[1].sum());
            int idx = 1;
            while (Double.compare(t, tree[idx].weight) > 0) {
                t -= tree[idx].weight;
                if (idx * 2 + 1 <= tree.length) {
                    boolean left = StdRandom.bernoulli();
                    if (left) {
                        // 减去右子树权值
                        t -= tree[idx * 2 + 1].sum();
                        idx *= 2;
                    } else {
                        // 减去左子树权值
                        t -= tree[idx * 2].sum();
                        idx *= 2;
                        idx += 1;
                    }
                } else if (idx * 2 <= tree.length) {
                    idx = idx * 2;
                    break;
                } else {
                    break;
                }
            }

            return idx - 1;
        }

        public void change(int i, double v) {
            i = i + 1;
            double diff = tree[i].weight - v;
            tree[i].weight = v;
            for (i /= 2; i >= 1; i /= 2)
                tree[i].sumSubTreeWeights -= diff;
        }
    }
}
