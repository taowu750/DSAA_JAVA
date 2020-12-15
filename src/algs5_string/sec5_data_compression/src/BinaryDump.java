package algs5_string.sec5_data_compression.src;

import util.algs.BinaryIn;
import util.algs.StdOut;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;

/**
 * 以指定宽度打印文件的二进制内容。
 */
public class BinaryDump {

    public static void dump(InputStream in, int width) {
        BinaryIn binaryIn = new BinaryIn(in);
        int cnt = 0;
        for (; !binaryIn.isEmpty(); cnt++) {
            if (width == 0) continue;
            if (cnt != 0 && cnt % width == 0) {
                System.out.println();
            }
            if (binaryIn.readBoolean())
                StdOut.print("1");
            else
                StdOut.print("0");
        }
        System.out.println();
        System.out.println(cnt + " bits");
    }

    public static void main(String[] args) throws IOException {
        InputStream in = new FileInputStream(Paths.get("src", "algs5_string",
                "sec5_data_compression", "src", "abra.txt").toAbsolutePath().toString());
        dump(in,16);
    }
}
