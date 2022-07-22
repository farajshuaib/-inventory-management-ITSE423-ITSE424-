package com.inventorymanagement.java.controllers;

import com.inventorymanagement.java.dao.Main_DAO;
import com.inventorymanagement.java.dao.components.UsersDB;
import com.inventorymanagement.java.models.User;
import com.inventorymanagement.java.utils.Alerts;
import com.inventorymanagement.java.utils.Constants;
import com.inventorymanagement.java.utils.LayoutsActions;
import com.inventorymanagement.java.utils.ShowTrayNotification;
import com.inventorymanagement.java.utils.validators.EmailValidation;
import com.inventorymanagement.java.utils.validators.FacadeValidator;
import com.inventorymanagement.java.utils.validators.UserNameValidation;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXRadioButton;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import tray.animations.AnimationType;
import tray.notification.NotificationType;

import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.Objects;


public class Registration  extends LayoutsActions {
    UsersDB usersDB = Main_DAO.getInstance().users();
    FacadeValidator validator = new FacadeValidator(new EmailValidation(),new UserNameValidation());
    @FXML
    private StackPane primaryPane;
    @FXML
    private AnchorPane mainPane;
    @FXML
    private TextField firstNameField;
    @FXML
    private TextField lastNameField;
    @FXML
    private TextField emailField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private JFXRadioButton maleRadio;
    @FXML
    private ToggleGroup gender;
    @FXML
    private JFXRadioButton femaleRadio;
    @FXML
    private JFXButton signUpBtn;



    public void initialize() {
        //setting stage draggable
        setStageDraggable();
    }


    public void Submit() {
        if (firstNameField.getText().isEmpty() || firstNameField.getText().trim().isEmpty()) {
            Alerts.jfxAlert("Error", "First Name Field cannot be empty");
            registrationFail();
            return;
        }

        if (lastNameField.getText().isEmpty() || lastNameField.getText().trim().isEmpty()) {
            Alerts.jfxAlert("Error", "Last Name Field cannot be empty");
            registrationFail();
            return;
        }

        if (emailField.getText().isEmpty() || emailField.getText().trim().isEmpty()) {
            Alerts.jfxAlert("Error", "Email Field cannot be empty");
            registrationFail();
            return;
        }

        if (!validator.isEmailValid(emailField.getText())) {
            Alerts.jfxAlert("Error", "Invalid email address");
            registrationFail();
            return;
        }

        if (passwordField.getText().isEmpty() || passwordField.getText().trim().isEmpty()) {
            Alerts.jfxAlert("Error", "Password Field cannot be empty");
            registrationFail();
            return;
        }

        if (!maleRadio.isSelected()) {
            if (!femaleRadio.isSelected()) {
                Alerts.jfxAlert("Error", "Gender must be selected");
                registrationFail();
                return;
            }
        }

        if (passwordField.getText().length() < 6) {
            Alerts.jfxAlert("Error", "Password must be greater than 6 characters");
            registrationFail();
            return;
        }

        if (usersDB.userExist(emailField.getText()) == 1) {
            Alerts.jfxAlert("Error", "Email already register");
            ShowTrayNotification
                    .trayNotification("Account creation unsuccessful", "Email already registered",
                            AnimationType.SLIDE, NotificationType.ERROR);
            return;
        }

        String getGender = null;

        if (maleRadio.isSelected()) getGender = "Male";
        if (femaleRadio.isSelected()) getGender = "Female";

        User user = new User(
                0, firstNameField.getText(), lastNameField.getText(), emailField.getText(),
                getGender, 0 + "", passwordField.getText()
        );
        if (usersDB.create(user) != 1) {
            Alerts.jfxAlert("Error", "Error occured");
            registrationFail();
            return;
        }

        resetSignUpProperties();

        Alerts.jfxAlert("Successfully", "Account was successfully created");

        ShowTrayNotification
                .trayNotification("Account creation success", "Account Successfully created, login now",
                        AnimationType.SLIDE, NotificationType.SUCCESS);

    }

    private void resetSignUpProperties() {
        firstNameField.setText("");
        lastNameField.setText("");
        emailField.setText("");
        passwordField.setText("");
        maleRadio.setSelected(false);
        femaleRadio.setSelected(false);
    }



    private void registrationFail() {
        ShowTrayNotification
                .trayNotification("Account registration fails", "Account creation was unsuccessful",
                        AnimationType.SLIDE, NotificationType.ERROR);
    }
}
