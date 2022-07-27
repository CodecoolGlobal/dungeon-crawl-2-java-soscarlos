package com.codecool.dungeoncrawl.dao;

import com.codecool.dungeoncrawl.data.actors.Player;
import com.codecool.dungeoncrawl.model.PlayerModel;
import org.json.*;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class PlayerDaoJdbc implements PlayerDao {
    private DataSource dataSource;

    public PlayerDaoJdbc(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void add(PlayerModel player) {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "INSERT INTO player (player_name, hp, strength, inventory, x, y) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, player.getPlayerName());
            statement.setInt(2, player.getHp());
            statement.setInt(3, player.getStrength());
            statement.setString(4, player.convertInventoryToString(player.getInventory()));
            statement.setInt(5, player.getX());
            statement.setInt(6, player.getY());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            resultSet.next();
            player.setId(resultSet.getInt(1));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(Player player, int playerId, String inventory) {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "UPDATE player SET hp = ?, strength = ?, inventory = ?, x = ?, y = ? WHERE id = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, player.getHealth());
            statement.setInt(2, player.getAttackStrength());
            statement.setString(3, inventory);
            statement.setInt(4, player.getX());
            statement.setInt(5, player.getY());
            statement.setInt(6, playerId);
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            resultSet.next();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public PlayerModel get(int id) {
        try (Connection conn = dataSource.getConnection()) {

            String sql = "SELECT * FROM player WHERE id = ?;";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, id);
            ResultSet result = statement.executeQuery();

            if (!result.next()) return null;

            String name = result.getString(2);
            int hp = result.getInt(3); // TODO: create method getPlayerStats
            int strength = result.getInt(4);
            String inventory = result.getString(5);
            int x = result.getInt(6);
            int y = result.getInt(7);

            PlayerModel playerModel = new PlayerModel(name, hp, strength, inventory, x, y);
            playerModel.setId(id);

            return playerModel;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<PlayerModel> getAll() {
        try (Connection conn = dataSource.getConnection()) {

            String sql = "SELECT * FROM player;";

            PreparedStatement statement = conn.prepareStatement(sql);

            ResultSet result = statement.executeQuery();

            List<PlayerModel> playerModels = new ArrayList<>();

            while (result.next()) {
                int playerId = result.getInt(1);
                String name = result.getString(2);
                int hp = result.getInt(3);
                int strength = result.getInt(4);
                String inventory = result.getString(5);
                int x = result.getInt(6);
                int y = result.getInt(7);
                PlayerModel playerModel = new PlayerModel(name, hp, strength, inventory, x, y);
                playerModel.setId(playerId);
                playerModels.add(playerModel);
            }

            return playerModels;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public JSONArray convertPlayerTableToJSON() {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "SELECT * FROM player;";
            PreparedStatement statement = conn.prepareStatement(sql);
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
