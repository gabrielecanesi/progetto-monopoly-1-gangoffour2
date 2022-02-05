package com.gangoffour2.monopoly.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.StompWebSocketEndpointRegistration;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class EventiControllerConfig implements WebSocketMessageBrokerConfigurer {
    public static final String BROKER_PREFIX = "/topic/partite/";
    @Autowired
    private Environment env;

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        String urls = env.getProperty("cors.urls");
        StompWebSocketEndpointRegistration reg = registry.addEndpoint("/stomp");

        if (urls != null) {
            for (String url : urls.split(",")) {
                reg.setAllowedOriginPatterns(url);
            }
        }
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.setApplicationDestinationPrefixes("/app");
        config.enableSimpleBroker("/topic", "/queue");
        config.setUserDestinationPrefix("/user");
    }
}