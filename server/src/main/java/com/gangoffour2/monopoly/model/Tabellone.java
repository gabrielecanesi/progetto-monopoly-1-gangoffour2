package com.gangoffour2.monopoly.model;

import java.util.ArrayList;

import com.gangoffour2.monopoly.model.casella.Casella;
import lombok.Builder;
import lombok.AllArgsConstructor;

@Builder
@AllArgsConstructor
public class Tabellone{
    private ArrayList<Casella> caselle;

}
