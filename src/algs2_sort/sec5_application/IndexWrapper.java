package algs2_sort.sec5_application;

/**
 * 用来包装元素和下标的包装器。
 */
public class IndexWrapper<T extends Comparable<T>> implements Comparable<T> {

    private int index;
    private T item;


    public IndexWrapper(int index, T item) {
        this.index = index;
        this.item = item;
    }


    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public T getItem() {
        return item;
    }

    public void setItem(T item) {
        this.item = item;
    }


    @Override
    public int compareTo(T o) {
        return item.compareTo(o);
    }
}
