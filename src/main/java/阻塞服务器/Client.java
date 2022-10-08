package 阻塞服务器;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;

/**
 * Topic：
 *
 * @Author:Pinkman
 * @Date:2022/10/3 19:48
 * @Tips:
 */
@Slf4j
public class Client {
    public static void main(String[] args) throws IOException {
        SocketChannel sc = SocketChannel.open();
        sc.connect(new InetSocketAddress("localhost",8080));
        System.out.println("waiting...");
        sc.write(Charset.defaultCharset().encode("yep"));
    }
}
