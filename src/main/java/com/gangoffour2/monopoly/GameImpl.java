package com.gangoffour2.monopoly;

import com.gangoffour2.monopoly.commands.GameCommand;
import com.gangoffour2.monopoly.service.GameManager;
import com.gangoffour2.monopoly.service.exception.MonopolyException;
import com.gangoffour2.monopoly.service.exception.PlayerNotFoundException;
import com.gangoffour2.monopoly.service.exception.SquareNotFoundException;
import com.gangoffour2.monopoly.service.factory.BoardFactory;
import com.gangoffour2.monopoly.squares.Square;
import com.gangoffour2.monopoly.util.MessageManager;

import java.io.IOException;
import java.util.*;

public class GameImpl extends Game {

    private final Random roll;
    private final MessageManager messageManager;
    private Integer turn;

    public GameImpl(Config config) throws IOException {
        this.config = config;
        id = UUID.randomUUID();
        turn = 0;
        roll = new Random();
        commands = new Stack<>();
        players = new LinkedList<>();
        currentState = null;
        board = BoardFactory.getInstance().createBoard();
        board.setGame(this);
        messageManager = new MessageManager(this);
    }
    private boolean endGame(){
        return false;
    }


    private Vector<Integer> rollDice(){
        Vector<Integer> result = new Vector<>(2);
        result.add(roll.nextInt(5) + 1);
        result.add(roll.nextInt(5) + 1);
        return result;
    }

    private void nextTurn(){
        turn = (turn + 1) % players.size();
    }

    private Player currentPlayer(){
        return getPlayers().get(turn);
    }

    private void gameLoop() throws InterruptedException, SquareNotFoundException {

        while(!endGame()){
            System.out.println("Turn " + turn);
            Vector<Integer> dice = rollDice();
            int sum = dice.stream().reduce(0, Integer::sum);

            for (int i = 1; i <= sum; ++i){
                GameCommand action = i < sum ?
                        board.intermediateStep(currentPlayer()) :
                        board.finalStep(currentPlayer());

                Thread.sleep(200);
                Square square = board.getSquareByPosition(currentPlayer().getPosition());
                messageManager.broadcastNewSquare(currentPlayer(), square);
                messageManager.sendPrivate(currentPlayer(), "Message to " + currentPlayer().getUsername());

                commands.push(action);
                consumeCommandsStack();
            }
            nextTurn();
        }
    }

    private void consumeCommandsStack() throws InterruptedException {
        while (!commands.empty()){
            GameCommand action = commands.pop();
            currentState = action;
            action.execute();
            currentState = null;
        }
    }

    @Override
    public void addPlayer(Player player) throws InterruptedException, MonopolyException {
        players.add(player);
        GameManager.getInstance().addPlayer(player);
        player.setGame(this);
        if (players.size() == config.getPlayers()){
            start();
        }
    }

    @Override
    public synchronized void removePlayer(Player player) throws PlayerNotFoundException {
        players.remove(player);
        GameManager.getInstance().removePlayer(player);
    }

    private void start() throws InterruptedException, SquareNotFoundException {
        messageManager.broadcastGame();
        gameLoop();
    }
}
