package algs5_string.sec5_data_compression.src;

import util.algs.BinaryIn;
import util.algs.Picture;

import java.awt.*;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.nio.file.Paths;

/**
 * 以指定高宽度打印文件的图片形式。黑色表示 1，白色表示 0。
 */
public class PictureDump {

    public static void dump(InputStream in, int width, int height, int bitSize) {
        try (BinaryIn binaryIn = new BinaryIn(in)) {
            Picture picture = new Picture(width * bitSize, height * bitSize);
            for (int row = 0; row < height; row++) {
                for (int col = 0; col < width; col++) {
                    Color color = Color.RED;
                    if (!binaryIn.isEmpty()) {
                        boolean bit = binaryIn.readBoolean();
                        color = Color.WHITE;
                        if (bit)
                            color = Color.BLACK;
                    }
                    for (int i = 0; i < bitSize; i++) {
                        for (int j = 0; j < bitSize; j++) {
                            picture.set(col * bitSize + i, row * bitSize + j, color);
                        }
                    }
                }
            }
            picture.show();
        }
    }

    public static void dump(InputStream in, int width, int height) {
        dump(in, width, height, 20);
    }

    public static void dump(byte[] bytes, int width, int height, int bitSize) {
        dump(new ByteArrayInputStream(bytes), width, height, bitSize);
    }

    public static void main(String[] args) throws FileNotFoundException {
        InputStream in = new FileInputStream(Paths.get("src", "algs5_string",
                "sec5_data_compression", "src", "abra.txt").toAbsolutePath().toString());
        dump(in, 16, 6);
    }
}
