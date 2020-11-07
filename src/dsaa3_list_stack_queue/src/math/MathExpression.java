package dsaa3_list_stack_queue.src.math;


import util.datastructure.MyStack;
import util.tuple.Tuple2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 解析数学表达式并可以进行计算
 */
public final class MathExpression {

    private static final List<MathOperator> MATH_OPERATORS      = new ArrayList<>();
    private static final List<String>       MATH_OPERANDS       = new ArrayList<>();
    private static final List<String>       MATH_OPERATOR_SIGNS = new ArrayList<>();


    static {
        MATH_OPERATORS.add(new MathOperator("(", MathOperator.Priority.LEVEL5, MathOperator.Orientation
                .LEFT_TO_RIGHT, MathOperator.BindingQuantity.NONE, new Operation<Double>() {
            @Override
            public Double execute(Object... params) {
                return null;
            }

            @Override
            public String description() {
                return null;
            }
        }));
        MATH_OPERATORS.add(new MathOperator(")", MathOperator.Priority.LEVEL5, MathOperator.Orientation
                .LEFT_TO_RIGHT, MathOperator.BindingQuantity.NONE, new Operation<Double>() {
            @Override
            public Double execute(Object... params) {
                return null;
            }

            @Override
            public String description() {
                return null;
            }
        }));
        MATH_OPERATORS.add(new MathOperator("^", MathOperator.Priority.LEVEL4, MathOperator.Orientation
                .RIGHT_TO_LEFT, MathOperator.BindingQuantity.TWO, new Operation<Double>() {
            @Override
            public Double execute(Object... params) {
                if (params.length < 2) {
                    throw new IllegalArgumentException("The number of arguments must have two!");
                }
                Double p1 = (Double) params[0];
                Double p2 = (Double) params[1];

                return Math.pow(p1, p2);
            }

            @Override
            public String description() {
                return "Power calculation";
            }
        }));
        MATH_OPERATORS.add(new MathOperator("+", MathOperator.Priority.LEVEL3, MathOperator.Orientation
                .RIGHT_TO_LEFT, MathOperator.BindingQuantity.ONE, new Operation<Double>() {
            @Override
            public Double execute(Object... params) {
                if (params.length < 1) {
                    throw new IllegalArgumentException("The number of arguments must have one!");
                }
                Double p1 = (Double) params[0];

                return +p1;
            }

            @Override
            public String description() {
                return "Positive sign";
            }
        }));
        MATH_OPERATORS.add(new MathOperator("-", MathOperator.Priority.LEVEL3, MathOperator.Orientation
                .RIGHT_TO_LEFT, MathOperator.BindingQuantity.ONE, new Operation<Double>() {
            @Override
            public Double execute(Object... params) {
                if (params.length < 1) {
                    throw new IllegalArgumentException("The number of arguments must have one!");
                }
                Double p1 = (Double) params[0];

                return -p1;
            }

            @Override
            public String description() {
                return "Minus sign";
            }
        }));
        MATH_OPERATORS.add(new MathOperator("*", MathOperator.Priority.LEVEL2, MathOperator.Orientation
                .LEFT_TO_RIGHT, MathOperator.BindingQuantity.TWO, new Operation<Double>() {
            @Override
            public Double execute(Object... params) {
                if (params.length < 2) {
                    throw new IllegalArgumentException("The number of arguments must have two!");
                }
                Double p1 = (Double) params[0];
                Double p2 = (Double) params[1];

                return p1 * p2;
            }

            @Override
            public String description() {
                return "Multiplication";
            }
        }));
        MATH_OPERATORS.add(new MathOperator("/", MathOperator.Priority.LEVEL2, MathOperator.Orientation
                .LEFT_TO_RIGHT, MathOperator.BindingQuantity.TWO, new Operation<Double>() {
            @Override
            public Double execute(Object... params) {
                if (params.length < 2) {
                    throw new IllegalArgumentException("The number of arguments must have two!");
                }
                Double p1 = (Double) params[0];
                Double p2 = (Double) params[1];

                return p1 / p2;
            }

            @Override
            public String description() {
                return "Division calculation";
            }
        }));
        MATH_OPERATORS.add(new MathOperator("%", MathOperator.Priority.LEVEL2, MathOperator.Orientation
                .LEFT_TO_RIGHT, MathOperator.BindingQuantity.TWO, new Operation<Double>() {
            @Override
            public Double execute(Object... params) {
                if (params.length < 2) {
                    throw new IllegalArgumentException("The number of arguments must have two!");
                }
                Double p1 = (Double) params[0];
                Double p2 = (Double) params[1];

                return (double) (p1.intValue() % p2.intValue());
            }

            @Override
            public String description() {
                return "Remainder calculation";
            }
        }));
        MATH_OPERATORS.add(new MathOperator("+", MathOperator.Priority.LEVEL1, MathOperator.Orientation
                .LEFT_TO_RIGHT, MathOperator.BindingQuantity.TWO, new Operation<Double>() {
            @Override
            public Double execute(Object... params) {
                if (params.length < 2) {
                    throw new IllegalArgumentException("The number of arguments must have two!");
                }
                Double p1 = (Double) params[0];
                Double p2 = (Double) params[1];

                return p1 + p2;
            }

            @Override
            public String description() {
                return "Addition calculation";
            }
        }));
        MATH_OPERATORS.add(new MathOperator("-", MathOperator.Priority.LEVEL1, MathOperator.Orientation
                .LEFT_TO_RIGHT, MathOperator.BindingQuantity.TWO, new Operation<Double>() {
            @Override
            public Double execute(Object... params) {
                if (params.length < 2) {
                    throw new IllegalArgumentException("The number of arguments must have two!");
                }
                Double p1 = (Double) params[0];
                Double p2 = (Double) params[1];

                return p1 - p2;
            }

            @Override
            public String description() {
                return "Subtraction calculation";
            }
        }));

        Collections.addAll(MATH_OPERANDS, "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", ".");
        Collections.addAll(MATH_OPERATOR_SIGNS, "(", ")", "^", "+", "-", "*", "/", "%");
    }


    private MathExpression() {
        throw new IllegalStateException("You cannot obtain objects of this class!");
    }


    /**
     * 根据输入的数学表达式，解析出它的后缀表达式形式，该方法不负责进行错误处理，因此输入必须合法，否则<br/>
     * 无法解析出正确的结果。
     *
     * @param mathInfixExpression 人类可读的中缀数学表达式
     * @return 该数学表达式的后缀形式
     */
    public static MathUnitSequence newMathPostfixExpression(String mathInfixExpression) {
        // 去除输入序列中的空格
        String[]      strings      = mathInfixExpression.split(" ");
        StringBuilder infixBuilder = new StringBuilder();
        for (String s : strings) {
            infixBuilder.append(s);
        }
        String infix = infixBuilder.toString();
        // 构造栈和输出
        MyStack<MathOperator> mathOperatorMyStack = new MyStack<>();
        MathUnitSequence      mathUnitSequence    = new MathUnitSequence();

//        FormatPrint.printInstruction("解析开始");
        // 解析中缀表达式
        for (int i = 0; i < infix.length(); i++) {
            String s = infix.charAt(i) + "";
            // 如果遇到一个操作数
            if (isOperand(s)) {
                // 首先，查看下一个字符是否为数字字符或小数点
                // 如果是，则将其解析为一个数字
                Tuple2<String, Integer> result = getDigitString(i, infix);
//                System.out.println("解析出来的数字：" + result.a + "起始位置和终止位置：" + i + " - " + result.b);
                mathUnitSequence.addUnit(new MathUnitSequence.Unit(MathUnitSequence.OPERAND, Double.valueOf(result.a)
                        , null));
                i = result.b - 1;
            }
            // 如果遇到操作符
            else if (isOperator(s)) {
                // 根据具体情况构造出单目运算符、双目运算符或无目操作符
                MathOperator operator;
                String       preSymbol   = i > 0 ? infix.charAt(i - 1) + "" : null;
                boolean      isMonocular = i == 0 || (isOperator(preSymbol) && !preSymbol.equals(")"));
                if (isMonocular) {
                    operator = getOperator(s, MathOperator.BindingQuantity.ONE);
                } else {
                    operator = getOperator(s, MathOperator.BindingQuantity.TWO);
                }
                if (operator == null) {
                    operator = getOperator(s, MathOperator.BindingQuantity.NONE);
                }
//                System.out.println("解析出来的操作符：" + operator);
                // 如果操作符是一个左括号，直接压入栈中
                if (operator.getSymbol().equals("(")) {
                    mathOperatorMyStack.push(operator);
                }
                // 如果操作符是一个右括号，从栈中一直弹出元素放到输出中，直到遇到一个左括号
                else if (operator.getSymbol().equals(")")) {
                    MathOperator temp = mathOperatorMyStack.pop();
                    while (!temp.getSymbol().equals("(")) {
                        mathUnitSequence.addUnit(new MathUnitSequence.Unit(MathUnitSequence.OPERATOR, null, temp));
                        temp = mathOperatorMyStack.pop();
                    }
                }
                // 否则当栈不为空，进入循环
                else {
                    while (!mathOperatorMyStack.isEmpty()) {
                        // 获取栈顶操作符及比较结果
                        MathOperator top           = mathOperatorMyStack.top();
                        int          compareResult = top.getPriority().compareTo(operator.getPriority());
                        // 如果栈顶操作符优先级大于它
                        if (compareResult > 0) {
                            // 如果不是左括号，将栈顶操作符弹出，放入到输出中，否则将它压入栈中，退出循环
                            if (!top.getSymbol().equals("(")) {
                                mathUnitSequence.addUnit(new MathUnitSequence.Unit(MathUnitSequence.OPERATOR, null,
                                        mathOperatorMyStack.pop()));
                            } else {
                                mathOperatorMyStack.push(operator);
                                break;
                            }
                        }
                        // 如果栈顶操作符优先级等于它
                        else if (compareResult == 0) {
                            // 如果它们的结合方向都是从右往左，则将它放入栈中，然后退出循环
                            if (operator.getOrientation() == MathOperator.Orientation.RIGHT_TO_LEFT &&
                                    top.getOrientation() == MathOperator.Orientation.RIGHT_TO_LEFT) {
                                mathOperatorMyStack.push(operator);
                                break;
                            }
                            // 否则将栈顶操作符弹出，放入到输出中
                            else {
                                mathUnitSequence.addUnit(new MathUnitSequence.Unit(MathUnitSequence.OPERATOR, null,
                                        mathOperatorMyStack.pop()));
                            }
                        } else {
                            // 如果栈顶操作符优先级小于它，将它放入栈中，然后退出循环
                            mathOperatorMyStack.push(operator);
                            break;
                        }
                    }
                    // 如果栈为空，将它放入到栈中
                    if (mathOperatorMyStack.isEmpty()) {
                        mathOperatorMyStack.push(operator);
                    }
                }
            }
        }
        // 将栈中内容全部弹出放到输出中并返回
        while (!mathOperatorMyStack.isEmpty()) {
            mathUnitSequence.addUnit(new MathUnitSequence.Unit(MathUnitSequence.OPERATOR, null,
                    mathOperatorMyStack.pop()));
        }
//        FormatPrint.printInstruction("解析终止");

        return mathUnitSequence;
    }

    /**
     * 计算出根据给定后缀表达式的结果
     *
     * @param postfixExpression 包含组成了后缀表达式单元的数学序列
     * @return 计算的结果
     */
    public static double calculatePostfixExpression(MathUnitSequence postfixExpression) {
//        构造一个栈，用来装载浮点数
        MyStack<Double> results = new MyStack<>();

//        对后缀表达式进行解析
        for (MathUnitSequence.Unit unit : postfixExpression) {
//            如果在序列中遇到操作数，将它压入栈中
            if (unit.getContentType() == MathUnitSequence.OPERAND) {
                results.push(unit.getOperand());
            } else if (unit.getContentType() == MathUnitSequence.OPERATOR) {
//            如果在序列中遇到操作符
//                根据它的结合数，从栈中取出相应数量的操作数，并进行计算，将计算结果压入栈中
                MathOperator operator = unit.getOperator();
                switch (operator.getBindingQuantity()) {
                    case NONE:
                        break;

                    case ONE: {
                        Double d = results.pop();
                        results.push(operator.getOperation().execute(d));
                        break;
                    }

                    case TWO: {
                        Double d2 = results.pop();
                        Double d1 = results.pop();
                        results.push(operator.getOperation().execute(d1, d2));
                        break;
                    }
                }
            }
        }

//        到达序列末尾后，从栈中弹出最终结果并返回
        return results.pop();
    }


    private static boolean isOperand(String s) {
        return MATH_OPERANDS.contains(s);
    }

    private static boolean isOperator(String s) {
        return MATH_OPERATOR_SIGNS.contains(s);
    }

    private static MathOperator getOperator(String symbol, MathOperator.BindingQuantity bindingQuantity) {
        for (MathOperator operator : MATH_OPERATORS) {
            if (operator.getSymbol().equals(symbol) && operator.getBindingQuantity().compareTo(bindingQuantity) == 0) {
                return operator;
            }
        }

        return null;
    }

    private static Tuple2<String, Integer> getDigitString(int digitStart, String source) {
        int index = digitStart;
        while (index < source.length()) {
            if (isOperand(source.charAt(index) + "")) {
                index++;
            } else {
                break;
            }
        }

        return new Tuple2<>(source.substring(digitStart, index), index);
    }
}
