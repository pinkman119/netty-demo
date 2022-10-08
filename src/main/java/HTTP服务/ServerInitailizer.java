package HTTP服务;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;

/**
 * Topic：
 *
 * @Author:Pinkman
 * @Date:2022/10/8 14:17
 * @Tips:
 */
public class ServerInitailizer extends ChannelInitializer<SocketChannel> {
    /**
     * 向管道加入处理器
     * @param socketChannel
     * @throws Exception
     */
    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline pipeline = socketChannel.pipeline();

        // 向管道中加入Netty的httpSeverCodec
        pipeline.addLast("HttpServerCodec",new HttpServerCodec());

        // 增加一个自定义的处理器
        pipeline.addLast("MyHttpServerCodec",new HttpServerHandler());

    }
}
