package com.codecool.dungeoncrawl.dao;

import com.codecool.dungeoncrawl.model.GameState;
import com.codecool.dungeoncrawl.model.PlayerModel;

import java.util.HashMap;
import java.util.List;

public interface GameStateDao {
    void add(GameState state, PlayerModel playerModel);
    void update(GameState state);
    GameState get(int id);
    List<GameState> getAll();

    HashMap<Integer, String> getGameStatesInfo();
}
