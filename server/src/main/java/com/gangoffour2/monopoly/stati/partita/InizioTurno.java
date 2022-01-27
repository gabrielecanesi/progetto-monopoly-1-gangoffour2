package com.gangoffour2.monopoly.stati.partita;

import com.gangoffour2.monopoly.azioni.casella.AttesaLancioDadi;
import lombok.Builder;

@Builder
public class InizioTurno extends StatoPartita {
    @Override
    public void onAzioneCasella(AttesaLancioDadi attesaLancioDadi) {
        partita.setStato(LancioDadi.builder().build());
        partita.getStato().esegui();
    }

    @Override
    public void onAzioneCasella(AttesaPrigione attesaPrigione) {
        partita.setStato(AttesaPrigione.builder().build());
    }
}
