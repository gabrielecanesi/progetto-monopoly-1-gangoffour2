package com.gangoffour2.monopoly.stati.partita;

import com.gangoffour2.monopoly.azioni.giocatore.EntraInPartita;
import com.gangoffour2.monopoly.eccezioni.GiocatoreEsistenteException;
import lombok.Builder;

@Builder
public class Lobby extends StatoPartita {

    @Override
    public void acceptRiprendi(StatoPartita statoPartita) {
        statoPartita.riprendi(this);
    }

    @Override
    public void onAzioneGiocatore(EntraInPartita entraInPartita) {
        partita.fermaAttesa();
        // Aggiorna i client e poi si rimette in attesa se non è stato raggiunto il numero
        partita.aggiungiGiocatore(entraInPartita.getGiocatore());

        if (partita.getGiocatori().size() == partita.getConfig().getNumeroGiocatori()) {
            partita.inizioPartita();
        }
    }
}
