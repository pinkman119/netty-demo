package Netty异步;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

/**
 * Topic：
 *
 * @Author:Pinkman
 * @Date:2022/10/8 10:46
 * @Tips:
 * 自定义一个handler，需要继承netty规定好的handerAdapter
 * 此时该类成为一个Handler
 */
public class NettyServerHandler extends ChannelInboundHandlerAdapter {


    /**
     * handler
     * 管道：业务逻辑的管道  通道：数据通道，关注数据
     * @param ctx 上下文对象，含有管道pipeline，通道channel
     * @param msg 客户端发送的数据，默认是Obj形式
     * @throws Exception
     * 模拟时间花费较长的任务，使用异步处理。
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        // 任务提交到nio的任务队列
        ctx.channel().eventLoop().execute(()->{

            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("长任务执行完毕");
        });

        System.out.println("接收到信息：" + msg);
    }

    /**
     * 数据读取完毕
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        // 数据写入到缓冲区，并刷新
        // 一般来说，对发送数据进行编码
        ctx.writeAndFlush(Unpooled.copiedBuffer("我是服务端，你好，客户端",CharsetUtil.UTF_8));

    }

    /**
     * 发生异常后回调
     * @param ctx
     * @param cause
     * @throws Exception
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
