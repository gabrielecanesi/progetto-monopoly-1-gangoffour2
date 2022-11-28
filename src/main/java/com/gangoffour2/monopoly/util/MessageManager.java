package com.gangoffour2.monopoly.util;

import com.gangoffour2.monopoly.Game;
import com.gangoffour2.monopoly.Player;
import com.gangoffour2.monopoly.messaging.MessageBroker;
import com.gangoffour2.monopoly.messaging.NewSquareMessage;
import com.gangoffour2.monopoly.squares.Square;
import lombok.Data;

@Data
public class MessageManager {

    private Game game;

    public MessageManager(Game game) {
        this.game = game;
    }

    public void broadcastNewSquare(Player player, Square square) {
        MessageBroker.getInstance()
                .broadcastGameMessage(new NewSquareMessage(player.getUsername(), square.getId()),
                        "/topic/games/" + game.getId() + "/newSquare");
    }


    public void sendPrivate(Player player, String message){
        MessageBroker.getInstance().sendMessageToSession(player.getId(), message, "/topic/test");
    }

    public void broadcastGame() {
        MessageBroker.getInstance().broadcastGameMessage(game, "/topic/games/" + game.getId() + "/started");
    }
}