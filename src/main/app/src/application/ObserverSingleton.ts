import IPartita from "../interfaces/IPartita";
import {ICarta} from "../interfaces/ICarta";
import PlayerPosition from "../interfaces/PlayerPosition";
import PlayerAction from "../interfaces/PlayerAction";
import IStatoPartita from "../interfaces/stati/partita/IStatoPartita";

interface Observer {

}

export interface ObserverPartita extends Observer {
    gameUpdate(partita: IPartita): void
}

export interface PlayerActionObserver extends Observer{
    onPlayerAction(action: PlayerAction): void
}

export interface PlayerPositionObserver extends Observer {
    onPlayerPosition(position: PlayerPosition): void
}

export interface ObserverCarta extends Observer {
    onCardExtraction(carta: ICarta): void
}

export interface NewStateObserver extends Observer {
    onNewState(state: IStatoPartita): void
}

export class ObserverSingleton {
    private static listListener:ObserverPartita[] = [];
    private static listListenerCarta:ObserverCarta[] = [];
    private static playerActionListener: PlayerActionObserver[] = [];
    private static playerPositionListener: PlayerPositionObserver[] = [];
    private static newStateListener: NewStateObserver[] = [];

    static addListener(listener: ObserverPartita){
        this.listListener.push(listener);
    }

    static addListenerCarta(listener: ObserverCarta){
        this.listListenerCarta.push(listener);
    }

    static addPlayerActionsListener(listener: PlayerActionObserver){
        this.playerActionListener.push(listener);
    }

    static addPlayerPositionListener(listener: PlayerPositionObserver) {
        this.playerPositionListener.push(listener);
    }

    static addNewStateListener(listener: NewStateObserver){
        this.newStateListener.push(listener);
    }

    static removeListener(listener: Observer) {
        this.listListener = this.listListener.filter(el => el !== listener);
        this.listListenerCarta = this.listListenerCarta.filter(el => el !== listener);
        this.playerActionListener = this.playerActionListener.filter(el => el !== listener);
        this.playerPositionListener = this.playerPositionListener.filter(el => el !== listener);
        this.newStateListener = this.newStateListener.filter(el => el !== listener);
    }

    static notify(partita: IPartita){
        this.listListener.forEach(el => el.gameUpdate(partita));
    }

    static onPlayerPosition(position: PlayerPosition){
        this.playerPositionListener.forEach(listener => listener.onPlayerPosition(position));
    }

    static onPlayerAction(action: PlayerAction){
        this.playerActionListener.forEach(listener => listener.onPlayerAction(action));
    }

    static onCardExtract(carta: ICarta){
        this.listListenerCarta.forEach(el => el.onCardExtraction(carta))
    }

    static onNewState(state: IStatoPartita) {
        this.newStateListener.forEach(listener => listener.onNewState(state));
    }
}
