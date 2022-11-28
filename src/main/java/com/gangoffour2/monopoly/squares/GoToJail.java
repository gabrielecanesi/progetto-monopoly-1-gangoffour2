package com.gangoffour2.monopoly.squares;

import com.gangoffour2.monopoly.Player;
import com.gangoffour2.monopoly.commands.EmptyCommand;
import com.gangoffour2.monopoly.commands.GameCommand;

public class GoToJail extends Square {
    @Override
    public GameCommand landTo(Player p) {
        return new EmptyCommand(getGame());
    }
}
