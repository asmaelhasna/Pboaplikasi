package com.siskema.gryffindor.ui;

import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Region;

public class StyleUtil {

    public static void styleRoundedButton(Button btn) {
        btn.setStyle(
            "-fx-background-color: " + UIConstants.HEX_PRIMARY + ";" +
            "-fx-text-fill: white;" +
            "-fx-background-radius: 20;" +
            "-fx-font-family: 'SansSerif';" +
            "-fx-font-size: 14px;" +
            "-fx-font-weight: bold;" +
            "-fx-cursor: hand;" +
            "-fx-padding: 8 24 8 24;"
        );
        
        // Efek hover
        btn.setOnMouseEntered(e -> btn.setStyle(
            "-fx-background-color: " + UIConstants.HEX_PRIMARY_DARK + ";" +
            "-fx-text-fill: white;" +
            "-fx-background-radius: 20;" +
            "-fx-font-family: 'SansSerif';" +
            "-fx-font-size: 14px;" +
            "-fx-font-weight: bold;" +
            "-fx-cursor: hand;" +
            "-fx-padding: 8 24 8 24;"
        ));
        
        btn.setOnMouseExited(e -> btn.setStyle(
            "-fx-background-color: " + UIConstants.HEX_PRIMARY + ";" +
            "-fx-text-fill: white;" +
            "-fx-background-radius: 20;" +
            "-fx-font-family: 'SansSerif';" +
            "-fx-font-size: 14px;" +
            "-fx-font-weight: bold;" +
            "-fx-cursor: hand;" +
            "-fx-padding: 8 24 8 24;"
        ));
    }

    public static void styleSecondaryButton(Button btn) {
        btn.setStyle(
            "-fx-background-color: " + UIConstants.HEX_BUTTON_GRAY + ";" +
            "-fx-text-fill: " + UIConstants.HEX_TEXT_DARK + ";" +
            "-fx-background-radius: 8;" +
            "-fx-font-family: 'SansSerif';" +
            "-fx-font-size: 12px;" +
            "-fx-cursor: hand;" +
            "-fx-padding: 8 20 8 20;"
        );
    }

    public static void styleRoundedTextField(TextField tf) {
        tf.setStyle(
            "-fx-background-color: white;" +
            "-fx-background-radius: 16;" +
            "-fx-border-color: #D2D2D2;" +
            "-fx-border-radius: 16;" +
            "-fx-padding: 8 10 8 10;" +
            "-fx-text-fill: " + UIConstants.HEX_TEXT_DARK + ";"
        );
        tf.setPrefHeight(38);
    }

    public static void styleRoundedPasswordField(PasswordField pf) {
        pf.setStyle(
            "-fx-background-color: white;" +
            "-fx-background-radius: 16;" +
            "-fx-border-color: #D2D2D2;" +
            "-fx-border-radius: 16;" +
            "-fx-padding: 8 10 8 10;" +
            "-fx-text-fill: " + UIConstants.HEX_TEXT_DARK + ";"
        );
        pf.setPrefHeight(38);
    }
}