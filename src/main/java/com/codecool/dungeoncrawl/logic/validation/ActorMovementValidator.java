package com.codecool.dungeoncrawl.logic.validation;

import com.codecool.dungeoncrawl.data.actors.Actor;
import com.codecool.dungeoncrawl.data.cells.Cell;
import com.codecool.dungeoncrawl.data.cells.CellType;

public class ActorMovementValidator {
    public boolean validateMove(Cell cell, int dx, int dy){
        Cell nextCell = cell.getNeighbor(dx, dy);
        CellType nextType= nextCell.getType();
        Actor actor = nextCell.getActor();
        return nextType == CellType.FLOOR && actor == null;
    }
}
