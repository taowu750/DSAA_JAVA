package algs2_sort.sec2_merge_sort;

import org.junit.jupiter.api.Test;
import util.datagen.ArrayData;
import util.datagen.RandomGenerator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * 链表排序
 */
public class RE17_ListSort {

    static class Node {
        int item;
        Node next;
    }

    static Node createList(Integer... items) {
        Node header = null, p = null;
        for (int item : items) {
            Node node = new Node();
            node.item = item;
            if (header == null) {
                header = node;
                p = node;
            } else {
                p.next = node;
                p = node;
            }
        }

        return header;
    }

    static int getListSize(Node header) {
        int size = 0;
        while (header != null) {
            size += 1;
            header = header.next;
        }

        return size;
    }

    static Node getNode(Node header, int pos) {
        return getNode(header, pos, false);
    }

    static Node getNode(Node header, int pos, boolean last) {
        Node res = header, prev = res;
        for (int i = 0; i < pos && res != null; i++) {
            prev = res;
            res = res.next;
        }
        if (last && res == null)
            return prev;

        return res;
    }

    static List<Integer> getListItems(Node header) {
        List<Integer> items = new ArrayList<>();
        while (header != null) {
            items.add(header.item);
            header = header.next;
        }

        return items;
    }

    public static Node sortList(Node header) {
        if (header == null || header.next == null)
            return header;

        Node nextStart = header.next, prev = header;
        // 寻找左边的有序链表
        while (nextStart != null && nextStart.item >= prev.item) {
            prev = nextStart;
            nextStart = nextStart.next;
        }
        // 如果链表从头到开始都有序则返回
        if (nextStart == null)
            return header;

        do {
            // 将左边和右边分开，以便后续判断
            prev.next = null;
            // 查找右边链表的结尾
            prev = nextStart;
            Node thirdStart = nextStart.next;
            while (thirdStart != null && thirdStart.item >= prev.item) {
                prev = thirdStart;
                thirdStart = thirdStart.next;
            }

            // 合并两个有序链表
            prev = null;
            Node p = header, q = nextStart;
            while (p != null && q != thirdStart) {
                // 注意prev也需要变到已排序列表的末尾
                if (p.item <= q.item) {
                    if (prev != null)
                        prev.next = p;
                    else
                        header = p;
                    prev = p;
                    p = p.next;
                } else {
                    if (prev != null)
                        prev.next = q;
                    else
                        header = q;
                    prev = q;
                    q = q.next;
                }
            }
            while (p != null) {
                prev.next = p;
                prev = p;
                p = p.next;
            }
            while (q != thirdStart) {
                prev.next = q;
                prev = q;
                q = q.next;
            }

            nextStart = thirdStart;
        } while (nextStart != null);

        return header;
    }

    @Test
    public void test_sortList() {
        Integer[] arr = ArrayData.array(Integer.class,
                new RandomGenerator.Integer(-100, 100), 100);
        Node list = createList(arr);
        Arrays.sort(arr);
        assertEquals(getListItems(sortList(list)), Arrays.asList(arr));
    }
}
