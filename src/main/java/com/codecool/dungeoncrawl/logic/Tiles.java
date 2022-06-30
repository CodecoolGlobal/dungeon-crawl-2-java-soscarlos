package com.codecool.dungeoncrawl.logic;

import com.codecool.dungeoncrawl.data.Drawable;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.util.HashMap;
import java.util.Map;

public class Tiles {
    public static int TILE_WIDTH = 32;

    private static Image tileset = new Image("/tiles.png", 543 * 2, 543 * 2, true, false);
    private static Map<String, Tile> tileMap = new HashMap<>();
    public static class Tile {
        public final int x, y, w, h;
        Tile(int i, int j) {
            x = i * (TILE_WIDTH + 2);
            y = j * (TILE_WIDTH + 2);
            w = TILE_WIDTH;
            h = TILE_WIDTH;
        }
    }

    static {
        tileMap.put("empty", new Tile(0, 0));
        tileMap.put("wall", new Tile(10, 17));
        tileMap.put("floor", new Tile(2, 0));
        tileMap.put("player", new Tile(27, 0));
        tileMap.put("skeleton", new Tile(29, 6));
        tileMap.put("demon", new Tile(24, 7));
        tileMap.put("troll", new Tile(30, 6));
        tileMap.put("key", new Tile (16, 23));
        tileMap.put("sword", new Tile (0, 30));
        tileMap.put("closed door", new Tile (5, 9));
        tileMap.put("open door", new Tile (6, 9));
        tileMap.put("tree", new Tile(1, 1));
        tileMap.put("bookshelf", new Tile(13, 11));
        tileMap.put("apple", new Tile (15, 29));
        tileMap.put("stairs down", new Tile(3, 6));
        tileMap.put("bazooka", new Tile(11, 31));
    }

    public static void drawTile(GraphicsContext context, Drawable d, int x, int y) {
        Tile tile = tileMap.get(d.getTileName());
        context.drawImage(tileset, tile.x, tile.y, tile.w, tile.h,
                x * TILE_WIDTH, y * TILE_WIDTH, TILE_WIDTH, TILE_WIDTH);
    }
}
