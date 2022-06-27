package com.codecool.dungeoncrawl.util;

import java.util.Random;

public class RandomIntegerGenerator {
    public static int randomInteger (int bound) {
        Random random = new Random();
        return random.nextInt(bound);
    }
    public static int randomIntegerRange (int lower, int upper) {
        return randomInteger(upper - lower) + lower;
    }
}
