package com.codecool.dungeoncrawl.dao;

import com.codecool.dungeoncrawl.model.GameState;
import com.codecool.dungeoncrawl.model.PlayerModel;
import org.json.JSONArray;

import java.util.List;

public interface GameStateDao {
    void add(GameState state, PlayerModel playerModel);
    void update(GameState state);
    GameState get(int id);
    List<GameState> getAll();
    JSONArray convertGameStateTableToJSON();
}
