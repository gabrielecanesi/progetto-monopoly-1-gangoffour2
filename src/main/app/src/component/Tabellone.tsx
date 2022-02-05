import React from 'react';
import ICasella from "../interfaces/caselle/ICasella";
import './caselle/caselle.scss';
import {Casella} from "./caselle/Casella";
import IGiocatore from "../interfaces/IGiocatore";
import "./tabellone.css"
import PlayerPosition from "../interfaces/PlayerPosition";
import {ObserverSingleton, PlayerPositionObserver} from "../application/ObserverSingleton";

interface Props{
    caselle: ICasella[],
    giocatori: IGiocatore[]
}

interface State {
    caselle: ICasella[],
    giocatori: IGiocatore[]
}

export default class Tabellone extends React.Component<Props, State> implements PlayerPositionObserver {


    constructor(props: Props) {
        super(props);
        this.state = {
            giocatori: this.props.giocatori,
            caselle: this.props.caselle
        }
    }

    override componentDidMount() {
        ObserverSingleton.addPlayerPositionListener(this);
    }

    override componentWillUnmount() {
        ObserverSingleton.removeListener(this);
    }

    costruisciRiga(inizio: number, fine: number, classe: string, reverse: boolean, rotate = 0){
        let riga : JSX.Element[] = []
        this.state.caselle.slice(inizio, fine).forEach((casella: ICasella) => {
            riga.push(<Casella rotate={rotate} key={casella.id} space={casella} players={this.getSpacePlayers(casella)}/>);
        });
        if (reverse){
            riga = riga.reverse();
        }
        return <div className={classe}>
            {riga}
        </div>;
    }

    onPlayerPosition(position: PlayerPosition) {
        let {caselle, giocatori} = this.state;
        let i: number = 0;
        let found = false;
        while (i < giocatori.length && !found){
            if (giocatori[i].nick === position.player.nick){
                found = true;
                giocatori[i].casellaCorrente = caselle[position.position];
            }
            ++i;
        }
        this.setState({
            giocatori: giocatori,
            caselle: caselle
        });
    }

    getSpacePlayers(space: ICasella){
        let result: string[] = [];
        this.state.giocatori.forEach(player => {
            if (player.casellaCorrente.id == space.id){
                result.push(player.nick)
            }
        });
        return result;
    }


    render() : JSX.Element {

        let jsxCaselle = []
        jsxCaselle.push(<Casella key={this.state.caselle[0].id} space={this.state.caselle[0]} players={this.getSpacePlayers(this.state.caselle[0])}/>);
        jsxCaselle.push(this.costruisciRiga(1, 10, "row horizontal-row bottom-row", true));
        jsxCaselle.push(<Casella key={this.state.caselle[10].id} space={this.state.caselle[10]} players={this.getSpacePlayers(this.state.caselle[10])}/>);
        jsxCaselle.push(this.costruisciRiga(11, 20, "row vertical-row left-row", true, 270));
        jsxCaselle.push(<Casella key={this.state.caselle[20].id} space={this.state.caselle[20]} players={this.getSpacePlayers(this.state.caselle[20])}/>);
        jsxCaselle.push(this.costruisciRiga(21, 30, "row horizontal-row top-row", false, 180));
        jsxCaselle.push(<Casella key={this.state.caselle[30].id} space={this.state.caselle[30]} players={this.getSpacePlayers(this.state.caselle[30])}/>);
        jsxCaselle.push(this.costruisciRiga(31, 40, "row vertical-row right-row", false, 90));

        console.log(this.state.caselle[0])
        return <div className={"container_tabellone"}>
            <div className="board">
                <div className="center">
                    <div className="community-chest-deck">
                        <h2 className="label">Probabilita</h2>
                        <div className="deck"/>
                    </div>
                    <h1 className="title">GANG OF FOUR 2</h1>
                    <div className="chance-deck">
                        <h2 className="label">Imprevisti</h2>
                        <div className="deck"/>
                    </div>
                </div>
                {jsxCaselle}
            </div>
        </div>
    }
}