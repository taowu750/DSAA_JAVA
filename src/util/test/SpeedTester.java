package util.test;

import util.base.Procedure;
import util.io.FormatPrint;

/**
 * 用来测试程序运行速度
 *
 * @author wutao
 */
public class SpeedTester {


    /**
     * 返回操作所需的运行时间，时间单位为秒
     *
     * @param runner 需要测试的一组操作
     * @return 运行时间
     */
    public static double testRunTime(Procedure runner) {
        long start, end;

        start = System.currentTimeMillis();
        runner.run();
        end = System.currentTimeMillis();

        return (double) (end - start) / 1000.0;
    }

    /**
     * 打印操作运行时间
     *
     * @param operationName 操作名称
     * @param runner        需要测试的一组操作
     */
    public static void printRunTime(String operationName, Procedure runner) {
        FormatPrint.group(() -> System.out.printf("\n\n运行时间为: %.3fs\n", testRunTime(runner)),
                operationName);
    }
}
