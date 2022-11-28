package com.gangoffour2.monopoly;

import com.gangoffour2.monopoly.commands.GameCommand;
import com.gangoffour2.monopoly.service.exception.MonopolyException;
import com.gangoffour2.monopoly.service.exception.PlayerNotFoundException;
import lombok.Data;

import java.util.LinkedList;
import java.util.Stack;
import java.util.UUID;

@Data
public abstract class Game {
    protected UUID id;
    protected Config config;
    protected LinkedList<Player> players;
    protected Board board;
    protected Stack<GameCommand> commands;
    protected GameCommand currentState;
    public abstract void addPlayer(Player player) throws InterruptedException, MonopolyException;
    public abstract void removePlayer(Player player) throws PlayerNotFoundException;
}