package com.gangoffour2.monopoly;

import lombok.Data;

@Data
public class Config {
    private final Integer timeoutMilliseconds = 15000;
    private final Integer players = 4;
}
