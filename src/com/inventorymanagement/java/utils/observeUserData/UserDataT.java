package com.inventorymanagement.java.utils.observeUserData;

import com.inventorymanagement.java.models.User;

interface UserDataT {
    public static User getState(){
        return null;
    };

    public static void setState(User userData) {

    }

    public static void removeState(User userData) {

    }
}
