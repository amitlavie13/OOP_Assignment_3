package control.initializers;

import model.tiles.Empty;
import model.tiles.Tile;
import model.tiles.Wall;
import model.tiles.units.enemies.Enemy;
import model.tiles.units.players.Player;
import utils.Position;
import utils.callbacks.DeathCallback;
import utils.callbacks.MessageCallback;
import utils.generators.Generator;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public class TileFactory {
    private Player p;
    private static final List<Supplier<Player>> playerTypes = Arrays.asList(
            () -> new Player("Player1", 10, 5, 2),
            () -> new Player("Player2", 15, 3, 3),
            () -> new Player("Player3", 20, 2, 4)
    );

    private static final Map<Character, Supplier<Enemy>> enemyTypes = Map.of(
            's', () -> new Enemy('s', "Enemy1", 10, 5, 2, 0),
            'q', () -> new Enemy('q', "Enemy2", 15, 3, 3, 1),
            'T', () -> new Enemy('T', "Enemy3", 20, 2, 4, 2)
    );
    public TileFactory(){
    }

    public Player producePlayer(int playerID){
        Supplier<Player> supp = playerTypes.get(playerID-1);
        this.p = supp.get();
        return this.p;
    }

    public Player producePlayer(){
        return this.p;
    }

    public Enemy produceEnemy(char tile, Position p, DeathCallback c, Generator g, MessageCallback m){
        Enemy e = enemyTypes.get(tile).get();
        e.initialize(p, g, c, m);
        return e;
    }

    public Tile produceEmpty(Position p){
        return new Empty().initialize(p);
    }

    public Tile produceWall(Position p){
        return new Wall().initialize(p);
    }
}
