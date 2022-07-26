package com.codecool.dungeoncrawl;

import com.codecool.dungeoncrawl.dao.GameDatabaseManager;
import com.codecool.dungeoncrawl.data.Drawable;
import com.codecool.dungeoncrawl.data.GameMap;
import com.codecool.dungeoncrawl.data.Maps;
import com.codecool.dungeoncrawl.data.actors.Player;
import com.codecool.dungeoncrawl.data.cells.Cell;
import com.codecool.dungeoncrawl.data.items.Item;
import com.codecool.dungeoncrawl.logic.GameService;
import com.codecool.dungeoncrawl.logic.MapLoader;
import com.codecool.dungeoncrawl.logic.PlayService;
import com.codecool.dungeoncrawl.logic.Tiles;
import com.codecool.dungeoncrawl.logic.actors.MonsterService;
import com.codecool.dungeoncrawl.logic.actors.PlayerService;
import com.codecool.dungeoncrawl.logic.validation.ActorMovementValidator;
import com.codecool.dungeoncrawl.model.PlayerModel;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;

public class Main extends Application {
    String currentMap = Maps.mapOne;
    GameMap map = MapLoader.loadMap(currentMap);
    Canvas canvas = new Canvas(
            map.getWidth() * Tiles.TILE_WIDTH,
            map.getHeight() * Tiles.TILE_WIDTH);
    GraphicsContext context = canvas.getGraphicsContext2D();
    MonsterService monsterService = new MonsterService();
    PlayerService playerService = new PlayerService();
    PlayService playService = new PlayService();
    ActorMovementValidator validate = new ActorMovementValidator();
    GameDatabaseManager dbManager;
    GameService game;
    GridPane ui = new GridPane();
    Label healthLabel = new Label();
    Label inventoryLabel = new Label();
    Label strengthLabel = new Label();
    Label gameOver = new Label();
    Button pickUpItem = new Button("Pick up!");

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        setupDbManager();

        ui.setPrefWidth(200);
        ui.setPadding(new Insets(10));
        pickUpItem.setFocusTraversable(false);

        addLabels();
        ui.add(pickUpItem, 0, 4);
        hidePickUpButton();
        loadLabels();

        pickUpItem.setOnAction(actionEvent -> {
            if (map.getPlayer().getCell().getItem() != null) {
                map.getPlayer().getCell().getItem().pickUp(map.getPlayer());
            }
            hidePickUpButton();
            loadLabels();
        });

        startMenu(map.getPlayer(), dbManager);

        BorderPane borderPane = new BorderPane();

        borderPane.setCenter(canvas);
        borderPane.setRight(ui);

        Scene scene = new Scene(borderPane);
        primaryStage.setScene(scene);
        refresh();

        scene.setOnKeyReleased(this::onKeyReleased);

        scene.setOnKeyPressed(this::onKeyPressed);

        primaryStage.setTitle("Dungeon Crawl");
        primaryStage.show();
    }

    private void onKeyReleased(KeyEvent keyEvent) {
        KeyCombination exitCombinationMac = new KeyCodeCombination(KeyCode.W, KeyCombination.SHORTCUT_DOWN);
        KeyCombination exitCombinationPc = new KeyCodeCombination(KeyCode.X, KeyCombination.CONTROL_DOWN);

        KeyCombination saveCombinationMac = new KeyCodeCombination(KeyCode.S, KeyCombination.SHORTCUT_DOWN);
        KeyCombination saveCombinationPc = new KeyCodeCombination(KeyCode.S, KeyCombination.CONTROL_DOWN);

        if (exitCombinationMac.match(keyEvent)
                || exitCombinationPc.match(keyEvent)
                || keyEvent.getCode() == KeyCode.ESCAPE) {
            exit();
        } else if (saveCombinationMac.match(keyEvent)
                || saveCombinationPc.match(keyEvent)) {
            LocalDateTime time = LocalDateTime.now();
            saveDialog(map.toString(), time, dbManager.getModel());
        }
    }

    private void addLabels() {
        ui.add(healthLabel, 0, 0);
        ui.add(strengthLabel, 0, 1);
        ui.add(inventoryLabel, 0, 3);
        ui.add(gameOver, 0, 10);
    }

    public void showPickUpButton() {
        pickUpItem.setVisible(true);
    }

    public void hidePickUpButton() {
        pickUpItem.setVisible(false);
    }

    public void loadLabels() {
        if (map.getPlayer().isDead()) {
            gameOver.setText("YOU ARE DEAD!");
            healthLabel.setText("");
            strengthLabel.setText("");
            inventoryLabel.setText("");
        } else {
            healthLabel.setText("Health: " + map.getPlayer().getHealth());
            strengthLabel.setText("\nStrength: " + map.getPlayer().getAttackStrength());
            inventoryLabel.setText("\nInventory: \n" + map.getPlayer().inventoryToString());
        }
    }

    private void onKeyPressed(KeyEvent keyEvent) {
        int dx = 0;
        int dy = 0;

        if (map.getPlayer().isDead()) {
            loadLabels();
            return;
        }

        switch (keyEvent.getCode()) {
            case W:
            case UP:
                dy = -1;
                break;
            case S:
            case DOWN:
                dy = 1;
                break;
            case A:
            case LEFT:
                dx = -1;
                break;
            case D:
            case RIGHT:
                dx = 1;
                break;
        }

        playService.play(map, monsterService, playerService, dx, dy);
        refresh();
        togglePickUpButton();

        if (map.getPlayer().getLevel() == 2) {
            currentMap = Maps.mapTwo;
            ArrayList<Item> inventory = map.getPlayer().getInventory();
            loadNewMap(currentMap, inventory);
        }
    }

    private void loadNewMap(String newMap, ArrayList<Item> inventory) {
        int health = map.getPlayer().getHealth();
        int strength = map.getPlayer().getAttackStrength();
        map = MapLoader.loadMap(newMap);
        map.getPlayer().setHealth(health);
        map.getPlayer().setAttackStrength(strength);
        map.getPlayer().setInventory(inventory);
        refresh();
        loadLabels();
    }

    private void refresh() {
        context.setFill(Color.BLACK);
        context.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
        for (int x = 0; x < map.getWidth(); x++) {
            for (int y = 0; y < map.getHeight(); y++) {
                Cell cell = map.getCell(x, y);
                if (cell.hasDrawableElement()) {
                    Drawable drawable = cell.getDrawableElement(x, y);
                    Tiles.drawTile(context, drawable, x, y);

                } else {
                    Tiles.drawTile(context, cell, x, y);
                }
            }
        }
        healthLabel.setText("Health: " + map.getPlayer().getHealth());
    }

    private void setupDbManager() {
        dbManager = new GameDatabaseManager();
        try {
            dbManager.setup();
        } catch (SQLException ex) {
            System.out.println("Cannot connect to database.");
        }
    }

    private void exit() {
        try {
            stop();
        } catch (Exception e) {
            System.exit(1);
        }
        System.exit(0);
        healthLabel.setText("Health: " + map.getPlayer().getHealth());
    }

    private void togglePickUpButton() {
        if (validate.checkPlayerOnItem(map.getPlayer())) {
            showPickUpButton();
        } else hidePickUpButton();
    }

    private void saveDialog(String currentMap, LocalDateTime saveAt, PlayerModel player) {
        TextInputDialog dialog = new TextInputDialog("saved stage");
        dialog.setHeaderText("Please enter a name to save your progress");
        dialog.setTitle("Save Dialog");
        dialog.setContentText("Name: ");

        Optional<String> result = dialog.showAndWait();

        if (result.isPresent()) {
            String input = result.get();
            System.out.println(input);
            dbManager.saveGame(currentMap, saveAt, player);
        }
    }
    private void startMenu(Player player, GameDatabaseManager gDbManager){
        TextInputDialog dialog = new TextInputDialog("Enter name");
        dialog.setHeaderText("Please enter a name for the hero");
        dialog.setTitle("Main menu");
        dialog.setContentText("Name: ");

        Optional<String> result = dialog.showAndWait();

        if (result.isPresent()) {
            String inputName = result.get();
            player.setName(inputName);
            gDbManager.savePlayer(player);
        }
    }
}
