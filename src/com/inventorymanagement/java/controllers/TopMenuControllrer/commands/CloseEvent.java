package com.inventorymanagement.java.controllers.TopMenuControllrer.commands;

import javafx.application.Platform;
import javafx.scene.input.MouseEvent;

public class CloseEvent implements ButtonPressedEvent{

    MouseEvent mouseEvent;

    public CloseEvent(MouseEvent mouseEvent){
        this.mouseEvent = mouseEvent;

    }

    @Override
    public void onExecute() {
        Platform.exit();
    }
}
