import React, {FunctionComponent, useState} from "react";
import ICasellaProprieta from "../../../interfaces/caselle/ICasellaProprieta";
import "./proprieta.css"
import {Terreno} from "./proprieta/Terreno";
import {Societa} from "./proprieta/Societa";
import {Stazione} from "./proprieta/Stazione";

const translate: {[key: string]: (props: ICasellaProprieta) => IDescrizioneProprieta} = {
    "Terreno": (props) => new Terreno(props),
    "Societa": (props) => new Societa(props),
    "Stazione": props => new Stazione(props)
}

export interface IDescrizioneProprieta {
    getDescrizione(): string,
    render(): JSX.Element
}

const Proprieta: FunctionComponent<ICasellaProprieta> = (proprieta) => {

    const [open, set_open] = useState(false)

    const element = translate[proprieta.type](proprieta);

    return (
        <div className={"property_list_element"}>
            <div className={"property_list_dropdown"}>
                <div>
                    <div>
                        {proprieta.nome}
                    </div>
                    <div className={"subtitle_dashboard"}>
                        {element.getDescrizione()
                            /*translateDescrizione[proprieta.type] !== undefined ?
                            translateDescrizione[proprieta.type](proprieta) :
                            "Valore ipoteca: " + proprieta.ipoteca*/
                        }
                    </div>
                </div>
                <button onClick={() => set_open(!open)}><i className="fas fa-chevron-down"/></button>
            </div>
            <div className={"open_close_property"} aria-selected={open}>
                {element.render()
                    /*translate[proprieta.type] !== undefined ?
                    translate[proprieta.type](proprieta) :
                    <div></div>*/
                }
            </div>
        </div>
    )
}

export default Proprieta