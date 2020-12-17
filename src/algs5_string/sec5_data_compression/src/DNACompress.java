package algs5_string.sec5_data_compression.src;

import algs5_string.Alphabet;
import org.junit.jupiter.api.Test;
import util.algs.BinaryIn;
import util.algs.BinaryOut;
import util.io.AlgsDataIO;
import util.io.InputStreamGetter;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

public class DNACompress {

    private static final Alphabet DNA = new Alphabet("ACTG");

    public static byte[] compress(String genome) throws IOException {
        Objects.requireNonNull(genome);
        int N = genome.length();
        try (ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
             BinaryOut binaryOut = new BinaryOut(byteOut)) {
            // 首先写入 genome 长度
            binaryOut.write(N);
            for (int i = 0; i < N; i++) {
                // 将字符用双位编码表示
                int d = DNA.toIndex(genome.charAt(i));
                binaryOut.write(d, DNA.lgR());
            }
            binaryOut.flush();

            return byteOut.toByteArray();
        }
    }

    public static String expand(byte[] compressedGenome) throws IOException {
        int w = DNA.lgR();
        try (ByteArrayInputStream byteIn = new ByteArrayInputStream(compressedGenome);
             BinaryIn binaryIn = new BinaryIn(byteIn)) {
            int N = binaryIn.readInt();
            StringBuilder sb = new StringBuilder(N + 2);
            for (int i = 0; i < N; i++) {
                char c = binaryIn.readChar(w);
                sb.append(DNA.toChar(c));
            }

            return sb.toString();
        }
    }

    @Test
    public void testGenomeTiny() throws IOException {
        String genomeTiny = "ATAGATGCATAGCGCATAGCTAGATGTGCTAGC";

        // 原始 DNA 序列比特流
        BinaryDump.dump(BinaryDump.fromString(genomeTiny), 64);
        System.out.println();

        // 压缩后的 DNA 序列比特流
        BinaryDump.dump(compress(genomeTiny), 64);
        System.out.println();

        // 压缩后的 DNA 序列 16 进制流
        HexDump.dump(compress(genomeTiny), 8);
        System.out.println();

        // 压缩解压后的 DNA 序列
        System.out.println(expand(compress(genomeTiny)));
        System.out.println();
    }

    public static void main(String[] args) throws IOException {
        // 一个真实的病毒 DNA 序列（50000 位）
        PictureDump.dump(AlgsDataIO.openGenomeVirus(), 512, 98, 4);

        // 压缩后
        try (InputStream in = AlgsDataIO.openGenomeVirus()) {
            PictureDump.dump(new ByteArrayInputStream(compress(InputStreamGetter.getString(in))),
                    512, 25, 4);
        }
    }
}
