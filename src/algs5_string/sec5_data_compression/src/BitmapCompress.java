package algs5_string.sec5_data_compression.src;

import org.junit.jupiter.api.Test;
import util.algs.BinaryIn;
import util.algs.BinaryOut;
import util.io.AlgsDataIO;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * 位图压缩，使用了游程编码（Run-Length Encoding）。位图每行的开始和结束都是 0，所以每行的游程数量为奇数。
 *
 * 令游程长度在 0 到 255 之间，采用 8 位编码。游程不需要存储所在位是 0 还是 1。两个游程表示的位
 * 相反。当一串位长度大于游程最大长度，则需要添加 0 长度游程以使得位反转正确。
 */
public class BitmapCompress {

    public static byte[] expand(InputStream in) {
        try (BinaryIn binaryIn = new BinaryIn(in);
             ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
             BinaryOut binaryOut = new BinaryOut(byteOut)) {
            boolean b = false;
            while (!binaryIn.isEmpty()) {
                // BinaryIn 的 char 是 8 比特的
                char cnt = binaryIn.readChar();
                for (int i = 0; i < cnt; i++) {
                    binaryOut.write(b);
                }
                b = !b;
            }
            binaryOut.flush();

            return byteOut.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static byte[] compress(InputStream in) {
        try (BinaryIn binaryIn = new BinaryIn(in);
             ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
             BinaryOut binaryOut = new BinaryOut(byteOut)) {
            char cnt = 0;
            boolean b, old = false;
            while (!binaryIn.isEmpty()) {
                b = binaryIn.readBoolean();
                if (b != old) {
                    binaryOut.write(cnt);
                    cnt = 0;
                    old = !old;
                } else if (cnt == 255) {
                    // 如果达到了游程最大长度，则不仅需要写入长度，还要写入一个 0。
                    // 因为每遇到一个长度，old 就会翻转一次。我们确保要有连续的游程。
                    binaryOut.write(cnt);
                    cnt = 0;
                    binaryOut.write(cnt);
                }
                cnt++;
            }
            // 别忘了写入最后一个游程长度
            binaryOut.write(cnt);
            binaryOut.flush();

            return byteOut.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testHexOut() {
        HexDump.dump(AlgsDataIO.openQ32x48(), 16);
        System.out.println();

        HexDump.dump(new ByteArrayInputStream(compress(AlgsDataIO.openQ32x48())), 16);
        System.out.println();
    }

    public static void main(String[] args) {
        PictureDump.dump(AlgsDataIO.openQ32x48(), 32, 48, 6);
        PictureDump.dump(new ByteArrayInputStream(compress(AlgsDataIO.openQ32x48())),
                32, 36, 6);

        // 游程编码随着位图分辨率的提高，效果也会提高。
        PictureDump.dump(AlgsDataIO.openQ64x96(), 64, 96, 6);
        PictureDump.dump(new ByteArrayInputStream(compress(AlgsDataIO.openQ64x96())),
                64, 36, 6);
    }
}
