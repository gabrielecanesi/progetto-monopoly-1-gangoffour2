package com.gangoffour2.monopoly.controller;


import com.gangoffour2.monopoly.model.IPartita;
import lombok.Builder;
import lombok.Data;
import org.springframework.messaging.simp.SimpMessagingTemplate;


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
        template.convertAndSend(EventiControllerConfig.BROKER_PREFIX + partita.getId(), partita);
    }


    public <T> void broadcast(String gameId, String topic, T oggetto){
        template.convertAndSend(EventiControllerConfig.BROKER_PREFIX + gameId + "/" + topic, oggetto);
    }
}
