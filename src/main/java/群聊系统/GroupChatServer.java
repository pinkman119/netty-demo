package 群聊系统;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;

/**
 * Topic：
 *
 * @Author:Pinkman
 * @Date:2022/10/5 20:53
 * @Tips:
 */
public class GroupChatServer {

    private Selector selector;
    private ServerSocketChannel listener;
    private static final int PORT = 8848;

    public GroupChatServer() {
        try {
            // 开启选择器
            selector = selector.open();
            // 开启管道
            listener = ServerSocketChannel.open();
            // 给管道绑定端口
            listener.socket().bind(new InetSocketAddress(PORT));
            // 设置非阻塞模式
            listener.configureBlocking(false);
            // 将管道注册进入选择器
            listener.register(selector, SelectionKey.OP_ACCEPT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void listen(){

        try {
            while (true){
                int count = selector.select(2000);
                if (count > 0) {
                    Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                    while (iterator.hasNext()) {
                        // 找到被触发的key
                        SelectionKey key = iterator.next();
                        // 该key事件触发，则开始处理连接
                        if(key.isAcceptable()){
                            SocketChannel sc = listener.accept();
                            sc.register(selector,SelectionKey.OP_READ);
                            System.out.println(sc.getRemoteAddress() + "：已经上线");

                        }
                        // 该key事件触发read，则开始处理
                        if(key.isReadable()){

                        }
                        // 当前的key删除，防止重复处理
                        iterator.remove();
                    }
                }else{
                    System.out.println("listening...");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void read(SelectionKey key){
        try {
            SocketChannel channel = (SocketChannel)key.channel();
            // 创建缓冲buffer
            ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
            int count = channel.read(byteBuffer);
            if(count > 0){
                String msg = new String(byteBuffer.array());
                // 得到消息
                System.out.println("客户端：" + msg);
                // 向其他客户端转发消息。
                dispatch(msg,channel);
            }else{
                System.out.println("消息为空");
            }
        } catch (IOException e) {

            e.printStackTrace();
        }


    }
    // 转发给其他用户
    public void dispatch(String msg,SocketChannel selft){
        System.out.println("服务器转发中..");
        // 遍历所有selector，并排除自己
        try {
            for (SelectionKey key : selector.keys()) {
                SocketChannel channel = (SocketChannel) key.channel();
                if(channel == selft){
                    continue;
                }else{
                    // 将buffer中数据写入通道
                    channel.write(ByteBuffer.wrap(msg.getBytes(StandardCharsets.UTF_8)));

                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {

    }
}
