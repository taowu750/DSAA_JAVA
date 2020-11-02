package algs2_sort.sec2_merge_sort;

import algs2_sort.src.AbstractSort;
import org.junit.jupiter.api.Test;
import util.datagen.ArrayConverter;
import util.datagen.ArrayData;
import util.datagen.CountingGenerator;
import util.datagen.RandomGenerator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class Exam {

    static class MergeSort extends AbstractSort {
        Comparable[] aux;

        @Override
        public void sort(Comparable[] a) {
            aux = new Comparable[a.length];
            sort(a, 0, a.length - 1);
        }

        void sort(Comparable[] a, int lo, int hi) {
            if (lo < hi) {
                if (hi - lo == 1) {
                    if (less(a[hi], a[lo]))
                        exch(a, hi, lo);
                } else {
                    int mid = (hi - lo) / 2 + lo;
                    sort(a, lo, mid);
                    sort(a, mid + 1, hi);
                    // 当左半部分的最大值小于右半部分的最小值时，就已经有序了
                    if (lessEqual(a[mid], a[mid + 1])) {
                        return;
                    }
                    merge(a, lo, mid, hi);
                }
            }
        }

        void merge(Comparable[] a, int lo, int mid, int hi) {
            int i = lo, j = mid + 1, k = lo;
            while (i <= mid && j <= hi) {
                if (lessEqual(a[i], a[j])) {
                    aux[k++] = a[i++];
                } else {
                    aux[k++] = a[j++];
                }
            }
            while (i <= mid)
                aux[k++] = a[i++];
            while (j <= hi)
                aux[k++] = a[j++];
            for (k = lo; k <= hi; k++)
                a[k] = aux[k];
        }
    }

    @Test
    public void test_MergeSort() throws InterruptedException {
        AbstractSort.simpleTest(new MergeSort(), 200, false);
    }

    // TODO: 不行
    /**
     * ###11. 在归并中交换数组而不是复制
     */
    static class NoArrayCopyMergeSort extends AbstractSort {

        Comparable[] myAux;

        @Override
        public void sort(Comparable[] a) {
            myAux = Arrays.copyOf(a, a.length);
            sort(myAux, a, 0, a.length - 1, 0);
        }

        void sort(Comparable[] a, Comparable[] aux, int lo, int hi, int deep) {
            if (lo < hi) {
                if (hi - lo == 1) {
                    if (less(a[hi], a[lo]))
                        exch(a, hi, lo);
                } else {
                    int mid = (hi - lo) / 2 + lo;
                    sort(a, aux, lo, mid, deep + 1);
                    sort(a, aux, mid + 1, hi, deep + 1);
                    // 当左半部分的最大值小于右半部分的最小值时，就已经有序了
                    if (lessEqual(a[mid], a[mid + 1])) {
                        return;
                    }
                    if (deep % 2 == 0)
                        merge(aux, a, lo, mid, hi);
                    else
                        merge(a, aux, lo, mid, hi);
                }
            }
        }

        void merge(Comparable[] a, Comparable[] aux, int lo, int mid, int hi) {
            int i = lo, j = mid + 1, k = lo;
            while (i <= mid && j <= hi) {
                if (lessEqual(a[i], a[j])) {
                    aux[k++] = a[i++];
                } else {
                    aux[k++] = a[j++];
                }
            }
            while (i <= mid)
                aux[k++] = a[i++];
            while (j <= hi)
                aux[k++] = a[j++];
        }
    }

    @Test
    public void test_NoArrayCopyMergeSort() throws InterruptedException {
        AbstractSort.simpleTest(new NoArrayCopyMergeSort(), 200, false);
    }

    /**
     * ###12. 用大小M将数组分成N/M块，简单起见M是N的约数。实现一个归并方法，使额外空间减少到max(M, N/M)
     */
    static class MergeMSort extends AbstractSort {

        Comparable[] aux;
        int M = 10;

        @Override
        public void sort(Comparable[] a) {
            aux = new Comparable[M];

            int N = a.length;
            // 分别对每一块进行排序
            for (int i = 0; i < N / M; i++) {
                Arrays.sort(a, i * M, (i + 1) * M);
            }

            // 将每块的最小值当作键值，按照键值对块进行选择排序
            for (int i = 0; i < N / M - 1; i++) {
                int min_chunk_ind = i;
                for (int j = i + 1; j < N / M; j++) {
                    if (less(a[j * M], a[min_chunk_ind * M]))
                        min_chunk_ind = j;
                }
                if (min_chunk_ind != i) {
                    System.arraycopy(a, min_chunk_ind * M, aux, 0, M);
                    System.arraycopy(a, i * M, a, min_chunk_ind * M, M);
                    System.arraycopy(aux, 0, a, i * M, M);
                }
            }

            // 合并第一块和第二块，然后是合并后的块和第三块，这样一直到最后一块
            for (int n = 0; n < N / M - 1; n++) {
                merge(a, 0, (n + 1) * M - 1, (n + 2) * M - 1);
            }
        }

        private void merge(Comparable[] a, int lo, int mid, int hi) {
            // 将右边的块复制到aux中
            for (int k = mid + 1; k <= hi; k++)
                aux[k - mid - 1] = a[k];

            int i = mid, j = hi - mid - 1;
            for (int k = hi; k >= lo; k--) {
                if (i < lo) {
                    a[k] = aux[j--];
                } else if (j < 0) {
                    a[k] = a[i--];
                } else if (less(aux[j], a[i])) {
                    a[k] = a[i--];
                } else {
                    a[k] = aux[j--];
                }
            }
        }
    }

    @Test
    public void test_MergeMSort() throws InterruptedException {
        AbstractSort.simpleTest(new MergeMSort(), 200, false);
    }

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

    /**
     * ###17. 链表排序
     *
     * @param header
     * @return
     */
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

    // TODO: 不行
    /**
     * ###18. 实现一个打乱链表的分治算法，要求时间复杂度O(NlogN)，空间复杂度O(logN)
     * @param header
     * @return
     */
    public Node shuffleList(Node header) {
        return null;
    }

    /**
     * 19. 计算数组中倒置的数量，要求时间复杂度O(NlogN)
     * @param arr
     * @return
     */
    public static int invertNum(int[] arr) {
        return invertNum(arr, new int[arr.length], 0, arr.length - 1);
    }

    private static int invertNum(int[] arr, int[] aux, int lo, int hi) {
        if (lo < hi) {
            int mid  = lo + (hi - lo) / 2;
            int left = invertNum(arr, aux, lo, mid);
            int right = invertNum(arr, aux,mid + 1, hi);
            int center = invertMerge(arr, aux, lo, mid, hi);

            return left + right + center;
        }

        return 0;
    }

    private static int invertMerge(int[] arr, int[] aux, int lo, int mid, int hi) {
        int i = lo, j = mid + 1, k = lo;
        int invertNum = 0;
        while (i <= mid && j <= hi) {
            if (arr[i] <= arr[j]) {
                aux[k++] = arr[i++];
            } else {
                aux[k++] = arr[j++];
                invertNum += mid - i + 1;
            }
        }

        // 这里不需要再统计了，之前的循环里面统计过了
//        if (i <= mid) {
//            invertNum += (mid - i + 1) * (hi - mid);
//        }
        while (i <= mid) {
            aux[k++] = arr[i++];
        }
        while (j <= hi)
            aux[k++] = arr[j++];
        for (k = lo; k <= hi; k++)
            arr[k] = aux[k];

        return invertNum;
    }

    @Test
    public void test_invertNum() {
        int[] a = {3,4,0,1,2};

        assertEquals(invertNum(a), 6);
    }

    /**
     * ###20. 不改变原数组，返回原数组元素排序后的下标idx。idx[i]是原数组中第i小的元素的下标
     * @param arr
     */
    private static int[] idxAux;
    public static int[] indirectSort(Comparable[] arr) {
        int[] idx = ArrayConverter.to(ArrayData.array(Integer.class,
                new CountingGenerator.Integer(0, 1), arr.length));
        idxAux = new int[arr.length];
        indirectSort(arr, idx, 0, arr.length - 1);

        return idx;
    }

    private static void indirectSort(Comparable[] arr, int[] idx, int lo, int hi) {
        if (lo < hi) {
            int mid  = lo + (hi - lo) / 2;
            indirectSort(arr, idx, lo, mid);
            indirectSort(arr, idx, mid + 1, hi);
            indirectMerge(arr, idx, lo, mid, hi);
        }
    }

    private static void indirectMerge(Comparable[] arr, int[] idx, int lo, int mid, int hi) {
        for (int k = lo; k <= hi; k++)
            idxAux[k] = idx[k];

        for (int i = lo, j = mid + 1, k = lo; k <= hi; k++) {
            if (i > mid)
                idx[k] = idxAux[j++];
            else if (j > hi)
                idx[k] = idxAux[i++];
            else //noinspection unchecked
                if (arr[idxAux[i]].compareTo(arr[idxAux[j]]) <= 0)
                idx[k] = idxAux[i++];
            else
                idx[k] = idxAux[j++];
        }
    }

    @Test
    public void test_indirectSort() {
        Integer[] arr = ArrayData.array(Integer.class,
                new RandomGenerator.Integer(-100, 100), 100);
        int[] idx = indirectSort(arr);
        Integer[] brr = new Integer[arr.length];
        int j = 0;
        for (int i : idx) {
            brr[j++] = arr[i];
        }

        assertTrue(AbstractSort.isSorted(brr));
    }

    /**
     * ###21. 三个列表，分别包含N个名字，找出第一个公共的名字，要求时间复杂度O(NlogN)
     * @param l1
     * @param l2
     * @param l3
     * @return
     */
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
