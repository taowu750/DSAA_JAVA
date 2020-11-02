package dsaa3_list_stack_queue.practice;


import dsaa3_list_stack_queue.src.BalanceSymbolUtil;
import util.datastructure.MyStack;
import util.io.FormatPrint;

/**
 * 栈的各种应用
 *
 */
public class StackUse {

    /**
     * 对一个字符序列进行检查，检查其中的 ()、[]、{}、/*、begin/end 是否成对出现
     *
     * @param charSequence 字符序列
     * @return 成对出现返回true，否则返回false
     */
    public static boolean checkBalanceSymbol(String charSequence) {
        FormatPrint.printInstruction("Begin parsing");
        // 构造一个空栈
        MyStack<String> openSymbols = new MyStack<>();

        // 读取字符序列中每一个字符直到序列末尾
        for (int i = 0; i < charSequence.length(); i++) {
            String symbol = charSequence.charAt(i) + "";
            // 如果字符是一个单元素开放字符，将其压入栈中
            if (BalanceSymbolUtil.isOpenSymbol(symbol)) {
                System.out.println(i + " -- findIn2lgN single open symbol: " + symbol);
                openSymbols.push(symbol);
            }
            // 如果字符是一个多元素开放字符的一部分
            else if (BalanceSymbolUtil.isMultiElementOpenSymbolPart(symbol)) {
                // 检查下一个(或多个)字符能否与其组成多元素开放字符
                // 如果可以，将它压入栈中
                String multiElementOpenSymbol = BalanceSymbolUtil.getMultiElementOpenSymbolByPart(symbol);
                if (i + multiElementOpenSymbol.length() <= charSequence.length()) {
                    String symbols = charSequence.substring(i, i + multiElementOpenSymbol.length());
                    if (multiElementOpenSymbol.equals(symbols)) {
                        System.out.println(i + " -- findIn2lgN multi open symbol: " + symbols);
                        openSymbols.push(symbols);
                        i += multiElementOpenSymbol.length() - 1;
                    }
                }
            }
            // 如果字符是一个单元素封闭字符
            else if (BalanceSymbolUtil.isCloseSymbol(symbol)) {
                System.out.println(i + " -- findIn2lgN single close symbol: " + symbol);
                // 从栈中弹出一个开放字符
                String openSymbol = openSymbols.pop();
                // 如果栈为空或他们不匹配，则报错
                if (openSymbol == null || !BalanceSymbolUtil.match(openSymbol, symbol)) {
                    FormatPrint.printInstruction("End parsing");
                    FormatPrint.newLine();
                    return false;
                }
            }
            // 如果字符是一个多元封闭字符的一部分
            else if (BalanceSymbolUtil.isMultiElementCloseSymbolPart(symbol)) {
                // 检查下一个(或多个)字符能否与其组成多元素封闭字符
                String multiElementCloseSymbol = BalanceSymbolUtil.getMultiElementCloseSymbolByPart(symbol);
                if (i + multiElementCloseSymbol.length() <= charSequence.length()) {
                    String symbols = charSequence.substring(i, i + multiElementCloseSymbol.length());
                    if (multiElementCloseSymbol.equals(symbols)) {
                        System.out.println(i + " -- findIn2lgN multi close symbol: " + symbols);
                        i += multiElementCloseSymbol.length() - 1;
                        // 如果可以，从栈中弹出一个开放字符
                        // 检查它们是否匹配，如果不匹配，则报错
                        String openSymbol = openSymbols.pop();
                        if (openSymbol == null || !BalanceSymbolUtil.match(openSymbol, symbols)) {
                            FormatPrint.printInstruction("End parsing");
                            FormatPrint.newLine();
                            return false;
                        }
                    }
                }
            }
            // 如果字符是个普通字符，则略过
        }
        // 读取到字符序列末尾时，如果栈不为空，则报错
        FormatPrint.printInstruction("End parsing");
        FormatPrint.newLine();
        return openSymbols.size() == 0;
    }
}
