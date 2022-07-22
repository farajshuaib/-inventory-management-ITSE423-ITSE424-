package com.inventorymanagement.java.controllers.TopMenuControllrer.commands;

import com.inventorymanagement.java.main.Launcher;

public class MinimizeEvent implements ButtonPressedEvent {



    @Override
    public void onExecute() {
        Launcher.stage.setIconified(true);
    }
}
