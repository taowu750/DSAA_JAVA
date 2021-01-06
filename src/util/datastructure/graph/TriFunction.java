package util.datastructure.graph;

public interface TriFunction<F, S, T, R> {

    R apply(F f, S s, T t);
}
