package com.gangoffour2.monopoly.model.giocatore;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PlayerPosition {
    private Giocatore player;
    private Integer position;
}
