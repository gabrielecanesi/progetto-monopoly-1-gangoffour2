package com.gangoffour2.monopoly.squares.company;

import com.gangoffour2.monopoly.Player;
import com.gangoffour2.monopoly.commands.GameCommand;
import com.gangoffour2.monopoly.commands.PayRent;
import lombok.Getter;

@Getter
public class BoughtCompany extends CompanyState {
    private final Player owner;
    public BoughtCompany(Company company, Player owner) {
        super(company);
        this.owner = owner;
    }

    @Override
    public GameCommand landTo(Player player) {
        return new PayRent(getCompany().getGame(), player, owner);
    }
}
