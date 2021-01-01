package util.datastructure.graph;

import java.util.Objects;
import java.util.function.IntFunction;

/**
 * {@link IGraph}的工具类。
 */
public class Graphs {

    private Graphs() {
        throw new IllegalStateException("such objects cannot be constructed by reflection");
    }

    public static void addAll(IGraph graph, Iterable<IGraphVertex> vertices) {
        Objects.requireNonNull(graph);
        Objects.requireNonNull(vertices);

        for (IGraphVertex vertex : vertices) {
            graph.addVertex(vertex);
        }
    }

    public static void addAll(IGraph graph, IGraphVertex... vertices) {
        Objects.requireNonNull(graph);

        for (IGraphVertex vertex : vertices) {
            graph.addVertex(vertex);
        }
    }

    public static void addAll(IGraph graph, int num, IntFunction<IGraphVertex> vertexSupplier) {
        Objects.requireNonNull(graph);
        Objects.requireNonNull(vertexSupplier);
        if (num < 1)
            throw new IllegalArgumentException("num cannot be less than 1");

        for (int i = 0; i < num; i++) {
            graph.addVertex(vertexSupplier.apply(i));
        }
    }
}
