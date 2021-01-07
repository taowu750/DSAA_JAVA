package util.datastructure.graph;

import java.util.Objects;
import java.util.function.BiFunction;
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

    public static String graphString(IGraph graph,
                                     BiFunction<IGraph, StringBuilder, StringBuilder> graphAppend,
                                     BiFunction<IGraphVertex, StringBuilder, StringBuilder> vertexAppend,
                                     TriFunction<IGraphEdge, IGraphVertex, StringBuilder, StringBuilder> edgeAppend,
                                     int vertexOrder, int edgeOrder) {
        Objects.requireNonNull(graph);

        if (graphAppend == null) {
            graphAppend = (g, sb) -> sb.append(g.toString());
        }
        if (vertexAppend == null) {
            vertexAppend = (v, sb) -> sb.append(v.toString());
        }
        if (edgeAppend == null) {
            edgeAppend = (e, v, sb) -> sb.append(e.toString());
        }

        StringBuilder graphString = new StringBuilder();
        graphAppend.apply(graph, graphString).append(" {");

        StringBuilder undirectedEdgeString = new StringBuilder();
        StringBuilder directedOutEdgeString = new StringBuilder();
        StringBuilder directedInEdgeString = new StringBuilder();
        for (IGraphVertex vertex : graph.vertices(vertexOrder)) {
            vertexAppend.apply(vertex, graphString.append("\n\t"));
            if (vertex.hasEdge()) {
                graphString.append(" [");
                for (IGraphEdge edge : vertex.edges(edgeOrder)) {
                    if (!edge.isDirected()) {
                        edgeAppend.apply(edge, vertex, undirectedEdgeString.append("\n\t\t"));
                    } else if (edge.isFrom(vertex)) {
                        edgeAppend.apply(edge, vertex, directedOutEdgeString.append("\n\t\t"));
                    } else {
                        edgeAppend.apply(edge, vertex, directedInEdgeString.append("\n\t\t"));
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

    public static String graphString(IGraph graph,
                                     BiFunction<IGraph, StringBuilder, StringBuilder> graphAppend,
                                     BiFunction<IGraphVertex, StringBuilder, StringBuilder> vertexAppend,
                                     TriFunction<IGraphEdge, IGraphVertex, StringBuilder, StringBuilder> edgeAppend) {
        return graphString(graph, graphAppend, vertexAppend, edgeAppend, IGraph.ITER_DEFAULT, IGraph.ITER_DEFAULT);
    }

    public static String graphString(IGraph graph, int vertexOrder, int edgeOrder) {
        return graphString(graph, null, null, null,
                vertexOrder, edgeOrder);
    }

    public static String graphString(IGraph graph, BiFunction<IGraph, StringBuilder, StringBuilder> graphAppend) {
        return graphString(graph, graphAppend, null, null);
    }

    public static String graphString(IGraph graph) {
        return graphString(graph, null, null, null,
                IGraph.ITER_DEFAULT, IGraph.ITER_DEFAULT);
    }

    public static String simpleGraphString(IGraph graph, int vertexOrder, int edgeOrder) {
        BiFunction<IGraph, StringBuilder, StringBuilder> graphAppend = (g, sb) ->
                sb.append(graph.getClass().getSimpleName())
                        .append("(type=").append(graph.type())
                        .append(", vertexNum=").append(graph.vertexNum())
                        .append(", edgeNum=").append(graph.edgeNum())
                        .append(")");
        BiFunction<IGraphVertex, StringBuilder, StringBuilder> vertexAppend = (v, sb) ->
                sb.append(v.getClass().getSimpleName())
                        .append("(id=").append(v.id())
                        .append(", outDegree=").append(v.outDegree())
                        .append(", inDegree=").append(v.inDegree())
                        .append(", degree=").append(v.degree()).append(")");
        TriFunction<IGraphEdge, IGraphVertex, StringBuilder, StringBuilder> edgeAppend = (e, v, sb) -> {
            switch (e.type()) {
                case UNDIRECTED:
                    sb.append(e.getClass().getSimpleName())
                            .append("(id=").append(e.id())
                            .append(", from=").append(e.from().id())
                            .append(", to=").append(e.to().id()).append(")");
                    break;

                case DIRECTED:
                    if (e.isFrom(v))
                        sb.append(e.getClass().getSimpleName())
                                .append("(id=").append(e.id())
                                .append(", to=").append(e.to().id()).append(")");
                    else
                        sb.append(e.getClass().getSimpleName())
                                .append("(id=").append(e.id())
                                .append(", from=").append(e.from().id()).append(")");
            }

            return sb;
        };

        return graphString(graph, graphAppend, vertexAppend, edgeAppend, vertexOrder, edgeOrder);
    }

    public static String simpleGraphString(IGraph graph, int vertexOrder) {
        return simpleGraphString(graph, vertexOrder, IGraph.ITER_DEFAULT);
    }

    public static String simpleGraphString(IGraph graph) {
        return simpleGraphString(graph, IGraph.ITER_DEFAULT);
    }
}
