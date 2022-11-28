package com.gangoffour2.monopoly.service.factory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gangoffour2.monopoly.Board;
import com.gangoffour2.monopoly.squares.Square;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.FileCopyUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class BoardFactory {
    private static BoardFactory instance;
    private final Square[] squares;

    private BoardFactory() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        byte[] data = FileCopyUtils.copyToByteArray(new ClassPathResource("squares.json").getInputStream());
        String json = new String(data, StandardCharsets.UTF_8);
        squares = mapper.readValue(json, Square[].class);
    }

    public static synchronized BoardFactory getInstance() throws IOException {
        if (instance == null) {
            instance = new BoardFactory();
        }
        return instance;
    }

    public Board createBoard() {
        return Board.fromSquares(squares);
    }
}
