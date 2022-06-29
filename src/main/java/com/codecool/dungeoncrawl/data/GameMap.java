package com.codecool.dungeoncrawl.data;

import com.codecool.dungeoncrawl.data.actors.Actor;
import com.codecool.dungeoncrawl.data.cells.Cell;
import com.codecool.dungeoncrawl.data.cells.CellType;
import com.codecool.dungeoncrawl.data.actors.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class GameMap {
    private int width;
    private int height;
    private Cell[][] cells;

    private Player player;

    private List<Actor> monsters;

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

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return player;
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
    public void addMonster(Actor monster){
        monsters.add(monster);
    }
    public void removeMonster(){
        for (int i = 0; i < monsters.size(); i++) {
            Actor monster = monsters.get(i);
            if (monster.getHealth() <= 0){
                monsters.remove(monster);
            }
        }
    }
}
