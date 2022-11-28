package com.gangoffour2.monopoly.controller;


import com.gangoffour2.monopoly.dto.*;
import com.gangoffour2.monopoly.messaging.MessageBroker;
import com.gangoffour2.monopoly.service.PlayerActionsService;
import com.gangoffour2.monopoly.service.exception.MonopolyException;
import com.gangoffour2.monopoly.service.exception.PlayerNotFoundException;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.util.Objects;

@Controller
public class PlayerActionsController {

    @Setter(onMethod = @__({@Autowired}))
    private PlayerActionsService playerService;

    @Autowired
    public PlayerActionsController(SimpMessagingTemplate template){
        MessageBroker.getInstance().setTemplate(template);
    }

    @EventListener
    public void onApplicationEvent(SessionDisconnectEvent event) throws PlayerNotFoundException {
        playerService.onPlayerDisconnect(Objects.requireNonNull(event.getUser()).getName());
    }

    @MessageMapping("/games/{id}/join")
    public void joinGame(@Payload PlayerData data, @DestinationVariable String id, SimpMessageHeaderAccessor header) throws MonopolyException, InterruptedException {
        playerService.joinGame(data, id, Objects.requireNonNull(header.getUser()).getName());
    }

    @MessageMapping("/quit")
    public void quitGame(SimpMessageHeaderAccessor header) throws PlayerNotFoundException {
        playerService.quitGame(Objects.requireNonNull(header.getUser()).getName());
    }

    @MessageMapping("/rollDice")
    public void rollDice(SimpMessageHeaderAccessor header) throws PlayerNotFoundException {
        playerService.rollDice(Objects.requireNonNull(header.getUser()).getName());
    }

    @MessageMapping("/buy")
    public void buyProperty(@Payload BuyPropertyDto dto, SimpMessageHeaderAccessor header) throws MonopolyException {
        playerService.buyProperty(Objects.requireNonNull(header.getUser()).getName(), dto);
    }

    @MessageMapping("/offer")
    public void offer(@Payload OfferDto data, SimpMessageHeaderAccessor header) throws MonopolyException {
        playerService.offer(Objects.requireNonNull(header.getUser()).getName(), data);
    }

    @MessageMapping("/mortgage")
    public void mortgage(@Payload MortgageDto data, SimpMessageHeaderAccessor header) throws MonopolyException {
        playerService.mortgage(Objects.requireNonNull(header.getUser()).getName(), data);
    }

    @MessageMapping("/buildHouse")
    public void buildHouse(@Payload BuildHouseDto dto, SimpMessageHeaderAccessor header) throws MonopolyException {
        playerService.buildHouse(Objects.requireNonNull(header.getUser()).getName(), dto);
    }

    @MessageMapping("/sellHouse")
    public void sellHouse(@Payload SellHouseDto dto, SimpMessageHeaderAccessor header) throws MonopolyException {
        playerService.sellHouse(Objects.requireNonNull(header.getUser()).getName(), dto);
    }

    @MessageMapping
    public void pay(@Payload PayDto dto, SimpMessageHeaderAccessor header) throws MonopolyException {
        playerService.pay(Objects.requireNonNull(header.getUser()).getName(), dto);
    }
}
