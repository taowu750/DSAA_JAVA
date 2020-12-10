package algs5_string.sec4_regular_expr.src;

import algs4_graph.sec2_digraph.src.Digraph;
import algs4_graph.sec2_digraph.src.DigraphDFS;
import util.datastructure.MyStack;

import java.util.*;

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

    public RegExp(String regExp) {
        Objects.requireNonNull(regExp);
        assert regExp.length() > 0;

        // 添加括号
        regExp = "(" + regExp + ")";
        // 使用栈记住 ( 和 | 的位置
        MyStack<Integer> stack = new MyStack<>();
        re = regExp.toCharArray();
        int M = re.length;
        // M + 1，第 M 个状态是接受状态
        G = new Digraph(M + 1);

        for (int i = 0; i < M; i++) {
            int lp = i;
            // 遇到 ( 和 | 就压入栈中，| 是二元运算符
            if (re[i] == '(' || re[i] == '|')
                stack.push(i);
            else if (re[i] == ')') {
                // 遇到 )，从栈中弹出一个字符下标
                int or = stack.pop();
                if (re[or] == '|') {
                    or = stack.pop();
                    // 第 16 题： 实现多向或运算
                    List<Integer> orList = new ArrayList<>();
                    do {
                        orList.add(or);
                        // 添加 | 到 ) 的红边
                        G.addEdge(or, i);
                        or = stack.pop();
                    } while (re[or] == '|');
                    // 此时 lp 被设为 (，添加 ( 到每个 | 的红边
                    lp = or;
                    int finalLp = lp;
                    orList.forEach(o -> G.addEdge(finalLp, o));
                } else {
                    // 弹出来的是 (
                    lp = or;
                }
            }

            // 第 18 题：实现至少重复一次闭包 +

            // 闭包元字符 */+ 可能存在于普通字符后，此时添加普通字符和 */+ 的边；
            // 也可能 */+ 在 ) 后面，那么此时需要添加 */+ 和 ( 的边
            if (i < M - 1 && (re[i + 1] == '*' || re[i + 1] == '+')) {
                G.addEdge(lp, i + 1);
                G.addEdge(i + 1, lp);
            }
            // 如果 re[i] 是元字符（除了 | 和 +），那么需要添加它到下一个字符的黑边
            if (re[i] == '(' || re[i] == '*' || re[i] == ')')
                G.addEdge(i, i + 1);
            // + 至少要有一个匹配，+ 符号不能凭黑边转移到下一个状态
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
            char input = txt.charAt(i);
            Collection<Integer> matched = new HashSet<>();
            // 添加所有可能的匹配转换
            for (Integer status : statuses) {
                if (status < re.length && (re[status] == input || re[status] == '.')) {
                    matched.add(status + 1);
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
        assert new RegExp("(A*B|AC)D").match("AABD");

        // 16. 多项或运算测试
        RegExp multiOrReg = new RegExp("AB((C|D|E)F)*G");
        Map<String, Boolean> testAndResult = new HashMap<>();
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
        testAndResult.forEach((str, result) -> {
            assert multiOrReg.match(str) == result;
        });

        // 18. + 闭包测试
        RegExp plusReg = new RegExp("A+(B|C|D)*E+F");
        testAndResult.clear();
        testAndResult.put("ABEF", true);
        testAndResult.put("AAACEF", true);
        testAndResult.put("AADEEEF", true);
        testAndResult.put("BEEF", false);
        testAndResult.put("AACF", false);
        testAndResult.forEach((str, result) -> {
            assert plusReg.match(str) == result;
        });
    }
}
