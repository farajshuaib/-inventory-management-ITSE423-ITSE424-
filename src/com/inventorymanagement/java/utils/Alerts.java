
package com.inventorymanagement.java.utils;

import com.jfoenix.controls.JFXDialogLayout;
import javafx.scene.control.Alert;
import javafx.stage.Modality;

public class Alerts {

    public static void jfxAlert(String title, String body) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.initModality(Modality.APPLICATION_MODAL);

        JFXDialogLayout layout = new JFXDialogLayout();
        alert.setTitle("!ops...");
        alert.setHeaderText(title);
        alert.setContentText(body);


        alert.showAndWait();
    }

}
