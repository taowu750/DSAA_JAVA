package util.datastructure.graph;

public interface IGraphEdge extends IProps {

    /**
     * 如果这条边存在于某个图中，返回这个图，否则返回 null。
     *
     * @return 存在图返回这个图；否则返回 null
     */
    IGraph graph();

    /**
     * 这条边的起始顶点；没有的话返回 null。
     *
     * @return 起始顶点；没有的话返回 null。
     */
    IGraphVertex from();

    /**
     * 这条边的目的顶点；没有的话返回 null。
     *
     * @return 目的顶点；没有的话返回 null。
     */
    IGraphVertex to();

    /**
     * 给定这条边的一个顶点，返回另一个顶点。如果 vertex 不是这条边的顶点，抛出{@link IllegalArgumentException}。
     *
     * @param vertex 这条边的一个顶点
     * @return 另一个顶点
     * @throws IllegalArgumentException 如果 vertex 不是这条边的顶点
     */
    IGraphVertex other(IGraphVertex vertex);

    /**
     * 这条边是不是个自环（即 from 和 to 相等）。
     *
     * @return 如果是自环返回 true；不是或未设置边返回 false
     */
    boolean isSelfCircle();
}
