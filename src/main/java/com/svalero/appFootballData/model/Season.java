package com.svalero.appFootballData.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Season {
    private int id;
    private String startDate;
    private String endDate;
    private int currentMatchday;
    private String winner;
    private List<String> stages;
}
