package com.gangoffour2.monopoly.squares.company;

import com.gangoffour2.monopoly.Player;
import com.gangoffour2.monopoly.commands.GameCommand;
import lombok.Data;

@Data
public abstract class CompanyState {
    private final Company company;
    public CompanyState(Company company) {
        this.company = company;
    }

    public abstract GameCommand landTo(Player player);
}
