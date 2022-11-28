package com.gangoffour2.monopoly;

import com.gangoffour2.monopoly.commands.GameCommand;
import com.gangoffour2.monopoly.service.exception.SquareNotFoundException;
import com.gangoffour2.monopoly.squares.Square;
import lombok.Data;

import java.util.Objects;

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
        return squares[(player.getPosition() + 1) % squares.length].intermediateStep(player);
    }

    public GameCommand finalStep(Player player){
        player.setPosition((player.getPosition() + 1) % squares.length);
        return squares[(player.getPosition() + 1) % squares.length].landTo(player);
    }

    public void setGame(Game game){
        for (Square land : squares) {
            land.setGame(game);
        }
    }

    public Square getSquareByPosition(Integer id) throws SquareNotFoundException {
        int i = 0;
        while (i < squares.length && !Objects.equals(squares[i].getId(), id)){
            ++i;
        }
        if (i == squares.length){
            throw new SquareNotFoundException();
        }
        return squares[i];
    }

    public Square getSquareById(Integer id) throws SquareNotFoundException {
        Square result = null;
        int i = 0;
        while (result == null && i < squares.length){
            if (squares[i].getId().equals(id)){
                result = squares[i];
            }
            ++i;
        }
        if (result == null){
            throw new SquareNotFoundException();
        }

        return result;
    }
}
