package com.gangoffour2.monopoly.squares.company;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.gangoffour2.monopoly.Player;
import com.gangoffour2.monopoly.commands.GameCommand;
import com.gangoffour2.monopoly.squares.Property;
import lombok.Data;

@Data
public class Company extends Property {

    private Integer income;
    @JsonIgnore
    private CompanyState state = new FreeCompany(this);


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
        player.setAmount(player.getAmount() - cost);
        setState(new BoughtCompany(this, player));
    }
}
