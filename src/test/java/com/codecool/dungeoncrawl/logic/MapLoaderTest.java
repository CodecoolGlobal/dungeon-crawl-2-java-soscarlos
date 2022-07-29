package com.codecool.dungeoncrawl.logic;

import com.codecool.dungeoncrawl.data.GameMap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.io.FileNotFoundException;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class MapLoaderTest {
    private GameMap map;

    @BeforeEach
    void init() {
        map = MapLoader.loadMap("/map.txt");
    }

    @Test
    void loadMap() {
        GameMap expected = map;
        GameMap result = MapLoader.loadMap("/map.txt");

        assertEquals(expected.toString(), result.toString());
        assertEquals(expected.getCell(4, 4).getTileName(), result.getCell(4, 4).getTileName());
    }

    @Test
    void secondMapDoesNotEqualFirstMap() {
        GameMap expected = map;
        GameMap result = MapLoader.loadMap("/map2.txt");

        assertNotEquals(expected.toString(), result.toString());
    }
}