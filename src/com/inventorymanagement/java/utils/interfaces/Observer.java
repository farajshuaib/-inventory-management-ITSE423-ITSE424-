package com.inventorymanagement.java.utils.interfaces;

import tray.animations.AnimationType;
import tray.notification.NotificationType;
import tray.notification.TrayNotification;

public interface Observer {

    public static TrayNotification trayNotification(
            String title,
            String message,
            AnimationType animationType,
            NotificationType notificationType){
        return  new TrayNotification();
    };
}
