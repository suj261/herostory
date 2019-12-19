package org.tinygame.herostory;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ServerMain {
    /*
     * 日志对象
     * */
    static private final Logger LOGGER = LoggerFactory.getLogger(ServerMain.class);


    /*
    * 应用主函数
    * */
    static public void main(String[] argArray) {
        // http://cdn0001.afrxvk.cn/hero_story/demo/step010/index.html?serverAddr=127.0.0.1:12345&userId=1
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workGroup = new NioEventLoopGroup();

        ServerBootstrap b = new ServerBootstrap();
        b.group(bossGroup, workGroup);
        b.channel(NioServerSocketChannel.class);
        b.childHandler(new ChannelInitializer<SocketChannel>() {

            @Override
            protected void initChannel(SocketChannel ch) throws Exception {
                ch.pipeline().addLast(
                        new HttpServerCodec(),
                        new HttpObjectAggregator(65535),
                        new WebSocketServerProtocolHandler("/websocket"),
                        new GameMsgDecoder(),//自定义消息解码器
                        new GameMsgHandler()//自定义消息处理器
                );
            }
        });

        try {
            ChannelFuture f = b.bind(12345).sync();

            if (f.isSuccess()) {
                System.out.println("服务器启动成功");
            }
            f.channel().closeFuture().sync();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }


}
