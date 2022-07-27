package com.inventorymanagement.java.controllers;

import com.inventorymanagement.java.dao.Main_DAO;
import com.inventorymanagement.java.dao.components.UsersDB;
import com.inventorymanagement.java.models.User;
import com.inventorymanagement.java.utils.Alerts;
import com.inventorymanagement.java.utils.Constants;
import com.inventorymanagement.java.utils.LayoutsActions;
import com.inventorymanagement.java.utils.ShowTrayNotification;
import com.inventorymanagement.java.utils.observeUserData.UserData;
import com.inventorymanagement.java.utils.validators.EmailValidation;
import com.inventorymanagement.java.utils.validators.FacadeValidator;
import com.inventorymanagement.java.utils.validators.UserNameValidation;
import com.jfoenix.controls.JFXButton;
import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import tray.animations.AnimationType;
import tray.notification.NotificationType;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginController extends LayoutsActions implements Initializable {
    UsersDB usersDB = Main_DAO.getInstance().getUsers();
    @FXML
    private StackPane primaryPane;
    @FXML
    private TextField loginEmailBtn;
    @FXML
    private PasswordField loginPasswordField;
    @FXML
    private JFXButton loginBtn;

    @FXML
    private ImageView bannerImage;

    FacadeValidator validator = new FacadeValidator(new EmailValidation(),new UserNameValidation());




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

        Parent pane = null;
        MouseEvent mouseEvent = event;


        try {
            User user = usersDB.getUserByEmailAndPassword(loginEmailBtn.getText(), loginPasswordField.getText());
            System.out.println("user =>"+ user);
            UserData.setState(user);

            if(user == null){
                ShowTrayNotification
                        .trayNotification("User Not Found", "Login failed",
                                AnimationType.SLIDE, NotificationType.ERROR);
                return;
            }
            pane = FXMLLoader.load(getClass().getResource(Constants.HOME_FXML_DIR));
            Node node = (Node) mouseEvent.getSource();
            Stage stage = (Stage) node.getScene().getWindow();
            stage.close();
            Scene scene = new Scene(pane);
            scene.getStylesheets().add(getClass().getResource(Constants.STYLESHEET_DIR).toExternalForm());
            stage.setScene(scene);stage.setResizable(false);
            stage.show();


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


    private void playAnimations() {
        TranslateTransition translateTransition  = new TranslateTransition(Duration.seconds(2) , loginBtn);
        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(2), bannerImage);
        translateTransition.setFromY(40);
        translateTransition.setByY(-50);
        fadeTransition.setFromValue(0);
        fadeTransition.setToValue(1);
        fadeTransition.play();
        translateTransition.play();
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setStageDraggable();
        playAnimations();
    }
}
