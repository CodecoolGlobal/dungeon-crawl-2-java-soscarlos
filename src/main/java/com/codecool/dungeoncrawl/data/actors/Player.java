package com.codecool.dungeoncrawl.data.actors;

import com.codecool.dungeoncrawl.data.cells.Cell;
import com.codecool.dungeoncrawl.data.items.Item;
import com.codecool.dungeoncrawl.data.items.collectibles.Key;

import java.util.ArrayList;

public class Player extends Actor {
    private ArrayList<Item> inventory;

    public Player(Cell cell) {
        super(cell);
        initialPlayerStats();
    }

    public void initialPlayerStats() {
        // TODO: initiate health, attack, armor here as well
        this.inventory = new ArrayList<>();
    }

    public String getTileName() {
        return "player";
    }

    public ArrayList<Item> getInventory() {
        return inventory;
    }

    public void addToInventory(Item item) {
        inventory.add(item);
    }

    @Override
    public boolean hasKey() {
        boolean hasKey = false;
        for (Item item : inventory) {
            if (item instanceof Key) {
                hasKey = true;
            }
        }
        return hasKey;
    }

    public String inventoryToString(){
        StringBuilder itemNames = new StringBuilder();
        for (Item item : inventory) {
            String name = item.getTileName().toUpperCase();
            itemNames.append("o " + name + "\n");
        }
        return itemNames.toString();
    }
}
