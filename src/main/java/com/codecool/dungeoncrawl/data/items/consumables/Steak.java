package com.codecool.dungeoncrawl.data.items.consumables;

import com.codecool.dungeoncrawl.data.cells.Cell;

public class Steak extends Consumable {
    private final int healthBoost;

    public Steak(Cell cell) {
        super(cell);
        healthBoost = 20;
    }

    @Override
    public int getBoost() {
        return healthBoost;
    }

    @Override
    public String getType() {
        return "health";
    }

    @Override
    public String getTileName() {
        return "steak";
    }
}
