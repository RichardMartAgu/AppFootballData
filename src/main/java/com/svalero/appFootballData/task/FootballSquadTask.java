package com.svalero.appFootballData.task;

import com.svalero.appFootballData.model.Squad;
import com.svalero.appFootballData.service.FootballService;
import com.svalero.appFootballData.utils.ShowAlert;
import io.reactivex.functions.Consumer;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.scene.control.ProgressIndicator;

public class FootballSquadTask extends Task<Integer> {


    private final String selectedTeam;
    private final ProgressIndicator progressIndicator;
    private final ObservableList<Squad> squads;


    public FootballSquadTask(String selectedTeam, ObservableList<Squad> squads, ProgressIndicator progressIndicator) {
        this.selectedTeam = selectedTeam;
        this.squads = squads;
        this.progressIndicator = progressIndicator;
    }

    // Método que se ejecuta al iniciar la tarea
    @Override
    protected Integer call() {

        // Mostrar el indicador de progreso como indeterminado
        Platform.runLater(() -> progressIndicator.setVisible(true));
        Platform.runLater(() -> progressIndicator.setProgress(-1));

        // Crear una instancia del servicio de fútbol
        FootballService footballService = new FootballService();

        // Definir un Consumer para manejar los jugadores recibidos
        Consumer<Squad> user = (squad) -> {
            // Retardar la ejecución para simular una operación de red
            Thread.sleep(250);
            synchronized (squads) {
                // Agregar el jugador a la lista
                Platform.runLater(() -> this.squads.add(squad));
            }
        };

        // Suscribir el Consumer al servicio para recibir los jugadores
        footballService.getOneTeam(selectedTeam)
                .subscribe(user);

        // Ocultar el indicador de progreso al finalizar la tarea
        Platform.runLater(() -> progressIndicator.setVisible(false));
        Platform.runLater(() -> progressIndicator.setProgress(0));

        return null;
    }
}
