package algs5_string.sec5_data_compression.src;

import org.junit.jupiter.api.Test;
import util.algs.BinaryIn;
import util.algs.BinaryOut;
import util.datastructure.MyPriorityQueue;
import util.io.AlgsDataIO;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 霍夫曼编码，这是一种变长前缀码，可以用来压缩文本文件。一段文本中出现次数越频繁的字符，
 * 我们让它编码长度越短。每个字符的编码不是其他任何一种编码的前缀，这样我们就能将字符
 * 编码写入文件而不用加上任何分隔符。
 * <p>
 * 霍夫曼编码使用单词查找树表示前缀码。其中叶子节点存放不同的字符。左链接表示 0，右链接表示 1。
 * 从根节点出发到叶结点，也就是对应字符的编码。
 * <p>
 * 我们会先写入单词查找树的字节流形式，然后将文本编码写入。这样解码的时候就先读取单词查找树，
 * 然后利用它进行解码。
 * <p>
 * 霍夫曼算法不仅能用于自然语言文本，对各种类型的文件都有效。霍夫曼算法为输入中的定长模式
 * 产生一张变长的编码编译表。
 */
public class HuffmanCompress {

    @Test
    public void testCompress() {
        String s = String.join("\n", AlgsDataIO.getTinyTale());
        System.out.println(s + "\n");

        byte[] encoding = compress(s);
        System.out.println(expand(encoding));
    }

    public static void main(String[] args) {
        PictureDump.dump(AlgsDataIO.getMedTaleIn(), 512, 88, 3);

        PictureDump.dump(new ByteArrayInputStream(compress(String.join("\n", AlgsDataIO.getMedTale()))),
                512, 47, 3);
    }

    public static byte[] compress(String input) {
        // 计算各个字符出现频率
        Map<Character, Integer> freqs = new HashMap<>();
        for (int i = 0; i < input.length(); i++) {
            char ch = input.charAt(i);
            freqs.merge(ch, 1, Integer::sum);
        }

        // 构造字符和对应的编码序列
        Node root = buildTrie(freqs);
        Map<Character, String> chEncoding = buildCode(root);
        // 进行压缩
        try (ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
             BinaryOut binaryOut = new BinaryOut(byteOut)) {
            // 写入单词查找树的二进制表示
            writeTrie(binaryOut, root);
            // 写入字符个数
            binaryOut.write(input.length());
            // 写入文本压缩编码
            for (int i = 0; i < input.length(); i++) {
                String encoding = chEncoding.get(input.charAt(i));
                for (int j = 0; j < encoding.length(); j++) {
                    if (encoding.charAt(j) == '1')
                        binaryOut.write(true);
                    else
                        binaryOut.write(false);
                }
            }
            binaryOut.flush();

            return byteOut.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String expand(byte[] huffmanEncoding) {
        try (BinaryIn binaryIn = new BinaryIn(new ByteArrayInputStream(huffmanEncoding))) {
            Node root = readTrie(binaryIn);
            int N = binaryIn.readInt();
            StringBuilder sb = new StringBuilder(N + 2);
            for (int i = 0; i < N; i++) {
                // 展开第 i 个编码所对应的字符
                Node x = root;
                while (!x.isLeaf()) {
                    if (binaryIn.readBoolean())
                        x = x.right;
                    else
                        x = x.left;
                }
                sb.append(x.ch);
            }

            return sb.toString();
        }
    }

    private static class Node implements Comparable<Node> {

        private char ch;  // 内部结点不会使用此变量
        private int freq;  // 展开过程不会使用此变量
        private final Node left, right;

        Node(char ch, int freq, Node left, Node right) {
            this.ch = ch;
            this.freq = freq;
            this.left = left;
            this.right = right;
        }

        boolean isLeaf() {
            return left == null && right == null;
        }

        @Override
        public int compareTo(Node o) {
            return this.freq - o.freq;
        }
    }

    /**
     * 使用单词查找树构造编译表。
     *
     * @param root
     * @return
     */
    private static Map<Character, String> buildCode(Node root) {
        Map<Character, String> st = new HashMap<>();
        buildCode(st, root, "");

        return st;
    }

    private static void buildCode(Map<Character, String> st, Node x, String s) {
        if (x.isLeaf()) {
            st.put(x.ch, s);
            return;
        }
        buildCode(st, x.left, s + '0');
        buildCode(st, x.right, s + '1');
    }

    private static Node buildTrie(Map<Character, Integer> freq) {
        MyPriorityQueue<Node> pq = new MyPriorityQueue<>(MyPriorityQueue.Order.MIN);
        // 将所有叶结点和对应的字符、频率存入优先队列
        for (Map.Entry<Character, Integer> chFreq : freq.entrySet()) {
            pq.offer(new Node(chFreq.getKey(), chFreq.getValue(), null, null));
        }

        // 不断合并频率最小的两颗树的根结点，构造单词查找树
        while (pq.size() > 1) {
            Node x = pq.poll();
            Node y = pq.poll();
            Node parent = new Node('\0', x.freq + y.freq, x, y);
            pq.offer(parent);
        }

        return pq.poll();
    }

    /**
     * 以先序遍历的方式读取并解析单词查找树
     *
     * @param binaryIn
     * @return
     */
    private static Node readTrie(BinaryIn binaryIn) {
        // 由于最后一个是叶子节点，因此无需担心单词查找树二进制边界问题。
        if (binaryIn.readBoolean())
            return new Node(binaryIn.readChar(), 0, null, null);
        return new Node('\0', 0, readTrie(binaryIn), readTrie(binaryIn));
    }

    /**
     * 使用先序遍历的方式写入单词查找树的二进制表示
     *
     * @param binaryOut
     * @param root
     */
    private static void writeTrie(BinaryOut binaryOut, Node root) {
        if (root.isLeaf()) {
            // 叶子结点先写入一个 1
            binaryOut.write(true);
            // 写入 8 位字符编码
            binaryOut.write(root.ch);

            return;
        }
        // 内部节点先写入一个 1
        binaryOut.write(false);
        writeTrie(binaryOut, root.left);
        writeTrie(binaryOut, root.right);
    }
}
