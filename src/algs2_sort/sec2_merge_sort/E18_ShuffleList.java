package algs2_sort.sec2_merge_sort;

import algs2_sort.sec2_merge_sort.Exam.*;
import org.junit.jupiter.api.Test;

import java.util.List;

import static algs2_sort.sec2_merge_sort.Exam.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

// FIXME: 有bug
public class E18_ShuffleList {

    public static Node shuffleList(Node header) {
        int blockSize = 1;
        int size = getListSize(header);

        Node newHeader = null, lo = header, last = null;
        boolean first = true;
        while (blockSize <= size) {
            while (lo != null) {
                Node mid = getNode(lo, blockSize);
                if (mid == null || mid.next == null) {
                    break;
                }
                Node hi = getNode(mid, blockSize, true);
                Node[] merged = merge(lo, mid, hi);
                if (last != null)
                    last.next = merged[0];
                if (first) {
                    newHeader = merged[0];
                    first = false;
                }
                last = merged[1];
                lo = merged[1].next;
            }
            lo = newHeader;
            first = true;
            blockSize *= 2;
        }

        return newHeader;
    }

    @Test
    public void test_shuffleList() {
        Node list = createList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        list = shuffleList(list);
        List<Integer> items = getListItems(list);
        System.out.println(items);
        assertEquals(items.size(), 10);
    }

    static Node[] merge(Node lo, Node mid, Node hi) {
        Node left = lo, right = mid.next, end = hi.next;
        mid.next = null;
        hi.next = null;

        Node p = null, head = null;
        int i = 0;
        while (left != null && right != null) {
            if (i++ % 2 == 0) {
                if (head == null) {
                    head = left;
                } else {
                    p.next = left;
                }
                p = left;
                left = left.next;
            } else {
                if (head == null) {
                    head = right;
                } else {
                    p.next = right;
                }
                p = right;
                right = right.next;
            }
        }

        Node tail = null;
        if (left != null) {
            p.next = left;
            tail = mid;
        }
        if (right != null) {
            p.next = right;
            tail = hi;
        }
        tail.next = end;

        return new Node[]{head, tail};
    }
}
