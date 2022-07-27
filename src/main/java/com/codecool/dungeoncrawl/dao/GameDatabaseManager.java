package com.codecool.dungeoncrawl.dao;

import com.codecool.dungeoncrawl.data.actors.Player;
import com.codecool.dungeoncrawl.model.GameState;
import com.codecool.dungeoncrawl.model.PlayerModel;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import org.postgresql.ds.PGSimpleDataSource;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

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
        List<PlayerModel> playerModelList = playerDao.getAll();
        for (PlayerModel playerModel: playerModelList){
            if (playerModel.getPlayerName().equals(player.getName())){
                model = playerModel;
                return;
            }
        }
        model = new PlayerModel(player);
        playerDao.add(model);
    }

    public HashMap<Integer, String> getGameStates() {
        return gameStateDao.getGameStatesInfo();
    }

    public PlayerModel getPlayerModel(int playerId) {
        return playerDao.get(playerId);
    }

    public PlayerModel getModel() {
        return model;
    }

    public GameState getGameState(int id) {return gameStateDao.get(id);}

    public void saveGame(String currentMap, LocalDateTime savedAt, PlayerModel player, String inputName) {

        List<GameState> stateList = gameStateDao.getAll();

        if (!stateList.isEmpty()) {
            for (GameState state : stateList) {
                if (state.getPlayer().getPlayerName().equals(inputName) && player.getPlayerName().equals(inputName)) {
                    overWriteDialog(state, currentMap, savedAt);

                    //                TODO add additional maps: state.addDiscoveredMap(currentMap);

                } else saveNewGameState(currentMap, savedAt, player);
            }
        } else saveNewGameState(currentMap, savedAt, player);
    }

    public void saveNewGameState(String currentMap, LocalDateTime savedAt, PlayerModel player) {
        GameState newState = new GameState(currentMap, savedAt, player);
        gameStates.add(newState);
        gameStateDao.add(newState, player);
    }

    private void overWriteDialog(GameState state, String currentMap, LocalDateTime saveAt) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText("Overwrite saved game");
        alert.setContentText("Would you like to overwrite the already existing state?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            state.setSavedAt(saveAt);
            state.setCurrentMap(currentMap);
            gameStateDao.update(state);
        }
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
