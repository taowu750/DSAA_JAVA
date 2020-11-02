package util.datagen;

import java.util.Iterator;

/**
 * 计数器
 */
public class Counter implements Iterable<Integer>, Comparable<Counter> {

    private int count;
    private int step;
    private boolean isAdd;
    private int total;

    private int initialCount;
    private int initialStep;
    private boolean initialAdd;


    public Counter(int count, int step, boolean isAdd) {
        this.count = count;
        this.step = step;
        this.isAdd = isAdd;
        this.total = 0;

        this.initialCount = count;
        this.initialStep = step;
        this.initialAdd = isAdd;
    }

    public Counter(int count, int step) {
        this(count, step, true);
    }

    public Counter(int count) {
        this(count, 1);
    }

    public Counter() {
        this(0);
    }


    /**
     * 返回下一个计数值，如果是第一次使用，那么它将会返回初始值
     *
     * @return 下一个计数值
     */
    public int next() {
        int next = count;
        if (isAdd) {
            count += step;
        } else {
            count -= step;
        }
        total += 1;

        return next;
    }

    /**
     * 返回 Counter 对象自创建以来更新的次数
     *
     * @return 更新的次数
     */
    public int total() {
        return total;
    }

    /**
     * 返回当前的计数值
     *
     * @return 当前计数值
     */
    public int current() {
        return count;
    }

    /**
     * 将计数器设置为初始状态
     */
    public void reset() {
        count = initialCount;
        step = initialStep;
        isAdd = initialAdd;
    }


    public int getCount() {
        return count;
    }

    public int getStep() {
        return step;
    }

    public boolean isAdd() {
        return isAdd;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void setStep(int step) {
        this.step = step;
    }

    public void setAdd(boolean add) {
        isAdd = add;
    }


    @Override
    public String toString() {
        return "Counter{count=" + count + ", step=" + step + ", isAdd=" + isAdd + "}";
    }

    @Override
    public Iterator<Integer> iterator() {
        return new Iter();
    }

    @Override
    public int compareTo(Counter o) {
        if (o != null) {
            if (count < o.count) {
                return -1;
            } else if (count == o.count) {
                return 0;
            } else {
                return 1;
            }
        } else {
            throw new IllegalArgumentException("parameter cannot be null!");
        }
    }


    private class Iter implements Iterator<Integer> {

        @Override
        public boolean hasNext() {
            return true;
        }

        @Override
        public Integer next() {
            return Counter.this.next();
        }
    }
}
