package com.codecool.dungeoncrawl.logic;

import com.codecool.dungeoncrawl.data.actors.Actor;
import com.codecool.dungeoncrawl.data.actors.Skeleton;
import com.codecool.dungeoncrawl.data.cells.Cell;
import com.codecool.dungeoncrawl.data.directions.Direction;
import com.codecool.dungeoncrawl.data.directions.RandomDirectionPicker;
import com.codecool.dungeoncrawl.logic.validation.ActorMovementValidator;
import com.codecool.dungeoncrawl.util.RandomIntegerGenerator;

import java.util.List;

public class MonsterService {
    public void moveSkeleton(Actor skeleton){
        RandomDirectionPicker picker = new RandomDirectionPicker();
        Direction direction = picker.getRandomDirection();
        moveNext(skeleton, direction);
    }
    public void moveNext(Actor monster, Direction direction){
        int directionX = 0, directionY = 0;
        switch (direction){
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
        monster.move(directionX, directionY);
    }

    public void moveDemon(Actor demon){
        ActorMovementValidator validator = new ActorMovementValidator();
        Cell cell = demon.getCell();
        if (validator.validateMove(cell, 0, 1)) {
            moveNext(demon, Direction.DOWN);
        } else if (validator.validateMove(cell, -1, 0)) {
            moveNext(demon, Direction.LEFT);
        } else if (validator.validateMove(cell, 0, -1)) {
            moveNext(demon, Direction.UP);
        } else if (validator.validateMove(cell, 1, 0)) {
            moveNext(demon, Direction.RIGHT);
        }
    }

    public void moveMonsters(List<Actor> monsters){
        for (Actor monster: monsters) {
            if (monster.getTileName().equals("skeleton")){
                moveSkeleton(monster);
            } else if (monster.getTileName().equals("demon")) {
                moveDemon(monster);
            }
        }
    }
}
