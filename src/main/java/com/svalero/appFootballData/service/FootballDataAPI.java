package com.svalero.appFootballData.service;

import com.svalero.appFootballData.model.AllFootballData;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface FootballDataAPI {

    @GET("v4/competitions/{competition}/standings")
    Observable<AllFootballData> getFootballInformation(@Path("competition") String competition);

}
