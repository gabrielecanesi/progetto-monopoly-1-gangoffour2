package com.gangoffour2.monopoly.eccezioni;

import lombok.Data;

@Data
public class PlayerNotFoundException extends Exception {
    String id;
    public PlayerNotFoundException(String message, String id){
        super(message);
        this.id = id;
    }
}
