import React from "react";
import StompController from "../../../application/stompController";
import {ObserverCarta, ObserverSingleton} from "../../../application/ObserverSingleton";
import {ICarta} from "../../../interfaces/ICarta";
import IPartita from "../../../interfaces/IPartita";
import PopupRouterAzioni from "./PopupRouterAzioni";
import PopupCarta from "./PopupCarta";

interface Props {
    partita: IPartita,
    nickname: string
}

interface State {
    carta: ICarta | undefined
}

export default class PopupRouter extends React.Component<Props, State> implements ObserverCarta {

    constructor(props: Props) {
        super(props);
        this.state = {
            carta: undefined
        }
    }

    onCardExtraction(carta: ICarta): void {
        this.setState({carta: carta})
        setTimeout(() => {
            this.setState({carta: undefined})
        }, 5000);
    }

    componentDidMount() {
        ObserverSingleton.addListenerCarta(this)
        StompController.subscribeCarte();
    }

    componentWillUnmount() {
        ObserverSingleton.removeListener(this);
    }

    render() {
        return <div>
            <PopupCarta carta={this.state.carta}/>
            <PopupRouterAzioni partita={this.props.partita} nickname={this.props.nickname}/>
        </div>
    }
}