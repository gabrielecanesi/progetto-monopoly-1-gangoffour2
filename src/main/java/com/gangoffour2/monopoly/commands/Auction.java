package com.gangoffour2.monopoly.commands;

import com.gangoffour2.monopoly.Game;
import com.gangoffour2.monopoly.Player;
import com.gangoffour2.monopoly.actions.Offer;
import com.gangoffour2.monopoly.squares.Property;
import com.gangoffour2.monopoly.util.State;

public class Auction extends GameCommand {

    private State state = State.WAITING;
    private Integer bestOffer = 0;
    private Player bestPlayerOffer;
    private final Property property;

    public Auction(Game game, Property property) {
        super(game);
        this.property = property;
    }

    @Override
    public synchronized void execute() throws InterruptedException {
        System.out.println("Started auction");
        state = State.WAITING;
        while (state == State.WAITING){
            wait(15000);
            if (state == State.STOPPED){
                System.out.println("New offer " + bestOffer);
                state = State.WAITING;
            }
            else {
                state = State.TIMED_OUT;
            }
        }

        System.out.println("Auction finished " + bestOffer);
        if (bestPlayerOffer != null){
            bestPlayerOffer.setAmount(bestPlayerOffer.getAmount() - bestOffer);
            property.setBoughtBy(bestPlayerOffer, bestOffer);
        }
        state = State.STOPPED;
    }

    @Override
    public synchronized void onUserAction(Offer userAction) {
        if (state == State.WAITING) {
            if (userAction.getAmount() > bestOffer) {
                bestOffer = userAction.getAmount();
                bestPlayerOffer = userAction.getPlayer();
                state = State.STOPPED;
                notifyAll();
            }
        }
    }
}