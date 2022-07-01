package com.codecool.dungeoncrawl.logic.validation;

import com.codecool.dungeoncrawl.data.actors.Actor;
import com.codecool.dungeoncrawl.data.actors.Player;
import com.codecool.dungeoncrawl.data.cells.Cell;
import com.codecool.dungeoncrawl.data.cells.CellType;
import com.codecool.dungeoncrawl.data.items.Item;

public class ActorMovementValidator {
    public boolean validateMove(Cell cell, int dx, int dy) {
        Cell nextCell = cell.getNeighbor(dx, dy);
        CellType nextType = nextCell.getType();
        Actor actor = nextCell.getActor();

        boolean floor = nextType == CellType.FLOOR && actor == null;
        boolean openDoor = nextType == CellType.OPEN_DOOR;
        boolean stairsDown = nextType == CellType.STAIRS_DOWN;
        boolean stairs = nextType == CellType.STAIRS;

        return floor || openDoor || stairsDown || stairs;
    }

    public boolean checkPlayerOnItem(Player player) {
        Item item = player.getCell().getItem();
        return item != null;
    }

    public boolean isPlayer(Cell cell, int dx, int dy) {
        Cell nextCell = cell.getNeighbor(dx, dy);
        Actor enemy = nextCell.getActor();
        return enemy instanceof Player;
    }

    public boolean playerIsClose(Actor monster) {
        boolean playerIsNext = false;
        Cell monsterCell = monster.getCell();
        if (isPlayer(monsterCell, 0, -1) ||
                isPlayer(monsterCell, 0, 1) ||
                isPlayer(monsterCell, -1, 0) ||
                isPlayer(monsterCell, 1, 0)) {
            playerIsNext = true;
        }
        return playerIsNext;
    }

    public boolean monsterIsNext(Cell playerCell, Cell monsterCell, int dx, int dy) {
        return playerCell.getNeighbor(dx, dy) == monsterCell;
    }
}
