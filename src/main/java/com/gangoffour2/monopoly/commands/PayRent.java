package com.gangoffour2.monopoly.commands;

import com.gangoffour2.monopoly.Game;
import com.gangoffour2.monopoly.Player;
import com.gangoffour2.monopoly.actions.Pay;
import lombok.Getter;

@Getter
public class PayRent extends GameCommand {
    private final Player debtor;
    private final Player creditor;

    public PayRent(Game game, Player debtor, Player creditor) {
        super(game);
        this.debtor = debtor;
        this.creditor = creditor;
    }

    @Override
    public void execute() throws InterruptedException {

    }


    @Override
    public void onUserAction(Pay pay) {

    }
}
