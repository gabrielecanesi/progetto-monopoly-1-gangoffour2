import React from 'react';
import ICasellaPrigione from "../../interfaces/caselle/CasellaPrigione";
import ICasellaVia from "../../interfaces/caselle/ICasellaVia";

interface Props {
    casella:ICasellaVia
}

interface State {
}

export default class Via extends React.Component<Props, State>{
    render(){
        return <div className="space corner go">
            <div className="container">
                <div className="instructions">Ritira 200 al passaggio</div>
                <div className="go-word">VIA!</div>
                {this.props.children}
            </div>
            <div className="arrow fa fa-long-arrow-alt-left"/>
        </div>
    }
}
