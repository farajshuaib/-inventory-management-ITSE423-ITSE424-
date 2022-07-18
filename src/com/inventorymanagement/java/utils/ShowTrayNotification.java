
package com.inventorymanagement.java.utils;

import com.inventorymanagement.java.utils.interfaces.Observer;
import javafx.util.Duration;
import tray.animations.AnimationType;
import tray.notification.NotificationType;
import tray.notification.TrayNotification;


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
