package com.codecool.dungeoncrawl.data.actors;

import com.codecool.dungeoncrawl.data.cells.Cell;

public class Skeleton extends Actor {
    public Skeleton(Cell cell) {
        super(cell);
        setAttackStrength(2);
    }

    @Override
    public String getTileName() {
        return "skeleton";
    }
}
