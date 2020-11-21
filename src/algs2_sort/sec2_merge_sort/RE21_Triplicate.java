package algs2_sort.sec2_merge_sort;

import org.junit.jupiter.api.Test;

import static algs2_sort.sec2_merge_sort.RE20_IndirectSort.indirectSort;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * 三个列表，分别包含N个名字，找出第一个公共的名字，要求时间复杂度O(NlogN)
 */
public class RE21_Triplicate {

    public static String triplicate(String[] l1, String[] l2, String[] l3) {
        int[] l1idx = indirectSort(l1);
        int[] l2idx = indirectSort(l2);
        int[] l3idx = indirectSort(l3);

        int N = l1.length;
        for (int i = 0, j = 0, k = 0; i < N && j < N && k < N;) {
            String[] compared = {l1[l1idx[i]], l2[l2idx[j]], l3[l3idx[k]]};
            if (compared[0].equals(compared[1]) && compared[1].equals(compared[2]))
                return compared[0];
            else {
                // 在三个列表间迭代
                String max = compared[0].compareTo(compared[1]) > 0 ?
                        (compared[0].compareTo(compared[2]) > 0 ? compared[0] : compared[2]):
                        (compared[1].compareTo(compared[2]) > 0 ? compared[1] : compared[2]);
                while (i < N && l1[l1idx[i]].compareTo(max) < 0)
                    i++;
                while (j < N && l2[l2idx[j]].compareTo(max) < 0)
                    j++;
                while (k < N && l3[l3idx[k]].compareTo(max) < 0)
                    k++;
            }
        }

        return null;
    }

    @Test
    public void test_triplicate() {
        String[] name1 = new String[] { "Noah", "Liam", "Jacob", "Mason" };
        String[] name2 = new String[] { "Sophia", "Emma", "Mason", "Ava" };
        String[] name3 = new String[] { "Mason", "Marcus", "Alexander", "Ava" };

        assertEquals(triplicate(name1, name2, name3), "Mason");
    }
}
