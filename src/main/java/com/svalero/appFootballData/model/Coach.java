package com.svalero.appFootballData.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Coach {
    private Integer id;
    private String firstName;
    private String lastName;
    private String name;
    private String dateOfBirth;
    private String nationality;
    private Contract contract;
}
