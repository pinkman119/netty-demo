package Buffer测试;

import lombok.extern.slf4j.Slf4j;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * Topic：
 *
 * @Author:Pinkman
 * @Date:2022/9/27 20:23
 * @Tips:
 */
@Slf4j
public class TestByteBuffer {
    public static void main(String[] args) {
        // FileChannel
        // 1.输入输出流获取
        try {
            FileChannel channel = new FileInputStream("data.txt").getChannel();

            // 准备缓冲区，向内存中申请
            ByteBuffer byteBuffer = ByteBuffer.allocate(10);

            while (true){
                int len = channel.read(byteBuffer);
                log.debug("数量是" + String.valueOf(len));
                if(len == -1){
                    break;
                }
                // 从channel中读取数据，向buffer写入
                channel.read(byteBuffer);

                byteBuffer.flip();// 切换为读模式

                // 是否还有剩余未读数据
                while (byteBuffer.hasRemaining()){
                    log.debug(String.valueOf((char)  byteBuffer.get()));
                }
                // 转换模式
                byteBuffer.clear();
            }

            // 当读取完成以后，再次读取，则实际上position指针又从1开始

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
