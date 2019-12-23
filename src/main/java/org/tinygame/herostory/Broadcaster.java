package org.tinygame.herostory;

import io.netty.channel.Channel;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

public final class Broadcaster {

    static private final ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);


    private Broadcaster(){}

    static public void addChannel(Channel channel){
        channelGroup.add(channel);
    }

    static public void removeChannel(Channel channel){
        channelGroup.remove(channel);
    }

    static public void broadcast(Object msg){
        if(null == msg){
            return;
        }

        channelGroup.writeAndFlush(msg);
    }

}
