package com.codecool.dungeoncrawl.data.items.collectibles;

import com.codecool.dungeoncrawl.data.actors.Player;
import com.codecool.dungeoncrawl.data.cells.Cell;
import com.codecool.dungeoncrawl.data.items.Item;

public abstract class Collectible extends Item {

    public Collectible(Cell cell) {
        super(cell);
    }

    @Override
    public void pickUp(Player player) {
        Item item = player.getCell().getItem();
        if (item != null) {
            player.addToInventory(item);
            player.getCell().setItem(null);
        }
    }
}
