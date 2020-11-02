package algs1_fundamentals.sec2_data_abstraction;


import util.algs.StdOut;
import util.io.FormatPrint;

/**
 * 1.2.6<br/>
 * 如果字符串 s 中的字符循环移动任意位置后能够得到另一个字符串 t，那么 s 就被称为 t 的回环变位。<br/>
 * 例如，ACTGACG 就是 TGACGAC 的一个回环变位，反之亦然。判断这个条件在基因序列的研究中是很重要的。
 * <p>
 * 提示：答案只需要一行用到 indexOf()、length() 和字符串连接的代码。
 */
public class E6_CirculationRotation {

    /**
     * 判断a和b是否互为回环变位字符串。<br/>
     * 找出a第一个字符在b中的位置，然后是第二个，直到两者位置的差值不等于第一个的位置，
     * 然后截下从开始位置到不等于的位置并接到后面，判断新的字符串是否和b相同，若相同，则两者
     * 互为回环变位字符串。
     *
     * @param a 字符串
     * @param b 另一个字符串
     * @return 如果是回环变位字符串，返回true，否则返回false
     */
    public static boolean isCirculationRotation1(String a, String b) {
        if (a.length() == b.length()) {
            int length = a.length();
            int move   = b.indexOf(a.charAt(0));
            if (move == -1) {
                return false;
            }
            int i = 1;
            for (; i < length; i++) {
                if (b.indexOf(a.charAt(i)) - i != move) {
                    break;
                }
            }
            String c = a.substring(i, a.length()) + a.substring(0, i);
            if (c.equals(b)) {
                return true;
            }
        }

        return false;
    }

    /**
     * 第二中判断方法，更加的简洁
     *
     * @param a 字符串
     * @param b 另一个字符串
     * @return 如果是回环变位字符串，返回true，否则返回false
     */
    public static boolean isCirculationRotation2(String a, String b) {
        return a.length() == b.length() && a.concat(a).contains(b);
    }


    public static void main(String[] args) {
        String[] rightData = {"ACTGACG", "TGACGAC"};
        String[] errorData = {"ACTAGC", "AGCATC"};

        FormatPrint.printInstruction("第一种判断方法");
        StdOut.println(rightData[0] + " - " + rightData[1] + "：" + isCirculationRotation1(rightData[0], rightData[1]));
        StdOut.println(errorData[0] + " - " + errorData[1] + ": " + isCirculationRotation1(errorData[0], errorData[1]));
        FormatPrint.printInstruction("第二种判断方法");
        StdOut.println(rightData[0] + " - " + rightData[1] + "：" + isCirculationRotation2(rightData[0], rightData[1]));
        StdOut.println(errorData[0] + " - " + errorData[1] + ": " + isCirculationRotation2(errorData[0], errorData[1]));
    }
}
