package org.tinygame.herostory.cmdHandler;

import com.google.protobuf.GeneratedMessageV3;
import org.tinygame.herostory.msg.GameMsgProtocol;

import javax.annotation.Generated;
import java.util.HashMap;
import java.util.Map;

public final class CmdHandlerFactory {

    static private Map<Class<?>,ICmdHandler<? extends GeneratedMessageV3>> handlerMap = new HashMap<>();



    private CmdHandlerFactory(){
        handlerMap.put(GameMsgProtocol.UserEntryCmd.class,new UserEntryCmdHandler() );
        handlerMap.put(GameMsgProtocol.WhoElseIsHereCmd.class,new WhoElseIsHereCmdHandler() );
        handlerMap.put(GameMsgProtocol.UserMoveToCmd.class,new UserMoveToCmdHandler() );
    }

    static public  ICmdHandler<? extends GeneratedMessageV3> create(Object msg){
        if (msg instanceof GameMsgProtocol.UserEntryCmd) {
           return new UserEntryCmdHandler();
        } else if (msg instanceof GameMsgProtocol.WhoElseIsHereCmd) {
           return new WhoElseIsHereCmdHandler();
        } else if (msg instanceof GameMsgProtocol.UserMoveToCmd) {
           return new UserMoveToCmdHandler();
        }
        return  null;
    }

}
