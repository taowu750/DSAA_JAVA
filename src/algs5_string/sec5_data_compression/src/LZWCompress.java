package algs5_string.sec5_data_compression.src;

import algs5_string.sec2_search_tree.src.TST;
import org.junit.jupiter.api.Test;
import util.algs.BinaryIn;
import util.algs.BinaryOut;
import util.io.AlgsDataIO;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * LZW 压缩算法是由 A.Lempel、J.Ziv 和 T.Welch 发明的一张算法。这种方法是
 * 为输入中的变长模式生成一张定长的编码编译表。和霍夫曼编码不同，它在输出中
 * 不需要附加上这张编译表。
 * <p>
 * LZW 压缩算法的基础是维护一张字符串键和编码的编译表。
 * <p>
 * 假设需要读取一串由 7 位 ASCII 编码组成的字符串，LZW 压缩算法会输出一条
 * 8 位字节的输出流。（在实际中使用的参数值一般更大——实现中使用的是 8 位
 * 的输入和 12 位的输出）。
 * <p>
 * LZW 算法原理参见 LZW_Compress.png 和 LZW_ST.png。
 * <p>
 * 对于霍夫曼编码，使用单词查找树是因为任意编码都不会是其他编码的前缀；
 * 但对于 LZW 算法，使用单词查找树是因为每个输入字符串得到的键的前缀
 * 也都是符号表中的一个键。
 */
public class LZWCompress {

    @Test
    public void testCompress() {
        System.out.println(AlgsDataIO.getAbraLZW());

        HexDump.dump(compress(AlgsDataIO.getAbraLZW()), 4);

        System.out.println();
        System.out.println(expand(compress(AlgsDataIO.getAbraLZW())));
    }

    public static void main(String[] args) throws IOException {
        PictureDump.dump(DNACompress.compress(AlgsDataIO.getGenomeVirus()), 512, 25, 3);
        // 效果不如 DNACompress，因为重复数据很少
        PictureDump.dump(compress(AlgsDataIO.getGenomeVirus()), 512, 36, 3);

        // 双城记全文
        String tale = AlgsDataIO.getTale();
        BinaryDump.dump(HuffmanCompress.compress(tale), 0);  // 使用霍夫曼压缩
        BinaryDump.dump(compress(tale), 0);  // 使用 LZW 压缩，效果优于霍夫曼
    }

    private static final int R = 256;  // 输入不同字符个数
    private static final int L = 4096;  // 编码总数 2^12
    private static final int W = 12;  // 编码宽度。

    public static byte[] compress(String input) {
        // 单词查找树
        TST<Integer> st = new TST<>();
        // 将所有单个字符添加进单词查找树中
        for (int i = 0; i < R; i++) {
            st.put("" + (char) i, i);
        }

        try (ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
             BinaryOut binaryOut = new BinaryOut(byteOut)) {
            // 字符个数是 R 个，范围为 [0, R - 1]。令 R 为文件结束（EOF）的编码。
            int code = R + 1;
            while (input.length() > 0) {
                // 找到匹配的最长前缀
                String s = st.longestPrefixOf(input);
                // 将这个最长前缀对应的编码写入输出流
                binaryOut.write(st.get(s), W);

                int t = s.length();
                // 如果最长前缀不等于当前 input，并且编码空间没有被用完，
                // 就为 s+c 分配下一个编码（c 是 s 的后一个字符）
                if (t < input.length() && code < L) {
                    st.put(input.substring(0, t + 1), code++);
                }
                input = input.substring(t);
            }
            // 写入文件结束符
            binaryOut.write(R, W);
            binaryOut.flush();

            return byteOut.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String expand(byte[] encoding) {
        try (ByteArrayInputStream byteIn = new ByteArrayInputStream(encoding);
             BinaryIn binaryIn = new BinaryIn(byteIn)) {
            // 反编译表，编码到字符串
            String[] st = new String[L];

            int i;  // 下一个待补全的编码值
            for(i = 0; i < R; i++) {
                st[i] = "" + (char) i;
            }
            st[i++] = " ";  // 文件结束标记的对应字符串

            StringBuilder stringBuilder = new StringBuilder(encoding.length);
            int codeWord = binaryIn.readInt(W);
            String val = st[codeWord];
            while (true) {
                stringBuilder.append(val);
                // 遇到 EOF，跳出循环
                if (codeWord == R)
                    break;

                // 读取下一个编码
                codeWord = binaryIn.readInt(W);
                // 下一个编码对应的字符串
                String s = st[codeWord];
                // 如果读取的编码当前反编译表中没有，此时 s 为 null
                if (i == codeWord)
                    s = val + val.charAt(0);  // 将上一个字符串和它的首字母拼接得到 codeWord 对应的字符串
                // 编码空间未用尽时
                if (i < L)
                    // 为反编译表添加新的条目。上一个字符串加上下一个字符串的开头
                    st[i++] = val + s.charAt(0);
                // 更新当前编码字符串
                val = s;
            }

            return stringBuilder.toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
