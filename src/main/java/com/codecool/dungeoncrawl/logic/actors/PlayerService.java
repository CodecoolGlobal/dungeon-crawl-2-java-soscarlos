package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.data.actors.Actor;
import com.codecool.dungeoncrawl.data.cells.Cell;
import com.codecool.dungeoncrawl.logic.validation.ActorMovementValidator;

import java.util.List;

public class PlayerService {

    public void attackMonster(List<Actor> monsters, Actor actor, int dx, int dy) {
        ActorMovementValidator valid = new ActorMovementValidator();
        Cell playerCell = actor.getCell();
        for (Actor monster : monsters) {
            Cell monsterCell = monster.getCell();
            if (valid.monsterIsNext(playerCell, monsterCell, dx, dy)) {
                actor.attack(monster);
                if (monster.getHealth() <= 0) {
                    monsterCell.setActor(null);
                }
            }
        }
    }
}
