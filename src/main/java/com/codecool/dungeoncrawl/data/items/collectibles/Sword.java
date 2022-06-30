package com.codecool.dungeoncrawl.data.items.collectibles;

import com.codecool.dungeoncrawl.data.cells.Cell;

public class Sword extends Collectible {

    private final int attackStrength;

    public Sword(Cell cell) {
        super(cell);
        this.attackStrength = 5;
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
        return "sword";
    }
}
