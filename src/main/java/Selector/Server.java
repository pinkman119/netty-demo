package Selector;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * Topic：
 *
 * @Author:Pinkman
 * @Date:2022/10/4 19:48
 * @Tips:
 * accept事件  -  有连接请求时候触发
 * connect事件  -  客户端，连接建立后触发
 * read事件  -  可读事件
 * write事件  -  可写事件
 */
@Slf4j
public class Server {
    public static void main(String[] args) throws IOException {

        // 创建Selector
        Selector selector = Selector.open();
        // 使用nio来理解阻塞模式
        ServerSocketChannel ssc = ServerSocketChannel.open();
        ssc.configureBlocking(false);// 开启非阻塞模式

        // 事件发生后selectkey就是将来时间发生后，通过它可以得到是哪个发生的channel事件
        SelectionKey register = ssc.register(selector, 0, null);// 注册进入selector
        register.interestOps(SelectionKey.OP_ACCEPT);// 只关注accept事件
        log.debug("事件发生前的key{}",register);

        // 绑定监听端口
        ssc.bind(new InetSocketAddress(8080));
        // 建立连接
        List<SocketChannel> channels = new ArrayList<>();
        while (true){
            // 调用select方法，没有事件则阻塞，一旦有事件发生则selector恢复线程并运行。
            selector.select();
            // 处理事件，拿到事件集合，内部包含所有发生的事件，select事件触发后，必须处理或者取消，不可以不管，否则进入非阻塞状态。
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            Iterator<SelectionKey> iterator = selectionKeys.iterator();
            while(iterator.hasNext()){
                SelectionKey key = iterator.next();
                log.debug("事件发生后key{}",key);
                // 每次事件触发以后，都需要从队列中移除，否则会报空指针
                iterator.remove();;
                if (key.isAcceptable()) {// 如果是accept
                    ServerSocketChannel channel = (ServerSocketChannel) key.channel();
                    SocketChannel sc = channel.accept();
                    SelectionKey scKey = sc.register(selector, 0, null);
                    scKey.interestOps(SelectionKey.OP_READ);
                    log.debug("{}",sc);
                }else if(key.isReadable()){// 如果是read
                    SocketChannel channel = (SocketChannel) key.channel();
                    ByteBuffer byteBuffer = ByteBuffer.allocate(16);
                    channel.read(byteBuffer);
                    byteBuffer.flip();
                    System.out.println(byteBuffer.get());
                    System.out.println(byteBuffer.get());
                    System.out.println(byteBuffer.get());
                }
            }
        }
    }
}
