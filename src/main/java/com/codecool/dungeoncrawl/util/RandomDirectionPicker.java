package com.codecool.dungeoncrawl.util;

import com.codecool.dungeoncrawl.data.directions.Direction;

import java.util.Random;

public class RandomDirectionPicker {
    public static Direction getRandomDirection() {
        Random random = new Random();
        Direction[] values = Direction.values();
        return values[random.nextInt(values.length)];
    }
}
