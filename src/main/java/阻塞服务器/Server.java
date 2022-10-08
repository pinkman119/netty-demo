package 阻塞服务器;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.List;
/**
 * Topic：
 *
 * @Author:Pinkman
 * @Date:2022/10/3 19:48
 * @Tips:
 */
@Slf4j
public class Server {
    public static void main(String[] args) throws IOException {

        ByteBuffer byteBuffer = ByteBuffer.allocate(1668);

        // 使用nio来理解阻塞模式
        ServerSocketChannel ssc = ServerSocketChannel.open();

        // 绑定监听端口
        ssc.bind(new InetSocketAddress(8080));

        // 建立连接
        List<SocketChannel> channels = new ArrayList<>();
        while (true){
            log.debug("connecting...进入阻塞");
            // 用来与客户端之间通信，socketChanel
            SocketChannel channel = ssc.accept();
            log.debug("connected...有人建立起连接：{}",channel);
            channels.add(channel);
            // 接收客户端发送的数据
            for (SocketChannel socketChannel : channels) {
                socketChannel.read(byteBuffer);// 停止下来，客户端未发送数据。
                byteBuffer.flip();
                System.out.println((char) byteBuffer.get());
                System.out.println((char) byteBuffer.get());
                System.out.println((char) byteBuffer.get());
                byteBuffer.clear();
            }

        }
    }
}
