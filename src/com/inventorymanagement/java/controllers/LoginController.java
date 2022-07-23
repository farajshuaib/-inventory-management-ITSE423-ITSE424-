package com.inventorymanagement.java.controllers;

import com.inventorymanagement.java.dao_composite.Main_DAO;
import com.inventorymanagement.java.dao_composite.components.UsersDB;
import com.inventorymanagement.java.models.User;
import com.inventorymanagement.java.utils.Alerts;
import com.inventorymanagement.java.utils.LayoutsActions;
import com.inventorymanagement.java.utils.ShowTrayNotification;
import com.inventorymanagement.java.utils.observeUserData.UserData;
import com.inventorymanagement.java.utils.validators.EmailValidation;
import com.inventorymanagement.java.utils.validators.FacadeValidator;
import com.inventorymanagement.java.utils.validators.UserNameValidation;
import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import tray.animations.AnimationType;
import tray.notification.NotificationType;

public class LoginController extends LayoutsActions {
    UsersDB usersDB = Main_DAO.getInstance().users();
    @FXML
    private StackPane primaryPane;
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
        if(!validateValues()){
            loginFail();
            return;
        }

        setMouseEvent(event);

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



    }


    private void loginFail() {
        ShowTrayNotification
                .trayNotification("Login fails", "Invalid email or password",
                        AnimationType.SLIDE, NotificationType.ERROR);
    }


}
