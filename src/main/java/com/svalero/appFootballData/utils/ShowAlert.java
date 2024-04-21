package com.svalero.appFootballData.utils;

import javafx.animation.PauseTransition;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Popup;
import javafx.stage.Window;
import javafx.util.Duration;

public class ShowAlert {
    public static void showErrorAlert(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public static void showInformationAlert(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public static void showAlert(Window owner, String message, int seconds) {
        Popup popup = new Popup();
        Label label = new Label(message);
        StackPane popupContent = new StackPane(label);

        popup.getContent().add(popupContent);


        PauseTransition delay = new PauseTransition(Duration.seconds(seconds));
        delay.setOnFinished(event -> popup.hide());

        popup.show(owner, 950, 70);
        delay.play();
    }
}

