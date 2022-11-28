package com.gangoffour2.monopoly.squares.station;

import com.gangoffour2.monopoly.Player;
import com.gangoffour2.monopoly.commands.GameCommand;
import lombok.Getter;

@Getter
public abstract class StationState {
    private final Station station;

    public StationState(Station station){
        this.station = station;
    }

    public abstract GameCommand landTo(Player player);
}
