package com.gangoffour2.monopoly.commands;

import com.gangoffour2.monopoly.Game;
import com.gangoffour2.monopoly.Player;
import com.gangoffour2.monopoly.actions.*;
import com.gangoffour2.monopoly.squares.Square;

public abstract class GameCommand {

    protected Game game;
    protected Square square;

    public GameCommand(Game game) {
        this.game = game;
    }
    public abstract void execute() throws InterruptedException;

    public void onUserAction(Offer offer){

    }

    public void onUserAction(Mortgage mortgage){

    }

    public void onUserAction(BuyProperty property){

    }

    public void onUserQuit(Player player){

    }

    public void rollDice(Player player) {

    }

    public void onUserAction(BuildHouse action) {

    }

    public void onUserAction(SellHouse action) {

    }

    public void onUserAction(Pay pay){

    }
}
