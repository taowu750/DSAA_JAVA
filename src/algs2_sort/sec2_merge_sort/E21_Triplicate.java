package algs2_sort.sec2_merge_sort;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * <p>
 * 一式三份。给定三个列表，每个列表中含有 N 个名字，编写一个线性对数级别的算法来
 * 判定三份列表中是否含有公共的名字，如果有，返回第一个被找到的这种名字。
 * </p>
 */
public class E21_Triplicate {

    public String findCommon(String[] l1, String[] l2, String[] l3) {
        Arrays.sort(l1);
        Arrays.sort(l2);
        Arrays.sort(l3);

        int i = 0, j = 0, k = 0;
        while (i < l1.length && j < l2.length && k < l3.length) {
            if (l1[i].equals(l2[j]) && l1[i].equals(l3[k])) {
                return l1[i];
            } else {
                int min = min(l1[i], l2[j], l3[k]);
                switch (min) {
                    case 0:
                        if (l1[i].equals(l2[j]))
                            j++;
                        if (l1[i].equals(l3[k]))
                            k++;
                        i++;
                        break;

                    case 1:
                        if (l2[j].equals(l1[i]))
                            i++;
                        if (l2[j].equals(l3[k]))
                            k++;
                        j++;
                        break;

                    case 2:
                        if (l3[k].equals(l1[i]))
                            i++;
                        if (l3[k].equals(l2[j]))
                            j++;
                        k++;
                        break;
                }
            }
        }

        return null;
    }

    @Test
    public void testFindCommon() throws Exception {
        String[] l1 = {"wutao", "wuhan", "wyf"};
        String[] l2 = {"hyj", "af", "wuhan"};
        String[] l3 = {"wuhan", "jahhsd", "fxxk"};

        assertEquals(findCommon(l1, l2, l3), "wuhan");
    }


    private int min(String i, String j, String k) {
        String min = i;

        if (min.compareTo(j) > 0) {
            min = j;
        }
        if (min.compareTo(k) > 0) {
            min = k;
        }

        return min == i ? 0 : min == j ? 1 : 2;
    }
}
