package algs2_sort.sec4_heap_sort;

import util.algs.StdRandom;

// TODO: 接近
/**
 * 离散概率分布的采样。编写一个Sample类，它的构造函数接受一个double数组p。
 * random() 以概率p[i]/T返回下标i，其中T是p的元素之和。
 * change(i, v) 将i处的值改变为v。
 */
public class E35_DisperedDistrubtion {

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
