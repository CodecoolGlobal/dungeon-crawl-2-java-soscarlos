package com.codecool.dungeoncrawl;

import com.codecool.dungeoncrawl.data.Drawable;
import com.codecool.dungeoncrawl.data.GameMap;
import com.codecool.dungeoncrawl.data.cells.Cell;
import com.codecool.dungeoncrawl.logic.MapLoader;
import com.codecool.dungeoncrawl.logic.PlayService;
import com.codecool.dungeoncrawl.logic.Tiles;
import com.codecool.dungeoncrawl.logic.actors.MonsterService;
import com.codecool.dungeoncrawl.logic.actors.PlayerService;
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
    String currentMap = "/map.txt";
    GameMap map = MapLoader.loadMap(currentMap);
    MonsterService monsterService = new MonsterService();

    PlayerService playerService = new PlayerService();
    PlayService playService = new PlayService();
    ActorMovementValidator validate = new ActorMovementValidator();
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
        strengthLabel.setText("" + map.getPlayer().getAttackStrength());
        inventoryLabel.setText("" + map.getPlayer().inventoryToString());
    }

    private void onKeyPressed(KeyEvent keyEvent) {
        int dx = 0;
        int dy = 0;
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
    }

    private void refresh() {
        context.setFill(Color.BLACK);
        context.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
        for (int x = 0; x < map.getWidth(); x++) {
            for (int y = 0; y < map.getHeight(); y++) {
                Cell cell = map.getCell(x, y);
                if (cell.hasDrawableElement()){
                    Drawable drawable = cell.getDrawableElement(x, y);
                    Tiles.drawTile(context, drawable, x, y);
                } else {
                    Tiles.drawTile(context, cell, x, y);
                }
            }
        }
        healthLabel.setText("" + map.getPlayer().getHealth());
        // make separate method for this,
        // playerStats refresh method,
        // map refresh own method
    }

    private void togglePickUpButton(){
        if (validate.checkPlayerOnItem(map.getPlayer())) {
            showPickUpButton();
        } else hidePickUpButton();
    }
}
