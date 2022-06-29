package com.codecool.dungeoncrawl.logic;

import com.codecool.dungeoncrawl.data.actors.Actor;
import com.codecool.dungeoncrawl.data.cells.Cell;
import com.codecool.dungeoncrawl.logic.validation.ActorMovementValidator;

import java.util.List;

public class PlayerService {
    // TODO: Place movement method of player here
    public void attackMonster(List<Actor> monsters, Actor actor, int dx, int dy) {
        ActorMovementValidator valid = new ActorMovementValidator();
        Cell playerCell = actor.getCell();
        for (Actor monster : monsters) {
            Cell monsterCell = monster.getCell();
            if (valid.monsterIsNext(playerCell, monsterCell, dx, dy)) {
                actor.attack(monster);
            }
        }
    }


}
