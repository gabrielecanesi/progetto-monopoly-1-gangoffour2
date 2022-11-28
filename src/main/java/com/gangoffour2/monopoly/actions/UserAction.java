package com.gangoffour2.monopoly.actions;

import com.gangoffour2.monopoly.Player;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public abstract class UserAction {

    private Player player;
    public UserAction(Player player) {
        this.player = player;
    }
}
