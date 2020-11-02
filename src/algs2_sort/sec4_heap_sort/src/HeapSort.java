package algs2_sort.sec4_heap_sort.src;

import algs2_sort.src.AbstractSort;

/**
 * 堆排序。
 */
public class HeapSort extends AbstractSort {

    @Override
    public void sort(Comparable[] a) {
        Comparable[] heap = new Comparable[a.length + 1];
        System.arraycopy(a, 0, heap, 1, a.length);

        int N = a.length;
        // 构建堆
        for (int k = N / 2; k >= 1; k--) {
            sink(heap, k, N);
        }
        // 不断将最大值和堆尾交换，再下沉。缩小堆同时构建了有序序列。
        while (N > 1) {
            exch(heap, 1, N--);
            sink(heap, 1, N);
        }

        System.arraycopy(heap, 1, a, 0, a.length);
    }

    public static void main(String[] args) throws InterruptedException {
        simpleTest(new HeapSort());
    }


    /**
     * 将 k 处的元素下沉。
     *
     * @param k
     */
    private void sink(Comparable[] a, int k, int N) {
        while (2 * k <= N) {
            int j = 2 * k;
            if (j < N && less(a[j], a[j + 1]))
                j++;
            if (!less(a[k], a[j]))
                break;
            exch(a, k, j);
            k = j;
        }
    }
}
