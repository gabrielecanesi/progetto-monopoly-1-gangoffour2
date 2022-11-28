import {CompatClient, IMessage, Stomp} from "@stomp/stompjs";
import websocket from "websocket"
import IPartita from "../interfaces/IPartita";
import IConfigurazione from "../interfaces/IConfigurazione";
import {ObserverSingleton} from "./ObserverSingleton";
import ICasellaProprieta from "../interfaces/caselle/ICasellaProprieta";
import {ICarta} from "../interfaces/ICarta";
import ICasellaTerreno from "../interfaces/caselle/ICasellaTererno";
import ICasella from "../interfaces/caselle/ICasella";
import {PlayerType} from "../interfaces/PlayerType";
import PlayerPosition from "../interfaces/PlayerPosition";
import PlayerAction from "../interfaces/PlayerAction";
import IStatoPartita from "../interfaces/stati/partita/IStatoPartita";

Object.assign(global, {WebSocket: websocket.w3cwebsocket})

const URL = "http://" + (process.env.NODE_ENV === "development" ? "localhost:8080" : window.location.host);
const WS_URL = "ws://" + (process.env.NODE_ENV === "development" ? "localhost:8080" : window.location.host);

export default class StompController {

    static client: CompatClient;
    static idPartita: string;

    static creaPartita(configuration: IConfigurazione): Promise<string> {
        return fetch(URL + "/partite", {
            method: "POST",
            body: JSON.stringify(configuration),
            headers: {
                'Content-Type': 'application/json'
            }
        })
            .then((res) => {
                if (res.status === 200) {
                    return res.text()
                }
                throw new Error("Impossibile creare una partita")
            })
    }

    static getPartite(): Promise<IPartita[]> {
        return fetch(URL + "/partite")
            .then((res) => {
                if (res.status === 200) {
                    return res.json()
                }
                throw new Error("Impossibile ottenere le partita")
            })
    }

    static getPartita(idPartita: string) {
        return fetch(URL + "/partite/" + idPartita)
            .then((res) => {
                if (res.status === 200) {
                    return res.json()
                }
                throw new Error("Impossibile ottenere la partita")
            })
    }

    static accediPartita(idPartita: string, nickname: string, isImprenditore: PlayerType) {
        const client = Stomp.client(WS_URL + "/stomp");
        this.client = client;
        this.idPartita = idPartita;
        client.connect({}, () => {

            client.subscribe("/topic/partite/" + idPartita + "/position", (res) => {
                console.log(JSON.parse(res.body));
                ObserverSingleton.onPlayerPosition(JSON.parse(res.body) as PlayerPosition);
            });

            client.subscribe("/topic/partite/" + idPartita + "/playerAction", (message) => {
                ObserverSingleton.onPlayerAction(JSON.parse(message.body) as PlayerAction);
                console.log(JSON.parse(message.body));
            });

            client.subscribe("/topic/partite/" + idPartita + "/newState", (message: IMessage) => {
                console.log(JSON.parse(message.body));
                ObserverSingleton.onNewState(JSON.parse(message.body) as IStatoPartita);
            })

            client.subscribe("/topic/partite/" + idPartita + "/game", (res) => {
                console.log(JSON.parse(res.body));
                ObserverSingleton.notify(JSON.parse(res.body) as IPartita);
            });

            client.send("/app/partite/" + idPartita + "/entra", {},
                JSON.stringify({nickname: nickname, isImprenditore: isImprenditore}));
        });
    }

    static subscribeCarte() {
        this.client.subscribe("/topic/partite/" + this.idPartita + "/carta", (res) => {
                ObserverSingleton.onCardExtract(JSON.parse(res.body) as ICarta)
            }
        )
    }

    static ipoteca(casella: ICasellaProprieta) {
        console.log(casella)
        this.client.send("/app/partite/" + this.idPartita + "/ipoteca", {}, JSON.stringify(casella))
    }

    static lanciaDadi() {
        this.client.send("/app/partite/" + this.idPartita + "/lanciaDadi")
    }

    static acquista() {
        this.client.send("/app/partite/" + this.idPartita + "/acquista")
    }

    static paga() {
        this.client.send("/app/partite/" + this.idPartita + "/paga")
    }

    static avviaAsta(casella: ICasella) {
        this.client.send("/app/partite/" + this.idPartita + "/avviaAsta", {}, JSON.stringify(casella))
    }

    static fineTurno() {
        this.client.send("/app/partite/" + this.idPartita + "/terminaTurno")
    }

    static offri(importo: number) {
        this.client.send("/app/partite/" + this.idPartita + "/offri", {}, String(importo))
    }

    static upgradeTerreno(terreno: ICasellaTerreno){
        this.client.send("/app/partite/" + this.idPartita + "/upgradeTerreno", {}, JSON.stringify(terreno))
    }

    static downgradeTerreno(terreno: ICasellaTerreno){
        this.client.send("/app/partite/" + this.idPartita + "/downgradeTerreno", {}, JSON.stringify(terreno))
    }
}
