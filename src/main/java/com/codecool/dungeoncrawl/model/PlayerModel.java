package com.codecool.dungeoncrawl.model;

import com.codecool.dungeoncrawl.data.actors.Player;
import com.codecool.dungeoncrawl.data.items.Item;
import com.codecool.dungeoncrawl.data.items.collectibles.Bazooka;
import com.codecool.dungeoncrawl.data.items.collectibles.Key;
import com.codecool.dungeoncrawl.data.items.collectibles.Sword;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class PlayerModel extends BaseModel {
    private String playerName;
    private int hp;
    private int strength;
    private int x;
    private int y;
    private ArrayList<Item> inventory;


    public PlayerModel(String playerName, int x, int y) {
        this.playerName = playerName;
        this.x = x;
        this.y = y;
    }

    public PlayerModel(Player player) {
        this.playerName = player.getName();
        this.hp = player.getHealth();
        this.strength = player.getAttackStrength();
        this.inventory = player.getInventory();
        this.x = player.getX();
        this.y = player.getY();

    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getStrength() {
        return strength;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }

    public ArrayList<Item> getInventory() {
        return inventory;
    }

    public void setInventory(ArrayList<Item> inventory) {
        this.inventory = inventory;
    }

    public String convertInventoryToString() {
        return inventory.stream().map(Item::getTileName)
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
