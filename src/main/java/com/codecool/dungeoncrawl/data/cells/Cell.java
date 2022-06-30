package com.codecool.dungeoncrawl.data.cells;

import com.codecool.dungeoncrawl.data.Drawable;
import com.codecool.dungeoncrawl.data.Coordinates;
import com.codecool.dungeoncrawl.data.GameMap;
import com.codecool.dungeoncrawl.data.actors.Actor;
import com.codecool.dungeoncrawl.data.items.Item;

public class Cell implements Drawable {
    private CellType type;
    private Actor actor;
    private final GameMap gameMap;
    private final Coordinates coordinates;

    private Item item;

    public Cell(GameMap gameMap, int x, int y, CellType type) {
        this.gameMap = gameMap;
        this.coordinates = new Coordinates(x, y);
        this.type = type;
    }

    public CellType getType() {
        return type;
    }

    public void setType(CellType type) {
        this.type = type;
    }

    public void setActor(Actor actor) {
        this.actor = actor;
    }

    public Actor getActor() {
        return actor;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public Item getItem() {
        return item;
    }

    public Cell getNeighbor(int dx, int dy) {
        return gameMap.getCell(coordinates.getX() + dx, coordinates.getY() + dy);
    }

    public boolean hasDrawableElement(){
        return getActor() != null || getItem() != null;
    }

    public Drawable getDrawableElement(int x, int y){
        if (gameMap.getCell(x, y).getActor() != null){
            return getActor();
        } else if (gameMap.getCell(x, y).getItem() != null) {
            return getItem();
        }
        return null;
    }

    @Override
    public String getTileName() {
        return type.getTileName();
    }

    public int getX() {
        return coordinates.getX();
    }

    public int getY() {
        return coordinates.getY();
    }
}
