package util.io;

import util.algs.In;

/**
 * 用来获取 algs 测试数据。
 */
public class AlgsDataIO {

    private static final String DIR = "D:\\projects\\idea\\DSAA_JAVA\\src\\algs_data\\";


    private AlgsDataIO() {
        throw new IllegalAccessError("\"" + getClass().getSimpleName() +
                "\" cannot be constructed");
    }


    public static int[] get1Kints() {
        return newIn("1Kints.txt").readAllInts();
    }

    public static int[] get2Kints() {
        return newIn("2Kints.txt").readAllInts();
    }

    public static int[] get4Kints() {
        return newIn("4Kints.txt").readAllInts();
    }

    public static int[] get8Kints() {
        return newIn("8Kints.txt").readAllInts();
    }

    public static int[] get1Mints() {
        return newIn("1Mints.txt").readAllInts();
    }

    public static In openTinyUF() {
        return newIn("tinyUF.txt");
    }

    public static In openMediumUF() {
        return newIn("mediumUF.txt");
    }

    public static In openLargeUF() {
        return newIn("largeUF.txt");
    }

    public static String[] getTinyTale() {
        return newIn("tinyTale.txt").readAllStrings();
    }

    public static String[] getTale() {
        return newIn("tale.txt").readAllStrings();
    }

    public static String[] getLeipzig1M() {
        return newIn("leipzig1M.txt").readAllStrings();
    }

    /**
     * 小型有环图
     *
     * @return
     */
    public static In openTinyCG() {
        return newIn("tinyCG.txt");
    }

    public static In openTinyG() {
        return newIn("tinyG.txt");
    }

    public static In openMediumG() {
        return newIn("mediumG.txt");
    }

    /**
     * 用于符号图
     * @return
     */
    public static String fileRoutes() {
        return DIR + "routes.txt";
    }

    /**
     * 用于图间隔的度数
     *
     * @return
     */
    public static String fileMovies() {
        return DIR + "movies.txt";
    }

    /**
     * 包含5个强连通分量的有向图
     * @return
     */
    public static In openTinyDG() {
        return newIn("tinyDG.txt");
    }

    public static String fileJobs() {
        return DIR + "jobs.txt";
    }

    /**
     * 小型加权无向图
     * @return
     */
    public static In openTinyEWG() {
        return newIn("tinyEWG.txt");
    }

    /**
     * 包含250个顶点的加权无向欧拉图
     * @return
     */
    public static In openMediumEWG() {
        return newIn("mediumEWG.txt");
    }

    /**
     * 包含100万个顶点的加权无向欧拉图
     * @return
     */
    public static In openLargeEWG() {
        return newIn("largeEWG.txt");
    }

    /**
     * 小型加权有向图
     * @return
     */
    public static In openTinyEWD() {
        return newIn("tinyEWD.txt");
    }

    /**
     * 小型加权有向无环图
     * @return
     */
    public static In openTinyEWDAG() {
        return newIn("tinyEWDAG.txt");
    }

    /**
     * 用于图算法关键路径
     *
     * @return
     */
    public static In openJobsPC() {
        return newIn("jobsPC.txt");
    }

    /**
     * 用于图算法负权重图最短路径
     *
     * @return
     */
    public static In openTinyEWDn() {
        return newIn("tinyEWDn.txt");
    }

    /**
     * 用于图算法检测负权重环
     *
     * @return
     */
    public static In openTinyEWDnc() {
        return newIn("tinyEWDnc.txt");
    }

    /**
     * 将负权重图算法应用在套汇问题上
     *
     * @return
     */
    public static In openRates() {
        return newIn("rates.txt");
    }


    private static In newIn(String fileName) {
        return new In(DIR + fileName);
    }
}
