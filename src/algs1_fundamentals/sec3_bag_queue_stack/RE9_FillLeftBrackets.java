package algs1_fundamentals.sec3_bag_queue_stack;

import org.junit.jupiter.api.Test;
import util.datastructure.MyStack;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RE9_FillLeftBrackets {

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
}
