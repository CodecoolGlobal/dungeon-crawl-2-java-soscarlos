package com.codecool.dungeoncrawl.logic.validation;

import com.codecool.dungeoncrawl.data.items.Item;
import com.codecool.dungeoncrawl.data.items.collectibles.Sword;

import java.util.ArrayList;

public class InventoryValidator {
    public boolean hasWeapon(ArrayList<Item> inventory){
        return inventory.stream().anyMatch(item -> item instanceof Sword);
    }
}
