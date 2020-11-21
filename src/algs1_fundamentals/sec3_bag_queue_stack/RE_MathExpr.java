package algs1_fundamentals.sec3_bag_queue_stack;

import org.junit.jupiter.api.Test;
import util.datastructure.MyStack;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * 计算数学表达式
 */
public class RE_MathExpr {

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
}
