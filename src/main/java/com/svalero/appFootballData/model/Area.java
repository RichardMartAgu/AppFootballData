package com.svalero.appFootballData.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Area {
    private int id;
    private String name;
    private String code;
    private String flag;
}
