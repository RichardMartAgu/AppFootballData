package com.svalero.appFootballData.controller;

import com.svalero.appFootballData.model.Squad;
import com.svalero.appFootballData.task.FootballCompetitionTask;
import com.svalero.appFootballData.task.FootballSquadTask;
import com.svalero.appFootballData.utils.ShowAlert;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.net.URL;
import java.util.ResourceBundle;

public class FootballTaskController implements Initializable {
    @FXML
    private TableView<Squad> squadTableView;
    @FXML
    private TableColumn<Squad, String> nameColumn;
    @FXML
    private TableColumn<Squad, String> positionColumn;
    @FXML
    private TableColumn<Squad, String> nationalityColumn;
    @FXML
    private TableColumn<Squad, String> birthColumn;
    @FXML
    private ListView<String> teamListView;
    @FXML
    private ProgressIndicator progressIndicator;
    private ObservableList<String> names;
    private ObservableList<Squad> squads;
    private FootballCompetitionTask footballCompetitionTask;
    private FootballSquadTask footballSquadTask;
    private String selectedCompetition;
    private String selectedTeam;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        //inicializamos la visualización de la tableView y la listView
        teamListView.setVisible(true);
        squadTableView.setVisible(true);

        //hacemos instancia de la lista
        this.squads = FXCollections.observableArrayList();

        //añadimos la lista al tableView
        squadTableView.setItems(squads);

        //añadir valores a las columnas del tableView
        nameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
        positionColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPosition()));
        nationalityColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNationality()));
        birthColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDateOfBirth()));

        // inicializar la lista de nombres
        this.names = FXCollections.observableArrayList();
        this.teamListView.setItems(this.names);
    }

    //recuperamos la competición seleccionada y a la vez creamos la tarea
    public void createCompetitionTask(String selectedCode) {
        this.selectedCompetition = selectedCode;

        createFootballCompetitionTask();
    }

    //recuperamos el equipo seleccionado y a la vez creamos la tarea
    public void createFootballTeamTask(String teamId) {
        this.selectedTeam = teamId;

        createFootballTeamTask();
    }

    //crea la tarea para recibir los equipos de una competición
    private void createFootballCompetitionTask() {

        //Ocultamos el tableView de lso jugadores
        squadTableView.setVisible(false);

        // Verificar si competitionCode no es nulo antes de imprimirlo
        if (selectedCompetition != null) {
            // creamos la instancia de FootballTask con el código de competición, la lista de nombres y la barra de progreso
            footballCompetitionTask = new FootballCompetitionTask(selectedCompetition, names, progressIndicator);

            // Iniciar la tarea en un hilo separado
            new Thread(footballCompetitionTask).start();
        } else {
            // Manejar el caso en que competitionCode sea nulo
            ShowAlert.showErrorAlert("Error", "error al crear: ", "No se puede crear la tarea FootballTask: competitionCode es nulo");
        }
    }

    //crea la tarea para recibir los jugadores de un equipo
    private void createFootballTeamTask() {

        //Ocultamos el listView de los equipos
        teamListView.setVisible(false);

        // Verificar si competitionCode no es nulo antes de imprimirlo
        if (selectedTeam != null) {
            // creamos la instancia de FootballTeamTask con Id del equipo, la lista de jugadores y la barra de progreso
            footballSquadTask = new FootballSquadTask(selectedTeam, squads, progressIndicator);

            // Iniciar la tarea en un hilo separado
            new Thread(footballSquadTask).start();
        } else {
            // Manejar el caso en que el equipo sea nulo
            ShowAlert.showErrorAlert("Error", "error al crear: ", "No se puede crear la tarea FootballTask: competitionCode es nulo");
        }
    }
}
