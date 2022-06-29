package com.codecool.dungeoncrawl.data.actors;

import com.codecool.dungeoncrawl.data.cells.Cell;

public class Demon extends Actor {
    public Demon(Cell cell) {
        super(cell);
        setHealth(15);
        setAttackStrength(15);
    }
    @Override
    public String getTileName() {
        return "demon";
    }
}
