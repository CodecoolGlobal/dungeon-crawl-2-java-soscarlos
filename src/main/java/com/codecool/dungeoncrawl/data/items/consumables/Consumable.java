package com.codecool.dungeoncrawl.data.items.consumables;

import com.codecool.dungeoncrawl.data.actors.Player;
import com.codecool.dungeoncrawl.data.cells.Cell;
import com.codecool.dungeoncrawl.data.items.Item;

import java.util.Objects;

public abstract class Consumable extends Item {

    public Consumable(Cell cell) {
        super(cell);
    }

    @Override
    public void pickUp(Player player) {
        Item item = player.getCell().getItem();
        if (item != null) {
            player.getCell().setItem(null);
        }

        if (Objects.equals(this.getType(), "health")) {
            int currentHealth = player.getHealth();
            player.setHealth(currentHealth + this.getBoost());
        }
    }

    public abstract String getType();

    public abstract int getBoost();
}
