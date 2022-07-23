package com.inventorymanagement.java.controllers.TopMenuControllrer.commands;

import com.inventorymanagement.java.utils.Constants;
import com.inventorymanagement.java.utils.observeUserData.UserData;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class LogoutEvent implements ButtonPressedEvent {

    MouseEvent mouseEvent;

    public  LogoutEvent(MouseEvent mouseEvent){
        this.mouseEvent = mouseEvent;
    }


    @Override
    public void onExecute()
    {
        Parent pane = null;
        try {
            UserData.removeState();
            pane = FXMLLoader.load(getClass().getResource(Constants.AUTH_FXML_DIR));
        } catch (IOException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

        Node node = (Node) mouseEvent.getSource();

        Stage stage = (Stage) node.getScene().getWindow();

        stage.close();
        Scene scene = new Scene(pane);
        scene.getStylesheets().add(getClass().getResource(Constants.STYLESHEET_DIR).toExternalForm());
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }
}
