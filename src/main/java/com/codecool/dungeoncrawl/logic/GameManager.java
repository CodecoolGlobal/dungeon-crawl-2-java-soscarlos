package com.codecool.dungeoncrawl.logic;

import com.codecool.dungeoncrawl.model.GameState;

import java.util.ArrayList;
import java.util.List;

public class GameManager {

    List<GameState> gameStates;
    GameState gameState;

    public GameManager() {
        this.gameStates = new ArrayList<>();
    }

    public void loadSavedGame() {
        // TODO load an existing saved game state
    }
}