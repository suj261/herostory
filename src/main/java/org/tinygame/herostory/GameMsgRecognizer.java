package org.tinygame.herostory;


import com.google.protobuf.GeneratedMessageV3;
import com.google.protobuf.Message;
import org.tinygame.herostory.msg.GameMsgProtocol;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

public class GameMsgRecognizer {

    private GameMsgRecognizer() {

    }

    static private final Map<Integer, GeneratedMessageV3> msgCodeAndeMsgBodyMap = new HashMap<>();

    static private final Map<Class<?>,Integer> msgClazzAndMsgCodeMap = new HashMap<>();

//    static public Message.Builder getBuilderByMsgCode(int msgCode) {
//        switch (msgCode) {
//            case GameMsgProtocol.MsgCode.USER_ENTRY_CMD_VALUE:
//                return GameMsgProtocol.UserEntryCmd.newBuilder();
//
//
//            case GameMsgProtocol.MsgCode.WHO_ELSE_IS_HERE_CMD_VALUE:
//                return GameMsgProtocol.WhoElseIsHereCmd.newBuilder();
//
//
//            case GameMsgProtocol.MsgCode.USER_MOVE_TO_CMD_VALUE:
//                return GameMsgProtocol.UserMoveToCmd.newBuilder();
//
//        }
//        return null;
//
//    }

    static public void init() {
        Class<?>[] innerClazzArray = GameMsgProtocol.class.getDeclaredClasses();
        for(Class<?> innerClazz : innerClazzArray){
            if(!GeneratedMessageV3.class.isAssignableFrom(innerClazz)){
                continue;
            }
            String clazzName = innerClazz.getSimpleName();
            clazzName.toLowerCase();
            for(GameMsgProtocol.MsgCode msgCode:GameMsgProtocol.MsgCode.values()){
                String strMsgCode = msgCode.name();
                strMsgCode = strMsgCode.replaceAll("_","");
                strMsgCode = strMsgCode.toLowerCase();

                if(!strMsgCode.startsWith(clazzName)){
                    continue;
                }

                try {
                    Object returnObj = innerClazz.getDeclaredMethod("getDefaultInstance").invoke(innerClazz);
                    msgCodeAndeMsgBodyMap.put(msgCode.getNumber(), (GeneratedMessageV3) returnObj);

                    msgClazzAndMsgCodeMap.put(innerClazz,msgCode.getNumber());


                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                }
            }

        }





//        msgCodeAndeMsgBodyMap.put(GameMsgProtocol.MsgCode.USER_ENTRY_CMD_VALUE, GameMsgProtocol.UserEntryCmd.getDefaultInstance());
//        msgCodeAndeMsgBodyMap.put(GameMsgProtocol.MsgCode.WHO_ELSE_IS_HERE_RESULT_VALUE, GameMsgProtocol.WhoElseIsHereResult.getDefaultInstance());
//        msgCodeAndeMsgBodyMap.put(GameMsgProtocol.MsgCode.USER_MOVE_TO_RESULT_VALUE, GameMsgProtocol.UserMoveToResult.getDefaultInstance());
//
//        msgClazzAndMsgCodeMap.put(GameMsgProtocol.UserEntryResult.class, GameMsgProtocol.MsgCode.USER_ENTRY_RESULT_VALUE);
//        msgClazzAndMsgCodeMap.put(GameMsgProtocol.WhoElseIsHereResult.class, GameMsgProtocol.MsgCode.WHO_ELSE_IS_HERE_RESULT_VALUE);
//        msgClazzAndMsgCodeMap.put(GameMsgProtocol.UserMoveToResult.class, GameMsgProtocol.MsgCode.USER_MOVE_TO_RESULT_VALUE);
//        msgClazzAndMsgCodeMap.put(GameMsgProtocol.UserQuitResult.class, GameMsgProtocol.MsgCode.USER_QUIT_RESULT_VALUE);

    }

    static public Message.Builder getBuilderByMsgCode(int msgCode) {
        if (msgCode < 0) {
            return null;
        }
        GeneratedMessageV3 msg = msgCodeAndeMsgBodyMap.get(msgCode);
        if (null == msg) {
            return null;
        }
        return msg.newBuilderForType();
    }

    static public int getMsgCodeByMsgClazz(Class<?> msgClazz) {
       if(null == msgClazz){
           return -1;
       }
       return  msgClazzAndMsgCodeMap.get(msgClazz);



    }
}
