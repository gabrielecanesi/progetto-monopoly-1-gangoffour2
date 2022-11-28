package com.gangoffour2.monopoly.service;

import com.gangoffour2.monopoly.Game;
import com.gangoffour2.monopoly.Player;
import com.gangoffour2.monopoly.actions.*;
import com.gangoffour2.monopoly.controller.PlayerData;
import com.gangoffour2.monopoly.dto.*;
import com.gangoffour2.monopoly.service.exception.MonopolyException;
import com.gangoffour2.monopoly.service.exception.PlayerNotFoundException;
import com.gangoffour2.monopoly.service.exception.SquareNotFoundException;
import com.gangoffour2.monopoly.service.factory.PlayerActionFactory;
import com.gangoffour2.monopoly.service.factory.PlayerFactory;
import org.springframework.stereotype.Service;

@Service
public class PlayerActionsService {

    public void onPlayerDisconnect(String sessionId) throws PlayerNotFoundException {
        Player player = GameManager.getInstance().getPlayerBySessionId(sessionId);
        Game game = player.getGame();
        game.removePlayer(player);
    }

    public void joinGame(PlayerData data, String id, String sessionId) throws MonopolyException, InterruptedException {
        Game game = GameManager.getInstance().getGameById(id);
        Player player = PlayerFactory.getInstance().build(data, sessionId);
        game.addPlayer(player);
    }

    public void quitGame(String sessionId) throws PlayerNotFoundException {
        Player player = GameManager.getInstance().getPlayerBySessionId(sessionId);
        GameManager.getInstance().removePlayer(player);
        player.getGame().removePlayer(player);
    }

    public void rollDice(String sessionId) throws PlayerNotFoundException {
        Player player = GameManager.getInstance().getPlayerBySessionId(sessionId);
        player.getGame().getCurrentState().rollDice(player);
    }

    public void buyProperty(String sessionId, BuyPropertyDto dto) throws PlayerNotFoundException, SquareNotFoundException {
        Player player = GameManager.getInstance().getPlayerBySessionId(sessionId);
        BuyProperty action = PlayerActionFactory.getInstance().build(dto, player);
        player.getGame().getCurrentState().onUserAction(action);
    }

    public void offer(String sessionId, OfferDto data) throws PlayerNotFoundException {
        Player player = GameManager.getInstance().getPlayerBySessionId(sessionId);
        Offer action = PlayerActionFactory.getInstance().build(data, player);
        player.getGame().getCurrentState().onUserAction(action);
    }

    public void mortgage(String sessionId, MortgageDto data) throws PlayerNotFoundException, SquareNotFoundException {
        Player player = GameManager.getInstance().getPlayerBySessionId(sessionId);
        Mortgage action = PlayerActionFactory.getInstance().build(data, player);
        player.getGame().getCurrentState().onUserAction(action);
    }

    public void buildHouse(String sessionId, BuildHouseDto data) throws PlayerNotFoundException, SquareNotFoundException {
        Player player = GameManager.getInstance().getPlayerBySessionId(sessionId);
        BuildHouse action = PlayerActionFactory.getInstance().build(data, player);
        player.getGame().getCurrentState().onUserAction(action);
    }

    public void sellHouse(String sessionId, SellHouseDto data) throws PlayerNotFoundException, SquareNotFoundException {
        Player player = GameManager.getInstance().getPlayerBySessionId(sessionId);
        SellHouse action = PlayerActionFactory.getInstance().build(player, data);
        player.getGame().getCurrentState().onUserAction(action);
    }

    public void pay(String sessionId, PayDto data) throws PlayerNotFoundException {
        Player player = GameManager.getInstance().getPlayerBySessionId(sessionId);
        Pay action = PlayerActionFactory.getInstance().pay(player, data);
    }
}
