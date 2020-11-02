package util.tuple;


/**
 * 能够包含三个元素的元组
 */
public class ThreeTuple<A, B, C> extends TwoTuple<A, B> {

    public final C c;

    public ThreeTuple(A a, B b, C c) {
        super(a, b);
        this.c = c;
    }
}
