package org.tinygame.herostory.cmdHandler;

import com.google.protobuf.GeneratedMessageV3;
import io.netty.channel.ChannelHandlerContext;
import org.tinygame.herostory.msg.GameMsgProtocol;

public interface ICmdHandler<Tcmd extends GeneratedMessageV3> {
    void handle(ChannelHandlerContext ctx,Tcmd cmd);
}
