package algs5_string.sec5_data_compression.src;

import util.algs.BinaryOut;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * 展示随机生成的数据。
 */
public class RandomBits {

    public static void main(String[] args) {
        try (ByteArrayOutputStream byteArrayOut = new ByteArrayOutputStream();
        BinaryOut binaryOut = new BinaryOut(byteArrayOut)) {
            int x = 11111;
            for (int i = 0; i < 100_0000; i++) {
                x = x * 314159 + 218281;
                binaryOut.write(x > 0);
            }
            binaryOut.flush();

            try (ByteArrayInputStream byteArrayIn = new ByteArrayInputStream(byteArrayOut.toByteArray())) {
                PictureDump.dump(byteArrayIn, 2000, 500, 1);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
