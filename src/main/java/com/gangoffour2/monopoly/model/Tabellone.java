package com.gangoffour2.monopoly.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.gangoffour2.monopoly.model.casella.Casella;
import com.gangoffour2.monopoly.model.casella.strategy.ProprietaCaselleStrategy;
import com.gangoffour2.monopoly.model.casella.strategy.StrategiaCasellaVanilla;
import com.gangoffour2.monopoly.model.giocatore.Giocatore;
import com.gangoffour2.monopoly.model.giocatore.PlayerPosition;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.function.Predicate;

@Data
@Builder
@AllArgsConstructor
public class Tabellone implements ITabellone, Serializable {
    @JsonIgnore
    private IPartita partita;
    private List<Casella> caselle;

    @JsonIgnore
    @Builder.Default
    private transient ProprietaCaselleStrategy strategia = new StrategiaCasellaVanilla();

    @Override
    public Casella getCasella(int posizione) {
        return caselle.get(posizione);
    }

    @Override
    public void muoviGiocatore(Giocatore giocatore, int quantita) {
        int currentPosition = caselle.indexOf(giocatore.getCasellaCorrente());
        giocatore.setCasellaCorrente(caselle.get((currentPosition + quantita) % caselle.size()));
        partita.broadcast("position", PlayerPosition.builder()
                .player(giocatore)
                .position((currentPosition + quantita) % caselle.size())
                .build());
    }

    @Override
    public void muoviGiocatoreIntero(Giocatore giocatore, int quantita) {
        if (quantita != 0) {
            Turno turno = partita.getTurnoCorrente();
            if (quantita > 0) {
                turno.setCasellaDaVisitare(quantita);
                partita.continuaTurno();
            } else
                giocatore.getCasellaCorrente().arrivo(giocatore);
        }
    }

    private int findOffsetProssimaCasella(Giocatore giocatore, Predicate<Casella> predicato){
        Casella corrente = giocatore.getCasellaCorrente();
        int i = caselle.indexOf(corrente);
        int cont = 0;
        Casella prossimaCasella;
        do {
            prossimaCasella = caselle.get((i + 1) % caselle.size());
            ++i;
            ++cont;
        } while (!predicato.test(prossimaCasella));

        return cont;
    }

    /**
     * Funzione che sposta un giocatore sul tabellone sulla base della prima occorrenza trovata (tipo, nome, ecc...).
     * Bisogna passare una lambda che prende come argomento la casella trovata e ritorna vero o falso.
     *
     * @param giocatore Il giocatore da spostare
     * @param predicato La funzione da applicare per capire quando si è arrivati alla casella giusta
     */
    @Override
    public void muoviAProssimaCasellaIntero(Giocatore giocatore, Predicate<Casella> predicato) {
        muoviGiocatoreIntero(giocatore, findOffsetProssimaCasella(giocatore, predicato));
    }

    @Override
    public void muoviAProssimaCasellaSemplice(Giocatore giocatore,Predicate<Casella> predicato){
        muoviGiocatore(giocatore, findOffsetProssimaCasella(giocatore, predicato));
    }

    @Override
    public void applicaEffetto(Giocatore giocatore, int offset) {
        muoviGiocatore(giocatore, 1);
        if (offset == 0) {
            giocatore.getCasellaCorrente().arrivo(giocatore);
        } else {
            giocatore.getCasellaCorrente().passaggio();
        }
    }

    @Override
    public void randomizzaCaselle() {
        caselle.forEach(c -> strategia.randomizzaCasella(c));
    }
}
