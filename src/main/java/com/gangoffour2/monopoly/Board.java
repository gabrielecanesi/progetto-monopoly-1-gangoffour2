package com.gangoffour2.monopoly;

import com.gangoffour2.monopoly.commands.GameCommand;
import com.gangoffour2.monopoly.service.exception.SquareNotFoundException;
import com.gangoffour2.monopoly.squares.Square;
import lombok.Data;

@Data
public class Board {
    private final Square[] squares;

    public Board(Square[] squares){
        this.squares = squares;
        for (int i = 0; i < squares.length; ++i){
            squares[i].setId(i);
        }
    }

    public static Board fromSquares(Square[] landsArray) {
        return new Board(landsArray);
    }

    public GameCommand intermediateStep(Player player){
        player.setPosition((player.getPosition() + 1) % squares.length);
        return squares[player.getPosition()].intermediateStep(player);
    }

    public GameCommand finalStep(Player player){
        player.setPosition((player.getPosition() + 1) % squares.length);
        return squares[player.getPosition()].landTo(player);
    }

    public void setGame(Game game){
        for (Square land : squares) {
            land.setGame(game);
        }
    }

    private Square get(SquarePredicate predicate) throws SquareNotFoundException {
        int i = 0;
        while (i < squares.length && predicate.exec(squares[i])){
            ++i;
        }

        if(i == squares.length)
            throw new SquareNotFoundException();

        return squares[i];
    }

    public Square getSquareByPosition(Integer id) throws SquareNotFoundException {
        if(id < 0 || id >= squares.length)
            throw new SquareNotFoundException();

        return squares[id];
    }

    public Square getSquareById(Integer id) throws SquareNotFoundException {
        return get((square) -> square.getId().equals(id));
    }
}
