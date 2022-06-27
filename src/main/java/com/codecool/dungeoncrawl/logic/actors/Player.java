package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.data.cells.Cell;

public class Player extends Actor {
    public Player(Cell cell) {
        super(cell);
    }

    public String getTileName() {
        return "player";
    }
}
