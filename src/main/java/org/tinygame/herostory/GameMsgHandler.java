package org.tinygame.herostory;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;
import io.netty.util.concurrent.GlobalEventExecutor;
import org.tinygame.herostory.msg.GameMsgProtocol;

import java.util.HashMap;
import java.util.Map;

public class GameMsgHandler extends SimpleChannelInboundHandler<Object> {

    static private final ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    static private  final Map<Integer,User> userMap = new HashMap<>();

    @Override
    public void channelActive(ChannelHandlerContext ctx)throws Exception{
        super.channelActive(ctx);
        channelGroup.add(ctx.channel());
    }
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Object msg) throws Exception {
        System.out.println("收到客户端消息,msg = " + msg+"msgClazz"+msg.getClass().getName());

        if(msg instanceof GameMsgProtocol.UserEntryCmd){
            GameMsgProtocol.UserEntryCmd cmd = (GameMsgProtocol.UserEntryCmd) msg;
            int userId = cmd.getUserId();
            String heroAvatar = cmd.getHeroAvatar();

            GameMsgProtocol.UserEntryResult.Builder resultBuilder = GameMsgProtocol.UserEntryResult.newBuilder();
            resultBuilder.setUserId(userId);
            resultBuilder.setHeroAvatar(heroAvatar);

            User newUser = new User();
            newUser.userId = userId;
            newUser.heroAvatar = heroAvatar;
            userMap.put(newUser.userId,newUser);


            GameMsgProtocol.UserEntryResult newResult = resultBuilder.build();
            channelGroup.writeAndFlush(newResult);
        }else if(msg instanceof  GameMsgProtocol.WhoElseIsHereCmd){
            GameMsgProtocol.WhoElseIsHereResult.Builder resultBuilder = GameMsgProtocol.WhoElseIsHereResult.newBuilder();
            for(User currUser:userMap.values()){
                GameMsgProtocol.WhoElseIsHereResult.UserInfo.Builder userInfoBuilder
                        =  GameMsgProtocol.WhoElseIsHereResult.UserInfo.newBuilder();
                userInfoBuilder.setUserId(currUser.userId);
                userInfoBuilder.setHeroAvatar(currUser.heroAvatar);

                resultBuilder.addUserInfo(userInfoBuilder);

            }

            GameMsgProtocol.WhoElseIsHereResult newResult = resultBuilder.build();
            channelHandlerContext.writeAndFlush(newResult);
        }

    }
}
