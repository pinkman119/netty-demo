package Netty异步;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * Topic：
 *
 * @Author:Pinkman
 * @Date:2022/10/8 10:34
 * @Tips:
 */
public class SimpleNettyClient {
    public static void main(String[] args) throws InterruptedException {
        // 客户端只需要一个事件循环组
        NioEventLoopGroup eventExecutors = new NioEventLoopGroup();

        // 创建一个客户端启动对象
        Bootstrap bootstrap = new Bootstrap();

        // 设置相关参数
        bootstrap.group(eventExecutors)// 设置线程组
                .channel(NioSocketChannel.class)// 设置客户端通道实现类
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        socketChannel.pipeline().addLast(new NettyClientHandler());// 加入自己的处理器
                    }
                });

        System.out.println("Client Running...");

        // 启动客户端去连接服务器端
        ChannelFuture channelFuture = bootstrap.connect("127.0.0.1", 6668).sync();

        // future可以通过监听器拿到执行结果的回执，实现完成任务后回调
        channelFuture.addListeners(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture channelFuture) throws Exception {
                if(channelFuture.isSuccess()) {
                    System.out.println("Listener runing ...");
                }
            }
        });
        // 关闭客户端通道
        channelFuture.channel().closeFuture().sync();

    }
}
