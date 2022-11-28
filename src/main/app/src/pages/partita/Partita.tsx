import {
    NewStateObserver,
    ObserverPartita,
    ObserverSingleton,
    PlayerPositionObserver
} from "../../application/ObserverSingleton";
import React from "react";
import IPartita from "../../interfaces/IPartita";
import Tabellone from "../../component/Tabellone";
import StompController from "../../application/stompController";
import CasellaSingleton from "../../component/caselle/CasellaSingleton";
import BarraAzioni from "../../component/barraAzioni/barraAzioni";
import style from "./partita.module.css"
import {PlayerType} from "../../interfaces/PlayerType";
import PlayerPosition from "../../interfaces/PlayerPosition";
import IStatoPartita from "../../interfaces/stati/partita/IStatoPartita";

interface Props {
    idPartita: string,
    nickname: string,
    isImprenditore: PlayerType
}

interface State {
    partita?: IPartita
}

export default class Partita extends React.Component<Props, State> implements ObserverPartita, NewStateObserver {

    constructor(props: Props) {
        super(props);
        this.state = {
            partita: undefined
        }
    }

    componentDidMount() {
        ObserverSingleton.addListener(this);
        ObserverSingleton.addNewStateListener(this);
        StompController.accediPartita(this.props.idPartita, this.props.nickname, this.props.isImprenditore);
    }

    componentWillUnmount() {
        ObserverSingleton.removeListener(this);
    }

    onNewState(state: IStatoPartita) {
        let newPartita: IPartita | undefined = this.state.partita;
        if (newPartita !== undefined){
            newPartita.stato = state;
            this.setState({
               partita: newPartita
            });
        }
    }

    gameUpdate(partita: IPartita) {
        this.setState({
            partita: partita
        })
    }

    render() {
        if(!this.state.partita)
            return null;

        this.state.partita.giocatori.forEach(el => {
            CasellaSingleton.addGiocatore(el.nick, el.casellaCorrente)
        })

        return <div className={style.container}>
            <BarraAzioni partita={this.state.partita} nickname={this.props.nickname}/>
            <Tabellone caselle={this.state.partita.tabellone.caselle} giocatori={this.state.partita.giocatori}/>
        </div>
    }
}