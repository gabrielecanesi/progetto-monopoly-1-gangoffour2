package com.gangoffour2.monopoly.squares;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.gangoffour2.monopoly.Game;
import com.gangoffour2.monopoly.Player;
import com.gangoffour2.monopoly.commands.EmptyCommand;
import com.gangoffour2.monopoly.commands.GameCommand;
import com.gangoffour2.monopoly.squares.company.Company;
import com.gangoffour2.monopoly.squares.land.Land;
import com.gangoffour2.monopoly.squares.station.Station;
import lombok.Data;

@Data
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.EXISTING_PROPERTY, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = Land.class, name = "Property"),
        @JsonSubTypes.Type(value = Company.class, name = "Company"),
        @JsonSubTypes.Type(value = Station.class, name = "Station"),
        @JsonSubTypes.Type(value = Start.class, name = "Start"),
        @JsonSubTypes.Type(value = Chest.class, name = "Chest"),
        @JsonSubTypes.Type(value = Chance.class, name = "Chance"),
        @JsonSubTypes.Type(value = Jail.class, name = "Jail"),
        @JsonSubTypes.Type(value = Parking.class, name = "Parking"),
        @JsonSubTypes.Type(value = Tax.class, name = "Tax"),
        @JsonSubTypes.Type(value = GoToJail.class, name = "GoToJail"),
})
public abstract class Square {

    @JsonProperty("type")
    public String type(){
        return getClass().getSimpleName();
    }

    private Integer id;
    private String name;

    @JsonIgnore
    private Game game;

    public abstract GameCommand landTo(Player p);

    public GameCommand intermediateStep(Player player){
        return new EmptyCommand(getGame());
    }
}
