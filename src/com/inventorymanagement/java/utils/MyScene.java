
package com.inventorymanagement.java.utils;

import javafx.scene.Parent;
import javafx.scene.Scene;

public class MyScene {
    public static Scene getScene(Parent node) {
        Scene scene = new Scene(node);
        scene.getStylesheets().add(MyScene.class.getResource(Constants.STYLESHEET_DIR).toExternalForm());
        return scene;
    }
}
