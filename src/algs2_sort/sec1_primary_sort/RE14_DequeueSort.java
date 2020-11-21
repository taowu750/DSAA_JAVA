package algs2_sort.sec1_primary_sort;

import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.LinkedList;

/**
 * 只能查看最左边的两个数字，可以交换它们，或是将最左边的一个数字放到最右边。将这样的序列排序。
 */
public class RE14_DequeueSort {

    public static void dequeueSort(LinkedList<Integer> list) {
        boolean exchanged = true;
        while (exchanged) {
            exchanged = false;
            for (int i = 0; i < list.size(); i++) {
                if (i != list.size() - 1) {
                    if (list.get(0) > list.get(1)) {
                        int temp = list.get(0);
                        list.set(0, list.get(1));
                        list.set(1, temp);
                        exchanged = true;
                    }
                }
                list.add(list.remove(0));
            }
        }
    }

    @Test
    public void test_dequeueSort() {
        LinkedList<Integer> list = new LinkedList<>();
        Collections.addAll(list, 3, 1, 9, 5, 2, 1, 8, 4, 2, 3, 5);
        dequeueSort(list);
        System.out.println(list);
    }
}
