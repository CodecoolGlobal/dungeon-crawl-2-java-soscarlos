package com.codecool.dungeoncrawl.model;

import com.codecool.dungeoncrawl.data.actors.Player;
import com.codecool.dungeoncrawl.data.items.Item;
import com.codecool.dungeoncrawl.data.items.collectibles.Bazooka;
import com.codecool.dungeoncrawl.data.items.collectibles.Key;
import com.codecool.dungeoncrawl.data.items.collectibles.Sword;
import com.codecool.dungeoncrawl.logic.InventoryService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class PlayerModel extends BaseModel {
    private InventoryService service;
    private String playerName;
    private int hp;
    private int strength;
    private int x;
    private int y;
    private ArrayList<Item> inventory;


    public PlayerModel(String playerName, int hp, int strength, String inventory, int x, int y) {
        service = new InventoryService();
        this.playerName = playerName;
        this.hp = hp;
        this.strength = strength;
        this.inventory = service.convertStringToInventory(inventory);
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

    public int getHp() {
        return hp;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getStrength() {
        return strength;
    }

    public ArrayList<Item> getInventory() {
        return inventory;
    }

}
