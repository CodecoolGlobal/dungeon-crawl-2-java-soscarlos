package com.codecool.dungeoncrawl.data.items.collectibles;

import com.codecool.dungeoncrawl.data.actors.Player;
import com.codecool.dungeoncrawl.data.cells.Cell;
import com.codecool.dungeoncrawl.data.items.Item;

public abstract class Collectible extends Item {

    @Override
    public void pickUp(Player player) {
        //TODO: write pickUp method here
    }
    public Collectible(Cell cell) {
        super(cell);
    }
}
