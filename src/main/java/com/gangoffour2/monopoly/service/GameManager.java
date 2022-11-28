package com.gangoffour2.monopoly.service;

import com.gangoffour2.monopoly.Game;
import com.gangoffour2.monopoly.Player;
import com.gangoffour2.monopoly.service.exception.GameNotFoundException;
import com.gangoffour2.monopoly.service.exception.MonopolyException;
import com.gangoffour2.monopoly.service.exception.PlayerAlreadyExistingException;
import com.gangoffour2.monopoly.service.exception.PlayerNotFoundException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameManager {

    private static GameManager instance;
    private final Map<String, Game> runningGames;
    private final Map<String, Player> connectedPlayers;

    private GameManager(){
        runningGames = new HashMap<>();
        connectedPlayers = new HashMap<>();
    }

    public static synchronized GameManager getInstance(){
        if (instance == null){
            instance = new GameManager();
        }
        return instance;
    }

    public void addGame(Game game){
        runningGames.put(game.getId().toString(), game);
    }

    public List<Game> getGames() {
        return runningGames.values().stream().toList();
    }

    public Player getPlayerBySessionId(String sessionId) throws PlayerNotFoundException {
        if (!connectedPlayers.containsKey(sessionId)){
            throw new PlayerNotFoundException();
        }
        return connectedPlayers.get(sessionId);
    }

    public Game getGameById(String id) throws GameNotFoundException {
        if (!runningGames.containsKey(id)){
            throw new GameNotFoundException();
        }
        return runningGames.get(id);
    }

    public void addPlayer(Player player) throws MonopolyException {
        if (connectedPlayers.containsKey(player.getId())){
            throw new PlayerAlreadyExistingException();
        }
        connectedPlayers.put(player.getId(), player);
    }

    public void removePlayer(Player player) throws PlayerNotFoundException {
        if (!connectedPlayers.containsKey(player.getId())){
            throw new PlayerNotFoundException();
        }
        connectedPlayers.remove(player.getId());
    }
}
