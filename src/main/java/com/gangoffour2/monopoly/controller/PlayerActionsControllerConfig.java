package com.gangoffour2.monopoly.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.StompWebSocketEndpointRegistration;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

import java.security.Principal;
import java.util.Map;
import java.util.UUID;

@Configuration
@EnableWebSocketMessageBroker
public class PlayerActionsControllerConfig implements WebSocketMessageBrokerConfigurer {

    @Value("#{'${cors.urls}'.split(',')}")
    private String[] urls;

    @Value("${stomp.endpoint}")
    private String stompEndpoint;

    @Value("#{'${stomp.destination.prefixes}'.split(',')}")
    private String[] destinationPrefixes;

    @Value("#{'${stomp.application.prefixes}'.split(',')}")
    private String[] applicationPrefixes;

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        StompWebSocketEndpointRegistration reg = registry.addEndpoint(stompEndpoint)
                .setHandshakeHandler(new CustomHandshakeHandler());
        reg.setAllowedOriginPatterns(urls);
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.setApplicationDestinationPrefixes(applicationPrefixes);
        registry.enableSimpleBroker(destinationPrefixes);
    }
}

class StompPrincipal implements Principal {
    String name;

    StompPrincipal(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }
}

class CustomHandshakeHandler extends DefaultHandshakeHandler {
    @Override
    protected Principal determineUser(ServerHttpRequest request,WebSocketHandler wsHandler, Map<String, Object> attributes) {
        return new StompPrincipal(UUID.randomUUID().toString());
    }
}
