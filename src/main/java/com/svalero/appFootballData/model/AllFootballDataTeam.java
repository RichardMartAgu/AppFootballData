package com.svalero.appFootballData.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class AllFootballDataTeam {

    private Area area;
    private int id;
    private String name;
    private String shortName;
    private String tla;
    private String crest;
    private String address;
    private String website;
    private int founded;
    private String clubColors;
    private String venue;
    private List<RunningCompetitions> runningCompetitions;
    private Coach coach;
    private String marketValue;
    private List<Squad> squad;
    private List<Staff> staff;
    private String lastUpdated;
}
