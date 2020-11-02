package algs1_fundamentals.sec3_bag_queue_stack;

import org.junit.jupiter.api.Test;
import util.datastructure.MyStack;

import java.io.File;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class Exam {
    /**
     * 补全左括号
     * @param infix
     * @return
     */
    public static String completionLeftQuote(String infix) {
        MyStack<Character> operatorStack = new MyStack<>();
        MyStack<String> formulaStack = new MyStack<>();
        for (char c : infix.toCharArray()) {
            if (Character.isDigit(c)) {
                formulaStack.push(c + "");
            } else {
                switch (c) {
                    case '+':
                    case '-':
                    case '*':
                    case '/':
                        operatorStack.push(c);
                        break;

                    case ')':
                        char ope = operatorStack.pop();
                        String right = formulaStack.pop();
                        String left = formulaStack.pop();
                        formulaStack.push("(" + left + ope + right + ")");
                        break;

                    default:
                        break;
                }
            }
        }

        return formulaStack.pop();
    }

    @Test
    public void test_completionLeftQuote() {
        String infix = "1 + 2) * 3 - 4) * 5 - 6)))";
        assertEquals(completionLeftQuote(infix), "((1+2)*((3-4)*(5-6)))");
    }

    public static Map<Character, Integer> primaryMap = new HashMap<>();
    static {
        primaryMap.put('+', 1);
        primaryMap.put('-', 1);
        primaryMap.put('*', 2);
        primaryMap.put('/', 2);
        primaryMap.put('(', 0);
    }

    /**
     * 中序转后序
     * @param infix
     * @return
     */
    public static String infix2Postfix(String infix) {
        StringBuilder postfix = new StringBuilder();
        MyStack<Character> operatorStack = new MyStack<>();

        for (char c : infix.toCharArray()) {
            if (Character.isDigit(c)) {
                postfix.append(c);
            } else {
                switch (c) {
                    case '(':
                        operatorStack.push(c);
                        break;

                    case ')':
                        Character op = operatorStack.pop();
                        while (op != null && op != '(') {
                            postfix.append(op);
                            op = operatorStack.pop();
                        }
                        break;

                    case '+':
                    case '-':
                    case '*':
                    case '/':
                        op = operatorStack.top();
                        while ((op != null) && (primaryMap.get(op) >= primaryMap.get(c))) {
                            postfix.append(operatorStack.pop());
                            op = operatorStack.top();
                        }
                        operatorStack.push(c);
                        break;

                    default:
                        break;
                }
            }
        }

        while (!operatorStack.isEmpty()) {
            postfix.append(operatorStack.pop());
        }

        return postfix.toString();
    }

    @Test
    public void test_infix2Postfix() {
        String infix = "((1 + 2) * ((3 - 4) * (5 - 6)))";
        assertEquals(infix2Postfix(infix), "12+34-56-**");

        infix = "3 * 4 + 2 - 5 * 9 / 2 + (5-6) * (4 + 3)";
        assertEquals(infix2Postfix(infix), "34*2+59*2/-56-43+*+");
    }

    /**
     *
     * @param postfix
     * @return
     */
    public int calcPostfix(String postfix) {
        MyStack<Integer> digitStack = new MyStack<>();
        for (char c : postfix.toCharArray()) {
            if (Character.isDigit(c)) {
                digitStack.push(Integer.parseInt(c + ""));
            } else {
                int right = digitStack.pop();
                int left = digitStack.pop();
                switch (c) {
                    case '+':
                        digitStack.push(left + right);
                        break;

                    case '-':
                        digitStack.push(left - right);
                        break;

                    case '*':
                        digitStack.push(left * right);
                        break;

                    case '/':
                        digitStack.push(left / right);
                        break;
                }
            }
        }

        return digitStack.pop();
    }

    @Test
    public void test_calcPostfix() {
        String infix = "((1 + 2) * ((3 - 4) * (5 - 6)))";
        assertEquals(calcPostfix(infix2Postfix(infix)), 3);

        infix = "3 * 4 + 2 - 5 * 9 / 2 + (5-6) * (4 + 3)";
        assertEquals(calcPostfix(infix2Postfix(infix)), -15);
    }

    /**
     * 循环报数
     * @param N N个人
     * @param M 数M次
     */
    public List<Integer> josephus(int N, int M) {
        boolean[] arr = new boolean[N];
        Arrays.fill(arr, true);
        List<Integer> killed = new ArrayList<>();

        int killNum = 0;
        int idx = 0;
        while (killNum != N) {
            int step = 0;
            while (true) {
                if (arr[idx]) {
                    step++;
                    if (step == M)
                        break;
                }
                idx++;
                if (idx == N)
                    idx = 0;
            }
            arr[idx] = false;
            killed.add(idx);
            killNum++;
        }

        return killed;
    }

    @Test
    public void test_josephus() {
        assertEquals(josephus(7, 2), Arrays.asList(1,3,5,0,4,2,6));
    }

    public void listFiles(File file, int deep) {
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < deep; i++) {
            s.append(" ");
        }
        System.out.println(s + file.getName());
        if (file.isDirectory()) {
            for (File f : file.listFiles()) {
                listFiles(f, deep + 1);
            }
        }
    }

    @Test
    public void test_listFiles() {
        listFiles(new File("D:\\MyDrivers"), 0);
    }
}
