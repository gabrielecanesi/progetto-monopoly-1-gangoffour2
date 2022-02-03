package com.gangoffour2.monopoly.azioni.giocatore;

import com.gangoffour2.monopoly.eccezioni.GiocatoreEsistenteException;
import com.gangoffour2.monopoly.model.giocatore.Giocatore;
import com.gangoffour2.monopoly.stati.casella.StatoCasella;
import com.gangoffour2.monopoly.stati.partita.StatoPartita;
import lombok.Data;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

@Data
@SuperBuilder
public abstract class AzioneGiocatore implements Serializable {
    protected Giocatore giocatore;

    protected AzioneGiocatore() {

    }

    public abstract void accept(StatoCasella statoCasella);

    public abstract void accept(StatoPartita statoPartita);
}
