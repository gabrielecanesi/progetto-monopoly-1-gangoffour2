package com.gangoffour2.monopoly.model;


import com.gangoffour2.monopoly.azioni.casella.AzioneCasella;

import java.io.Serializable;

/**
 * Interfaccia utilizzata per ricevere dagli stati delle caselle le notifiche
 * relative alle azioni da eseguire.
 */

public interface PartitaObserver extends Serializable {
    void onAzioneCasella(AzioneCasella azione);
    <T> void broadcast(String topic, T object);
}
