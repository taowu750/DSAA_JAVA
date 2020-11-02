package dsaa3_list_stack_queue.src;


import util.datastructure.MyStack;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;


/**
 * 平衡符号的工具类，提供了检测哪些符号或符号组是平衡符号、平衡符号的匹配等基本方法。
 * 它还提供了对多元素平衡符号的检查。
 */
public class BalanceSymbolUtil {

    // 构造一个包含所有平衡符号的映射
    // 构造包含所有开放符号的序列，以及包含所有封闭符号的序列
    // 构造包含所有多元素开放符号的序列，以及多元素封闭符号的序列
    private static Map<String, String> balanceSymbols = new HashMap<>();
    private static Set<String>        openSymbols;
    private static Collection<String> closeSymbols;
    private static Map<String, String> multiElementBalanceSymbols = new HashMap<>();
    private static Set<String>        multiElementOpenSymbols;
    private static Collection<String> multiElementCloseSymbols;


    static {
        balanceSymbols.put("(", ")");
        balanceSymbols.put("[", "]");
        balanceSymbols.put("{", "}");
        balanceSymbols.put("/*", "*/");
        balanceSymbols.put("begin", "end");
        multiElementBalanceSymbols.put("/*", "*/");
        multiElementBalanceSymbols.put("begin", "end");

        openSymbols = balanceSymbols.keySet();
        closeSymbols = balanceSymbols.values();
        multiElementOpenSymbols = multiElementBalanceSymbols.keySet();
        multiElementCloseSymbols = multiElementBalanceSymbols.values();
    }


    private BalanceSymbolUtil() {
        throw new IllegalStateException("You cannot obtain objects of this class!");
    }


    public static boolean isOpenSymbol(String symbol) {
        return openSymbols.contains(symbol);
    }

    public static boolean isCloseSymbol(String symbol) {
        return closeSymbols.contains(symbol);
    }

    public static boolean isMultiElementOpenSymbol(String symbol) {
        return multiElementOpenSymbols.contains(symbol);
    }

    public static boolean isMultiElementCloseSymbol(String symbol) {
        return multiElementCloseSymbols.contains(symbol);
    }

    public static boolean isMultiElementOpenSymbolPart(String symbolPart) {
        return getMultiElementOpenSymbolByPart(symbolPart) != null;
    }

    public static boolean isMultiElementCloseSymbolPart(String symbolPart) {
        return getMultiElementCloseSymbolByPart(symbolPart) != null;
    }

    public static boolean isBalanceSymbol(String symbol) {
        return isOpenSymbol(symbol) || isCloseSymbol(symbol);
    }

    public static boolean isMultiElemnentSymbol(String symbol) {
        return isMultiElementOpenSymbol(symbol) || isMultiElementCloseSymbol(symbol);
    }

    public static boolean match(String openSymbol, String closeSymbol) {
        if (isOpenSymbol(openSymbol) && isCloseSymbol(closeSymbol)) {
            return balanceSymbols.get(openSymbol).equals(closeSymbol);
        }

        return false;
    }

    /**
     * 首先判断 symbolPart 的开头字符是否与多元素开放符号的开头相匹配；<br/>
     * 如果是，则判断多元素开放字符是否包含symbolPart；<br/>
     * 全部相符合的话，就返回对应的多元素开放符号，否则返回null。
     *
     * @param symbolPart 开放符号的一部分
     * @return 对应的多元素开放符号，不存在的话返回null
     */
    public static String getMultiElementOpenSymbolByPart(String symbolPart) {
        for (String symbol : multiElementOpenSymbols) {
            if (symbol.charAt(0) == symbolPart.charAt(0) && symbol.contains(symbolPart)) {
                return symbol;
            }
        }

        return null;
    }

    /**
     * 首先判断 symbolPart 的开头字符是否与多元素封闭符号的开头相匹配；<br/>
     * 如果是，则判断多元素封闭字符是否包含symbolPart；<br/>
     * 全部相符合的话，就返回对应的多元素封闭符号，否则返回null。
     *
     * @param symbolPart 封闭符号的一部分
     * @return 对应的多元素封闭符号，不存在的话返回null
     */
    public static String getMultiElementCloseSymbolByPart(String symbolPart) {
        for (String symbol : multiElementCloseSymbols) {
            if (symbol.charAt(0) == symbolPart.charAt(0) && symbol.contains(symbolPart)) {
                return symbol;
            }
        }

        return null;
    }

    /**
     * 添加开放/封闭符号对，需要注意的是，当开放封闭符号的长度都为1，或都大于1时，才是合法的，
     * 否则会抛出 IllegalStateException 异常
     *
     * @param openSymbol  开放符号
     * @param closeSymbol 封闭符号
     * @throws IllegalStateException 当符号非法时抛出
     */
    public static void addBalanceSymbol(String openSymbol, String closeSymbol) {
        boolean isValid = (openSymbol.length() == 1 && closeSymbol.length() == 1) || (openSymbol.length() > 1 &&
                closeSymbol.length() > 1);
        if (!isValid) {
            throw new IllegalStateException("Invalid open/close symbol!: openSymbol=" + openSymbol + ", " +
                    "closeSymbol=" + closeSymbol);
        }

        balanceSymbols.put(openSymbol, closeSymbol);
        openSymbols = balanceSymbols.keySet();
        closeSymbols = balanceSymbols.values();
        if (openSymbol.length() > 1) {
            multiElementBalanceSymbols.put(openSymbol, closeSymbol);
            multiElementOpenSymbols = multiElementBalanceSymbols.keySet();
            multiElementCloseSymbols = multiElementBalanceSymbols.values();
        }
    }

    public static void removeBalanceSymbol(String openSymbol) {
        if (balanceSymbols.remove(openSymbol) != null) {
            openSymbols = balanceSymbols.keySet();
            closeSymbols = balanceSymbols.values();
        }

        if (openSymbol.length() > 1) {
            if (multiElementBalanceSymbols.remove(openSymbol) != null) {
                multiElementOpenSymbols = multiElementBalanceSymbols.keySet();
                multiElementCloseSymbols = multiElementBalanceSymbols.values();
            }
        }
    }

    /**
     * 对一个字符序列进行检查，检查其中的 ()、[]、{}、/*、begin/end 是否成对出现
     *
     * @param charSequence 字符序列
     * @return 成对出现返回true，否则返回false
     */
    public static boolean checkBalanceSymbol(String charSequence) {
        // 构造一个空栈
        MyStack<String> openSymbols = new MyStack<>();

        // 读取字符序列中每一个字符直到序列末尾
        for (int i = 0; i < charSequence.length(); i++) {
            String symbol = charSequence.charAt(i) + "";
            // 如果字符是一个单元素开放字符，将其压入栈中
            if (BalanceSymbolUtil.isOpenSymbol(symbol)) {
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
                        openSymbols.push(symbols);
                        i += multiElementOpenSymbol.length() - 1;
                    }
                }
            }
            // 如果字符是一个单元素封闭字符
            else if (BalanceSymbolUtil.isCloseSymbol(symbol)) {
                // 从栈中弹出一个开放字符
                String openSymbol = openSymbols.pop();
                // 如果栈为空或他们不匹配，则报错
                if (openSymbol == null || !BalanceSymbolUtil.match(openSymbol, symbol)) {
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
                        i += multiElementCloseSymbol.length() - 1;
                        // 如果可以，从栈中弹出一个开放字符
                        // 检查它们是否匹配，如果不匹配，则报错
                        String openSymbol = openSymbols.pop();
                        if (openSymbol == null || !BalanceSymbolUtil.match(openSymbol, symbols)) {
                            return false;
                        }
                    }
                }
            }
            // 如果字符是个普通字符，则略过
        }
        // 读取到字符序列末尾时，如果栈不为空，则报错
        return openSymbols.size() == 0;
    }
}
