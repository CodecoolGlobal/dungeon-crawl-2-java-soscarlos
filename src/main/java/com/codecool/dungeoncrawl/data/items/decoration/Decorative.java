package com.codecool.dungeoncrawl.data.items.decoration;

import com.codecool.dungeoncrawl.data.actors.Player;
import com.codecool.dungeoncrawl.data.cells.Cell;
import com.codecool.dungeoncrawl.data.items.Item;

public abstract class Decorative extends Item {
    public Decorative(Cell cell) {
        super(cell);
    }
}
