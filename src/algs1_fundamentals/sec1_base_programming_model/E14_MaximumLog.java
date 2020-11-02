package algs1_fundamentals.sec1_base_programming_model;


/**
 * 1.1.14<br/>
 * 编写一个方法lg，接受一个整型参数，返回不大于log2N的最大整数。<br/>
 */
public class E14_MaximumLog {

    public static int lg(int n) {
        int x = -1, v = n;

        while (v > 0) {
            v >>>= 1;
            x += 1;
        }

        return x;
    }

    public static void main(String[] args) {
        System.out.println(lg(8));
        System.out.println(lg(17));
    }
}
