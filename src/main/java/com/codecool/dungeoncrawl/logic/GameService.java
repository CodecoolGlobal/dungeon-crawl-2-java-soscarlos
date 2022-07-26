package com.codecool.dungeoncrawl.logic;

import com.codecool.dungeoncrawl.model.GameState;
import com.codecool.dungeoncrawl.model.PlayerModel;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class GameService {

    List<GameState> gameStates;
    GameState gameState;

    public GameService() {
        this.gameStates = new ArrayList<>();
    }

    public void loadSavedGame() {
        // TODO load an existing saved game state
    }

//    public void saveGame(String currentMap, LocalDateTime savedAt, PlayerModel player) {
//        if (!gameStates.isEmpty()) {
//            for (GameState state : gameStates) {
//                if (state.getPlayer().getPlayerName().equals(player.getPlayerName())) {
//                    state.setSavedAt(savedAt);
//                    state.setCurrentMap(currentMap);
////                TODO add additional maps: state.addDiscoveredMap(currentMap);
//
//                } else saveNewGameState(currentMap, savedAt, player);
//            }
//        } else saveNewGameState(currentMap, savedAt, player);
//    }
//
//    public void saveNewGameState(String currentMap, LocalDateTime savedAt, PlayerModel player){
//        gameState = new GameState(currentMap, savedAt, player);
//        gameStates.add(gameState);
//    }
}