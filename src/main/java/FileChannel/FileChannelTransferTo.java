package FileChannel;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

/**
 * Topic：
 *
 * @Author:Pinkman
 * @Date:2022/9/28 20:23
 * @Tips:
 */
public class FileChannelTransferTo {
    public static void main(String[] args) {
        try {
            FileChannel from = new FileInputStream("data.txt").getChannel();
            FileChannel to = new FileOutputStream("to.txt").getChannel();

            // 效率高：底层使用操作系统零拷贝优化，每次最多传输2G，多余部分则无法复制
            from.transferTo(0,from.size(),to);
            long size = from.size();
            for (long left = size; left > 0; ) {
                System.out.println(left);
                left -= from.transferTo(size - left,left,to);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
