package util.io;

import util.algs.In;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

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
        try {
            return Files.readAllLines(Paths.get(DIR, "tinyTale.txt")).toArray(new String[0]);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static InputStream getTinyTaleIn() {
        try {
            return new FileInputStream(DIR + "tinyTale.txt");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 《双城记》第一章
     *
     * @return
     */
    public static String[] getMedTale() {
        try {
            return Files.readAllLines(Paths.get(DIR, "medTale.txt")).toArray(new String[0]);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 《双城记》全文
     *
     * @return
     */
    public static String getTale() {
        try {
            return String.join("\n", Files.readAllLines(Paths.get(DIR, "tale.txt")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static InputStream getMedTaleIn() {
        try {
            return new FileInputStream(DIR + "medTale.txt");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
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

    /**
     * 小型无向图
     *
     * @return
     */
    public static In openTinyG() {
        return newIn("tinyG.txt");
    }

    /**
     * 中型无向图
     *
     * @return
     */
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

    public static String fileJobs() {
        return DIR + "jobs.txt";
    }

    /**
     * 包含5个强连通分量的有向图。13个顶点、22条边
     *
     * @return
     */
    public static In openTinyDG() {
        return newIn("tinyDG.txt");
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

    /**
     * 一段病毒 DNA 序列
     *
     * @return
     */
    public static InputStream openGenomeVirus() {
        try {
            return new FileInputStream(DIR + "genomeVirus.txt");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static String getGenomeVirus() {
        try {
            return InputStreamGetter.getString(new FileInputStream(DIR + "genomeVirus.txt"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 字母 q 32x48 的位图。
     *
     * @return
     */
    public static InputStream openQ32x48() {
        try {
            return new FileInputStream(DIR + "q32x48.bin");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 字母 q 64x96 的位图。
     *
     * @return
     */
    public static InputStream openQ64x96() {
        try {
            return new FileInputStream(DIR + "q64x96.bin");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 用来测试 LZW 的简单文件
     *
     * @return
     */
    public static String getAbraLZW() {
        try {
            return InputStreamGetter.getString(new FileInputStream(DIR + "abraLZW.txt"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 小型容量图，由于网络流算法中。
     *
     * @return
     */
    public static In openTinyFN() {
        return newIn("tinyFN.txt");
    }


    private static In newIn(String fileName) {
        return new In(DIR + fileName);
    }
}
