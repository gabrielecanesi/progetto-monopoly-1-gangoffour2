package com.gangoffour2.monopoly.controller;

import com.gangoffour2.monopoly.services.PlayerFactory;
import lombok.Data;

@Data
public class GiocatoreEntraRequestBody {
    private String nickname;
    private PlayerFactory.PlayerType isImprenditore;
}
