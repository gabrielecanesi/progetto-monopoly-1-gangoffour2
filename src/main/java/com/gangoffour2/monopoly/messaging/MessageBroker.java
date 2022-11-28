package com.gangoffour2.monopoly.messaging;

import lombok.Data;
import org.springframework.messaging.simp.SimpMessagingTemplate;

@Data
public class MessageBroker {
    private static MessageBroker instance;
    private SimpMessagingTemplate template;

    public static synchronized MessageBroker getInstance() {
        if (instance == null){
            instance = new MessageBroker();
        }
        return instance;
    }

    public <T> void sendMessageToSession(String sessionId, T data, String topic){
        template.convertAndSendToUser(sessionId, topic, data);
    }

    public <T> void broadcastGameMessage(T data, String topic){
        template.convertAndSend(topic, data);
    }

}
