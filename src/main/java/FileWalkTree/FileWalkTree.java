package FileWalkTree;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

/**
 * Topic：
 *
 * @Author:Pinkman
 * @Date:2022/9/28 20:47
 * @Tips:
 */
public class FileWalkTree {
    public static void main(String[] args) throws IOException {
        // 从start文件夹开始
        Files.walkFileTree(Paths.get("C:\\Users\\11918\\Desktop\\FastMiter设计\\fastemiter-center\\src\\main\\java\\com\\pinkman"),new SimpleFileVisitor<Path>(){
            /**
             * 遍历文件夹
             * @param dir
             * @param attrs
             * @return
             * @throws IOException
             */
            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                System.out.println("--->" + dir);
                return super.preVisitDirectory(dir, attrs);
            }

            /**
             * 遍历文件
             * @param file
             * @param attrs
             * @return
             * @throws IOException
             */
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                System.out.println("--->" + file);
                return super.visitFile(file, attrs);
            }

            @Override
            public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                System.out.println("退出");
                // 退出遍历可以删除文件夹，使用的是递归方式，该方式不推荐，直接从硬盘中移除，无法恢复。
                return super.postVisitDirectory(dir, exc);
            }


        });
    }
}
