package Selector;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

/**
 * Topic：
 *
 * @Author:Pinkman
 * @Date:2022/10/5 20:08
 * @Tips:
 */
public class NIOServer {
    public static void main(String[] args) throws IOException {
        // 开启socket管道
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();

        // 得到Selector对象
        Selector selector = Selector.open();

        // 绑定端口
        serverSocketChannel.socket().bind(new InetSocketAddress(6666));
        serverSocketChannel.configureBlocking(false);// 非阻塞模式

        // 注册channel进入selector中，并绑定监听的事件
        SelectionKey register = serverSocketChannel.register(selector,SelectionKey.OP_ACCEPT);

        while (true){
            // 等待1s，如果没有事件发生则返回。
            int select = selector.select(3000);
            if (select == 0) {
                System.out.println("ServerRunning...");
                continue;
            }else{
                // 监听到事件发生，获取集合
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                Iterator<SelectionKey> iterator = selectionKeys.iterator();
                // 必须使用迭代器
                while (iterator.hasNext()) {
                    SelectionKey key = iterator.next();
                    // 反向获取通道
                    SelectableChannel channel = key.channel();
                    // 根据不同的key进行不同处理

                    if(key.isAcceptable() ){
                        SocketChannel socketChannel = serverSocketChannel.accept();

                        // 将socketchannel也注册到selector中,并绑定一个Buffer
                        socketChannel.configureBlocking(false);// 这里新加入的channel也要非阻塞，否则报错
                        socketChannel.register(selector,SelectionKey.OP_READ, ByteBuffer.allocate(1024));
                    }
                    if(key.isReadable()){
                        System.out.println("服务端接收客户端");
                        // 通过key反向获取channel
                        SocketChannel channel1 = (SocketChannel)key.channel();
                        ByteBuffer buffer = (ByteBuffer) key.attachment();
                        channel1.read(buffer);
                        buffer.clear();
                        System.out.println(channel1);
                        System.out.println("读取内容：" + new String(buffer.array()));
                        // 手动移除当前的key，防止重复操作
                    }
                    iterator.remove();
                }
            }

        }
    }

}
