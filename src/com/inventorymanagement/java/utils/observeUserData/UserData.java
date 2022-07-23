package com.inventorymanagement.java.utils.observeUserData;


import com.inventorymanagement.java.models.User;

public class UserData implements UserDataT {

    private static User state = null;

    private UserData(){}

    public static User getState() {
        return state;
    }

    public static void setState(User userData) {
        state = userData;
    }

    public static void removeState(){
        state = null;
    }

}
