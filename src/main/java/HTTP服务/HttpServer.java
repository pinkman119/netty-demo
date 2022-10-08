package HTTP服务;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoop;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * Topic：
 *
 * @Author:Pinkman
 * @Date:2022/10/8 14:16
 * @Tips:
 */
public class HttpServer {
    public static void main(String[] args) {
        EventLoopGroup boss = new NioEventLoopGroup();
        EventLoopGroup workers = new NioEventLoopGroup();

        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(boss,workers).
                    channel(NioServerSocketChannel.class)
                    .childHandler(new ServerInitailizer());
            ChannelFuture channelFuture = bootstrap.bind(8848).sync();

            channelFuture.channel().closeFuture().sync();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            boss.shutdownGracefully();
            workers.shutdownGracefully();
        }
    }
}
