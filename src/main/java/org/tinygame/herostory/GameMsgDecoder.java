package org.tinygame.herostory;

import com.google.protobuf.GeneratedMessageV3;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;
import org.tinygame.herostory.msg.GameMsgProtocol;

public class GameMsgDecoder extends SimpleChannelInboundHandler<Object> {
    @Override
    public void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (!(msg instanceof BinaryWebSocketFrame)) {
            return;

        }

        BinaryWebSocketFrame frame = (BinaryWebSocketFrame) msg;
        ByteBuf byteBuf = frame.content();

        byteBuf.readShort();//消息长度
        int msgCode = byteBuf.readShort();//消息编号

        //拿到消息体
        byte[] msgBody = new byte[byteBuf.readableBytes()];

        byteBuf.readBytes(msgBody);

        GeneratedMessageV3 cmd = null;
        switch (msgCode) {
            case GameMsgProtocol.MsgCode.USER_ENTRY_CMD_VALUE:
                cmd = GameMsgProtocol.UserEntryCmd.parseFrom(msgBody);
                break;
        }
        if(null != cmd){
            ctx.fireChannelRead(cmd);
        }


    }
}
