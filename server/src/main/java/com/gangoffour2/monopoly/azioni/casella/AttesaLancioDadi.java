package com.gangoffour2.monopoly.azioni.casella;

import com.gangoffour2.monopoly.stati.partita.StatoPartita;
import lombok.Builder;

@Builder
public class AttesaLancioDadi implements AzioneCasella {
    @Override
    public void accept(StatoPartita statoPartita) throws InterruptedException {
        statoPartita.onAzioneCasella(this);
    }
}
