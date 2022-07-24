package com.inventorymanagement.java.utils.observeUserData;


import com.inventorymanagement.java.models.User;

import java.util.ArrayList;
import java.util.List;

public class UserData implements UserDataT {

    protected static List<Observer> observerList = new ArrayList<Observer>();

    private static User state = null;

    private UserData(){}

    public static User getState() {
        return state;
    }

    public static void setState(User userData) {
        state = userData;
        notifyObserver();
    }

    public static void removeState(){
        state = null;
        notifyObserver();
    }

    public static void addObserver(Observer observer){
        observerList.add(observer);
    }

    public static void notifyObserver(){
        observerList.forEach(observer -> {
            observer.update();
        });
    }

}
