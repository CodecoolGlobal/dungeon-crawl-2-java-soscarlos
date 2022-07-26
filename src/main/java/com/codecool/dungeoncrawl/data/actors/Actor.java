package com.codecool.dungeoncrawl.data.actors;

import com.codecool.dungeoncrawl.data.Drawable;
import com.codecool.dungeoncrawl.data.cells.Cell;
import com.codecool.dungeoncrawl.data.cells.CellType;
import com.codecool.dungeoncrawl.logic.validation.ActorMovementValidator;

public abstract class Actor implements Drawable {
    private Cell cell;

    private int health = 10;
    private int level = 1;

    private int attackStrength = 5;

    public Actor(Cell cell) {
        this.cell = cell;
        this.cell.setActor(this);
    }

    public void move(int dx, int dy) {
        ActorMovementValidator validate = new ActorMovementValidator();
        Cell nextCell = cell.getNeighbor(dx, dy);
        cell.setActor(null);

        if (validate.validateMove(cell, dx, dy)) {
            nextCell.setActor(this);
            cell = nextCell;
        } else cell.setActor(this);

        if (nextCell.getType() == CellType.CLOSED_DOOR && this.hasKey()) {
            nextCell.setType(CellType.OPEN_DOOR);
        }

        if (nextCell.getType() == CellType.STAIRS_DOWN && this instanceof Player) {
            setLevel(getLevel() + 1);
        }
    }

    public void attack(Actor enemy) {
        int enemyHealth = enemy.getHealth();
        if (enemy.getHealth() >= 0) enemy.setHealth(enemyHealth - this.attackStrength);
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
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

    public boolean hasKey() {
        return false;
    }

    public int getAttackStrength() {
        return attackStrength;
    }

    public void setAttackStrength(int attackStrength) {
        this.attackStrength = attackStrength;
    }
}
