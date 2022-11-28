import ICasella from "../../interfaces/caselle/ICasella";
import React from "react";
import {Imprevisto} from "./Imprevisto";
import {Terreno} from "./Terreno";
import {Societa} from "./Societa";
import {Stazione} from "./Stazione";
import Prigione from "./Prigione";
import {Probabilita} from "./Probabilita";
import {Tassa} from "./Tassa";
import {VaiInPrigione} from "./VaiInPrigione";
import {Parcheggio} from "./Parcheggio";
import Via from "./Via";
import CasellaSingleton from "./CasellaSingleton";
import translateCarteProprieta from "../../pages/partita/popup/TranslateCarteProprieta";
import {ICasellaImprevisto} from "../../interfaces/caselle/ICasellaImprevisto";
import ICasellaTerreno from "../../interfaces/caselle/ICasellaTererno";
import ICasellaSocieta from "../../interfaces/caselle/ICasellaSocieta";
import ICasellaStazione from "../../interfaces/caselle/ICasellaStazione";
import CasellaPrigione from "../../interfaces/caselle/CasellaPrigione";
import {ICasellaProbabilita} from "../../interfaces/caselle/ICasellaProbabilita";
import {ICasellaTassa} from "../../interfaces/caselle/ICasellaTassa";
import {ICasellaVaiInPrigione} from "../../interfaces/caselle/ICasellaVaiInPrigione";
import ICasellaParcheggio from "../../interfaces/caselle/ICasellaParcheggio";
import ICasellaVia from "../../interfaces/caselle/ICasellaVia";

const translate = {
    "Imprevisto": (props: ICasellaImprevisto, giocatoriJsx: JSX.Element) => <Imprevisto casella={props}> {giocatoriJsx} </Imprevisto>,
    "Terreno": (props: ICasellaTerreno, giocatoriJsx: JSX.Element, ca: () => {}) => <Terreno casella={props} caHover={ca}> {giocatoriJsx} </Terreno>,
    "Societa": (props: ICasellaSocieta, giocatoriJsx: JSX.Element, ca: () => {}) => <Societa caHover={ca} casella={props}>{giocatoriJsx}</Societa>,
    "Stazione": (props: ICasellaStazione, giocatoriJsx: JSX.Element, ca: () => {}) => <Stazione caHover={ca} casella={props}>{giocatoriJsx}</Stazione>,
    "Prigione": (props: CasellaPrigione, giocatoriJsx: JSX.Element) => <Prigione casella={props}>{giocatoriJsx}</Prigione>,
    "Probabilita": (props: ICasellaProbabilita, giocatoriJsx: JSX.Element) => <Probabilita casella={props}>{giocatoriJsx}</Probabilita>,
    "Tassa": (props: ICasellaTassa, giocatoriJsx: JSX.Element) => <Tassa casella={props}>{giocatoriJsx}</Tassa>,
    "VaiInPrigione": (props: ICasellaVaiInPrigione, giocatoriJsx: JSX.Element) => <VaiInPrigione casella={props}>{giocatoriJsx}</VaiInPrigione>,
    "Parcheggio": (props: ICasellaParcheggio, giocatoriJsx: JSX.Element) => <Parcheggio casella={props}>{giocatoriJsx}</Parcheggio>,
    "Via": (props: ICasellaVia, giocatoriJsx: JSX.Element) => <Via casella={props}>{giocatoriJsx}</Via>
}

interface Props {
    space: ICasella,
    players: string[]
    rotate?: number
}

interface State {
    hover: boolean
}

export class Casella extends React.Component<Props, State>{

    constructor(props: Props) {
        super(props);
        this.state = {
            hover: false
        }
    }

    render() {
        const carta_attributo = translateCarteProprieta[this.props.space.type]

        const giocatoriJsx = <>
            <div className={"popup_carta_tabellone"} aria-level={this.props.rotate}>
                <div>
                    {carta_attributo !== undefined && this.state.hover ? carta_attributo(this.props.space) : null}
                </div>
            </div>
            <div className={"overlap_player"}>
                {this.props.players.map(player => {
                        return <div title={player} style={{backgroundColor: CasellaSingleton.giocatoreColore[player]}}/>;
                    })}
            </div>

        </>
        // @ts-ignore
        return translate[this.props.space.type](this.props.space, giocatoriJsx, () => this.setState({hover: !this.state.hover}))
    }

}
