package org.tinygame.herostory.model;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public final class UserManager {


    static private final Map<Integer,User> userMap = new HashMap<>();

    private UserManager(){}

    static public void addUser(User newUser){
        userMap.put(newUser.userId,newUser);
    }


    static public void  removeUserById(int userId){
        userMap.remove(userId);
    }

    static public Collection<User>  listUser(){
        return userMap.values();
    }
}
