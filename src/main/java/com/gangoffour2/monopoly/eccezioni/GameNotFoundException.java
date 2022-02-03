package com.gangoffour2.monopoly.eccezioni;

import lombok.Data;

@Data
public class GameNotFoundException extends Exception {
    private final String id;
    public GameNotFoundException(String message, String id){
        super(message);
        this.id = id;
    }
}
