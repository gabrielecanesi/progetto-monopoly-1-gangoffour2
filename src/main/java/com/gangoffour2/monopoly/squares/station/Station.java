package com.gangoffour2.monopoly.squares.station;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.gangoffour2.monopoly.Player;
import com.gangoffour2.monopoly.commands.EmptyCommand;
import com.gangoffour2.monopoly.commands.GameCommand;
import com.gangoffour2.monopoly.squares.Property;
import lombok.Data;

@Data
public class Station extends Property {

    private Integer income;
    @JsonIgnore
    private StationState state = new FreeStation(this);


    @JsonProperty("state")
    public String getState(){
        return state.getClass().getSimpleName();
    }

    @Override
    public GameCommand landTo(Player p) {
        return new EmptyCommand(getGame());
    }

    @Override
    public Integer computeIncome() {
        return income;
    }

    @Override
    public void setBoughtBy(Player player, Integer cost) {

    }
}
