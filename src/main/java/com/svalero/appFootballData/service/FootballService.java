package com.svalero.appFootballData.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.svalero.appFootballData.model.AllFootballData;
import com.svalero.appFootballData.model.Team;
import io.reactivex.Observable;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;

public class FootballService {
    private String BASE_URL = "https://api.football-data.org";
    private FootballDataAPI footballDataAPI;

    public FootballService() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder client = new OkHttpClient.Builder();

        client.addInterceptor(chain -> {
            Request original = chain.request();
            String url = original.url().toString();

            // Resto del código del interceptor
            Request.Builder requestBuilder = original.newBuilder()
                    .header("X-Auth-Token", "99221d2217e7415788792e6d215281ab")
                    .method(original.method(), original.body());
            Request request = requestBuilder.build();
            Response response = chain.proceed(request);
            System.out.println("URL de la solicitud: " + url);

            if (!response.isSuccessful()) {

                // Si la respuesta no es exitosa, lanza una excepción o maneja el error de acuerdo a tus necesidades.
                throw new IOException("Error al realizar la solicitud: " + response.code());
            } else System.out.println("Petition correcta");

            return response;
        });
        client.addInterceptor(interceptor);

        Gson gsonParser = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client.build())
                .addConverterFactory(GsonConverterFactory.create(gsonParser))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        this.footballDataAPI = retrofit.create(FootballDataAPI.class);
    }

    public Observable<Team> getTeams(String competition) {
        return this.footballDataAPI.getFootballInformation(competition)
                .map(AllFootballData::getStandings)
                .flatMapIterable(standings -> standings)
                .map(Standings -> Standings.getTable())
                .flatMapIterable(table -> table)
                .map(Table -> Table.getTeam());


    }
}
