package algs2_sort.src;

import algs2_sort.sec1_primary_sort.src.InsertionSort;
import algs2_sort.sec1_primary_sort.src.SelectionSort;
import algs2_sort.sec1_primary_sort.src.ShellSort;
import algs2_sort.sec2_merge_sort.src.MergeSort;
import algs2_sort.sec3_quick_sort.src.QuickSort;
import util.datagen.ArrayData;
import util.datagen.RandomGenerator;
import util.io.FormatPrint;
import util.test.SpeedTester;

import java.util.Arrays;

/**
 * 比较不同排序算法的性能。
 */
public class SortCompare {

    public static void compare(int size, int runTimes, AbstractSort... sorts) {
        if (sorts.length < 1)
            return;
        if (size < 2) {
            size = 1000;
        }
        if (runTimes < 1)
            runTimes = 1;

        double[] times= new double[sorts.length];
        for (int i = 0; i < runTimes; i++) {
            Integer[] a = ArrayData.array(Integer.class, new RandomGenerator.Integer(-size, size),
                    size);
            for (int j = 0; j < sorts.length; j++) {
                Integer[] aCopy = Arrays.copyOf(a, a.length);
                int finalJ = j;
                times[j] += SpeedTester.testRunTime(() -> sorts[finalJ].sort(aCopy));
            }
        }
        for (int i = 0; i < times.length; i++) {
            times[i] = times[i] / runTimes;
        }

        FormatPrint.group(() -> {
            for (int i = 0; i < times.length; i++) {
                System.out.printf("%s: 平均运行时间 %.3fms\n", sorts[i].name(), times[i]);
            }
        }, "排序性能比较: 每个算法排序了" + runTimes + "次，每次" + size + "个数据");
    }

    public static void compare(int size, AbstractSort... sorts) {
        compare(size, 3, sorts);
    }

    public static void compare(AbstractSort... sorts) {
        compare(20000, sorts);
    }


    public static void main(String[] args) {
        compare(new SelectionSort(),
                new InsertionSort(),
                new ShellSort(),
                new MergeSort(),
                new QuickSort());
    }
}
