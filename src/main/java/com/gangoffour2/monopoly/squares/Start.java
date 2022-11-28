package com.gangoffour2.monopoly.squares;

import com.gangoffour2.monopoly.Player;
import com.gangoffour2.monopoly.commands.EmptyCommand;
import com.gangoffour2.monopoly.commands.GameCommand;

public class Start extends Square {
    @Override
    public GameCommand landTo(Player p) {
        return new EmptyCommand(getGame());
    }

    @Override
    public GameCommand intermediateStep(Player p){
        System.out.println("Pass through start");
        return new EmptyCommand(getGame());
    }
}
