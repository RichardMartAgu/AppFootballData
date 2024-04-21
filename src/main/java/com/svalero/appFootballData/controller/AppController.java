package com.svalero.appFootballData.controller;

import com.svalero.appFootballData.utils.ShowAlert;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class AppController implements Initializable {

    @FXML
    private ComboBox<String> competitionComboBox;
    @FXML
    private ComboBox<String> teamComboBox;
    @FXML
    private Button searchCompetitionButton;

    @FXML
    private Button searchTeamButton;
    @FXML
    private TabPane tabFootball;
    @FXML
    private ChoiceBox<Integer> maxTabsChoiceBox;

    private Map<String, String> competitions = new HashMap<>();

    private Map<String, String> teams = new HashMap<>();
    private String selectedCompetitionCode;
    private String selectedTeamId;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        searchCompetitionButton.getStyleClass().add("btn");
        searchCompetitionButton.getStyleClass().add("btn-primary");


        // inicializamos el maxTabsChoiceBox para elegir un máximo de tabs
        ObservableList<Integer> choices = FXCollections.observableArrayList(1, 2, 3, 4, 5);
        maxTabsChoiceBox.setItems(choices);
        maxTabsChoiceBox.setValue(5);

        // creamos un mapa de competiciones con clave (código) y valor (nombre)
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

        // agregar las competiciones al ComboBox competitionComboBox
        competitionComboBox.setItems(FXCollections.observableArrayList(competitions.values()));

        //seleccionar una competición para mostrar
        competitionComboBox.getSelectionModel().selectFirst();

        teams.put("77", "Athletic Club");
        teams.put("79", "CA Osasuna");
        teams.put("81", "FC Barcelona");
        teams.put("82", "Getafe CF");
        teams.put("83", "Granada CF");
        teams.put("86", "Real Madrid CF");
        teams.put("87", "Rayo Vallecano de Madrid");
        teams.put("89", "RCD Mallorca");
        teams.put("90", "Real Betis Balompié");
        teams.put("92", "Real Sociedad de Fútbol");
        teams.put("94", "Villarreal CF");
        teams.put("95", "Valencia CF");
        teams.put("263", "Deportivo Alavés");
        teams.put("264", "Cádiz CF");
        teams.put("275", "UD Las Palmas");
        teams.put("298", "Girona FC");
        teams.put("558", "RC Celta de Vigo");
        teams.put("559", "Sevilla FC");

        // agregar las competiciones al ComboBox competitionComboBox
        teamComboBox.setItems(FXCollections.observableArrayList(teams.values()));

        //seleccionar una competición para mostrar
        teamComboBox.getSelectionModel().selectFirst();
    }

    // generamos un nuevo hilo
    @FXML
    public void findCompetitionTeams(ActionEvent event) throws IOException {

        // recogemos el valor del máximo de tabs
        int maxTabs = maxTabsChoiceBox.getValue();

        //miramos si es posible crear una tab más o excede el máximo
        if (tabFootball.getTabs().size() < maxTabs) {

            //creamos la instancia del nuevo fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com.svalero.appFootballData/football_pane.fxml"));

            //cogemos la competición seleccionada
            String selectedCompetitionName = competitionComboBox.getValue();

            //creamos la nueva pestaña
            AnchorPane anchorPane = loader.load(); //cuidado
            tabFootball.getTabs().add(new Tab(selectedCompetitionName, anchorPane));

            //añadimos el string asociado a la selección de competición
            for (Map.Entry<String, String> entry : competitions.entrySet()) {
                if (entry.getValue().equalsIgnoreCase(selectedCompetitionName)) {
                    selectedCompetitionCode = entry.getKey();
                    break;
                }
            }
            // Obtenemos el controlador del FXML cargado
            FootballTaskController footballTaskController = loader.getController();

            System.out.println("AppController competition : " + selectedCompetitionCode);

            // Enviamos el código de la competición al controlador
            footballTaskController.createCompetitionTask(selectedCompetitionCode);

        } else {
            ShowAlert.showInformationAlert("Information", "DEMASIADAS PESTAÑAS", "máximo de pestañas alcanzado");
        }
    }

    @FXML
    public void findTeam(ActionEvent event) throws IOException {

        // recogemos el valor del máximo de tabs
        int maxTabs = maxTabsChoiceBox.getValue();

        //miramos si es posible crear una tab más o excede el máximo
        if (tabFootball.getTabs().size() < maxTabs) {

            //creamos la instancia del nuevo fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com.svalero.appFootballData/football_pane.fxml"));

            //cogemos el team seleccionado
            String selectedTeamName = teamComboBox.getValue();

            //creamos la nueva pestaña
            AnchorPane anchorPane = loader.load(); //cuidado
            tabFootball.getTabs().add(new Tab(selectedTeamName, anchorPane));

            //añadimos Id asociado al equipo
            for (Map.Entry<String, String> entry : teams.entrySet()) {
                if (entry.getValue().equalsIgnoreCase(selectedTeamName)) {
                    selectedTeamId = entry.getKey();
                    break;
                }
            }
            // Obtenemos el controlador del FXML cargado
            FootballTaskController footballTaskController = loader.getController();

            System.out.println("AppController teamId : " + selectedTeamId);

            // Enviamos el código del equipo al controlador
            footballTaskController.createFootballTeamTask(selectedTeamId);

        } else {
            ShowAlert.showInformationAlert("Information", "Máximo de pestañas alcanzado", "por favor cerrar alguna pestaña antes de continuar");
        }
    }
}