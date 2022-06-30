package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.data.actors.Actor;
import com.codecool.dungeoncrawl.data.actors.Player;
import com.codecool.dungeoncrawl.data.cells.Cell;
import com.codecool.dungeoncrawl.data.directions.Direction;
import com.codecool.dungeoncrawl.util.RandomDirectionPicker;
import com.codecool.dungeoncrawl.logic.validation.ActorMovementValidator;

import java.util.List;

public class MonsterService {
    Direction previousDirection = null;

    public void moveSkeleton(Actor skeleton, Player player) {
        Direction direction = RandomDirectionPicker.getRandomDirection();
        moveNext(skeleton, player, direction);
    }

    public void moveNext(Actor monster, Player player, Direction direction) {
        int directionX = 0, directionY = 0;
        switch (direction) {
            case UP:
                directionY = -1;
                break;
            case DOWN:
                directionY = 1;
                break;
            case LEFT:
                directionX = -1;
                break;
            case RIGHT:
                directionX = 1;
                break;
        }
        tryAttackPlayer(monster, player, directionX, directionY);
        monster.move(directionX, directionY);
    }

    public void moveDemon(Actor demon, Player player) {
        Direction[] values = Direction.values();
        for (Direction direction : values) {
            if (previousDirection != null) {
                direction = getDirection(values);
            }
            moveNext(demon, player, direction);
            previousDirection = direction;
        }
    }

    public void moveTroll(Actor troll, Player player){
        ActorMovementValidator validator = new ActorMovementValidator();
        if (validator.playerIsClose(troll)){
            Direction direction = RandomDirectionPicker.getRandomDirection();
            moveNext(troll, player, direction);
        }
    }

    private Direction getDirection(Direction[] values) {
        int nextIndex = previousDirection.ordinal() + 1;
        if (nextIndex >= values.length) {
            nextIndex = 0;
        }
        return values[nextIndex];
    }

    public void moveMonsters(List<Actor> monsters, Player player) {
        for (Actor monster : monsters) {
            if (monster.getTileName().equals("skeleton")) {
                moveSkeleton(monster, player);
            } else if (monster.getTileName().equals("demon")) {
                moveDemon(monster, player);
            } else if (monster.getTileName().equals("troll")) {
                moveTroll(monster, player);
            }
        }
    }
    public void tryAttackPlayer(Actor monster, Player player, int dx, int dy){
        ActorMovementValidator validator = new ActorMovementValidator();
        Cell monsterCell = monster.getCell();
        Cell playerCell = player.getCell();
        if (validator.isPlayer(monsterCell, dx, dy)){
            monster.attack(player);
            if (player.isDead()){
                playerCell.setActor(null);
            }
        }
    }
}
