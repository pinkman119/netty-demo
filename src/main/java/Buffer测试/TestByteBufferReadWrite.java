package Buffer测试;

import lombok.extern.slf4j.Slf4j;

import java.nio.ByteBuffer;

/**
 * Topic：
 *
 * @Author:Pinkman
 * @Date:2022/9/28 19:17
 * @Tips:
 */
@Slf4j
public class TestByteBufferReadWrite {
    public static void main(String[] args) {
        ByteBuffer byteBuffer = ByteBuffer.allocate(10);
        byteBuffer.put((byte) 1);
        byteBuffer.put((byte) 2);
        byteBuffer.put((byte) 3);
        byteBuffer.put((byte) 4);
        byteBuffer.put(new byte[]{5,6,7,8,9,0});

        // 切换读模式
        byteBuffer.flip();
        while(true){
            log.debug("数据是：{}",byteBuffer.get());
        }
    }
}
