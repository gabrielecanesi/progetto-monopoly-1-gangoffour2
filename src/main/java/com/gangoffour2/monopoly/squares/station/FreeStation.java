package com.gangoffour2.monopoly.squares.station;

import com.gangoffour2.monopoly.Player;
import com.gangoffour2.monopoly.commands.EmptyCommand;
import com.gangoffour2.monopoly.commands.GameCommand;

public class FreeStation extends StationState {
    public FreeStation(Station station) {
        super(station);
    }

    @Override
    public GameCommand landTo(Player player) {
        return new EmptyCommand(getStation().getGame());
    }
}
