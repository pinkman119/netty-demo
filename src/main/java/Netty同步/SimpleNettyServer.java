package Netty同步;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * Topic：
 *
 * @Author:Pinkman
 * @Date:2022/10/8 10:34
 * @Tips:
 */
public class SimpleNettyServer {
    public static void main(String[] args) throws InterruptedException {
        // 创建 BossGroup 和 WorkerGroup
        // 处理连接请求
        NioEventLoopGroup bossGroup = new NioEventLoopGroup();
        // bossGroup请求建立后交给workerGroup进行处理
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();
        try {


            // 创建服务器启动引导对象，配置参数
            ServerBootstrap bootstrap = new ServerBootstrap();

            bootstrap.group(bossGroup,workerGroup)
                    .channel(NioServerSocketChannel.class)  // 设置管道
                    .option(ChannelOption.SO_BACKLOG,128) // 设置线程队列个数
                    .childOption(ChannelOption.SO_KEEPALIVE,true) // 设置保持活动状态
                    .childHandler(new ChannelInitializer<SocketChannel>() {  // 给 workerGroup 和 EventLoop 对应管道设置处理器
                        // 给pipeline设置处理器
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            // 将处理器加入管道
                            socketChannel.pipeline().addLast(new NettyServerHandler());
                        }
                    });

            System.out.println("Server is Ready...");

            // 绑定一个端口，并且同步，生成了一个ChannelFuture对象
            // 启动服务器
            ChannelFuture cf = bootstrap.bind(6668).sync();

            // 对关闭通道进行监听
            ChannelFuture sync = cf.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            // 优雅关闭
            workerGroup.shutdownGracefully();
        }
    }
}
