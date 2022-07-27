package com.codecool.dungeoncrawl.logic;

import com.codecool.dungeoncrawl.dao.PlayerDao;

public class PlayerManager implements EntityManager {

    private PlayerDao playerDao;

    public PlayerManager(PlayerDao playerDao) {
        this.playerDao = playerDao;
    }

    @Override
    public void list() {

    }

    @Override
    public void add() {

    }

    @Override
    public void edit() {

    }
}
