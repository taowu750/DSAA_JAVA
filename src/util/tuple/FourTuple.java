package util.tuple;


/**
 * 能够包含四个元素的元组
 */
public class FourTuple<A, B, C, D> extends ThreeTuple<A, B, C> {

    public final D d;


    public FourTuple(A a, B b, C c, D d) {
        super(a, b, c);
        this.d = d;
    }
}
