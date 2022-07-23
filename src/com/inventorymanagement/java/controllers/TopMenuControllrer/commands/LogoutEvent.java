package com.inventorymanagement.java.controllers.TopMenuControllrer.commands;

import com.inventorymanagement.java.utils.Constants;
import com.inventorymanagement.java.utils.LayoutsActions;
import com.inventorymanagement.java.utils.observeUserData.UserData;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class LogoutEvent extends LayoutsActions implements ButtonPressedEvent {

    MouseEvent mouseEvent;

    public LogoutEvent(MouseEvent mouseEvent){
        this.mouseEvent = mouseEvent;

    }


    @Override
    public void onExecute() {
        Parent pane = null;
        try {
            UserData.removeState();
            pane = FXMLLoader.load(getClass().getResource(Constants.AUTH_FXML_DIR));
        } catch (IOException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

        try {
            Node node = (Node) mouseEvent.getSource();
            System.out.println("node =>"+ node);

            Stage stage = (Stage) node.getScene().getWindow();
            System.out.println("stage =>"+ stage);

            if(stage != null){
                stage.close();
            }

            Scene scene = new Scene(pane);
            System.out.println("scene =>"+ scene);
            scene.getStylesheets().add(getClass().getResource(Constants.STYLESHEET_DIR).toExternalForm());
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();

        } catch (Exception e){
            System.out.println("error while setting the main pane => " + e);
        }
    }


}
