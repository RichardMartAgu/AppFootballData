package com.svalero.appFootballData.task;

import com.svalero.appFootballData.model.Team;
import com.svalero.appFootballData.service.FootballService;
import io.reactivex.functions.Consumer;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.scene.control.ProgressIndicator;

public class FootballCompetitionTask extends Task<Integer> {

    private final String selectedCompetition;
    private final ObservableList<String> names;
    private final ProgressIndicator progressIndicator;

    public FootballCompetitionTask(String selectedCompetition, ObservableList<String> names, ProgressIndicator progressIndicator) {
        this.selectedCompetition = selectedCompetition;
        this.names = names;
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

        // Definir un Consumer para manejar los equipos recibidos
        Consumer<Team> user = (team) -> {
            // Retardar la ejecución para simular una operación de red
            Thread.sleep(250);
            // Agregar el nombre del equipo a la lista
            Platform.runLater(() -> this.names.add(team.getName()));
        };

        // Suscribir el Consumer al servicio para recibir los equipos
        footballService.getTeams(selectedCompetition)
                .subscribe(user);

        // Ocultar el indicador de progreso al finalizar la tarea
        Platform.runLater(() -> progressIndicator.setVisible(false));
        Platform.runLater(() -> progressIndicator.setProgress(0));

        return null;
    }

}
