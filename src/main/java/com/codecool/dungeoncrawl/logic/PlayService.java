package com.codecool.dungeoncrawl.logic;

import com.codecool.dungeoncrawl.data.GameMap;
import com.codecool.dungeoncrawl.logic.actors.MonsterService;
import com.codecool.dungeoncrawl.logic.actors.PlayerService;

public class PlayService {

    public void play(GameMap map, MonsterService monster, PlayerService player, int dx, int dy) {
        map.getPlayer().move(dx, dy);
        monster.moveMonsters(map.getMonsters());

        player.attackMonster(map.getMonsters(), map.getPlayer(), dx, dy);
        monster.attackPlayer(map.getMonsters(), map.getPlayer());

        map.removeMonster();
    }
}
