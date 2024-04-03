package com.svalero.appFootballData.task;

import com.svalero.appFootballData.model.Team;
import com.svalero.appFootballData.service.FootballService;
import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;

public class FootballTask extends Task<Void> {

    private final String selectedCompetition;
    private final ObservableList<Team> team;
    private Disposable disposable;

    public FootballTask(String selectedCompetition, ObservableList<Team> teamList) {
        this.selectedCompetition = selectedCompetition;
        this.team = teamList;
    }

    @Override
    protected Void call()  {
        System.out.println("FootballTask.call() comenzó");

        FootballService footballService = new FootballService();
        System.out.println("FootballTask.call() sigue");
        Consumer<Team> user = (team) ->{
            Thread.sleep(250);
            Platform.runLater(()-> this.team.add(team));
        };
        System.out.println("FootballTask.call() sigue 2");

            System.out.println("FootballTask.call() sigue 3");

        System.out.println("FootballTask.call() terminó");
        footballService.getStandings(selectedCompetition).subscribe(user);
        return null;
    }

}
