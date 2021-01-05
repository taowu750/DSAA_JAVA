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

    public static String simpleGraphString(IGraph graph, int vertexOrder, int edgeOrder) {
        Objects.requireNonNull(graph);

        StringBuilder graphString = new StringBuilder();
        graphString.append(graph.getClass().getSimpleName())
                .append("(type=").append(graph.type())
                .append(", vertexNum=").append(graph.vertexNum())
                .append(", edgeNum=").append(graph.edgeNum())
                .append(") {");

        StringBuilder undirectedEdgeString = new StringBuilder();
        StringBuilder directedOutEdgeString = new StringBuilder();
        StringBuilder directedInEdgeString = new StringBuilder();
        for (IGraphVertex vertex : graph.vertices(vertexOrder)) {
            graphString.append("\n\t").append(vertex.getClass().getSimpleName())
                    .append("(id=").append(vertex.id())
                    .append(", outDegree=").append(vertex.outDegree())
                    .append(", inDegree=").append(vertex.inDegree())
                    .append(", degree=").append(vertex.degree()).append(")");
            if (vertex.hasEdge()) {
                graphString.append("[");
                for (IGraphEdge edge : vertex.edges(edgeOrder)) {
                    if (!edge.isDirected()) {
                        undirectedEdgeString.append("\n\t\t").append(edge.getClass().getSimpleName())
                                .append("(id=").append(edge.id())
                                .append(", from=").append(edge.from().id())
                                .append(", to=").append(edge.to().id()).append(")");
                    } else if (edge.isFrom(vertex)) {
                        directedOutEdgeString.append("\n\t\t").append(edge.getClass().getSimpleName())
                                .append("(id=").append(edge.id())
                                .append(", to=").append(edge.to().id()).append(")");
                    } else {
                        directedInEdgeString.append("\n\t\t").append(edge.getClass().getSimpleName())
                                .append("(id=").append(edge.id())
                                .append(", from=").append(edge.from().id()).append(")");
                    }
                }
                if (undirectedEdgeString.length() != 0) {
                    graphString.append("\n\t\tUndirected Edges:")
                            .append(undirectedEdgeString);
                    undirectedEdgeString.setLength(0);
                }
                if (directedOutEdgeString.length() != 0) {
                    graphString.append("\n\t\tDirected Out Edges:")
                            .append(directedOutEdgeString);
                    directedOutEdgeString.setLength(0);
                }
                if (directedInEdgeString.length() != 0) {
                    graphString.append("\n\t\tDirected In Edges:")
                            .append(directedInEdgeString);
                    directedInEdgeString.setLength(0);
                }
                graphString.append("\n\t]");
            }
        }

        graphString.append("\n}");

        return graphString.toString();
    }

    public static String simpleGraphString(IGraph graph, int vertexOrder) {
        return simpleGraphString(graph, vertexOrder, IGraph.ITER_DEFAULT);
    }

    public static String simpleGraphString(IGraph graph) {
        return simpleGraphString(graph, IGraph.ITER_DEFAULT);
    }
}
