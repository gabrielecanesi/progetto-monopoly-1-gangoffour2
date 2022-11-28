package com.gangoffour2.monopoly.actions;

import com.gangoffour2.monopoly.Player;
import com.gangoffour2.monopoly.squares.land.Land;
import lombok.Data;

@Data
public class BuildHouse extends UserAction {
    private Land land;

    public BuildHouse(Player player, Land land){
        super(player);
        this.land = land;
    }
}
