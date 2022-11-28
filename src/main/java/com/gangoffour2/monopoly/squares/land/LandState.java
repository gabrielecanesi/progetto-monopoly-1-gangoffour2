package com.gangoffour2.monopoly.squares.land;

import com.gangoffour2.monopoly.Player;
import com.gangoffour2.monopoly.commands.GameCommand;
import com.gangoffour2.monopoly.commands.PropertyBuyRequestCommand;

public abstract class LandState {


    protected Land property;
    public LandState(Land property) {
        this.property = property;
    }
    public GameCommand landTo(Player player){
        return new PropertyBuyRequestCommand(player, property);
    }
}
