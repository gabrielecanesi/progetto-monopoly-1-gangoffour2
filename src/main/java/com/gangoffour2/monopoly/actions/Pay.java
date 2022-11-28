package com.gangoffour2.monopoly.actions;

import com.gangoffour2.monopoly.Player;
import lombok.Data;

@Data
public class Pay extends UserAction {
    private Integer amount;

    public Pay(Player player, Integer amount){
        super(player);
        this.amount = amount;
    }
}
