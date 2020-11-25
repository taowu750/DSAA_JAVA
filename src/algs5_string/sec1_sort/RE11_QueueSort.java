package algs5_string.sec1_sort;

import algs5_string.sec1_sort.src.AbstractStringSort;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

/**
 * <p>
 * 队列排序。为每个码点设置一个队列。在第一次遍历所有字符串时，将每个字符串的首字母插入到适当的队列中。
 * 然后，将每个子列表排序并合并所有队列得到一个完整的排序结果。
 * </p>
 * <p>
 * 注意，这种方法中 count[] 数组不需要在递归内创建。
 * </p>
 */
public class RE11_QueueSort {

    @Test
    public void testQueueMSD() {
        AbstractStringSort.check(new QueueMSD());
    }
}

class QueueMSD extends AbstractStringSort {

    // 每个码位对应一个队列，队列中存放字符串
    private Queue<String>[] buckets;
    // 计数
    private int[] count;

    public QueueMSD() {
        // 加 1 是因为有到头的字符串
        //noinspection unchecked
        buckets = (Queue<String>[]) new Queue[R + 1];
        Arrays.parallelSetAll(buckets, i -> new LinkedList<>());
        count = new int[R + 1];
    }

    @Override
    public void sort(String[] a) {
        sort(a, 0, a.length - 1, 0);
    }

    private void sort(String[] a, int lo, int hi, int index) {
        if (hi - lo <= M) {
            insertSort(a, lo, hi, index);
        } else {
            // 将字符串根据 index 处的字符分桶
            for (int i = lo; i <= hi; i++) {
                int ch = charAt(a[i], index);
                buckets[ch + 1].offer(a[i]);
            }

            int sortedIdx = lo, bucketIdx = 0;
            for (Queue<String> bucket : buckets) {
                // 记录每个桶的字符串数量
                count[bucketIdx++] = bucket.size();
                // 按顺序排列字符串并写入到原来的数组中
                while (!bucket.isEmpty()) {
                    a[sortedIdx++] = bucket.poll();
                }
            }

            // 递归地对每个组进行排序
            int preBucketCounts = 0;
            for (int i = 0; i < buckets.length - 1; i++) {
                sort(a, lo + preBucketCounts + count[i], lo + preBucketCounts + count[i + 1] - 1, index + 1);
                preBucketCounts += count[i];
            }
        }
    }
}
