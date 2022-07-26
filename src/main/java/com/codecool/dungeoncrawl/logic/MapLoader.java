package com.codecool.dungeoncrawl.logic;

import com.codecool.dungeoncrawl.data.GameMap;
import com.codecool.dungeoncrawl.data.actors.*;
import com.codecool.dungeoncrawl.data.cells.Cell;
import com.codecool.dungeoncrawl.data.cells.CellType;
import com.codecool.dungeoncrawl.data.items.collectibles.*;
import com.codecool.dungeoncrawl.data.items.consumables.*;

import java.io.InputStream;
import java.util.Scanner;

public class MapLoader {

    String stringMap;

    public static GameMap loadMap(String currentMap) {
        InputStream is = MapLoader.class.getResourceAsStream(currentMap);

        Scanner scanner = new Scanner(is);
        int width = scanner.nextInt();
        int height = scanner.nextInt();

        scanner.nextLine(); // empty line

        GameMap map = new GameMap(width, height, CellType.EMPTY);
        for (int y = 0; y < height; y++) {
            String line = scanner.nextLine();
            for (int x = 0; x < width; x++) {
                if (x < line.length()) {
                    Cell cell = map.getCell(x, y);
                    switch (line.charAt(x)) {
                        case ' ':
                            cell.setType(CellType.EMPTY);
                            break;
                        case '#':
                            cell.setType(CellType.WALL);
                            break;
                        case '.':
                            cell.setType(CellType.FLOOR);
                            break;
                        case 's':
                            cell.setType(CellType.FLOOR);
                            Actor skeleton = new Skeleton(cell);
                            map.addMonster(skeleton);
                            break;
                        case 'e':
                            cell.setType(CellType.FLOOR);
                            Actor demon = new Demon(cell);
                            map.addMonster(demon);
                            break;
                        case 't':
                            cell.setType(CellType.FLOOR);
                            Actor troll = new Troll(cell);
                            map.addMonster(troll);
                            break;
                        case '@':
                            cell.setType(CellType.FLOOR);
                            map.setPlayer(new Player(cell));
                            break;
                        case 'k':
                            cell.setType(CellType.FLOOR);
                            new Key(cell);
                            break;
                        case 'w':
                            cell.setType(CellType.FLOOR);
                            new Sword(cell);
                            break;
                        case 'd':
                            cell.setType(CellType.CLOSED_DOOR);
                            break;
                        case 'T':
                            cell.setType(CellType.TREE);
                            break;
                        case 'B':
                            cell.setType(CellType.BOOKSHELF);
                            break;
                        case 'A':
                            cell.setType(CellType.FLOOR);
                            new Apple(cell);
                            break;
                        case 'D':
                            cell.setType(CellType.STAIRS_DOWN);
                            break;
                        case 'S':
                            cell.setType(CellType.STAIRS);
                            break;
                        case 'b':
                            cell.setType(CellType.FLOOR);
                            new Bazooka(cell);
                            break;
                        case 'W':
                            cell.setType(CellType.WATER);
                            break;
                        case 'x':
                            cell.setType(CellType.TORCH);
                            break;
                        case 'F':
                            cell.setType(CellType.FLOOR);
                            new Steak(cell);
                            break;
                        default:
                            throw new RuntimeException("Unrecognized character: '" + line.charAt(x) + "'");
                    }
                }
            }
        }
        return map;
    }

}
