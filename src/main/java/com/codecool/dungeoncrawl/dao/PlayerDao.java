package com.codecool.dungeoncrawl.dao;

import com.codecool.dungeoncrawl.data.actors.Player;
import com.codecool.dungeoncrawl.model.PlayerModel;
import org.json.JSONArray;

import java.util.List;

public interface PlayerDao {
    void add(PlayerModel player);
    void update(Player player, int id, String inventory);
    PlayerModel get(int id);
    List<PlayerModel> getAll();
}