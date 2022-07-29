package com.codecool.dungeoncrawl.dao;

import com.codecool.dungeoncrawl.data.GameMap;
import com.codecool.dungeoncrawl.logic.MapLoader;
import com.codecool.dungeoncrawl.model.GameState;
import com.codecool.dungeoncrawl.model.PlayerModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class GameStateDaoJdbcTest {
    private GameStateDaoJdbc gameStateDaoJdbc;
    private PlayerDao playerDao;
    private PlayerModel playerModel;
    private GameState gameState;
    private GameMap map;
    private LocalDateTime localDateTime;

    @BeforeEach
    void init() throws SQLException {
        GameDatabaseManager gameDatabaseManager = new GameDatabaseManager();
        DataSource dataSource = gameDatabaseManager.connect();
        playerDao = new PlayerDaoJdbc(dataSource);
        gameStateDaoJdbc = new GameStateDaoJdbc(dataSource, playerDao);
        playerModel = new PlayerModel("name", 23, 10, "sword", 4, 14);
        playerDao.add(playerModel);
        map = MapLoader.loadMap("/map.txt");
        localDateTime = LocalDateTime.now();
        gameState = new GameState(map.toString(), localDateTime, playerModel);
    }

    @Test
    void add() {
        gameStateDaoJdbc.add(gameState, playerModel);
        GameState expected = gameState;
        int id = gameState.getId();
        GameState result = gameStateDaoJdbc.get(id);

        assertEquals(expected.getCurrentMap(), result.getCurrentMap());
        assertEquals(expected.getId(), result.getId());
    }

    @Test
    void getGameStateReturnsNullIfNotExists() {
        assertNull(gameStateDaoJdbc.get(1000));
        assertNull(gameStateDaoJdbc.get(-1));
    }
}