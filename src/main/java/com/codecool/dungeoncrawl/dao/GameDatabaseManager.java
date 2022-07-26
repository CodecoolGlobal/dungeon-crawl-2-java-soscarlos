package com.codecool.dungeoncrawl.dao;

import com.codecool.dungeoncrawl.dao.PlayerDao;
import com.codecool.dungeoncrawl.dao.PlayerDaoJdbc;
import com.codecool.dungeoncrawl.data.actors.Player;
import com.codecool.dungeoncrawl.model.GameState;
import com.codecool.dungeoncrawl.model.PlayerModel;
import org.postgresql.ds.PGSimpleDataSource;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class GameDatabaseManager {
    private PlayerDao playerDao;

    private PlayerModel model;
    private GameStateDao gameStateDao;
    private List<GameState> gameStates;



    public void setup() throws SQLException {
        DataSource dataSource = connect();
        playerDao = new PlayerDaoJdbc(dataSource);
        gameStateDao = new GameStateDaoJdbc(dataSource, playerDao);
        gameStates = new ArrayList<>();

    }

    public void savePlayer(Player player) {
        model = new PlayerModel(player);
        playerDao.add(model);
    }

    public PlayerModel getModel() {
        return model;
    }

    public void saveGame(String currentMap, LocalDateTime savedAt, PlayerModel player) {
        if (!gameStates.isEmpty()) {
            for (GameState state : gameStates) {
                if (state.getPlayer().getPlayerName().equals(player.getPlayerName())) {
                    state.setSavedAt(savedAt);
                    state.setCurrentMap(currentMap);
//                TODO add additional maps: state.addDiscoveredMap(currentMap);

                } else saveNewGameState(currentMap, savedAt, player);
            }
        } else saveNewGameState(currentMap, savedAt, player);
    }

    public void saveNewGameState(String currentMap, LocalDateTime savedAt, PlayerModel player){
        GameState newState = new GameState(currentMap, savedAt, player);
        gameStates.add(newState);
        gameStateDao.add(newState, player);
    }

    private DataSource connect() throws SQLException {

        PGSimpleDataSource dataSource = new PGSimpleDataSource();
        String dbName = System.getenv("PSQL_DB_NAME");
        String user = System.getenv("PSQL_USER_NAME");
        String password = System.getenv("PSQL_PASSWORD");

        dataSource.setDatabaseName(dbName);
        dataSource.setUser(user);
        dataSource.setPassword(password);

        System.out.println("Trying to connect");
        dataSource.getConnection().close();
        System.out.println("Connection ok.");

        return dataSource;
    }
}
