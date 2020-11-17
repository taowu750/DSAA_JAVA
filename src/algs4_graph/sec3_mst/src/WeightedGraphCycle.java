package algs4_graph.sec3_mst.src;

import util.datastructure.MyStack;

import java.util.HashSet;

public class WeightedGraphCycle {

    private boolean visited[];
    private Edge[] edgeTo;
    private MyStack<Edge> cycle;
    private boolean[] onStack;
    private HashSet<Edge> visitedEdges;
    private boolean cycleFound;

    public WeightedGraphCycle(SimpleWeighedGraph g) {
        initVariables(g);

        for(int vertex = 0; vertex < g.vertexNum(); vertex++) {
            if (cycleFound) {
                break;
            }

            if (!visited[vertex]) {
                dfs(g, vertex);
            }
        }
    }

    // 构造函数，它只使用作为参数传递的顶点作为源来搜索循环。
    // 对于子图搜索或当必须执行许多搜索而不是所有顶点都需要分析时，非常有用。
    public WeightedGraphCycle(SimpleWeighedGraph g, HashSet<Integer> vertices) {
        initVariables(g);

        for(int vertex : vertices) {
            if (cycleFound) {
                break;
            }

            if (!visited[vertex]) {
                dfs(g, vertex);
            }
        }
    }

    private void initVariables(SimpleWeighedGraph g) {
        visited = new boolean[g.vertexNum()];
        edgeTo = new Edge[g.vertexNum()];
        onStack = new boolean[g.vertexNum()];
        visitedEdges = new HashSet<>();
        cycleFound = false;
        cycle = null;
    }

    private void dfs(SimpleWeighedGraph g, int vertex) {
        onStack[vertex] = true;
        visited[vertex] = true;

        for(Edge neighbor : g.adj(vertex)) {
            if (visitedEdges.contains(neighbor)) {
                continue;
            }

            visitedEdges.add(neighbor);
            int neighborVertex = neighbor.other(vertex);

            if (hasCycle()) {
                return;
            } else if (!visited[neighborVertex]) {
                edgeTo[neighborVertex] = neighbor;
                dfs(g, neighborVertex);
            } else if (onStack[neighborVertex]) {
                cycleFound = true;
                cycle = new MyStack<>();

                for(int currentVertex = vertex; currentVertex != neighborVertex;
                    currentVertex = edgeTo[currentVertex].other(currentVertex)) {
                    cycle.push(edgeTo[currentVertex]);
                }

                cycle.push(neighbor);
            }
        }

        onStack[vertex] = false;
    }

    public boolean hasCycle() {
        return cycle != null;
    }

    public MyStack<Edge> cycle() {
        return cycle;
    }
}
