package com.gangoffour2.monopoly;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
public abstract class Player {
    private final String id;
    private final String username;

    @JsonIgnore
    private Game game;
    private Integer position;
    private Integer amount;
    public Player(String id, String username, Integer amount){
        this.id = id;
        this.username = username;
        this.amount = amount;
        position = 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Player player = (Player) o;
        return id.equals(player.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}
