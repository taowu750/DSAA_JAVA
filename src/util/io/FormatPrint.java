package util.io;

import util.base.Procedure;

import java.util.Arrays;

/**
 * 在控制台格式化的输出标题等内容
 *
 * @author wutao
 */
public class FormatPrint {
    private static final char TITLE_SIGN = '=';
    private static final int DEFAULT_TITLE_SIGNS_LENGTH = 16;

    private static final char INSTRUCTION_SIGN = '-';
    private static final int DEFAULT_INSTRUCTION_SIGNS_LENGTH = 6;


    /**
     * 在 procedure 运行前打印标题，当 isPrintEnd 为 true 时在结尾打印分隔线。
     *
     * @param procedure
     * @param title
     * @param sign
     * @param signLen
     * @param endLines
     * @param isPrintEnd
     */
    public static void group(Procedure procedure,
                             String title,
                             char sign,
                             int signLen,
                             int endLines,
                             boolean isPrintEnd) {
        char[] signs = new char[signLen];
        Arrays.fill(signs, sign);
        String frag = new String(signs);

        String head = frag + " " + title + " " + frag;
        System.out.println(head);
        procedure.run();
        if (isPrintEnd) {
            signs = new char[title.length()];
            Arrays.fill(signs, sign);
            System.out.println(frag + sign + new String(signs) + sign + frag);
        }
        for (int i = 0; i < endLines; i++) {
            System.out.println();
        }
    }

    public static void group(Procedure procedure,
                             String title,
                             char sign,
                             int signLen,
                             int endLines) {
        group(procedure, title, sign, signLen, endLines, true);
    }

    public static void group(Procedure procedure,
                             String title,
                             char sign,
                             int signLen) {
        group(procedure, title, sign, signLen, 1);
    }

    public static void group(Procedure procedure,
                             String title,
                             char sign) {
        group(procedure, title, sign, 10);
    }

    public static void group(Procedure procedure,
                             String title,
                             int signLen,
                             int endLines) {
        group(procedure, title, '=', signLen, endLines);
    }

    public static void group(Procedure procedure,
                             String title,
                             int endLines) {
        group(procedure, title, '=', 10, endLines);
    }

    public static void group(Procedure procedure,
                             String title,
                             boolean isPrintEnd) {
        group(procedure, title, '=', 10, 1, isPrintEnd);
    }

    public static void group(Procedure procedure,
                             String title) {
        group(procedure, title, '=');
    }

    @Deprecated
    public static void printTitle(String title, int signNums) {
        StringBuilder s = new StringBuilder();

        for (int i = 0; i < signNums; i++) {
            s.append(TITLE_SIGN);
        }
        s.append("   ");
        s.append(title);
        s.append("   ");
        for (int i = 0; i < signNums; i++) {
            s.append(TITLE_SIGN);
        }

        System.out.println(s);
    }

    @Deprecated
    public static void printTitle(String title) {
        printTitle(title, DEFAULT_TITLE_SIGNS_LENGTH);
    }

    @Deprecated
    public static void printInstruction(String instruction, int signNums) {
        StringBuilder s = new StringBuilder();

        for (int i = 0; i < signNums; i++) {
            s.append(INSTRUCTION_SIGN);
        }
        s.append("  ");
        s.append(instruction);
        s.append("  ");
        for (int i = 0; i < signNums; i++) {
            s.append(INSTRUCTION_SIGN);
        }

        System.out.println(s);
    }

    @Deprecated
    public static void printInstruction(String instruction) {
        printInstruction(instruction, DEFAULT_INSTRUCTION_SIGNS_LENGTH);
    }

    @Deprecated
    public static void newLine() {
        System.out.println();
    }

    public static void newLine(int lines) {
        for (int i = 0; i < lines; i++) {
            System.out.println();
        }
    }
}
