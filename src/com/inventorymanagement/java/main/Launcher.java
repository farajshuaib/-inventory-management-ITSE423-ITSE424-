
package com.inventorymanagement.java.main;

import com.inventorymanagement.java.utils.Constants;
import com.inventorymanagement.java.utils.DBConnection;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class Launcher extends Application {

    public static Stage stage = null;

    public static void main(String[] args) {
        DBConnection.getInstance();
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource(Constants.AUTH_FXML_DIR));
        } catch (IOException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource(Constants.STYLESHEET_DIR).toExternalForm());

        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.setTitle("Inventory Management System");
        primaryStage.getIcons().add(new Image(Constants.STAGE_ICON));
        primaryStage.setScene(scene);
        primaryStage.show();

        stage = primaryStage;
    }

    @Override
    public void init() throws Exception {
        super.init();
    }

    @Override
    public void stop() throws Exception {
        super.stop();
    }
}
