package algs5_string.sec1_sort.src;

/**
 * 键索引计数法排序。
 * 数据被分为若干组，标号 1，2，...。我们希望将数组按组分类并排序。
 */
public class KeyIndexSort {

    public static class KI {
        /*
        key >= 0
         */
        private int key;
        private String value;


        public KI(int key, String value) {
            this.key = key;
            this.value = value;
        }


        public int key() {
            return key;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public String value() {
            return value;
        }
    }


    /**
     *
     * @param a
     * @param R 最大索引号+1，索引从 0 开始
     */
    public static void sort(KI[] a, int R) {
        int N = a.length;
        String[] aux = new String[N];
        int[] count = new int[R + 1];

        // 计算出现频率
        for (int i = 0; i < N; i++)
            count[a[i].key() + 1]++;
        // 将频率转化为索引
        for (int r = 0; r < R; r++)
            count[r + 1] += count[r];
        // 将元素分类
        for (int i = 0; i < N; i++)
            aux[count[a[i].key()]++] = a[i].value();
        // 回写
        for (int i = 0; i < N; i++)
            a[i].setValue(aux[i]);
    }
}
