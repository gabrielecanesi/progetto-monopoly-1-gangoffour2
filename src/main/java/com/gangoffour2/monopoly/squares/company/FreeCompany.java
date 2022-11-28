package com.gangoffour2.monopoly.squares.company;

import com.gangoffour2.monopoly.Player;
import com.gangoffour2.monopoly.commands.GameCommand;
import com.gangoffour2.monopoly.commands.PropertyBuyRequestCommand;

public class FreeCompany extends CompanyState {
    public FreeCompany(Company company) {
        super(company);
    }

    @Override
    public GameCommand landTo(Player player) {
        return new PropertyBuyRequestCommand(player, getCompany());
    }

}
