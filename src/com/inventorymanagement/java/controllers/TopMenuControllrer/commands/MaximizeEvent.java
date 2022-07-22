package com.inventorymanagement.java.controllers.TopMenuControllrer.commands;

import com.inventorymanagement.java.main.Launcher;

public class MaximizeEvent  implements ButtonPressedEvent {



    @Override
    public void onExecute() {
        Launcher.stage.setFullScreen(!Launcher.stage.isFullScreen());

    }
}
