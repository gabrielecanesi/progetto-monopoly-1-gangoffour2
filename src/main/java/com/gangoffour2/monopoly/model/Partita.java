package com.gangoffour2.monopoly.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.gangoffour2.monopoly.azioni.casella.AzioneCasella;
import com.gangoffour2.monopoly.azioni.giocatore.AzioneGiocatore;
import com.gangoffour2.monopoly.azioni.giocatore.VaiInPrigioneAzione;
import com.gangoffour2.monopoly.controller.MessageBrokerSingleton;
import com.gangoffour2.monopoly.eccezioni.GiocatoreEsistenteException;
import com.gangoffour2.monopoly.eccezioni.PartitaPienaException;
import com.gangoffour2.monopoly.model.giocatore.Giocatore;
import com.gangoffour2.monopoly.stati.partita.*;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Iterator;
import java.util.LinkedList;

@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class Partita extends IPartita implements PartitaObserver {

    @JsonIgnore
    @Builder.Default
    private transient LinkedList<StatoPartita> stackStati = new LinkedList<>();


    protected Partita(IPartitaBuilder<?, ?> b) {
        super(b);
    }

    public synchronized void inizioPartita() {
        turnoCorrente = Turno.builder()
                .giocatore(giocatori.get(0))
                .build();
        cambiaTurno();
    }

    public synchronized void aggiungiGiocatore(Giocatore g) throws PartitaPienaException, GiocatoreEsistenteException {
        if (giocatori.size() == config.getNumeroGiocatori()) {
            throw new PartitaPienaException("Partita piena");
        }
        if (giocatori.contains(g)) {
            throw new GiocatoreEsistenteException("Already existing user");
        }
        g.setCasellaCorrente(tabellone.getCasella(0));
        g.setConto(config.getSoldiIniziali());
        giocatori.add(g);
        g.setPartita(this);
        broadcast("game", this);
    }

    public synchronized void rimuoviGiocatore(Giocatore g) {
        Giocatore giocatore = getGiocatoreByNick(g.getNick());
        if (giocatore != null) {
            giocatore.abbandona();
            giocatori.remove(giocatore);
        }
    }

    public synchronized void cambiaTurno() {
        Giocatore curr = turnoCorrente.getGiocatore();
        if(curr == giocatori.get(0)) {
            tabellone.randomizzaCaselle();
            mazzo.randomizzaCarte();
        }
        setStato(InizioTurno.builder().build());
        setTurnoCorrente(Turno.builder()
                .giocatore(giocatori.get((giocatori.indexOf(curr) + 1) % giocatori.size()))
                .triggerDadiUguali(config.getTriggerDadiUguali())
                .build());

        turnoCorrente.inizializzaDadi(config.getNumeroDadi());
        turnoCorrente.getGiocatore().getCasellaCorrente().inizioTurno(turnoCorrente.getGiocatore());
        broadcast("newTurn", turnoCorrente);
    }

    @Override
    public <T> void broadcast(String topic, T object){
        MessageBrokerSingleton.getInstance().broadcast(id, topic, object);
    }

    @Override
    public synchronized void onAzioneCasella(AzioneCasella azione) {
        azione.accept(stato);
    }

    @Override
    public synchronized void onAzioneGiocatore(AzioneGiocatore azione) {
        azione.accept(stato);
    }

    public synchronized void setStato(StatoPartita nuovoStato) {
        stato = nuovoStato;
        stato.setPartita(this);
        broadcast("newState", nuovoStato);
    }

    private void sleep(){
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            Logger logger = LoggerFactory.getLogger(Partita.class);
            logger.error("Wait fallita");
            Thread.currentThread().interrupt();
        }
    }

    public void continuaTurno() {
        sleep();

        if (turnoCorrente.inVisita()){
            turnoCorrente.prossimoEffetto(tabellone);
        }
        else if (!turnoCorrente.isTerminato()){
            turnoCorrente.lancioDadi(config.getFacceDadi());
            if (turnoCorrente.limitePrigione()){
                Giocatore giocatore = turnoCorrente.getGiocatore();
                tabellone.muoviAProssimaCasellaSemplice(giocatore, casella -> casella.getNome().equals("Prigione"));
                giocatore.getCasellaCorrente().onAzioneGiocatore(VaiInPrigioneAzione
                        .builder()
                        .giocatore(giocatore)
                        .build());
                cambiaTurno();
            }
            else {
                turnoCorrente.prossimoEffetto(tabellone);
            }
        }
        else {
            setStato(FineTurno.builder().build());
            stato.esegui();
        }
    }

    public synchronized Giocatore getGiocatoreByNick(String nick) {
        Iterator<Giocatore> iter = giocatori.iterator();
        Giocatore res = null;
        while (iter.hasNext() && (res == null || !res.getNick().equals(nick))) {
            res = iter.next();
        }
        return res;
    }

    public synchronized void inizializza() {
        attendiAzione();
    }

    public synchronized void fermaAttesa() {
        listenerTimeoutEventi.stopTimeout();
    }

    public synchronized void attendiAzione() {
        listenerTimeoutEventi.setTimeout(() -> stato.onTimeout(), 10000);
    }

    @Override
    public void memorizzaStato(StatoPartita statoPartita){
        stackStati.addLast(stato);
    }

    @Override
    public void continueFrom(StatoPartita statoPartita){
        if (stackStati.isEmpty()){
            setStato(LancioDadi.builder().build());
            turnoCorrente.inizializzaDadi(config.getNumeroDadi());
            continuaTurno();
        }
        else {
            StatoPartita rimosso = stackStati.removeLast();
            setStato(rimosso);
            statoPartita.acceptRiprendi(rimosso);
        }
    }

    public void distruggi(){
        listenerTimeoutEventi.stopTimeout();
    }
}
