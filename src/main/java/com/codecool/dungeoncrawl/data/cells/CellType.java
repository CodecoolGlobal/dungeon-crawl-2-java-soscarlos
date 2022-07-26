package com.codecool.dungeoncrawl.data.cells;

public enum CellType {
    EMPTY("empty"),
    FLOOR("floor"),
    WALL("wall"),
    CLOSED_DOOR("closed door"),
    OPEN_DOOR("open door"),
    TREE("tree"),
    BOOKSHELF("bookshelf"),
    STAIRS_DOWN("stairs down"),
    STAIRS("stairs"),
    WATER("water"),
    TORCH("torch");

    private final String tileName;

    CellType(String tileName) {
        this.tileName = tileName;
    }

    public String getTileName() {
        return tileName;
    }
}
