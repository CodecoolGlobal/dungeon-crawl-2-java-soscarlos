package com.codecool.dungeoncrawl.logic;

import com.codecool.dungeoncrawl.data.items.Item;
import com.codecool.dungeoncrawl.data.items.collectibles.Bazooka;
import com.codecool.dungeoncrawl.data.items.collectibles.Key;
import com.codecool.dungeoncrawl.data.items.collectibles.Sword;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class InventoryService {
    public String convertInventoryToString(ArrayList<Item> dbInventory) {
        return dbInventory.stream().map(Item::getTileName)
                .collect(Collectors.joining(", "));
    }
    public ArrayList<Item> convertStringToInventory(String dbInventory) {
        List<String> inventoryAsString = new ArrayList<>(Arrays.asList(dbInventory.split(", ")));
        ArrayList<Item> inventory = new ArrayList<>();
        for (String item : inventoryAsString) {
            switch (item) {
                case "sword":
                    inventory.add(new Sword());
                    break;
                case "key":
                    inventory.add(new Key());
                    break;
                case "bazooka":
                    inventory.add(new Bazooka());
                    break;
            }
        }
        return inventory;
    }
}
