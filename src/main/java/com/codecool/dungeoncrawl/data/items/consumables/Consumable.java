package com.codecool.dungeoncrawl.data.items.consumables;

import com.codecool.dungeoncrawl.data.actors.Player;
import com.codecool.dungeoncrawl.data.cells.Cell;
import com.codecool.dungeoncrawl.data.items.Item;

public abstract class Consumable extends Item {

    @Override
    public void pickUp(Player player) {
        //TODO: write pickUp method here
    }
    public Consumable(Cell cell) {
        super(cell);
    }
}
