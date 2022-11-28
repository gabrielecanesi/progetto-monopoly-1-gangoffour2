package com.gangoffour2.monopoly.commands;

import com.gangoffour2.monopoly.Game;

public class EmptyCommand extends GameCommand {
    public EmptyCommand(Game game) {
        super(game);
    }

    @Override
    public void execute() throws InterruptedException {

    }
}
