package com.inventorymanagement.java.controllers;

import com.inventorymanagement.java.dao_composite.Main_DAO;
import com.inventorymanagement.java.dao_composite.components.UsersDB;
import com.inventorymanagement.java.main.Launcher;
import com.inventorymanagement.java.models.User;
import com.inventorymanagement.java.utils.Alerts;
import com.inventorymanagement.java.utils.Constants;
import com.inventorymanagement.java.utils.ShowTrayNotification;
import com.inventorymanagement.java.utils.observeUserData.UserData;
import com.inventorymanagement.java.utils.validators.EmailValidation;
import com.inventorymanagement.java.utils.validators.FacadeValidator;
import com.inventorymanagement.java.utils.validators.UserNameValidation;
import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import tray.animations.AnimationType;
import tray.notification.NotificationType;

import java.io.IOException;

public class LoginController {
    double xOffset;
    double yOffset;
    UsersDB usersDB = Main_DAO.getInstance().users();
    @FXML
    private StackPane primaryPane;
    @FXML
    private VBox mainPane;
    @FXML
    private TextField loginEmailBtn;
    @FXML
    private PasswordField loginPasswordField;
    @FXML
    private JFXButton loginBtn;

    FacadeValidator validator = new FacadeValidator(new EmailValidation(),new UserNameValidation());




    public void initialize() {

        setStageDraggable();
    }


    private boolean validateValues(){
        boolean isValidUser = false;
        if (loginEmailBtn.getText().isEmpty() || loginEmailBtn.getText().trim().isEmpty()) {
            Alerts.jfxAlert("Error", "Email Field cannot be empty");
            return false;
        }

        if (loginPasswordField.getText().isEmpty() || loginPasswordField.getText().trim().isEmpty()) {
            Alerts.jfxAlert("Error", "Password Field cannot be empty");
            return false;
        }

        if ( !validator.isEmailValid(loginEmailBtn.getText())) {
            Alerts.jfxAlert("Error", "Invalid email address");
            return false;
        }

        try {
            isValidUser = usersDB.authenticate(loginEmailBtn.getText(), loginPasswordField.getText());
        } catch (Exception e){
            Alerts.jfxAlert("Error", "Email or password incorrect");
        }


        if (!isValidUser) {
            Alerts.jfxAlert("Error", "Email or password incorrect");
            return false;
        }

        return  true;
    }


    public void loginBtnAction(MouseEvent event)  {
        Parent pane = null;

        if(!validateValues()){
            loginFail();
            return;
        }


        try {
            User user = usersDB.getUserByEmailAndPassword(loginEmailBtn.getText(), loginPasswordField.getText());
            if(user == null){
                ShowTrayNotification
                        .trayNotification("User Not Found", "Login failed",
                                AnimationType.SLIDE, NotificationType.ERROR);
                return;
            }
            UserData.setState(user);
            ShowTrayNotification
                    .trayNotification("Login in success", "Welcome back",
                            AnimationType.SLIDE, NotificationType.SUCCESS);
        } catch (Exception e){
            System.out.println(e);
        }


        try {
            pane = FXMLLoader.load(getClass().getResource(Constants.HOME_FXML_DIR));
        } catch (IOException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

        Node node = (Node) event.getSource();

        Stage stage = (Stage) node.getScene().getWindow();

        stage.close();
        Scene scene = new Scene(pane);
        scene.getStylesheets().add(getClass().getResource(Constants.STYLESHEET_DIR).toExternalForm());
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    private void resetLoginProperties() {
        loginEmailBtn.setText("");
        loginPasswordField.setText("");
    }

    private void loginFail() {
        ShowTrayNotification
                .trayNotification("Login fails", "Invalid email or password",
                        AnimationType.SLIDE, NotificationType.ERROR);
    }


    private void setStageDraggable() {
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

}
