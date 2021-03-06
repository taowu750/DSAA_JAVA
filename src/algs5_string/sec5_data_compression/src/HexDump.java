package algs5_string.sec5_data_compression.src;

import util.algs.BinaryIn;
import util.algs.StdOut;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.nio.file.Paths;

/**
 * 以指定宽度打印文件的十六进制内容。
 */
public class HexDump {

    public static void dump(InputStream in, int width) {
        try (BinaryIn binaryIn = new BinaryIn(in)) {
            int cnt = 0;
            for (; !binaryIn.isEmpty(); cnt++) {
                if (width == 0) continue;
                if (cnt != 0 && cnt % width == 0) {
                    System.out.println();
                }
                StdOut.print(String.format("%02x", binaryIn.readChar() & 0xff) + " ");
            }
            System.out.println();
            System.out.println((cnt * 8) + " bits");
        }
    }

    public static void dump(byte[] bytes, int width) {
        dump(new ByteArrayInputStream(bytes), width);
    }

    public static void main(String[] args) throws FileNotFoundException {
        InputStream in = new FileInputStream(Paths.get("src", "algs5_string",
                "sec5_data_compression", "src", "abra.txt").toAbsolutePath().toString());
        dump(in,4);
    }
}
