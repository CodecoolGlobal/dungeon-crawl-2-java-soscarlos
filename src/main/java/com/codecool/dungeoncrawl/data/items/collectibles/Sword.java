package com.codecool.dungeoncrawl.data.items.collectibles;

import com.codecool.dungeoncrawl.data.cells.Cell;

public class Sword extends Collectible{

    public Sword(Cell cell) {
        super(cell);
    }

    @Override
    public String getTileName() {return "sword";}
}
