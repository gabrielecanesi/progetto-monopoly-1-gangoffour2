package com.gangoffour2.monopoly.service.factory;

import com.gangoffour2.monopoly.Config;
import com.gangoffour2.monopoly.Game;
import com.gangoffour2.monopoly.GameImpl;

import java.io.IOException;

public class GameFactory {

    private static GameFactory instance;

    private GameFactory(){}

    public synchronized static GameFactory getInstance(){
        if (instance == null){
            instance = new GameFactory();
        }
        return instance;
    }

    public Game fromConfig(Config config) throws IOException {
        return new GameImpl(config);
    }
}
