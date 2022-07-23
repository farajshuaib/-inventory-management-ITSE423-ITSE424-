package com.inventorymanagement.java.utils;

import com.inventorymanagement.java.main.Launcher;
import com.inventorymanagement.java.models.User;
import com.inventorymanagement.java.utils.observeUserData.Observer;
import com.inventorymanagement.java.utils.observeUserData.UserData;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class LayoutsActions implements Observer {
    private MouseEvent mouseEvent;
    public User userData = UserData.getState();

    @FXML
    protected AnchorPane mainPane;
    @FXML
    private Button homeButton;
    @FXML
    protected Button categoryButton;
    @FXML
    protected Button usersButton;
    @FXML
    protected Button productsButton;
    @FXML
    protected Button historyButton;
    protected double xOffset;
    protected double yOffset;


    public LayoutsActions(){
        UserData.addObserver(this);
    }


    public void setMouseEvent(MouseEvent mouseEvent) {
        this.mouseEvent = mouseEvent;
    }

    public void homeBtnEvent(MouseEvent mouseEvent) {
        Parent parent = null;
        try {
            parent = FXMLLoader.load(getClass().getResource(Constants.HOME_FXML_DIR));
            Stage stage = (Stage) mainPane.getScene().getWindow();
            stage.setScene(MyScene.getScene(parent));
        } catch (IOException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
    public void  usersBtnEvent(MouseEvent mouseEvent){
        if(!userData.getRole().equals("admin")) return;
        Parent parent = null;
        try {
            parent = FXMLLoader.load(getClass().getResource(Constants.USERS_FXML_DIR));
            Stage stage = (Stage) mainPane.getScene().getWindow();
            stage.setScene(MyScene.getScene(parent));
        } catch (IOException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
    public void productBtnEvent(MouseEvent mouseEvent) {
        Parent parent = null;
        try {
            parent = FXMLLoader.load(getClass().getResource(Constants.PRODUCT_FXML_DIR));
            Stage stage = (Stage) mainPane.getScene().getWindow();
            stage.setScene(MyScene.getScene(parent));
        } catch (IOException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
    public void categoryBtnEvent(MouseEvent mouseEvent) {
        if(!userData.getRole().equals("admin")) return;
        Parent parent = null;
        try {
            parent = FXMLLoader.load(getClass().getResource(Constants.CATEGORY_FXML_DIR));
            Stage stage = (Stage) mainPane.getScene().getWindow();
            stage.setScene(MyScene.getScene(parent));
        } catch (IOException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
    public void historyBtnEvent(MouseEvent mouseEvent) {
        if(!userData.getRole().equals("admin")) return;
        Parent parent = null;
        try {
            parent = FXMLLoader.load(getClass().getResource(Constants.HISTORY_FXML_DIR));
            Stage stage = (Stage) mainPane.getScene().getWindow();
            stage.setScene(MyScene.getScene(parent));
        } catch (IOException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    protected void setStageDraggable() {
        mainPane.setOnMousePressed(event -> {
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        });

        mainPane.setOnMouseDragged(event -> {
            Launcher.stage.setX(event.getScreenX() - xOffset);
            Launcher.stage.setY(event.getScreenY() - yOffset);
            Launcher.stage.setOpacity(0.5f);
        });

        mainPane.setOnMouseReleased(event -> {
            Launcher.stage.setOpacity(1);
        });
    }


    @Override
    public void update() {
        User user = UserData.getState();

        System.out.println("user =>>>  "+ user);
        Parent pane = null;
        if(user == null){
            try {
                pane = FXMLLoader.load(getClass().getResource(Constants.AUTH_FXML_DIR));
            } catch (IOException e) {
                System.out.println(e.getMessage());
                e.printStackTrace();
            }
        } else {
            try {
                pane = FXMLLoader.load(getClass().getResource(Constants.HOME_FXML_DIR));
            } catch (IOException e) {
                System.out.println(e.getMessage());
                e.printStackTrace();
            }
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
