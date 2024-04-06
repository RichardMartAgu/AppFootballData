package com.svalero.appFootballData.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AllFootballData {
    private Filters filters;
    private Area area;
    private Competition competition;
    private Season season;
    private List<Standings> standings;
}
