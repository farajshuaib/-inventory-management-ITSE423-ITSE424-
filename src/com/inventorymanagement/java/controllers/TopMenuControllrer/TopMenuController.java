
package com.inventorymanagement.java.controllers.TopMenuControllrer;

import com.inventorymanagement.java.controllers.TopMenuControllrer.commands.*;
import com.inventorymanagement.java.main.Launcher;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

public class TopMenuController {
    double xOffset;
    double yOffset;

    @FXML
    private ImageView log_out_btn;
    @FXML
    private ImageView exit_btn;
    @FXML
    private ImageView maximize_btn;
    @FXML
    private ImageView minimize_btn;

    @FXML
    private AnchorPane mainPane;

    public void initialize() {
        mouseEvents();
        setStageDraggable();
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

    private void mouseEvents() {

        exit_btn.setOnMouseClicked(event -> {
            onButtonPressed(new CloseEvent(event));
        });

        maximize_btn.setOnMouseClicked(event -> {
            onButtonPressed(new MaximizeEvent());
        });

        minimize_btn.setOnMouseClicked(event -> onButtonPressed(new MinimizeEvent()));

        log_out_btn.setOnMouseClicked(event -> {
            onButtonPressed(new LogoutEvent(event));
        });
    }

    private  void onButtonPressed(ButtonPressedEvent pressedEvent){
        pressedEvent.onExecute();
    }}
