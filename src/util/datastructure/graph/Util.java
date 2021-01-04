package util.datastructure.graph;

class Util {

    static String identityString(Object obj) {
        return obj != null ? obj.getClass().getSimpleName() + "@" + System.identityHashCode(obj)
                : "null";
    }

    static String vertexSimpleString(IGraphVertex vertex) {
        return vertex != null ? vertex.getClass().getSimpleName() + "{id=" + vertex.id() + "}"
                : "null";
    }
}
