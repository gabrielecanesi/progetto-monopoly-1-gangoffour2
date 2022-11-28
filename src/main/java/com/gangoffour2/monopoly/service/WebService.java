package com.gangoffour2.monopoly.service;

import com.gangoffour2.monopoly.Config;
import com.gangoffour2.monopoly.Game;
import com.gangoffour2.monopoly.service.factory.GameFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class WebService {

    public String createGame(Config config) throws IOException {
        Game g = GameFactory.getInstance().fromConfig(config);
        GameManager.getInstance().addGame(g);
        return g.getId().toString();
    }

    public List<Game> getGames() {
        return GameManager.getInstance().getGames();
    }
}
