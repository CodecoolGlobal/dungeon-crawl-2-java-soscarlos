package com.codecool.dungeoncrawl;

import com.codecool.dungeoncrawl.data.GameMap;
import com.codecool.dungeoncrawl.data.cells.Cell;
import com.codecool.dungeoncrawl.logic.MapLoader;
import com.codecool.dungeoncrawl.logic.MonsterService;
import com.codecool.dungeoncrawl.logic.PlayerService;
import com.codecool.dungeoncrawl.logic.validation.ActorMovementValidator;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Main extends Application {
    GameMap map = MapLoader.loadMap();
    MonsterService monsterService = new MonsterService();

    PlayerService playerService = new PlayerService();
    Canvas canvas = new Canvas(
            map.getWidth() * Tiles.TILE_WIDTH,
            map.getHeight() * Tiles.TILE_WIDTH);
    GraphicsContext context = canvas.getGraphicsContext2D();
    GridPane ui = new GridPane();
    Label healthLabel = new Label();
    Label inventoryLabel = new Label();

    Label strengthLabel = new Label();
    Button pickUpItem = new Button("Pick up!");

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        ui.setPrefWidth(200);
        ui.setPadding(new Insets(10));
        pickUpItem.setFocusTraversable(false);

        addLabels();
        ui.add(pickUpItem, 0, 3);
        hidePickUpButton();
        loadLabels();

        pickUpItem.setOnAction(actionEvent -> {
            if (map.getPlayer().getCell().getItem() != null) {
                map.getPlayer().getCell().getItem().pickUp(map.getPlayer());
            }
            hidePickUpButton();
            loadLabels();
        });

        BorderPane borderPane = new BorderPane();

        borderPane.setCenter(canvas);
        borderPane.setRight(ui);

        Scene scene = new Scene(borderPane);
        primaryStage.setScene(scene);
        refresh();
        scene.setOnKeyPressed(this::onKeyPressed);

        primaryStage.setTitle("Dungeon Crawl");
        primaryStage.show();
    }

    private void addLabels() {
        ui.add(new Label("Health: "), 0, 0);
        ui.add(healthLabel, 1, 0);
        ui.add(new Label("Strength: "), 0, 1);
        ui.add(strengthLabel, 1, 1);
        ui.add(new Label("\nInventory: "), 0, 2);
        ui.add(inventoryLabel, 0, 3);
    }

    public void showPickUpButton() {
        pickUpItem.setVisible(true);
    }

    public void hidePickUpButton() {
        pickUpItem.setVisible(false);
    }

    public void loadLabels() {
        healthLabel.setText("" + map.getPlayer().getHealth());
        inventoryLabel.setText("" + map.getPlayer().inventoryToString());
    }

    private void onKeyPressed(KeyEvent keyEvent) {
        ActorMovementValidator validate = new ActorMovementValidator();
        switch (keyEvent.getCode()) {
            case W:
            case UP:
                map.getPlayer().move(0, -1);
                monsterService.moveMonsters(map.getMonsters());
                playerService.attackMonster(map.getMonsters(), map.getPlayer(), 0, -1);
                monsterService.attackPlayer(map.getMonsters(), map.getPlayer());
                map.removeMonster();
                refresh();
                break;
            case S:
            case DOWN:
                map.getPlayer().move(0, 1);
                monsterService.moveMonsters(map.getMonsters());
                playerService.attackMonster(map.getMonsters(), map.getPlayer(), 0, 1);
                monsterService.attackPlayer(map.getMonsters(), map.getPlayer());
                map.removeMonster();
                refresh();
                break;
            case A:
            case LEFT:
                map.getPlayer().move(-1, 0);
                monsterService.moveMonsters(map.getMonsters());
                playerService.attackMonster(map.getMonsters(), map.getPlayer(), -1, 0);
                monsterService.attackPlayer(map.getMonsters(), map.getPlayer());
                map.removeMonster();
                refresh();
                break;
            case D:
            case RIGHT:
                map.getPlayer().move(1, 0);
                monsterService.moveMonsters(map.getMonsters());
                playerService.attackMonster(map.getMonsters(), map.getPlayer(), 1, 0);
                monsterService.attackPlayer(map.getMonsters(), map.getPlayer());
                map.removeMonster();
                refresh();
                break;
        }

        if (validate.checkPlayerOnItem(map.getPlayer())) {
            showPickUpButton();
        } else hidePickUpButton();
    }

    private void refresh() {
        context.setFill(Color.BLACK);
        context.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
        for (int x = 0; x < map.getWidth(); x++) {
            for (int y = 0; y < map.getHeight(); y++) {
                Cell cell = map.getCell(x, y);
                if (cell.getActor() != null) {
                    Tiles.drawTile(context, cell.getActor(), x, y);
                } else if (cell.getItem() != null) {
                    Tiles.drawTile(context, cell.getItem(), x, y);
                } else {
                    Tiles.drawTile(context, cell, x, y);
                }
            }
        }
        healthLabel.setText("" + map.getPlayer().getHealth());
        strengthLabel.setText("" + map.getPlayer().getAttackStrength());
        // make separate method for this,
        // playerStats refresh method,
        // map refresh own method
    }
}
