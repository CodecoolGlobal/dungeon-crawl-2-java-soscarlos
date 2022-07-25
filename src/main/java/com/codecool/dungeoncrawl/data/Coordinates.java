package com.codecool.dungeoncrawl.data;

public class Coordinates {
    private int x, y;

    public Coordinates(int x, int y) {
        setX(x);
        setY(y);
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }
}
