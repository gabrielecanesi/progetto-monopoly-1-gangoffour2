package com.gangoffour2.monopoly.eccezioni;

public class PartitaPienaException extends RuntimeException {
    String message;

    public PartitaPienaException(String message){
        this.message = message;
    }
}
