package com.svalero.appFootballData.service;



import com.svalero.appFootballData.model.Standings;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

import java.util.List;

public interface FootballDataAPI {

    @GET("api.football-data.org/v4/{competition}/standings")
    Observable<List<Standings>> getStandings(@Path("competition") String competition);


}
