import React, {FunctionComponent} from "react";
import style from "./barraAzioni.module.css"
import {Dashboard} from "../../pages/partita/dashboard/Dashboard";
import IPartita from "../../interfaces/IPartita";
import {Classifica} from "../../pages/partita/classifica/Classifica";
import PopupRouter from "../../pages/partita/popup/PopupRouter";
import {NewStateObserver, ObserverSingleton, PlayerActionObserver} from "../../application/ObserverSingleton";
import PlayerAction from "../../interfaces/PlayerAction";
import IStatoPartita from "../../interfaces/stati/partita/IStatoPartita";

interface Props {
    partita: IPartita,
    nickname: string
}

interface State {
    partita: IPartita;
}

export default class BarraAzioni extends React.Component<Props, State> implements PlayerActionObserver, NewStateObserver{

    constructor(props: Props){
        super(props);
        this.state = {
            partita: props.partita
        };
    }

    componentDidMount(){
        ObserverSingleton.addNewStateListener(this);
    }

    componentWillUnmount() {
        ObserverSingleton.removeListener(this);
    }

    onPlayerAction(action: PlayerAction): void {

    }

    onNewState(state: IStatoPartita) {
        let p = this.state.partita;
        p.stato = state;
        this.setState({
            partita: p
        });
    }

    render (){
        const { partita, nickname } = this.props
        return <div className={style.container}>
            <Classifica partita={partita}/>
            <Dashboard partita={partita} nickname={nickname}/>
            <PopupRouter partita = {partita} nickname={nickname}/>
        </div>
    }

}
