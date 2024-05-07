package com.svalero.appFootballData.service;

import com.svalero.appFootballData.model.AllFootballDataCompetitions;
import com.svalero.appFootballData.model.AllFootballDataTeam;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface FootballDataAPI {

    @GET("v4/competitions/{competition}/standings")
    Observable<AllFootballDataCompetitions> getFootballInformationCompetition(@Path("competition") String competition);

    @GET("v4/teams/{teamId}")
    Observable<AllFootballDataTeam> getFootballInformationTeam(@Path("teamId") String teamId);
}
