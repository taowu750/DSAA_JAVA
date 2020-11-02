package util.datastructure;

/**
 * 比较接口。
 */
@FunctionalInterface
public interface Less {

    boolean less(Comparable i, Comparable j);
}
