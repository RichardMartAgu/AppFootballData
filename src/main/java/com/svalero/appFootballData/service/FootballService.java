package com.svalero.appFootballData.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.svalero.appFootballData.model.AllFootballDataCompetitions;
import com.svalero.appFootballData.model.AllFootballDataTeam;
import com.svalero.appFootballData.model.Squad;
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

        // Configuración del interceptor para el registro de solicitudes HTTP
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder client = new OkHttpClient.Builder();

        // Interceptor para agregar el token de autenticación a todas las solicitudes
        client.addInterceptor(chain -> {
            Request original = chain.request();
            String url = original.url().toString();

            // Configuración del encabezado con el token de autenticación
            Request.Builder requestBuilder = original.newBuilder()
                    .header("X-Auth-Token", "99221d2217e7415788792e6d215281ab")
                    .method(original.method(), original.body());
            Request request = requestBuilder.build();
            Response response = chain.proceed(request);
            System.out.println("URL de la solicitud: " + url);

            if (!response.isSuccessful()) {

                // Manejo de errores en caso de que la respuesta no sea exitosa
                throw new IOException("Error al realizar la solicitud: " + response.code());
            } else System.out.println("Petition correcta");

            return response;
        });
        client.addInterceptor(interceptor);

        // Configuración de Gson para el análisis de JSON
        Gson gsonParser = new GsonBuilder()
                .setLenient()
                .create();

        // Configuración de Retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client.build())
                .addConverterFactory(GsonConverterFactory.create(gsonParser))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        // Creación de una instancia de la interfaz FootballDataAPI
        this.footballDataAPI = retrofit.create(FootballDataAPI.class);
    }

    // Método para obtener los equipos de una competición específica
    public Observable<Team> getTeams(String competition) {
        // Realizar la solicitud a la API y mapear la respuesta
        return this.footballDataAPI.getFootballInformationCompetition(competition)
                .map(AllFootballDataCompetitions::getStandings)
                .flatMapIterable(standings -> standings)
                .map(Standings -> Standings.getTable())
                .flatMapIterable(table -> table)
                .map(Table -> Table.getTeam());
    }

    // Método para obtener datos de  un equipo
    public Observable<Squad> getOneTeam(String teamId) {
        // Realizar la solicitud a la API y mapear la respuesta
        return this.footballDataAPI.getFootballInformationTeam(teamId)
                .map(AllFootballDataTeam::getSquad)
                .flatMapIterable(squad -> squad);
    }
}
