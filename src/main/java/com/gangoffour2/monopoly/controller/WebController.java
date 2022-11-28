package com.gangoffour2.monopoly.controller;

import com.gangoffour2.monopoly.Config;
import com.gangoffour2.monopoly.Game;
import com.gangoffour2.monopoly.service.WebService;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/games")
public class WebController {

    @Setter(onMethod=@__({@Autowired}))
    private WebService service;

    @PostMapping
    public String createGame(@RequestBody Config config) throws IOException {
        return service.createGame(config);
    }

    @GetMapping
    public List<Game> getGames(){
        return service.getGames();
    }
}


