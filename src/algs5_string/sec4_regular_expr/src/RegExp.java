package algs5_string.sec4_regular_expr.src;

import algs4_graph.sec2_digraph.src.Digraph;
import algs4_graph.sec2_digraph.src.DigraphDFS;
import util.datastructure.MyStack;

import java.util.*;
import java.util.function.Predicate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * <p>
 * 正则表达式。定义了五种基本模式：
 * <ul>
 *     <li>连接操作：也就是相邻的字符</li>
 *     <li>括号：(、) 元字符</li>
 *     <li>闭包操作：* 元字符</li>
 *     <li>或表达式：| 元字符</li>
 *     <li>通配符：. 元字符</li>
 * </ul>
 * 正则表达式中的元字符即正则表达式的操作符，如 (、)、*、|、.
 * </p>
 * <p>
 * 除了基本模型之外，还有如下额外规则：
 * <ol>
 *     <li>字符集合：
 *     <ul>
 *         <li>指定集合：[]</li>
 *         <li>范围集合：[-]</li>
 *         <li>补集：[^]</li>
 *     </ul>
 *     </li>
 *     <li>闭包的简写：
 *     <ul>
 *         <li>至少重复一次：+</li>
 *         <li>重复 0 或 1 次：?</li>
 *         <li>指定重复次数s和范围：{}</li>
 *     </ul>
 *     </li>
 *     <li>转义序列：\\</li>
 * </ol>
 * </p>
 * <p>
 * 正则表达式的自动机不确定下一个字符是什么，它无法仅根据一个字符就判断模式是否出现。
 * 所以我们需要非确定优先状态自动机（NFA）。NFA 有以下特点：
 * <ul>
 *     <li>NFA 中，字符表示状态，也就是结点，而不是 DFA 中的边</li>
 *     <li>长度为 M 的正则表达式中的每个字符在 NFA 中都有且只有一个对应的状态。NFA 还包含一个虚拟的接受状态 M</li>
 *     <li>除了 | 和 + 字符，其他字符所对应的状态，都有一条指向模式中下一个状态的边，这条边称为黑边</li>
 *     <li>元字符所对应的状态至少含有一条可能指向任意状态的边，这条边称红边（参见图 NFA.png）</li>
 *     <li>有些状态有多条指出的边，但一个状态只能有一条黑边</li>
 * </ul>
 * </p>
 * <p>
 * 在 NFA 中，状态的转换有以下两种方式：
 * <ul>
 *     <li>匹配转换：如果当前状态是普通字符(非元字符)，且和文本中的当前字符相匹配，自动机可以由黑边转到下一个状态</li>
 *     <li>epsilon 转换：自动机可以由红色的边转移到另一个状态而不扫描文本中的任何字符，
 *     也就是它对应的匹配是空字符串</li>
 * </ul>
 * </p>
 * <p>
 * 由于 NFA 的下一个状态可能由多种，对于一个给定的文本我们需要进行一系列状态转换，
 * 每次状态转换的结果可能有多种。当转换的最终状态中含有接受状态，则和文本相匹配。
 * </p>
 * <p>
 * 匹配转换可以由正则表达式字符数组表示；epsilon 转换则使用有向图表示。
 * 为了运行 NFA，我们会记录 NFA 在检查当前输入字符时可能遇到的所有状态的集合。
 * 我们会查找从状态 0 通过 epsilon 转换可达的状态来初始化这个集合。对于集合中的每个状态，
 * 检查它是否能和第一个输入字符相匹配，检查并匹配之后就可以获得 NFA 在匹配第一个字符之后可能达到
 * 的所有状态的集合。这里还需要向该集合加入所有从该集合任意状态通过 epsilon 转换可以
 * 达到的其他状态。对第二个匹配字符也这样转换，一直运行下去可能得到两种结果：
 * <ul>
 *     <li>可能达到的状态集合中含有接受状态</li>
 *     <li>可能达到的状态集合中不含有接受状态</li>
 * </ul>
 * </p>
 * <p>
 * 注意，下面的实现需要模式字符串被"()"包含。
 * </p>
 */
public class RegExp {

    // 匹配转换
    private char[] re;
    // epsilon 字符转换
    private Digraph G;
    // 用于存储补集的判断规则。其中值是一个 Predicate 用来判断输入是否在补集中
    private Map<Integer, Predicate<Character>> complementRules;
    // 用于存储重复次数的判断规则。其中值是一个 int 数组，存储次数的下限、上限、已重复次数
    private Map<Integer, int[]> countRules;
    // 存储符合特殊规则后转移的边
    private Map<Integer, Integer> specialRuleEdges;

    public RegExp(String regExp) {
        Objects.requireNonNull(regExp);

        // 添加括号
        regExp = "(" + regExp + ")";
        // 使用栈记住 (、| 的位置
        MyStack<Integer> stack = new MyStack<>();
        re = regExp.toCharArray();
        int M = re.length;
        // M + 1，第 M 个状态是接受状态
        G = new Digraph(M + 1);
        complementRules = new HashMap<>();
        countRules = new HashMap<>();
        specialRuleEdges = new HashMap<>();

        for (int i = 0; i < M; i++) {
            int lp = i;
            // 遇到 ( 和 | 就压入栈中，| 是二元运算符
            if (re[i] == '(' || re[i] == '|') {
                stack.push(i);
            } else if (re[i] == ')') {
                // 遇到 )，从栈中弹出一个字符下标
                int or = stack.pop();
                if (re[or] == '|') {
                    // 第 16 题： 实现多向或运算
                    List<Integer> orList = new ArrayList<>();
                    do {
                        orList.add(or);
                        // 添加 | 到 ) 的红边
                        G.addEdge(or, i);
                        or = stack.pop();
                    } while (re[or] == '|');
                    // 此时 lp 被设为 (，添加 ( 到每个 | 下个字符的红边
                    lp = or;
                    int finalLp = lp;
                    orList.forEach(o -> G.addEdge(finalLp, o + 1));
                } else {
                    // 弹出来的是 (
                    lp = or;
                }
            }

            // 第 20 题：字符集范围集合
            if (re[i] == '[') {
                // 找到 ] 位置
                int j = i + 2;
                for (; re[j] != ']'; j++) ;
                i = j;
                if (re[lp + 1] == '^') {
                    // 第 21 题：补集
                    // 如果是补集，则需要使用特殊规则
                    Set<Character> exclusiveChars = new HashSet<>();
                    for (j = i - 1; j >= lp + 2; j--) {
                        exclusiveChars.add(re[j]);
                    }
                    complementRules.put(lp, ch -> !exclusiveChars.contains(ch));
                    specialRuleEdges.put(lp, i);
                } else {
                    // 添加 [ 到 [] 内字符的红边，和 [] 内字符到 ] 的红边
                    for (j = i - 1; j >= lp + 1; j--) {
                        G.addEdge(lp, j);
                        G.addEdge(j, i);
                    }
                }
            }

            if (i < M - 1) {
                if (re[i + 1] == '*') {
                    // 如果 * 前面是普通字符，添加 * 和普通字符的边；
                    // 如果 * 前面是 )]，添加 * 和 ([ 的边
                    G.addEdge(lp, i + 1);
                    G.addEdge(i + 1, lp);
                } else if (re[i + 1] == '+') {
                    // 第 18 题：实现至少匹配一次 +
                    // 如果 + 前面是普通字符，添加 + 到普通字符的边；
                    // 如果 + 前面是 )]，添加 + 到 ([ 的边
                    // 因为 + 至少要匹配一次，所以不能添加到 + 的边，否则第一次运行时就会把
                    // + 后面的字符也加入状态
                    G.addEdge(i + 1, lp);
                } else if (re[i + 1] == '{') {
                    G.addEdge(i + 1, lp);
                    // 第 19 题：指定重复次数
                    // 使用 count 记录字符出现次数和上下限
                    int[] count = new int[3];
                    int j = i + 3;
                    // } 位置
                    int toStatus;
                    // count[0] 存储下限，count[1] 存储上限，count[2] 存储已出现次数
                    if (re[j] == '-') {
                        toStatus = i + 5;
                        count[0] = re[i + 2] - '0';
                        count[1] = re[i + 4] - '0';
                    } else {
                        toStatus = i + 3;
                        count[0] = count[1] = re[i + 2] - '0';
                    }
                    countRules.put(i + 1, count);
                    specialRuleEdges.put(i + 1, toStatus);
                }
            }

            // 如果 re[i] 是 (*)]+}，那么需要添加它到下一个字符的黑边。
            if (re[i] == '(' || re[i] == '*' || re[i] == ')' ||
                    re[i] == ']' || re[i] == '+' || re[i] == '}')
                G.addEdge(i, i + 1);
        }
    }

    public boolean match(String txt) {
        // 状态集合
        Collection<Integer> statuses = new HashSet<>();
        // 检查状态可达性
        DigraphDFS dfs = new DigraphDFS(G, 0);
        // 首先查找从 0 状态能够通过 epsilon 转换可达的状态集合
        for (int v = 0; v < G.vertexNum(); v++) {
            if (dfs.marked(v)) {
                statuses.add(v);
            }
        }

        int N = txt.length();
        for (int i = 0; i < N; i++) {
            if (statuses.size() == 0)
                break;

            char input = txt.charAt(i);
            Collection<Integer> matched = new HashSet<>();
            // 添加所有可能的匹配转换
            for (Integer status : statuses) {
                if (status < re.length) {
                    // 应用补集的特殊规则
                    if (re[status] == '[' &&  complementRules.containsKey(status) &&
                            complementRules.get(status).test(input)) {
                        matched.add(specialRuleEdges.get(status));
                    } else if (re[status] == '{') {
                        // 应用重复匹配次数的特殊规则。找到匹配，次数加 1
                        countRules.get(status)[2]++;
                    } else if ((re[status] == input || re[status] == '.')) {
                        matched.add(status + 1);
                    }
                }
            }
            if (matched.size() == 0) {
                // 当未匹配时，看看有没有 {
                for (Integer s : statuses) {
                    if (s < re.length && re[s] == '{') {
                        int[] count = countRules.get(s);
                        // 如果有 { 且在指定重复次数范围内，表示重复次数匹配成功，添加 }
                        if (count[2] >= count[0] && count[2] <= count[1]) {
                            // 需要回退一次输入
                            i--;
                            matched.add(specialRuleEdges.get(s));
                        }
                        // 将次数置 0
                        count[2] = 0;
                    }
                }
            }
            statuses = matched;
            // 从该集合任意状态通过 epsilon 转换可以达到的其他状态
            dfs = new DigraphDFS(G, statuses);
            for (int v = 0; v < G.vertexNum(); v++) {
                if (dfs.marked(v)) {
                    statuses.add(v);
                }
            }
        }

        // 如果最终状态集合中有接受状态，表示匹配成功
        return statuses.contains(re.length);
    }

    public static void main(String[] args) {
        // 基本测试
        assertTrue(new RegExp("(A*B|AC)D").match("AABD"));

        // 16. 多项或运算测试
        RegExp multiOrReg = new RegExp("AB((C|D|E)F)*G");
        Map<String, Boolean> testAndResult = new LinkedHashMap<>();
        testAndResult.put("ABCFG", true);
        testAndResult.put("ABDFG", true);
        testAndResult.put("ABEFG", true);
        testAndResult.put("ABG", true);
        testAndResult.put("ABCFDFG", true);
        testAndResult.put("ABCFEFG", true);
        testAndResult.put("ABDFDFG", true);
        testAndResult.put("CFG", false);
        testAndResult.put("ACFG", false);
        testAndResult.put("ABCG", false);
        testAndResult.put("ABFG", false);
        testAndResult.put("ABGFG", false);
        testAndResult.forEach((str, result) -> assertEquals(multiOrReg.match(str), result, str));

        // 18. + 闭包测试
        RegExp plusReg = new RegExp("A+(B|C|D)*E+F");
        testAndResult.clear();
        testAndResult.put("ABEF", true);
        testAndResult.put("AAACEF", true);
        testAndResult.put("AADEEEF", true);
        testAndResult.put("BEEF", false);
        testAndResult.put("AACF", false);
        testAndResult.forEach((str, result) -> assertEquals(plusReg.match(str), result, str));

        // 19. 重复次数测试
        RegExp countReg = new RegExp("A{2}B*(C[^123]){2-4}D");
        testAndResult.clear();
        testAndResult.put("AABC9C4D", true);
        testAndResult.put("AAC4CQD", true);
        testAndResult.put("AACJCMCCD", true);
        testAndResult.put("AACJCMCCC8D", true);
        testAndResult.put("ABC4CQD", false);
        testAndResult.put("AABC4D", false);
        testAndResult.put("AABC4CMCCC8CYD", false);
        testAndResult.forEach((str, result) -> assertEquals(countReg.match(str), result, str));

        // 20. 范围集合测试
        RegExp rangeReg = new RegExp("(A[BC])+D[EF]*J");
        testAndResult.clear();
        testAndResult.put("ABDEJ", true);
        testAndResult.put("ACDFJ", true);
        testAndResult.put("ACABDEFFEFEJ", true);
        testAndResult.put("ABABDJ", true);
        testAndResult.put("AEDEJ", false);
        testAndResult.put("ABADDFJ", false);
        testAndResult.put("ACDGJ", false);
        testAndResult.put("ACDEEGJ", false);
        testAndResult.put("ACDFEFQJ", false);
        testAndResult.forEach((str, result) -> assertEquals(rangeReg.match(str), result, str));

        // 21. 补集测试
        RegExp complementReg = new RegExp("(A[^BC])+DE[^FGH]*I");
        testAndResult.clear();
        testAndResult.put("ADDEAI", true);
        testAndResult.put("AEAQDEI", true);
        testAndResult.put("AKAFAJDEQMNI", true);
        testAndResult.put("ABDEFI", false);
        testAndResult.put("ACDEHI", false);
        testAndResult.put("AKDEGI", false);
        testAndResult.put("AKABDEI", false);
        testAndResult.put("AKAJDEGI", false);
        testAndResult.put("AKAJDEQCFI", false);
        testAndResult.forEach((str, result) -> assertEquals(complementReg.match(str), result, str));
    }
}
