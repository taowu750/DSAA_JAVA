package util.tuple;


/**
 * 能够包含两个元素的元组
 */
public class Tuple2<A, B> {

    public final A a;
    public final B b;


    public Tuple2(A a, B b) {
        this.a = a;
        this.b = b;
    }
}
