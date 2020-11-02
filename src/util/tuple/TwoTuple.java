package util.tuple;


/**
 * 能够包含两个元素的元组
 */
public class TwoTuple<A, B> {

    public final A a;
    public final B b;


    public TwoTuple(A a, B b) {
        this.a = a;
        this.b = b;
    }
}
