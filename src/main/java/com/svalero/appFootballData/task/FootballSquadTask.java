package com.svalero.appFootballData.task;

import com.svalero.appFootballData.model.Squad;
import com.svalero.appFootballData.service.FootballService;
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

    @Override
    protected Integer call() {

        Platform.runLater(() -> progressIndicator.setVisible(true));
        Platform.runLater(() -> progressIndicator.setProgress(-1));

        FootballService footballService = new FootballService();

        Consumer<Squad> user = (squad) -> {
            Thread.sleep(250);
            synchronized (squads) {
                Platform.runLater(() -> this.squads.add(squad));
            }
        };

        footballService.getOneTeam(selectedTeam)
                .subscribe(user);

        Platform.runLater(() -> progressIndicator.setVisible(false));
        Platform.runLater(() -> progressIndicator.setProgress(0));

        return null;
    }
}
