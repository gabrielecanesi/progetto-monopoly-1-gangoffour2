package com.gangoffour2.monopoly.services;

import com.gangoffour2.monopoly.eccezioni.GameNotFoundException;
import com.gangoffour2.monopoly.eccezioni.PlayerNotFoundException;
import com.gangoffour2.monopoly.model.giocatore.Giocatore;
import com.gangoffour2.monopoly.model.IPartita;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PartiteRepository {
    private static PartiteRepository instance;
    private final HashMap<String, IPartita> partite;
    private final HashMap<String, Giocatore> giocatori;

    private PartiteRepository() {
        partite = new HashMap<>();
        giocatori = new HashMap<>();
    }

    public static synchronized PartiteRepository getInstance() {
        if (instance == null) {
            instance = new PartiteRepository();
        }

        return instance;
    }

    public synchronized void rimuoviGiocatoreById(String idSessione) {
        giocatori.remove(idSessione);
    }

    public synchronized Giocatore getGiocatoreByIdSessione(String idSessione) throws PlayerNotFoundException {
        Giocatore player = giocatori.get(idSessione);
        if (player == null){
            throw new PlayerNotFoundException("No player matches the session id", idSessione);
        }
        return player;
    }

    public synchronized void registraGiocatore(String idSessione, Giocatore giocatore) {
        giocatori.put(idSessione, giocatore);
    }

    public synchronized IPartita getPartitaById(String id) throws GameNotFoundException {
        IPartita game = partite.get(id);
        if (game == null){
            throw new GameNotFoundException("Game not found", id);
        }
        return game;
    }

    public synchronized void addPartita(IPartita partita) {
        partite.put(partita.getId(), partita);
    }

    public synchronized List<IPartita> getPartiteAperte() {
        return new ArrayList<>(partite.values());
    }

    public synchronized void rimuoviPartitaById(String id) {
        partite.remove(id);
    }

    public synchronized boolean gameExists(String gameId){
        return partite.containsKey(gameId);
    }
}
