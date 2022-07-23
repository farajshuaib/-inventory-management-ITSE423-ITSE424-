package com.inventorymanagement.java.controllers.TopMenuControllrer.commands;

import com.inventorymanagement.java.utils.LayoutsActions;
import com.inventorymanagement.java.utils.observeUserData.UserData;
import javafx.scene.input.MouseEvent;

public class LogoutEvent extends LayoutsActions implements ButtonPressedEvent {


    public LogoutEvent(MouseEvent mouseEvent){
        setMouseEvent(mouseEvent);

    }


    @Override
    public void onExecute() {
        UserData.removeState();
    }


}
