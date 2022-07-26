package com.codecool.dungeoncrawl.dao;

import com.codecool.dungeoncrawl.model.PlayerModel;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PlayerDaoJdbc implements PlayerDao {
    private DataSource dataSource;

    public PlayerDaoJdbc(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void add(PlayerModel player) {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "INSERT INTO player (player_name, hp, x, y) VALUES (?, ?, ?, ?)";
            PreparedStatement statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, player.getPlayerName());
            statement.setInt(2, player.getHp());
            statement.setInt(3, player.getX());
            statement.setInt(4, player.getY());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            resultSet.next();
            player.setId(resultSet.getInt(1));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(PlayerModel player) {

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
            int hp = result.getInt(3);
            int x = result.getInt(4);
            int y = result.getInt(5);

            PlayerModel playerModel = new PlayerModel(name, hp, x, y);
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
                int x = result.getInt(4);
                int y = result.getInt(5);
                PlayerModel playerModel = new PlayerModel(name, hp, x, y);
                playerModel.setId(playerId);
                playerModels.add(playerModel);
            }

            return playerModels;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
