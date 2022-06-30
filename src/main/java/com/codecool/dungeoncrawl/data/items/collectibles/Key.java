package com.codecool.dungeoncrawl.data.items.collectibles;

import com.codecool.dungeoncrawl.data.cells.Cell;

public class Key extends Collectible {

    public Key(Cell cell) {
        super(cell);
    }

    @Override
    public String getType() {
        return "mission";
    }

    @Override
    public String getTileName() {
        return "key";
    }
}
