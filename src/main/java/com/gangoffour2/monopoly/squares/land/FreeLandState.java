package com.gangoffour2.monopoly.squares.land;

import com.gangoffour2.monopoly.Player;
import com.gangoffour2.monopoly.commands.GameCommand;
import com.gangoffour2.monopoly.commands.PropertyBuyRequestCommand;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FreeLandState extends LandState {
    public FreeLandState(Land land) {
        super(land);
    }


    @Override
    public GameCommand landTo(Player player) {
        return new PropertyBuyRequestCommand(player, property);
    }
}
