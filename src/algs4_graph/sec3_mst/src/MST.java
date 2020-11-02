package algs4_graph.sec3_mst.src;

import util.algs.In;
import util.io.AlgsDataIO;

import java.lang.reflect.InvocationTargetException;

/**
 * 最小生成树。
 */
public interface MST {

    /**
     * 最小生成树的所有边。
     *
     * @return
     */
    Iterable<Edge> edges();

    /**
     * 最小生成树所有边的权值之和。
     *
     * @return
     */
    double weight();


    static void test(Class<? extends SimpleWeighedGraph> gClass, Class<? extends MST> mstClass) {
        try {
            SimpleWeighedGraph g = gClass.getConstructor(In.class)
                    .newInstance(AlgsDataIO.openTinyEWG());
            MST mst = mstClass.getConstructor(SimpleWeighedGraph.class)
                    .newInstance(g);

            System.out.println(gClass.getSimpleName() + "-" + mstClass.getSimpleName() +
                    String.format(" weight: %.2f", mst.weight()));
            for (Edge e : mst.edges()) {
                System.out.println(e);
            }
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }
}
