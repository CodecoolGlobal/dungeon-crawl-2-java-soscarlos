package com.codecool.dungeoncrawl.dao;

import com.codecool.dungeoncrawl.model.GameState;
import com.codecool.dungeoncrawl.model.PlayerModel;

import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GameStateDaoJdbc implements GameStateDao {
    private DataSource dataSource;
    private PlayerDao playerDao;

    public GameStateDaoJdbc(DataSource dataSource, PlayerDao playerDao) {
        this.dataSource = dataSource;
        this.playerDao = playerDao;
    }

    @Override
    public void add(GameState state, PlayerModel playerModel) {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "INSERT INTO game_state (current_map, saved_at, player_id) VALUES (?, ?, ?)";
            PreparedStatement statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, state.getCurrentMap());
            statement.setObject(2, state.getSavedAt());
            statement.setInt(3, playerModel.getId());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            resultSet.next();
            state.setId(resultSet.getInt(1));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(GameState state) {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "UPDATE game_state SET current_map = ?, saved_at = ? WHERE id = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, state.getCurrentMap());
            statement.setObject(2, state.getSavedAt());
            statement.setInt(3, state.getId());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            resultSet.next();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public GameState get(int id) {
        try (Connection conn = dataSource.getConnection()) {

            String sql = "SELECT * FROM game_state WHERE id = ?;";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, id);
            ResultSet result = statement.executeQuery();

            if (!result.next()) return null;

            String map = result.getString(2);
            Object timeObject = result.getObject(3);
            LocalDateTime time = convertTimestampToLocalDateTime(timeObject);
            int playerId = result.getInt(4);

            PlayerModel playerModel = playerDao.get(playerId);

            GameState state = new GameState(map, time, playerModel);
            state.setId(id);

            return state;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<GameState> getAll() {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "SELECT * FROM game_state";
            PreparedStatement statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ResultSet resultSet = statement.executeQuery();

            List<GameState> states = new ArrayList<>();

            while (resultSet.next()) {
                int id = resultSet.getInt(1);
                String map = resultSet.getString(2);

                int playerId = resultSet.getInt(4);
                Object timeObject = resultSet.getObject(3);
                LocalDateTime timeAt = convertTimestampToLocalDateTime(timeObject);
                PlayerModel playerModel = playerDao.get(playerId);

                GameState state = new GameState(map, timeAt, playerModel);
                state.setId(id);
                states.add(state);
            }

            return states;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public HashMap<Integer, String> getGameStatesInfo() {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "SELECT gs.id, gs.saved_at, p.player_name " +
                    "FROM game_state gs " +
                    "JOIN player p ON gs.player_id = p.id";
            PreparedStatement statement = conn.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            HashMap<Integer, String> gameStatesInfo = new HashMap<>();

            while (resultSet.next()) {
                int id = resultSet.getInt(1);
                Object timeObject = resultSet.getObject(2);

                LocalDateTime timeAt = convertTimestampToLocalDateTime(timeObject);
                String dateTime = timeAt.toString();
                String savedAt = assembleTimeStamp(dateTime);

                String playerName = resultSet.getString(3);
                String gameState = String.join(", ", playerName, savedAt);

                gameStatesInfo.put(id, gameState);
            }

            return gameStatesInfo;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private String assembleTimeStamp(String dateTime) {
        String date = dateTime.substring(0, dateTime.indexOf("T"));
        String time = dateTime.substring(dateTime.indexOf("T") + 1, dateTime.indexOf("."));

        return String.join(" ", date, time);
    }

    private LocalDateTime convertTimestampToLocalDateTime(Object data) {
        if (data == null) {
            return null;
        }
        final Timestamp timestamp = (Timestamp) data;
        return timestamp.toLocalDateTime();
    }
}
