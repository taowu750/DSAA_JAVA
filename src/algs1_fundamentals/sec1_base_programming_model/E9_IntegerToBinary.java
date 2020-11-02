package algs1_fundamentals.sec1_base_programming_model;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * 1.1.9<br/>
 * 编写一段代码，将一个正整数N用二进制表示并转换成一个字符串。<br/>
 * 提示：Java有一个内置方法Integer.toBinaryString()专门完成这个任务。
 */
public class E9_IntegerToBinary {

    public static String int2str(int i) {
        StringBuilder s = new StringBuilder();
        int temp = Math.abs(i);
        do {
            s.insert(0, temp % 2);
            temp /= 2;
        } while (temp != 0);

        if (i < 0) {
            s.insert(0, '-');
        }

        return s.toString();
    }

    public static String toBinaryString(int n) {
        int bufLen = 32, index = bufLen;
        char[] binaryStr = new char[bufLen];
        int value = n;
        do {
            binaryStr[--index] = (char) ('0' + (value & 1));
            value >>>= 1;
        } while (value != 0);

        return new String(binaryStr, index, bufLen - index);
    }

    @Test
    public void testToBinaryString() throws Exception {
        assertEquals(toBinaryString(1), Integer.toBinaryString(1));
        assertEquals(toBinaryString(-1), Integer.toBinaryString(-1));
        assertEquals(toBinaryString(0), Integer.toBinaryString(0));
        assertEquals(toBinaryString(34), Integer.toBinaryString(34));
        assertEquals(toBinaryString(57), Integer.toBinaryString(57));
        assertEquals(toBinaryString(-1024), Integer.toBinaryString(-1024));
    }
}
