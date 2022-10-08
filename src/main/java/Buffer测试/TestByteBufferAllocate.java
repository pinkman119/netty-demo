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
public class TestByteBufferAllocate {
    public static void main(String[] args) {
        // 使用堆内存，读写效率低，受到GC的影响
        ByteBuffer byteBuffer = ByteBuffer.allocate(10);
        // 使用直接内存，读写效率高（少一次拷贝），不受GC影响，但是需要及时释放，否则会造成内存泄露
        ByteBuffer directByteBuffer1 = ByteBuffer.allocateDirect(10);
    }
}
