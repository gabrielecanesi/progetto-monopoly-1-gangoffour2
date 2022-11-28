package com.gangoffour2.monopoly.actions;

import com.gangoffour2.monopoly.Player;
import lombok.Data;

@Data
public class Offer extends UserAction {
    private Integer amount;

    public Offer(Player p, Integer amount){
        super(p);
        this.amount = amount;
    }
}
