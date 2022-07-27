package com.codecool.dungeoncrawl.data.items;

import com.codecool.dungeoncrawl.data.Drawable;
import com.codecool.dungeoncrawl.data.actors.Player;
import com.codecool.dungeoncrawl.data.cells.Cell;

public abstract class Item implements Drawable {
    private Cell cell;

    public Item(Cell cell) {
        this.cell = cell;
        cell.setItem(this);
    }

    public Item() {
    }

    public void pickUp(Player player) {
    }

    public Cell getCell() {
        return cell;
    }

    public int getX() {
        return cell.getX();
    }

    public int getY() {
        return cell.getY();
    }
}
