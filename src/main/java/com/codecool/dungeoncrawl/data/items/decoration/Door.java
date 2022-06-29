package com.codecool.dungeoncrawl.data.items.decoration;

import com.codecool.dungeoncrawl.data.cells.Cell;

public class Door extends Decorative{

    public Door(Cell cell) {
        super(cell);
    }

    @Override
    public String getTileName() {
        return "door";
    }
}
