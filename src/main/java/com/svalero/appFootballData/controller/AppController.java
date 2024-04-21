package com.svalero.appFootballData.controller;

import com.svalero.appFootballData.task.FootballTask;
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
    private Button searchCompetitionButton;
    @FXML
    private TabPane tabFootball;
    @FXML
    private ChoiceBox<Integer> maxTabsChoiceBox;

    private Map<String, String> competitions = new HashMap<>();
    private String selectedCompetitionCode;




    @Override
    public void initialize(URL location, ResourceBundle resources) {

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

    }

    // generamos un nuevo hilo
    @FXML
    public void findTeams(ActionEvent event) throws IOException {

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
            footballTaskController.setCompetitionCode(selectedCompetitionCode);

        } else {
            ShowAlert.showErrorAlert("Information", "DEMASIADAS PESTAÑAS", "máximo de pestañas alcanzado");
        }
    }
}