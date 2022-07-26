package com.codecool.dungeoncrawl.model;

import com.codecool.dungeoncrawl.data.actors.Player;
import com.codecool.dungeoncrawl.data.items.Item;

import java.util.ArrayList;
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
        this.x = player.getX();
        this.y = player.getY();
        this.inventory = player.getInventory();

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
        return getInventory().stream().map(Object::toString)
                .collect(Collectors.joining(", "));
    }
}
