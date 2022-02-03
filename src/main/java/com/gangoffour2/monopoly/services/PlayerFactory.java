package com.gangoffour2.monopoly.services;

import com.gangoffour2.monopoly.eccezioni.InvalidPlayerCategoryException;
import com.gangoffour2.monopoly.model.giocatore.Giocatore;
import com.gangoffour2.monopoly.model.giocatore.Imprenditore;


public class PlayerFactory {
    public enum PlayerType{
        STANDARD,
        ENTERPRENEUR
    }
    private static PlayerFactory instance;
    private PlayerFactory(){
    }

    public static synchronized PlayerFactory getInstance(){
        if (instance == null){
            instance = new PlayerFactory();
        }
        return instance;
    }

    public Giocatore buildPlayer(String nickname, PlayerType type) throws InvalidPlayerCategoryException {
        Giocatore player;
        switch (type){
            case STANDARD:
                player = Giocatore.builder().nick(nickname).build();
                break;
            case ENTERPRENEUR:
                player = Imprenditore.builder().nick(nickname).build();
            default:
                throw new InvalidPlayerCategoryException("Invalid Player category");
        }
        return player;
    }
}
