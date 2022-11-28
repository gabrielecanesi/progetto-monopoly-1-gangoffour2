package com.gangoffour2.monopoly.actions;

import com.gangoffour2.monopoly.Player;
import com.gangoffour2.monopoly.squares.land.Land;
import lombok.Data;

@Data
public class SellHouse extends UserAction {
    private Land land;

    public SellHouse(Player player, Land land) {
        super(player);
        this.land = land;
    }
}
