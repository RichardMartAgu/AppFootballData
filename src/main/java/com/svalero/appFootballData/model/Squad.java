package com.svalero.appFootballData.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Squad {
    private int id;
    private String firstName;
    private String lastName;
    private String name;
    private String position;
    private String dateOfBirth;
    private String nationality;
    private int shirtNumber;
    private int marketValue;
    private Contract contract;
}
