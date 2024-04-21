package com.svalero.appFootballData.controller;

import com.svalero.appFootballData.task.FootballTask;
import com.svalero.appFootballData.utils.ShowAlert;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;

import java.net.URL;
import java.util.ResourceBundle;

public class FootballTaskController implements Initializable {

    @FXML
    private ListView<String> teamListView;
    private ObservableList<String> names;
    private FootballTask footballTask;
    private String competitionCode;

    @Override
    public void initialize(URL location, ResourceBundle resources) {


        teamListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == null) {
                teamListView.getSelectionModel().clearSelection();
            }
        });

        // inicializar la lista de nombres
        this.names = FXCollections.observableArrayList();
        this.teamListView.setItems(this.names);
    }

    //recuperamos la competición seleccionada y a la vez creamos la tarea
    public void setCompetitionCode(String selectedCompetition) {
        this.competitionCode = selectedCompetition;

        createFootballTask();
    }

    private void createFootballTask() {

        // Verificar si competitionCode no es nulo antes de imprimirlo
        if (competitionCode != null) {
            // creamos la instancia de FootballTask con el código de competición y la lista de nombres
            footballTask = new FootballTask(competitionCode, names);

            // Iniciar la tarea en un hilo separado
            new Thread(footballTask).start();
        } else {
            // Manejar el caso en que competitionCode sea nulo
            ShowAlert.showErrorAlert("Error","error al crear: ","No se puede crear la tarea FootballTask: competitionCode es nulo");
        }
    }
}
