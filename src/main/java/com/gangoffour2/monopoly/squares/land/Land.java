package com.gangoffour2.monopoly.squares.land;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.gangoffour2.monopoly.Player;
import com.gangoffour2.monopoly.commands.GameCommand;
import com.gangoffour2.monopoly.squares.Property;
import lombok.Getter;

import java.util.ArrayList;

enum Color{
    RED,
    BLUE,
    GREEN,
    YELLOW,
    ORANGE,
    PURPLE,
    CYAN,
    BROWN
}

@Getter
public class Land extends Property {

    private Integer houseCost;
    private Integer hotelCost;
    private Color color;
    private ArrayList<Integer> rent;
    private Integer income;

    @JsonIgnore
    private final LandState state = new FreeLandState(this);

    @JsonProperty("state")
    public String getState(){
        return state.getClass().getSimpleName();
    }

    @Override
    public GameCommand landTo(Player player) {
        return state.landTo(player);
    }

    @Override
    public Integer computeIncome() {
        return income;
    }

    @Override
    public void setBoughtBy(Player player, Integer cost) {

    }
}
