package com.codecool.dungeoncrawl.data.items.collectibles;

import com.codecool.dungeoncrawl.data.actors.Player;
import com.codecool.dungeoncrawl.data.cells.Cell;
import com.codecool.dungeoncrawl.data.items.Item;

import java.util.Objects;

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

        if (Objects.equals(this.getType(), "weapon")) {
            int currentStrength = player.getAttackStrength();
            player.setAttackStrength(currentStrength + this.getAttackBoost());
        }
    }

    public int getAttackBoost() {return 0;}

    public abstract String getType();
}
