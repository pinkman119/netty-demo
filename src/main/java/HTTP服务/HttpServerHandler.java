package HTTP服务;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;

import java.net.URI;
import java.net.URL;
import java.nio.charset.StandardCharsets;

/**
 * Topic：
 *
 * @Author:Pinkman
 * @Date:2022/10/8 14:16
 * @Tips:
 * 指定交互对象是HttpObject，表示客户端和服务器端相互通信的数据被封装成该类型。
 */
public class HttpServerHandler extends SimpleChannelInboundHandler<HttpObject> {

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, HttpObject httpObject) throws Exception {
        // 判断msg是不是Request请求
        if (httpObject instanceof HttpRequest) {

            System.out.println("连接成功！");
            // 回复信息给浏览器
            ByteBuf content = Unpooled.copiedBuffer("你好，这里是HTTP服务器", StandardCharsets.UTF_8);

            // 过滤特定请求
            HttpRequest httpRequest = (HttpRequest) httpObject;
            URI uri = new URI(httpRequest.uri());
            if (uri.getPath().equals("/favicon.ico")) {
                System.out.println("响应图片");
                return;
            }


            // 构造一个http的响应，组装响应
            DefaultFullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK,content);
            response.headers().set(HttpHeaderNames.CONTENT_TYPE,"text/plain;charset=utf-8;");
            response.headers().set(HttpHeaderNames.CONTENT_LENGTH,content.readableBytes());
            // 返回响应
            channelHandlerContext.writeAndFlush(response);

        }
    }
}
