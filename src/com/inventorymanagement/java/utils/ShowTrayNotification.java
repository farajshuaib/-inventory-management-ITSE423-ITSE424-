
package com.inventorymanagement.java.utils;

import javafx.util.Duration;
import tray.animations.AnimationType;
import tray.notification.NotificationType;
import tray.notification.TrayNotification;


interface Observer {
    public static TrayNotification trayNotification(
            String title,
            String message,
            AnimationType animationType,
            NotificationType notificationType){
        return  new TrayNotification();
    };
}

public class ShowTrayNotification implements Observer {
    public static TrayNotification trayNotification(
            String title,
            String message,
            AnimationType animationType,
            NotificationType notificationType
    ) {
        TrayNotification tray = new TrayNotification();

        tray.setAnimationType(animationType);
        tray.setTitle(title);
        tray.setMessage(message);
        tray.setNotificationType(notificationType);
        tray.showAndDismiss(Duration.millis(3000));
        return tray;
    }
}
