package com.gangoffour2.monopoly.eccezioni;

import lombok.Data;

@Data
public class InvalidPlayerCategoryException extends RuntimeException {
    private final String message;
    public InvalidPlayerCategoryException(String message){
        this.message = message;
    }
}
