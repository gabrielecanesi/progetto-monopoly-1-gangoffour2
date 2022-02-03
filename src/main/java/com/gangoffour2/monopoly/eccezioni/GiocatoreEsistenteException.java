package com.gangoffour2.monopoly.eccezioni;

import lombok.Data;

@Data
public class GiocatoreEsistenteException extends RuntimeException {
    private final String message;

    public GiocatoreEsistenteException(String message){
        this.message = message;
    }
}
