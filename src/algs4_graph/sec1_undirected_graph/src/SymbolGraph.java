package algs4_graph.sec1_undirected_graph.src;

import util.algs.In;
import util.algs.StdIn;
import util.io.AlgsDataIO;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

/**
 * 符号图。为每个顶点分配一个字符串标签
 */
public class SymbolGraph {

    private HashMap<String, Integer> labelMap;
    private String[] inverseMap;
    private SimpleGraph g;


    public SymbolGraph(String filename, String delimiter, Class<? extends SimpleGraph> graphClass) {
        labelMap = new HashMap<>();
        In in = new In(filename);
        while (in.hasNextLine()) {
            String[] a = in.readLine().split(delimiter);

            for (int i = 0; i < a.length; i++) {
                if (!labelMap.containsKey(a[i]))
                    labelMap.put(a[i], labelMap.size());
            }
        }

        inverseMap = new String[labelMap.size()];
        for (String name: labelMap.keySet()) {
            inverseMap[labelMap.get(name)] = name;
        }

        try {
            g = graphClass.getConstructor(int.class).newInstance(labelMap.size());
        } catch (InstantiationException | NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            throw new IllegalStateException(e);
        }
        in = new In(filename);
        while (in.hasNextLine()) {
            String[] a = in.readLine().split(delimiter);
            int v = labelMap.get(a[0]);
            for (int i = 1; i < a.length; i++) {
               g.addEdge(v, labelMap.get(a[i]));
            }
        }
    }

    public SymbolGraph(String filename, String delimiter) {
        this(filename, delimiter, UndirectedGraph.class);
    }


    public static void main(String[] args) {
        String filename = AlgsDataIO.fileMovies();
        String delimiter = "/";
        SymbolGraph sg = new SymbolGraph(filename, delimiter);
        SimpleGraph g = sg.g();

        System.out.println("请输入名称：");
        while (StdIn.hasNextLine()) {
            String s = StdIn.readLine();
            for (Integer w : g.adj(sg.index(s))) {
                System.out.println("\t" + sg.name(w));
            }
        }
    }


    public boolean contains(String s) {
        return labelMap.containsKey(s);
    }

    public int index(String s) {
        return labelMap.get(s);
    }

    public String name(int v) {
        return inverseMap[v];
    }

    public SimpleGraph g() {
        return g;
    }
}
