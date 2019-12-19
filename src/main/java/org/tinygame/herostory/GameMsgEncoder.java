package org.tinygame.herostory;

import com.google.protobuf.GeneratedMessageV3;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;

import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tinygame.herostory.msg.GameMsgProtocol;

import java.nio.ByteBuffer;

public class GameMsgEncoder extends ChannelOutboundHandlerAdapter {
    static private final Logger loggrer = LoggerFactory.getLogger(GameMsgEncoder.class);
    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        if(null == msg||!(msg instanceof GeneratedMessageV3)){
            super.write(ctx,msg,promise);
            return;
        }

        int msgCode = -1;

        if(msg instanceof GameMsgProtocol.UserEntryResult){
            msgCode = GameMsgProtocol.MsgCode.USER_ENTRY_RESULT_VALUE;
        }else {
            loggrer.error("无法识别");
            return;
        }
        byte[] byteArray = ((GeneratedMessageV3) msg).toByteArray();

        ByteBuf byteBuf = ctx.alloc().buffer();
        byteBuf.writeShort((short)0);
        byteBuf.writeShort((short)msgCode);
        byteBuf.writeBytes(byteArray);

        BinaryWebSocketFrame frame = new BinaryWebSocketFrame();
        super.write(ctx,frame,promise);

    }
}
