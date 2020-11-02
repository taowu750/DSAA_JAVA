package algs1_fundamentals.sec3_bag_queue_stack;


import util.algs.StdOut;

import java.io.File;

/**
 * 1.3.43<br/>
 * 从命令行接受一个文件夹名作为参数，打印出该文件夹下所有文件并用递归的方式在所有子文件夹的名下（缩进）<br/>
 * 列出其下所有文件。
 */
public class E43_ListFiles {

    public static void printFiles(String directoryPath, int piles) {
        File directory = new File(directoryPath);
        File[] files = directory.listFiles();
        for (File file : files) {
            printChars(' ', piles * 4);
            StdOut.println(file.getName());
            if (file.isDirectory()) {
                printFiles(file.getPath(), piles + 1);
            }
        }
    }


    private static void printChars(char c, int numbers) {
        for (int i = 0; i < numbers; i++) {
            StdOut.print(c);
        }
    }


    public static void main(String[] args) {
        printFiles(args[0], 0);
    }
}
