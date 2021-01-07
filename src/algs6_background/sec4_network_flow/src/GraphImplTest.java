package algs6_background.sec4_network_flow.src;

import org.junit.jupiter.api.Test;
import util.algs.In;
import util.datastructure.graph.*;
import util.io.AlgsDataIO;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 测试{@link GraphImpl}
 */
public class GraphImplTest {

    GraphImpl newGraph(String whichData) {
        // 13 个顶点，13 条边
        GraphImpl graph;
        In graphIn;
        switch (whichData) {
            case "tinyG":
                graphIn = AlgsDataIO.openTinyG();
                graph = new GraphImpl(IGraph.GraphType.UNDIRECTED);
                break;

            case "tinyDG":
                graphIn = AlgsDataIO.openTinyDG();
                graph = new GraphImpl(IGraph.GraphType.DIRECTED);
                break;

            default:
                throw new RuntimeException();
        }

        int vertexNum = graphIn.readInt();
        Graphs.addAll(graph, vertexNum, i -> new GraphVertexImpl());

        int edgeNum = graphIn.readInt();
        for (int i = 0; i < edgeNum; i++) {
            int from = graphIn.readInt(), to = graphIn.readInt();
            graph.addEdge(from, to, (fromVertex, toVertex) ->
                    new GraphEdgeImpl(graph.type() == IGraph.GraphType.DIRECTED
                            ? IGraphEdge.EdgeType.DIRECTED
                            : IGraphEdge.EdgeType.UNDIRECTED,
                            fromVertex, toVertex));
        }
        graphIn.close();

        return graph;
    }

    GraphImpl tinyUndirectedGraph() {
        // 13 个顶点，13 条边
        return newGraph("tinyG");
    }

    GraphImpl tinyDirectedGraph() {
        // 13 个顶点，22 条边，5 个强连通分量
        return newGraph("tinyDG");
    }

    @Test
    public void testGraphIter() {
        IGraph graph = tinyUndirectedGraph();
        System.out.println(graph + "\n");

        System.out.println(Graphs.simpleGraphString(graph, IGraph.ITER_DESC_BY_ID) + "\n");

        System.out.println(Graphs.simpleGraphString(graph, IGraph.ITER_RANDOM) + "\n");

        System.out.println(Graphs.simpleGraphString(graph, IGraph.ITER_ASC_BY_ID, IGraph.ITER_RANDOM) + "\n");
    }

    @Test
    public void testAddVertex() {
        IGraph graph = tinyUndirectedGraph();

        int vertexNum = graph.vertexNum();
        assertEquals(graph.addVertex(graph.vertex(9)), 9);
        assertEquals(graph.vertexNum(), vertexNum);

        assertEquals(graph.addVertex(new GraphVertexImpl()), vertexNum);
        assertEquals(graph.vertexNum(), vertexNum + 1);
    }

    @Test
    public void testInsertVertex() {
        IGraph graph = tinyUndirectedGraph();

        int vertexNum = graph.vertexNum();
        assertFalse(graph.insertVertex(7, new GraphVertexImpl()));
        assertEquals(graph.vertexNum(), vertexNum);

        assertTrue(graph.insertVertex(23, new GraphVertexImpl()));
        assertEquals(graph.vertexNum(), vertexNum + 1);

        assertEquals(graph.addVertex(new GraphVertexImpl()), 24);
        assertEquals(graph.vertexNum(), vertexNum + 2);
    }

    @Test
    public void testRemoveVertex() {
        IGraph graph = tinyUndirectedGraph();

        int vertexNum = graph.vertexNum();
        assertNull(graph.removeVertex(100));

        graph.vertex(0).putProp("test", "test");
        int edgeNum = graph.vDegree(0);
        List<Integer> edgeIds = new ArrayList<>();
        List<Integer> otherVertexIds = new ArrayList<>();
        for (IGraphEdge edge : graph.vEdges(0)) {
            edgeIds.add(edge.id());
            otherVertexIds.add(edge.other(graph.vertex(0)).id());
        }
        for (int i = 0; i < edgeIds.size(); i++) {
            int edgeId = edgeIds.get(i);
            assertTrue(graph.containsEdge(edgeId));
            assertTrue(graph.vertex(otherVertexIds.get(i)).isAttachEdge(edgeId));
        }
        IGraph.RemovedVertexWithEdge removed = graph.removeVertex(0);
        assertEquals(graph.vertexNum(), vertexNum - 1);
        assertNotNull(removed);
        assertEquals(removed.vertex.id(), -1);
        assertNull(removed.vertex.graph());
        assertEquals(removed.vertex.getProp("test"), "test");
        assertEquals(removed.edges.size(), edgeNum);
        for (int i = 0; i < edgeNum; i++) {
            assertNull(removed.edges.get(i).graph());
            assertNull(removed.edges.get(i).from());
            assertNull(removed.edges.get(i).to());
            assertEquals(removed.edges.get(i).id(), -1);
            assertFalse(graph.containsEdge(edgeIds.get(i)));
            assertFalse(graph.vertex(otherVertexIds.get(i)).isAttachEdge(edgeIds.get(i)));
        }
    }

    @Test
    public void testConstructDirectedGraph() {
        System.out.println(tinyDirectedGraph());
    }

    @Test
    public void testOutInEdges() {
        IGraph graph = tinyDirectedGraph();
        for (IGraphEdge edge : graph.vOutEdges(0)) {
            System.out.println(edge);
        }
        System.out.println();

        for (IGraphEdge edge : graph.vInEdges(5)) {
            System.out.println(edge);
        }
    }

    @Test
    public void testRemoveEdgeFromTo() {
        IGraph graph = tinyDirectedGraph();
        int edgeNum = graph.edgeNum();

        IGraphEdge edge = graph.edge(0, 5);
        List<IGraphEdge> removedEdges = graph.removeEdges(0, 5);

        assertSame(edge, removedEdges.get(0));
        assertNull(graph.edge(0, 5));
        assertEquals(graph.edgeNum(), edgeNum - 1);
    }

    @Test
    public void testRemoveOutEdges() {
        IGraph graph = tinyDirectedGraph();
        int edgeNum = graph.edgeNum();

        int outDegree = graph.vOutDegree(0);
        List<IGraphEdge> removedEdges = graph.removeVertexOutEdges(0);
        assertEquals(removedEdges.size(), outDegree);
        assertEquals(graph.edgeNum(), edgeNum - outDegree);
    }
}
