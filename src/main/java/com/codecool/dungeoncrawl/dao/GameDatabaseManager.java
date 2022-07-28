package com.codecool.dungeoncrawl.dao;

import com.codecool.dungeoncrawl.data.actors.Player;
import com.codecool.dungeoncrawl.data.items.Item;
import com.codecool.dungeoncrawl.logic.InventoryService;
import com.codecool.dungeoncrawl.model.GameState;
import com.codecool.dungeoncrawl.model.PlayerModel;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.postgresql.ds.PGSimpleDataSource;

import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class GameDatabaseManager {
    private PlayerDao playerDao;

    private InventoryService inventoryService;

    private PlayerModel model;
    private GameStateDao gameStateDao;
    private List<GameState> gameStates;

    public void setup() throws SQLException {
        DataSource dataSource = connect();
        inventoryService = new InventoryService();
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

    public void saveGame(String currentMap, LocalDateTime savedAt, PlayerModel playerModel, String inputName, Player player) {

        List<GameState> stateList = gameStateDao.getAll();

        if (!stateList.isEmpty()) {
            for (GameState state : stateList) {
                if (state.getPlayer().getPlayerName().equals(inputName) && playerModel.getPlayerName().equals(inputName)) {
                    overWriteDialog(state, currentMap, savedAt, player);

                    //                TODO add additional maps: state.addDiscoveredMap(currentMap);

                } else saveNewGameState(currentMap, savedAt, playerModel, player);
            }
        } else saveNewGameState(currentMap, savedAt, playerModel, player);
    }

    public void saveNewGameState(String currentMap, LocalDateTime savedAt, PlayerModel playerModel, Player player) {
        GameState newState = new GameState(currentMap, savedAt, playerModel);
        ArrayList<Item> inventoryList = player.getInventory();
        String inventory = inventoryService.convertInventoryToString(inventoryList);
        playerDao.update(player, playerModel.getId(), inventory);
        gameStates.add(newState);
        gameStateDao.add(newState, playerModel);
    }

    private void overWriteDialog(GameState state, String currentMap, LocalDateTime saveAt, Player player) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText("Overwrite saved game");
        alert.setContentText("Would you like to overwrite the already existing state?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            state.setSavedAt(saveAt);
            state.setCurrentMap(currentMap);
            gameStateDao.update(state);
            PlayerModel model = state.getPlayer();
            int playerId = model.getId();
            ArrayList<Item> inventoryList = player.getInventory();
            String inventory = inventoryService.convertInventoryToString(inventoryList);
            playerDao.update(player, playerId, inventory);
        }
    }

    public void setAttributes(Player player, JSONObject jsonObject){

        String name = (String) jsonObject.get("player_name");
        int hp = (int) jsonObject.get("hp");
        int strength = (int) jsonObject.get("strength");
        String inventoryString = (String) jsonObject.get("inventory");
        int x = (int) jsonObject.get("x");
        int y = (int) jsonObject.get("y");

        String currentMap = (String) jsonObject.get("current_map");
        LocalDateTime time = LocalDateTime.now();

        player.setName(name);
        player.setHealth(hp);
        player.setAttackStrength(strength);
        ArrayList<Item> inventoryList = inventoryService.convertStringToInventory(inventoryString);
        player.setInventory(inventoryList);
        savePlayer(player);

        saveGame(currentMap, time, model, name, player);
    }


    public DataSource connect() throws SQLException {

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

    public JSONArray convertTableToJSON(int id) {
        try (Connection conn = connect().getConnection()) {
            String sql = "SELECT * FROM player JOIN game_state gs on player.id = gs.player_id WHERE player.id = ?;";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();
            List<String> colNames = IntStream.range(0, columnCount)
                    .mapToObj(i -> {
                        try {
                            return metaData.getColumnName(i + 1);
                        } catch (SQLException e) {
                            e.printStackTrace();
                            return "?";
                        }
                    })
                    .collect(Collectors.toList());

            JSONArray result = new JSONArray();
            while (resultSet.next()) {
                JSONObject row = new JSONObject();
                colNames.forEach(colName -> {
                    try {
                        row.put(colName, resultSet.getObject(colName));
                    } catch (JSONException | SQLException e) {
                        e.printStackTrace();
                    }
                });
                result.put(row);
            }
            return result;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
