package com.codecool.dungeoncrawl.data;

import com.codecool.dungeoncrawl.data.actors.Actor;
import com.codecool.dungeoncrawl.data.actors.Player;
import com.codecool.dungeoncrawl.data.cells.Cell;
import com.codecool.dungeoncrawl.data.cells.CellType;

import java.util.ArrayList;
import java.util.List;

public class GameMap {
    private final int width;
    private final int height;
    private final Cell[][] cells;

    private Player player;

    private final List<Actor> monsters;

    public GameMap(int width, int height, CellType defaultCellType) {
        this.width = width;
        this.height = height;
        cells = new Cell[width][height];
        monsters = new ArrayList<>();
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                cells[x][y] = new Cell(this, x, y, defaultCellType);
            }
        }
    }

    public Cell getCell(int x, int y) {
        return cells[x][y];
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public List<Actor> getMonsters() {
        return monsters;
    }

    public void addMonster(Actor monster) {
        monsters.add(monster);
    }

    public void removeMonster() {
        monsters.removeIf(monster -> monster.getHealth() <= 0);
    }

    @Override
    public String toString() {
        StringBuilder stringMap = new StringBuilder(String.format("%d %d\n", width, height));
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                Cell cell = cells[i][j];
                if(cell.getActor() != null) {
                    switch (cell.getActor().getTileName()) {
                        case "player": stringMap.append("@");
                            break;
                        case "skeleton": stringMap.append("s");
                            break;
                        case "demon": stringMap.append("e");
                            break;
                        case "troll": stringMap.append("t");
                            break;
                    }
                } else if (cell.getItem() != null) {
                    switch(cell.getItem().getTileName()) {
                        case "bazooka": stringMap.append("b");
                            break;
                        case "key": stringMap.append("k");
                            break;
                        case "sword": stringMap.append("w");
                            break;
                        case "apple": stringMap.append("A");
                            break;
                        case "steak": stringMap.append("F");
                            break;
                    }
                } else {
                    switch(cell.getTileName()) {
                        case "empty": stringMap.append(" ");
                            break;
                        case "wall": stringMap.append("#");
                            break;
                        case "floor": stringMap.append(".");
                            break;
                        case "closed door": stringMap.append("d");
                            break;
                        case "tree": stringMap.append("T");
                            break;
                        case "bookshelf": stringMap.append("B");
                            break;
                        case "stairs down": stringMap.append("D");
                            break;
                        case "water": stringMap.append("W");
                            break;
                        case "torch": stringMap.append("x");
                            break;
                        case "stairs": stringMap.append("S");
                            break;
                    }
                }
            }
            stringMap.append("\n");
        }
        stringMap.append("\n");
        return stringMap.toString();
    }
}
