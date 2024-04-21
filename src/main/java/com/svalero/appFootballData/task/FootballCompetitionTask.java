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

    @Override
    protected Integer call() {

        Platform.runLater(() -> progressIndicator.setVisible(true));
        Platform.runLater(() -> progressIndicator.setProgress(-1));

        FootballService footballService = new FootballService();
        Consumer<Team> user = (team) -> {
            Thread.sleep(250);
            Platform.runLater(() -> this.names.add(team.getName()));
        };

        footballService.getTeams(selectedCompetition)
                .subscribe(user);

        Platform.runLater(() -> progressIndicator.setVisible(false));
        Platform.runLater(() -> progressIndicator.setProgress(0));

        return null;
    }

}
