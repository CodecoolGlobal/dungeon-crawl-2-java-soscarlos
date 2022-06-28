package com.codecool.dungeoncrawl.data.actors;

import com.codecool.dungeoncrawl.data.cells.Cell;

public class Troll extends Actor{
    public Troll(Cell cell) {
        super(cell);
        setAttackStrength(20);
        setHealth(20);
    }
    @Override
    public String getTileName() {
        return "troll";
    }
}
