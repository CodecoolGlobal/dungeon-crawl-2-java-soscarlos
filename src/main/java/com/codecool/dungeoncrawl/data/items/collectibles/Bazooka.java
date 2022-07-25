package com.codecool.dungeoncrawl.data.items.collectibles;

import com.codecool.dungeoncrawl.data.cells.Cell;

public class Bazooka extends Collectible{
    private final int attackStrength;

    public Bazooka(Cell cell) {
        super(cell);
        this.attackStrength = 50;
    }

    @Override
    public int getAttackBoost() {
        return attackStrength;
    }

    @Override
    public String getType() {
        return "weapon";
    }

    @Override
    public String getTileName() {
        return "bazooka";
    }
}
