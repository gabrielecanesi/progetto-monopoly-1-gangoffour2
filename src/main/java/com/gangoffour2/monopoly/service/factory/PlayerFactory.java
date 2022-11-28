package com.gangoffour2.monopoly.service.factory;

import com.gangoffour2.monopoly.Player;
import com.gangoffour2.monopoly.PlayerImpl;
import com.gangoffour2.monopoly.controller.PlayerData;

public class PlayerFactory {
    private static PlayerFactory instance;
    private PlayerFactory(){}

    public static synchronized PlayerFactory getInstance(){
        if (instance == null){
            instance = new PlayerFactory();
        }
        return instance;
    }
    public Player build(PlayerData data, String sessionId){
        return new PlayerImpl(sessionId, data.getUsername(), 1000);
    }
}
