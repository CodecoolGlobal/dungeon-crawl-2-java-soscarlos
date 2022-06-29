package com.codecool.dungeoncrawl.data.directions;

import java.util.Random;

public class RandomDirectionPicker {
    public Direction getRandomDirection(){
        Random random = new Random();
        Direction[] values = Direction.values();
        return values[random.nextInt(values.length)];
    }
}
