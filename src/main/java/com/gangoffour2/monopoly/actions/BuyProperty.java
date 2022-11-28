package com.gangoffour2.monopoly.actions;


import com.gangoffour2.monopoly.Player;
import com.gangoffour2.monopoly.squares.Property;
import lombok.Data;

@Data
public class BuyProperty extends UserAction {
    private Property property;
    public BuyProperty(Player player, Property property){
        super(player);
        this.property = property;
    }
}
