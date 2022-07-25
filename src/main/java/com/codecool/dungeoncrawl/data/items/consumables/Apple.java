package com.codecool.dungeoncrawl.data.items.consumables;

import com.codecool.dungeoncrawl.data.cells.Cell;

public class Apple extends Consumable {
    private final int healthBoost;

    public Apple(Cell cell) {
        super(cell);
        healthBoost = 5;
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
        return "apple";
    }
}
