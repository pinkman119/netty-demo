package Selector;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;

/**
 * Topic：
 *
 * @Author:Pinkman
 * @Date:2022/10/5 20:22
 * @Tips:
 */
public class NIOClient {
    public static void main(String[] args) throws IOException {
        // 开启socket管道
        SocketChannel serverSocketChannel = SocketChannel.open();
        // 非阻塞模式
        serverSocketChannel.configureBlocking(false);
        // 提供服务器端ip和端口
        boolean connected = serverSocketChannel.connect(new InetSocketAddress("127.0.0.1", 6666));

        if (!connected) {
            while (!serverSocketChannel.finishConnect()){
                System.out.println("连接需要花费时间，但是客户端不会阻塞");
            }
        }
        // 连接成功

        ByteBuffer buffer = ByteBuffer.wrap("hello,world！".getBytes(StandardCharsets.UTF_8));
        // 写入
        serverSocketChannel.write(buffer);
        System.out.println("完事~");
    }
}
