package util.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * 用来读取 InputStream 的工具类。
 */
public class InputStreamGetter {

    private static final String DEFAULT_ENCODE = "utf8";


    public static String getString(InputStream inputStream, String encode) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, encode));
        StringBuilder content = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            content.append(line).append("\n");
        }
        content.deleteCharAt(content.length() - 1);

        return content.toString();
    }

    public static String getString(InputStream inputStream) throws IOException {
        return getString(inputStream, DEFAULT_ENCODE);
    }

    public static byte[] getBytes(InputStream inputStream) throws IOException {
        byte[] bytes = new byte[inputStream.available()];
        inputStream.read(bytes);

        return bytes;
    }
}
