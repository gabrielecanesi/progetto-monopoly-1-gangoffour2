package com.gangoffour2.monopoly.commands;

import com.gangoffour2.monopoly.Player;
import com.gangoffour2.monopoly.actions.BuyProperty;
import com.gangoffour2.monopoly.squares.Property;
import com.gangoffour2.monopoly.util.State;


public class PropertyBuyRequestCommand extends GameCommand {
    private State state = State.WAITING;
    private final Player player;
    private final Property property;

    public PropertyBuyRequestCommand(Player player, Property property) {
        super(property.getGame());
        this.player = player;
        this.property = property;
    }

    @Override
    public synchronized void execute() throws InterruptedException {
        state = State.WAITING;
        System.out.println("Waiting for player");
        wait(15000);
        if (state == State.STOPPED){
            System.out.println("Stopped and bought");
            System.out.println("Amount: " + player.getAmount());
        }
        else {
            System.out.println("Ripped");
        }

        game.getCommands().push(new Auction(game, property));
    }

    @Override
    public synchronized void onUserAction(BuyProperty userAction) {
        if (state == State.TIMED_OUT || state == State.STOPPED){
            return;
        }
        state = State.STOPPED;
        player.setAmount(player.getAmount() - property.getBaseCost());
        System.out.println("Bought");
        notifyAll();
    }
}
