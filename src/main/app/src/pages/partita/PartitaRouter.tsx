import React from "react";
import Lobby from "./lobby/Lobby";
import Partita from "./Partita";
import {PlayerType} from "../../interfaces/PlayerType";


interface Props {

}

interface State {
    nickname?: string,
    isImprenditore: PlayerType
}

export default class PartitaRouter extends React.Component<Props, State> {

    private idPartita: string;

    constructor(props: Props) {
        super(props);
        const paths = window.location.href.split("/");
        this.idPartita = paths[paths.findIndex(el => el === "partita") + 1];

        this.state = {
            nickname: undefined,
            isImprenditore: PlayerType.STANDARD
        }
    }

    handle_set_giocatore = (nick: string, isImprenditore: PlayerType) => {
        this.setState({nickname: nick, isImprenditore})
    }

    render() {
        if(this.state.nickname === undefined)
            return <Lobby setGiocatore={this.handle_set_giocatore} idPartita={this.idPartita}/>
        else
            return <Partita isImprenditore={this.state.isImprenditore} nickname={this.state.nickname} idPartita={this.idPartita}/>
    }
}