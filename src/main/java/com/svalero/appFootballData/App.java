package com.svalero.appFootballData;

import com.svalero.appFootballData.controller.AppController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {

        @Override
        public void start(Stage stage) throws IOException {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com.svalero.appFootballData/view_football_data.fxml"));
            Scene scene = new Scene(loader.load());
            stage.setTitle("AppFootballData");
            stage.setScene(scene);
            stage.show();
        }

        public static void main(String[] args) {
            launch();
        }
}