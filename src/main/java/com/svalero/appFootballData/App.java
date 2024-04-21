package com.svalero.appFootballData;

import com.svalero.appFootballData.controller.AppController;
import com.svalero.appFootballData.controller.SplashScreenController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class App extends Application {

        // Método principal de inicio de la aplicación
        @Override
        public void start(Stage stage) throws IOException {

            // Se carga el archivo FXML del splash screen
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com.svalero.appFootballData/splashScreen.fxml"));
            // Se establece el controlador para el cargador FXMLLoader
            loader.setController(new SplashScreenController());
            // Se crea una escena con la vista del splash screen cargada desde el archivo FXML
            Scene scene = new Scene(loader.load());

            // Se configura la escena en el Stage y se establece el estilo como sin decoraciones
            stage.setScene(scene);
            stage.initStyle(StageStyle.UNDECORATED);
            stage.show();

            // Se inicia un hilo para esperar 3 segundos antes de cerrar el splash screen y mostrar la ventana principal
            new Thread(() -> {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                // Se ejecuta la siguiente tarea en el hilo de JavaFX
                javafx.application.Platform.runLater(() -> {
                    // Se cierra el splash screen
                    stage.close();
                    try {
                        // Se muestra la ventana principal de la aplicación
                        showMainStage();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });
            }).start();

        }

        // Método para mostrar la ventana principal de la aplicación
        private void showMainStage( ) throws IOException {

            // Se crea un nuevo Stage para la ventana principal
            Stage stage = new Stage();
            // Se carga el archivo FXML de la vista de la ventana principal
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com.svalero.appFootballData/view_football_data.fxml"));
            // Se crea una escena con la vista de la ventana principal cargada desde el archivo FXML
            Scene scene = new Scene(loader.load());
            // Se establece el título de la ventana principal
            stage.setTitle("AppFootballData");
            // Se configura la escena en el Stage
            stage.setScene(scene);
            // Se muestra la ventana principal
            stage.show();


        }

        public static void main(String[] args) {
            launch();
        }
}