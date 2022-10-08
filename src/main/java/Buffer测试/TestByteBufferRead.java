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
public class TestByteBufferRead {
    public static void main(String[] args) {
        ByteBuffer byteBuffer = ByteBuffer.allocate(10);

        byteBuffer.put(new byte[]{'a','b','c','d'});

        // 切换读模式
        byteBuffer.flip();
        log.debug("数据是：{}",byteBuffer.get(new byte[4]));

        // 本质上是把pos设置为了0，使得又可以从头开始读取。
        byteBuffer.rewind();
        log.debug("数据是：{}",byteBuffer.get(new byte[4]));
        byteBuffer.rewind();

        // mark & reset
        // mark做一个表级，而reset是将position重置
        byteBuffer.mark();
        log.debug("数据是：{}",byteBuffer.get());
        log.debug("数据是：{}",byteBuffer.get());
        log.debug("数据是：{}",byteBuffer.get());
        log.debug("数据是：{}",byteBuffer.get());
        byteBuffer.reset();
        log.debug("数据是：{}",byteBuffer.get());
    }
}
