package algs2_sort.sec3_quick_sort;

import org.junit.jupiter.api.Test;
import util.datagen.ArrayConverter;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

// TODO: 神秘
/**
 * 生成最佳数组。无重复元素，每次切分后子数组大小最多差1。
 */
public class RE16_BestCase {

    public static int[] bestCase(int N) {
        int[] arr = new int[N];
        bestCase(arr, 0, arr.length - 1, 0, 0);

        return arr;
    }

    private static void bestCase(int[] arr, int lo, int hi, int ind, int debugDeep) {
        if (lo <= hi) {
            int mid = lo + (hi - lo) / 2;
//            System.out.println(IntStream.range(0, debugDeep)
//                    .mapToObj(i -> "-").collect(Collectors.joining()) +
//                    "lo=" + lo + ", hi=" + hi + ", mid=" + mid + ", ind=" + ind);
            arr[ind] = mid;
            // 递归调用有层数，不能遍历整个数组
            if (lo < hi)
                arr[lo + 1] = lo;
            // 左子数组的中点需要放在mid，因为快排中a[lo]最后会和中间元素交换位置
            bestCase(arr, lo, mid - 1, mid, debugDeep + 1);
            bestCase(arr, mid + 1, hi, mid + 1, debugDeep + 1);
        }
    }

    @Test
    public void test_bestCase() throws Exception {
        int N = 100;
        assertArrayEquals(bestCase(N), ArrayConverter.to(E16_BestCase.bestCase(N)));
    }
}
