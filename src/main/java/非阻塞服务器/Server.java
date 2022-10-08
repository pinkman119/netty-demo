package 非阻塞服务器;

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
        ssc.configureBlocking(false);// 开启非阻塞模式

        // 绑定监听端口
        ssc.bind(new InetSocketAddress(8080));

        // 建立连接
        List<SocketChannel> channels = new ArrayList<>();
        while (true){
            // 用来与客户端之间通信，socketChanel
            SocketChannel channel = ssc.accept();

            if(channel != null){
                log.debug("connected...有人建立起连接：{}",channel);
                channels.add(channel);
            }


        }
    }
}
