package control.initializers;

import model.game.Board;
import model.tiles.Empty;
import model.tiles.Tile;
import model.tiles.units.enemies.Enemy;
import model.tiles.units.players.Player;
import utils.Position;
import utils.callbacks.DeathCallback;
import utils.callbacks.ObjectCallBack;
import utils.generators.RandomGenerator;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class LevelInitializer {
    private final int playerID;
    private boolean firstLevel = true;
    private final TileFactory tileFactory;

    public LevelInitializer(int playerID){
        this.playerID = playerID;
        tileFactory = new TileFactory();
    }
    public void initLevel(String levelPath)
    {
        List<String> lines;
        try {
            lines = Files.readAllLines(Paths.get(levelPath));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        List<Tile> tiles = new ArrayList<>();
        List<Enemy> enemies = new ArrayList<>();
        Player player = null;
        int width = 0;
        int y = 0,x=0;
        for(String line : lines)
        {
            x = 0;
            for(char c : line.toCharArray())
            {
                switch(c) {
                    case '.':
                        tiles.add(tileFactory.produceEmpty(new Position(x,y)));
                        break;
                    case '#':
                        tiles.add(tileFactory.produceWall(new Position(x,y)));
                        break;
                    case '@':
                        if(firstLevel)
                        {
                            player = tileFactory.producePlayer(playerID);
                            tiles.add(player);
                        }
                        else
                        {
                            tiles.add(tileFactory.producePlayer());
                        }
                        break;
                    default:
                        Enemy enemy = tileFactory.produceEnemy(c,new Position(x,y), new ObjectCallBack(),new RandomGenerator(),new ObjectCallBack());
                        tiles.add(enemy);
                        enemies.add(enemy);
                        break;
                }
                x++;
            }
            width = x;
            y++;
        }
        Board board = Board.getInstance(tiles,player,enemies,width);
        if(firstLevel)
            firstLevel = false;
    }
}
