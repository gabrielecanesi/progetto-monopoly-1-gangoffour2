package com.gangoffour2.monopoly.actions;

import com.gangoffour2.monopoly.Player;
import com.gangoffour2.monopoly.squares.Property;

public class Mortgage extends UserAction {
    private Property property;

    public Mortgage(Player player, Property property){
        super(player);
        this.property = property;
    }
}
