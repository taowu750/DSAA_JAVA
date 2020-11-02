package dsaa3_list_stack_queue.practice;

import util.datastructure.MyArrayList;

import java.util.Iterator;
import java.util.List;

/**
 * 关于List的集合操作 -- 练习3.4、3.5
 *
 * @author wutao
 */
public class List_SetOperation {

    /**
     * 给出两个已经排序的表L1、L2，求他们的交集 -- 练习3.4<br/>
     * 时间复杂度：O(N)
     *
     * @param L1  已经排序好的一个表
     * @param L2  已经排序好的另一个表
     * @param <T> Comparable接口的实现类
     * @return 交集List
     */
    public static <T extends Comparable<? super T>>
    List<T> intersect(List<T> L1, List<T> L2) {
        List<T> intersection = new MyArrayList<>();
        Iterator<T> iterL1 = L1.iterator();
        Iterator<T> iterL2 = L2.iterator();

        T itemL1 = null, itemL2 = null;
        if (iterL1.hasNext() && iterL2.hasNext()) {
            itemL1 = iterL1.next();
            itemL2 = iterL2.next();
        }

        while (itemL1 != null && itemL2 != null) {
            int compareResult = itemL1.compareTo(itemL2);
            if (compareResult == 0) {
                intersection.add(itemL1);
                itemL1 = iterL1.hasNext() ? iterL1.next() : null;
                itemL2 = iterL2.hasNext() ? iterL2.next() : null;
            } else if (compareResult < 0) {
                itemL1 = iterL1.hasNext() ? iterL1.next() : null;
            } else {
                itemL2 = iterL2.hasNext() ? iterL2.next() : null;
            }
        }

        return intersection;
    }

    /**
     * 给出两个已经排序的表L1、L2，求他们的并集 -- 练习3.5<br/>
     * 时间复杂度：O(N)
     *
     * @param L1  已经排序好的一个表
     * @param L2  已经排序好的另一个表
     * @param <T> Comparable接口的实现类
     * @return 并集List
     */
    public static <T extends Comparable<? super T>>
    List<T> union(List<T> L1, List<T> L2) {
        List<T> union = new MyArrayList<>();
        Iterator<T> iter1 = L1.iterator();
        Iterator<T> iter2 = L2.iterator();

        T i1 = null, i2 = null;
        if (iter1.hasNext() && iter2.hasNext()) {
            i1 = iter1.next();
            i2 = iter2.next();
        }

        while (i1 != null && i2 != null) {
            int result = i1.compareTo(i2);

            if (i1.compareTo(i2) == 0) {
                union.add(i1);
                i1 = iter1.hasNext() ? iter1.next() : null;
                i2 = iter2.hasNext() ? iter2.next() : null;
            } else if (result > 0) {
                union.add(i2);
                i2 = iter2.hasNext() ? iter2.next() : null;
            } else {
                union.add(i1);
                i1 = iter1.hasNext() ? iter1.next() : null;
            }
        }
        while (i1 != null) {
            union.add(i1);
            i1 = iter1.hasNext() ? iter1.next() : null;
        }
        while (i2 != null) {
            union.add(i2);
            i2 = iter2.hasNext() ? iter2.next() : null;
        }

        return union;
    }
}
