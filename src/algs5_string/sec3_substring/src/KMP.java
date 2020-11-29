package algs5_string.sec3_substring.src;

import java.util.Objects;

/**
 * <p>
 * KMP（由 Knuth、Morris 和 Pratt 发明）算法的基本思想是当出现不匹配时，就能知晓一部分文本的内容
 * （因为匹配失败之前它们已经和模式相匹配）。我们可以利用这些信息避免将指针回退到所有这些已知的字符之前。
 * </p>
 * <p>
 * 假设只有 A、B 两个字母，模式字符串为 B A A A A A A A A A。现在匹配了模式中的 5 个字符，第 6 个失败。
 * 那么文本中前 6 个字符肯定是 B A A A A B，文本指针现在指向末尾的字符 B。可以观察到，这里不需要回退
 * 文本指针 i。另外，i 指向的字符和模式中第一个字符匹配，所以可以直接将 i 加 1、j 加 1，以比较文本中
 * 下一个字符和模式中第二个字符。
 * </p>
 * <p>
 * 匹配失败时，如果模式字符串中的开头可以和匹配失败文本的某处相匹配，那么就不应该完全跳过所有字符。
 * 例如，在文本 A A B A A B A A A A 中查找模式 A A B A A A A 时，我们首先会在第 6 个字符处失败，
 * 但是应该在第 4 个字符处继续查找，否则就会错过已经匹配的部分。
 * </p>
 * <p>
 * 使用一个数组 dfa[][] 记录匹配失败时模式指针 j 应该回退多远。对于每个字符 c ，在比较了 c 和
 * pat.charAt(j) 之后，dfa[c][j] 表示的是应该和下个文本字符比较的模式字符的位置。
 * 并且，对于模式指针 j，前 j - 1 个字符已经匹配完毕。
 * 我们可以把 dfa[][] 看作是一个<strong>确定有限状态机（DFA）</strong>，模式下标是状态，字符是输入。
 * 对于每个输入字符 c，dfa[c][j] 表示的是状态 j 遇到输入字符 c 应该转移到的下一个状态。
 * 我们还会包含一个不会进行任何转换的停止状态，它等于模式字符串长度，意味着匹配成功。
 * 这个停止状态放在 dfa[-1][-1] 中（用 -1 表示数组最后一个下标）。
 * </p>
 * <p>
 * 对于每个模式字符串，我们都能预先生成一个状态转移矩阵。显然，如果遇到的字符 c 和 pat[j] 匹配的话，状态就应该向前推进一个，
 * 也就是说 next = j + 1，我们不妨称这种情况为状态推进。如果字符 c 和 pat[j] 不匹配的话，状态就要回退（或者原地不动），
 * 我们不妨称这种情况为状态重启。
 * 详细解释参见：https://zhuanlan.zhihu.com/p/83334559
 * </p>
 * <p>
 * KMP 的优势在于在重复性很高的文本中查找重复性很高的模式；此外，KMP不需要在输入中回退，这使得它更适合
 * 在长度不确定的输入流中进行查找。
 * </p>
 */
@SuppressWarnings("unused")
public class KMP extends AbstractStringSearch {

    private int R = 256;
    private String pat;
    private int[][] dfa;


    public KMP(String pat) {
        Objects.requireNonNull(pat);

        this.pat = pat;
        genDfa();
    }


    @Override
    public int search(String pat, String txt) {
        this.pat = pat;
        genDfa();
        return search(txt);
    }

    public int search(String txt) {
        int txtIdx = 0, state = 0, M = pat.length(), N = txt.length();

        for (; txtIdx < N && state < M; txtIdx++) {
            // 根据当前状态和输入决定下一个状态
            state = dfa[txt.charAt(txtIdx)][state];
        }

        if (state == M)
            // 找到匹配，到达模式字符串末尾
            return txtIdx - M;
        else
            return -1;
    }

    private void genDfa() {
        int M = pat.length();
        dfa = new int[R][M];
        // 构造 dfa
        dfa[pat.charAt(0)][0] = 1;
        for (int lastState = 0, curState = 1; curState < M; curState++) {
            // 将上一个状态的 dfa[][lastState] 复制到 dfa[][curState]
            // 因为上一个状态和当前状态的输入序列（即转移到这个状态的输入字符串）有着最长公共包含后缀。
            // 最长公共包含后缀指的是当前状态的输入序列后缀能够包含上一状态的输入序列。
            // 当匹配失败时，只能回退，我们希望尽可能少的回退，
            // 那么 curState 就可以通过 lastState 来获得最近的重启位置
            for (int ch = 0; ch < R; ch++) {
                dfa[ch][curState] = dfa[ch][lastState];
            }
            // 对每个匹配成功的输入，它的下一个状态就是当前状态加 1
            dfa[pat.charAt(curState)][curState] = curState + 1;

            // 对于当前输入字符，更新 lastState。
            // 我们需要计算和 curState + 1 状态具有最长后缀的状态。对于 curState + 1 的末尾后缀 pat[curState]，
            // lastState 会被转移到哪个状态，转移之后的状态和 curState + 1 状态有最长后缀
            lastState = dfa[pat.charAt(curState)][lastState];
        }
        // 对于模式串 A B A B A C，dfa 的构造流程参见 BMP的DFA构造过程.png
    }
}
