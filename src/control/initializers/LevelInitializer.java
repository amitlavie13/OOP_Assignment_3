package control.initializers;

import model.game.Board;
import model.game.Game;
import model.tiles.Tile;
import model.tiles.units.enemies.Enemy;
import model.tiles.units.players.Player;
import utils.Position;
import utils.generators.RandomGenerator;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class LevelInitializer {
    private final int playerID;
    private boolean firstLevel = true;
    private final TileFactory tileFactory;
    private final Game game;

    public LevelInitializer(int playerID,Game game){
        this.playerID = playerID;
        tileFactory = new TileFactory();
        this.game =game;
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
                            player.initialize(new Position(x, y));
                            tiles.add(player);
                        }
                        else
                        {
                            player =tileFactory.producePlayer();
                            player.initialize(new Position(x, y));
                            tiles.add(player);
                        }
                        break;
                    default:
                        Enemy enemy = tileFactory.produceEnemy(c,new Position(x,y), this.game::handleDeath,new RandomGenerator(),this.game::handleMessage);
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
