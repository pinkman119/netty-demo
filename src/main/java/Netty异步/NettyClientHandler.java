package Netty异步;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

import java.nio.charset.StandardCharsets;

/**
 * Client的处理器
 */
public class NettyClientHandler extends ChannelInboundHandlerAdapter {

    /**
     * 当通道启动就绪则会触发
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("client" + ctx);
        ctx.writeAndFlush(Unpooled.copiedBuffer("我是客户端，你好，服务端", StandardCharsets.UTF_8));
    }

    /**
     * 当通道有读取事件时，会触发
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        ByteBuf buf = (ByteBuf) msg;

        System.out.println("接收到服务器说：" + buf.toString(CharsetUtil.UTF_8));

        System.out.println("地址：" + ctx.channel().remoteAddress());
    }
}
