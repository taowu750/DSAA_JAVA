package algs1_fundamentals.sec3_bag_queue_stack;


import util.datastructure.MyArrayList;
import util.datastructure.MyStack;
import util.algs.StdIn;
import util.algs.StdOut;

import java.util.Collections;

/**
 * 1.3.9<br/>
 * 从标准输入得到一个缺少左括号的算术表达式并打印出补全左括号的中序表达式。<br/>
 * 例如：1+2)*3-4)*5-6)) --> ((1+2)*((3-4)*(5-6))。<br/>
 * 注意：算术表达式可能是一个数，或者是由一对括号、两个算术表达式和一个操作数组成的表达式；
 * 并且操作数为简单的一位十进制数。
 */
public class E9_FillLeftBrackets {

    private static final MyArrayList<String> OPERANDS = new MyArrayList<>();
    private static final MyArrayList<String> OPERATORS = new MyArrayList<>();
    static {
        Collections.addAll(OPERANDS, "0", "1", "2", "3", "4", "5", "6", "7", "8", "9");
        Collections.addAll(OPERATORS, "+", "-", "*", "/");
    }

    public static String printFilledExpression(String absentExpression) {
        MyStack<String> expressions = new MyStack<>();
        MyStack<String> operators = new MyStack<>();

        for (int i = 0; i < absentExpression.length(); i++) {
            String s = absentExpression.charAt(i) + "";
            if (OPERANDS.contains(s)) {
                expressions.push(s);
            } else if (OPERATORS.contains(s)) {
                operators.push(s);
            } else if (")".equals(s)) {
                String operator = operators.pop();
                String expression2 = expressions.pop();
                String expression1 = expressions.pop();
                expressions.push("(" + expression1 + operator + expression2 + ")");
            }
        }

        return expressions.pop();
    }


    public static void main(String[] args) {
        StdOut.print("请输入缺失了左括号的表达式：");
        String absentExpression = StdIn.readLine();
        StdOut.println("补全后的表达式：" + printFilledExpression(absentExpression));
    }
}
