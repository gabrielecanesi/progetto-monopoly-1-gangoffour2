package com.gangoffour2.monopoly.squares;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.gangoffour2.monopoly.Player;
import lombok.Data;

@Data
public abstract class Property  extends Square {

    @JsonIgnore
    private Player owner;
    private Integer baseCost;
    private Integer mortgage;

    public abstract Integer computeIncome();

    public abstract void setBoughtBy(Player player, Integer cost);
}
