package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.data.actors.Actor;
import com.codecool.dungeoncrawl.data.actors.Player;
import com.codecool.dungeoncrawl.data.cells.Cell;
import com.codecool.dungeoncrawl.data.directions.Direction;
import com.codecool.dungeoncrawl.data.directions.RandomDirectionPicker;
import com.codecool.dungeoncrawl.logic.validation.ActorMovementValidator;

import java.util.List;

public class MonsterService {
    Direction previousDirection = null;

    public void moveSkeleton(Actor skeleton) {
        RandomDirectionPicker picker = new RandomDirectionPicker();
        Direction direction = picker.getRandomDirection();
        moveNext(skeleton, direction);
    }

    public void moveNext(Actor monster, Direction direction) {
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
        // TODO here i can add the check for the player
        monster.move(directionX, directionY);
    }

    public void moveDemon(Actor demon) {
        Direction[] values = Direction.values();
        for (Direction direction : values) {
            if (previousDirection != null) {
                direction = getDirection(values);
            }
            moveNext(demon, direction);
            previousDirection = direction;
        }
    }

    private Direction getDirection(Direction[] values) {
        int nextIndex = previousDirection.ordinal() + 1;
        if (nextIndex >= values.length) {
            nextIndex = 0;
        }
        return values[nextIndex];
    }

    public void moveMonsters(List<Actor> monsters) {
        for (Actor monster : monsters) {
            if (monster.getTileName().equals("skeleton")) {
                moveSkeleton(monster);
            } else if (monster.getTileName().equals("demon")) {
                moveDemon(monster);
            }
        }
    }
    public void attackPlayer(List<Actor> monsters, Actor player){
        ActorMovementValidator validator = new ActorMovementValidator();
        for(Actor monster: monsters){
            if (validator.playerIsNext(monster)) monster.attack(player);
        }
    }
}
