package com.svalero.appFootballData.controller;

import com.svalero.appFootballData.model.Team;
import com.svalero.appFootballData.service.FootballService;
import com.svalero.appFootballData.task.FootballTask;
import io.reactivex.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class AppController implements Initializable {

    @FXML
    private ComboBox<String> competitionComboBox;
    @FXML
    private Button searchButton;
    @FXML
    private ListView<String> teamListView;
    private Map<String, String> competitions = new HashMap<>();
    private  ObservableList<String> names;
    private String selectedCompetitionCode;

    private FootballTask footballTask;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

//         Crear un mapa de competiciones con clave (código) y valor (nombre)

        competitions.put("WC", "FIFA World Cup");
        competitions.put("CL", "UEFA Champions League");
        competitions.put("BL1", "Bundesliga");
        competitions.put("DED", "Eredivisie");
        competitions.put("BSA", "Campeonato Brasileiro Série A");
        competitions.put("FL1", "Ligue 1");
        competitions.put("ELC", "Championship");
        competitions.put("PPL", "Primeira Liga");
        competitions.put("EC", "European Championship");
        competitions.put("SA", "Serie A");
        competitions.put("PL", "Premier League");
        competitions.put("CLI", "Copa Libertadores");
        competitions.put("PD", "Primera Division");

        // Agregar las competiciones al ComboBox competitionComboBox
        competitionComboBox.setItems(FXCollections.observableArrayList(competitions.values()));
        //seleccionar una competición para mostrar
        competitionComboBox.getSelectionModel().selectFirst();
        teamListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == null) {
                teamListView.getSelectionModel().clearSelection();
            }
        });
    }

    @FXML
    public void findTeams(ActionEvent event) {

        this.names = FXCollections.observableArrayList();

        String selectedCompetitionName = competitionComboBox.getValue();

        System.out.println("Creando la tarea FootballTask para la competición: " + selectedCompetitionName);


        for (Map.Entry<String, String> entry : competitions.entrySet()) {
            if (entry.getValue().equalsIgnoreCase(selectedCompetitionName)) {
                selectedCompetitionCode = entry.getKey();
                break;
            }
        }

        System.out.println("Creando la tarea FootballTask para la competición: " + selectedCompetitionCode);
        System.out.println("Equipos: " + this.names);

        this.teamListView.setItems(this.names);

        footballTask = new FootballTask(selectedCompetitionCode, this.names);
        new Thread(footballTask).start();

    }
}