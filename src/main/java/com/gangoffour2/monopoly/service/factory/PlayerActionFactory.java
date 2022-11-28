package com.gangoffour2.monopoly.service.factory;

import com.gangoffour2.monopoly.Game;
import com.gangoffour2.monopoly.Player;
import com.gangoffour2.monopoly.actions.*;
import com.gangoffour2.monopoly.dto.*;
import com.gangoffour2.monopoly.service.exception.SquareNotFoundException;
import com.gangoffour2.monopoly.squares.Property;
import com.gangoffour2.monopoly.squares.Square;
import com.gangoffour2.monopoly.squares.land.Land;

public class PlayerActionFactory {
    private static PlayerActionFactory instance;

    private PlayerActionFactory() {

    }

    public static synchronized PlayerActionFactory getInstance(){
        if (instance == null){
            instance = new PlayerActionFactory();
        }
        return instance;
    }

    public BuyProperty build(BuyPropertyDto dto, Player player) throws SquareNotFoundException {
        Game game = player.getGame();
        Square square = game.getBoard().getSquareById(dto.getPropertyId());
        if (!(square instanceof Property)){
            throw new IllegalArgumentException();
        }

        return new BuyProperty(player, (Property) square);
    }

    public Offer build(OfferDto dto, Player player) {
        return new Offer(player, dto.getAmount());
    }


    public Mortgage build(MortgageDto data, Player player) throws SquareNotFoundException {
        Game game = player.getGame();
        Square square = game.getBoard().getSquareById(data.getPropertyId());
        if (!(square instanceof Property)){
            throw new IllegalArgumentException();
        }

        return new Mortgage(player, (Property) square);
    }

    public BuildHouse build(BuildHouseDto data, Player player) throws SquareNotFoundException {
        Game game = player.getGame();
        Square square = game.getBoard().getSquareById(data.getLandId());
        if (!(square instanceof Land)){
            throw new IllegalArgumentException();
        }

        return new BuildHouse(player, (Land) square);
    }

    public SellHouse build(Player player, SellHouseDto data) throws SquareNotFoundException {
        Game game = player.getGame();
        Square square = game.getBoard().getSquareById(data.getLandId());
        if (!(square instanceof Land)){
            throw new IllegalArgumentException();
        }

        return new SellHouse(player, (Land) square);
    }

    public Pay pay(Player player, PayDto data){
        return new Pay(player, data.getAmount());
    }
}