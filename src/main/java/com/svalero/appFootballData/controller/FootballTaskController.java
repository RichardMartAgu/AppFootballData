package com.svalero.appFootballData.controller;

import com.svalero.appFootballData.model.Squad;
import com.svalero.appFootballData.model.Team;
import com.svalero.appFootballData.task.FootballCompetitionTask;
import com.svalero.appFootballData.task.FootballSquadTask;
import com.svalero.appFootballData.utils.ShowAlert;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ResourceBundle;

public class FootballTaskController implements Initializable {
    @FXML
    private TextField filterTextField;
    @FXML
    private Label filterTeam;
    @FXML
    private Label filterCompetition;
    @FXML
    private TableView<Team> teamTableView;
    @FXML
    private TableColumn<Team, String> teamColumn;
    @FXML
    private TableColumn<Team, String> tlaColumn;
    @FXML
    private TableColumn<Team, ImageView> crestColumn;
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
    private ProgressIndicator progressIndicator;
    private ObservableList<Team> teams;
    private ObservableList<Squad> squads;
    private FootballCompetitionTask footballCompetitionTask;
    private FootballSquadTask footballSquadTask;
    private String selectedCompetition;
    private String selectedTeam;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        //inicializamos la visualización de la tableView, la listView y los label del filtrado.
        teamTableView.setVisible(true);
        squadTableView.setVisible(true);
        filterCompetition.setVisible(true);
        filterTeam.setVisible(true);

        //inicializamos lista de la plantilla
        this.squads = FXCollections.observableArrayList();

        //añadimos la lista al tableView
        squadTableView.setItems(squads);

        //añadir valores a las columnas del tableView
        nameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
        positionColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPosition()));
        nationalityColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNationality()));
        birthColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDateOfBirth()));

        // inicializamos la lista de equipos en la competición
        this.teams = FXCollections.observableArrayList();

        //añadimos la lista al tableView
        teamTableView.setItems(teams);

        //añadir valores a las columnas del tableView
        teamColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
        tlaColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTla()));
        //añadimos la imagen a la columna del blasón
        crestColumn.setCellValueFactory(param -> {
            ImageView imageView = new ImageView();
            imageView.setFitHeight(50); // Establecer el alto de la imagen
            imageView.setFitWidth(50); // Establecer el ancho de la imagen
            Team team = param.getValue();
            Image image = new Image(team.getCrest(), true);
            imageView.setImage(image);
            return new javafx.beans.property.SimpleObjectProperty<>(imageView);
        });


        // Configurar un listener para el campo de texto
        filterTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            // Obtener el valor del campo de texto y aplicar el filtro
            String filterText = newValue.trim();
            if (filterText.isEmpty()) {
                if (selectedCompetition != null) {
                    // Cuando hay una competición seleccionada, muestra los nombres de los equipos
                    teamTableView.setItems(teams);
                } else if (selectedTeam != null) {
                    // Cuando hay un equipo seleccionado, muestra los jugadores
                    squadTableView.setItems(squads);
                }
            } else {
                // Si se ha introducido un filtro, aplicarlo a la lista correspondiente
                if (selectedCompetition != null) {
                    // Cuando hay una competición seleccionada, filtrar los nombres de los equipos
                    filterTeamList(filterText);
                } else if (selectedTeam != null) {
                    // Cuando hay un equipo seleccionado, filtrar los jugadores
                    filterPlayerList(filterText);
                }
            }
        });
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

        //Ocultamos el tableView de lso jugadores y el label del buscador de competiciones
        squadTableView.setVisible(false);
        filterTeam.setVisible(false);


        // Verificar si competitionCode no es nulo antes de imprimirlo
        if (selectedCompetition != null) {
            // creamos la instancia de FootballTask con el código de competición, la lista de nombres y la barra de progreso
            footballCompetitionTask = new FootballCompetitionTask(selectedCompetition, teams, progressIndicator);

            // Iniciar la tarea en un hilo separado
            new Thread(footballCompetitionTask).start();
        } else {
            // Manejar el caso en que competitionCode sea nulo
            ShowAlert.showErrorAlert("Error", "error al crear: ", "No se puede crear la tarea FootballTask: competitionCode es nulo");
        }
    }

    //crea la tarea para recibir los jugadores de un equipo
    private void createFootballTeamTask() {

        //Ocultamos el listView de los equipos y el label del buscador de equipos
        teamTableView.setVisible(false);
        filterCompetition.setVisible(false);

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

    // Método para filtrar la lista de equipos
    private void filterTeamList(String filterText) {
        // Crear una lista observable para almacenar los equipos filtrados
        ObservableList<Team> filteredTeams = FXCollections.observableArrayList();
        // Iterar sobre la lista de nombres de equipos para verificarlos (ignorar mayúsculas y minúsculas)
        for (Team team : teams) {
            if (team.getName().toLowerCase().contains(filterText.toLowerCase())) {
                // Si el equipo coincide con el filtro, añadirlo a la lista de equipos filtrados
                filteredTeams.add(team);
            }
        }
        // Establecer la lista de equipos filtrados en el ListView
        teamTableView.setItems(filteredTeams);
    }

    // Método para filtrar la lista de jugadores
    private void filterPlayerList(String filterText) {
        // Crear una lista observable para almacenar los jugadores filtrados
        ObservableList<Squad> filteredPlayers = FXCollections.observableArrayList();
        // Iterar sobre la lista de nombres de jugadores para verificarlos (ignorar mayúsculas y minúsculas)
        for (Squad player : squads) {
            if (player.getName().toLowerCase().contains(filterText.toLowerCase())) {
                // Si el jugador coincide con el filtro, añadirlo a la lista de jugadores filtrados
                filteredPlayers.add(player);
            }
        }
        // Establecer la lista de jugadores filtrados en el TableView
        squadTableView.setItems(filteredPlayers);
    }
}
