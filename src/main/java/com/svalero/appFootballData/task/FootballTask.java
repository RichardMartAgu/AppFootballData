package com.svalero.appFootballData.task;

import com.svalero.appFootballData.model.Team;
import com.svalero.appFootballData.service.FootballService;
import io.reactivex.functions.Consumer;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;

public class FootballTask extends Task<Integer> {

    private final String selectedCompetition;
    private final ObservableList<String> names;

    public FootballTask(String selectedCompetition, ObservableList<String> names) {
        this.selectedCompetition = selectedCompetition;
        this.names = names;
    }

    @Override
    protected Integer call() {
        System.out.println("FootballTask.call() comenzó");

        FootballService footballService = new FootballService();
        Consumer<Team> user = (team) -> {
            Thread.sleep(250);
            Platform.runLater(() -> this.names.add(team.getName()));
        };

        System.out.println("FootballTask.call() terminó");

        footballService.getTeams(selectedCompetition)
                .subscribe(user);

        return null;
    }

}
