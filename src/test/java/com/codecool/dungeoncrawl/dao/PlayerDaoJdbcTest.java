package com.codecool.dungeoncrawl.dao;

import com.codecool.dungeoncrawl.data.actors.Player;
import com.codecool.dungeoncrawl.model.PlayerModel;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.sql.DataSource;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

public class PlayerDaoJdbcTest {
    private PlayerDaoJdbc playerDaoJdbc;
    private PlayerModel playerModel;

    @BeforeEach
    void init() throws SQLException {
        GameDatabaseManager gameDatabaseManager = new GameDatabaseManager();
        DataSource dataSource = gameDatabaseManager.connect();
        playerDaoJdbc = new PlayerDaoJdbc(dataSource);
        playerModel = new PlayerModel("name", 10, 10, "sword", 5, 15);
    }

    @Test
    void addPlayerModel() {
        playerDaoJdbc.add(playerModel);
        PlayerModel expected = playerModel;
        int id = playerModel.getId();
        PlayerModel result = playerDaoJdbc.get(id);

        assertEquals(expected.getPlayerName(), result.getPlayerName());
        assertEquals(expected.getId(), result.getId());
        assertEquals(expected.getHp(), result.getHp());
        assertEquals(expected.getStrength(), result.getStrength());
        assertEquals(expected.getX(), result.getX());
        assertEquals(expected.getY(), result.getY());
    }

    @Test
    void getPlayer() {
        playerDaoJdbc.add(playerModel);
        PlayerModel expected = playerModel;
        int id = playerModel.getId();
        PlayerModel result = playerDaoJdbc.get(id);

        PlayerModel newPlayerModel = new PlayerModel("playerName", 5, 5, "sword, key", 15, 10);
        playerDaoJdbc.add(newPlayerModel);
        int newId = newPlayerModel.getId();
        PlayerModel newResult = playerDaoJdbc.get(newId);

        assertEquals(expected.getPlayerName(), result.getPlayerName());
        assertEquals(newPlayerModel.getPlayerName(), newResult.getPlayerName());
        assertNotEquals(expected.getPlayerName(), newResult.getPlayerName());
    }

    @Test
    void getPlayerReturnsNullWhenIdDoesNotExists() {
        assertNull(playerDaoJdbc.get(105));
        assertNull(playerDaoJdbc.get(-1));
    }
}
