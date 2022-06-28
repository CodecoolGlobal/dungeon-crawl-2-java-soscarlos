package com.codecool.dungeoncrawl.data.actors;

import com.codecool.dungeoncrawl.data.cells.Cell;
import com.codecool.dungeoncrawl.data.Drawable;
import com.codecool.dungeoncrawl.data.cells.CellType;
import com.codecool.dungeoncrawl.logic.validation.ActorMovementValidator;

import java.util.Objects;

public abstract class Actor implements Drawable {
    private Cell cell;
    private int health = 10;

    private ActorMovementValidator validate;

    public Actor(Cell cell) {
        this.cell = cell;
        this.cell.setActor(this);
    }

    public void move(int dx, int dy) {
        validate = new ActorMovementValidator();
        Cell nextCell = cell.getNeighbor(dx, dy);
        cell.setActor(null);
        if (validate.validateMove(cell, dx, dy)) {
            nextCell.setActor(this);
            cell = nextCell;
        } else cell.setActor(this);
    }

    public int getHealth() {
        return health;
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
