package com.gangoffour2.monopoly.controller;


import com.gangoffour2.monopoly.model.IPartita;
import lombok.Builder;
import lombok.Data;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageType;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;

@Data
@Builder
public class MessageBrokerSingleton {
    private static MessageBrokerSingleton instance;
    private final SimpMessagingTemplate template;

    public static MessageBrokerSingleton getInstance() {
        return instance;
    }

    public static void setInstance(MessageBrokerSingleton instance) {
        MessageBrokerSingleton.instance = instance;
    }

    public synchronized void broadcast(IPartita partita) {
        template.convertAndSend("/topic/partite/" + partita.getId(), partita);
    }


    public <T> void broadcast(String idPartita, String name,T oggetto){
        template.convertAndSend("/topic/partite/" + idPartita + "/" + name, oggetto);
    }
}
