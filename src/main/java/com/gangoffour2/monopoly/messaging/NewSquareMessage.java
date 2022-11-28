package com.gangoffour2.monopoly.messaging;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class NewSquareMessage extends Message {
    private String username;
    private Integer squareId;
}
