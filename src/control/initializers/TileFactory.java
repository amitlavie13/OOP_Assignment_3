package control.initializers;

import model.tiles.Empty;
import model.tiles.Tile;
import model.tiles.Wall;
import model.tiles.units.enemies.*;
import model.tiles.units.players.Mage;
import model.tiles.units.players.Player;
import model.tiles.units.players.Rogue;
import model.tiles.units.players.Hunter;
import model.tiles.units.players.Warrior;
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
    private static Player p;
    private static final List<Supplier<Player>> playerTypes = Arrays.asList(
            () -> new Warrior("Jon Snow",300,30,4,3),
            () -> new Warrior("The Hound",400,20,6,5),
            () -> new Mage("Melisandre",100,5,1,300,30,15,5,6),
            () -> new Mage("Thoros of Myr",250,25,4,150,20,20,3,4),
            () -> new Rogue("Arya Stark",150,40,2,20),
            () -> new Rogue("Bronn",250,35,3,50),
            () -> new Hunter("Ygritte",220,30,2,10,6)
    );


    private static final Map<Character, Supplier<Enemy>> enemyTypes = new HashMap<>();

    static {
        enemyTypes.put('s', () -> new Monster('s', "Lannister Soldier", 80, 8, 3, 25, 3));
        enemyTypes.put('k', () -> new Monster('k', "Lannister Knight", 200, 14, 8, 50, 4));
        enemyTypes.put('q', () -> new Monster('q', "Queen’s Guard", 400, 20, 15, 100, 5));
        enemyTypes.put('z', () -> new Monster('z', "Wright", 600, 30, 15, 100, 3));
        enemyTypes.put('b', () -> new Monster('b', "Bear-Wright", 1000, 75, 30, 250, 4));
        enemyTypes.put('g', () -> new Monster('g', "Giant-Wright", 1500, 100, 40, 500, 5));
        enemyTypes.put('w', () -> new Monster('w', "White Walker", 2000, 15, 50, 1000, 6));
        enemyTypes.put('M', () -> new Mountain());
        enemyTypes.put('C', () -> new QueenCersei());
        enemyTypes.put('K', () -> new NightsKing());
        enemyTypes.put('B', () -> new Trap('B', "Bonus Trap", 1, 1, 1, 250, 1, 5));
        enemyTypes.put('Q', () -> new Trap('Q', "Queen’s Trap", 250, 50, 10, 100, 3, 7));
        enemyTypes.put('D', () -> new Trap('D', "Death Trap", 500, 100, 20, 250, 1, 10));
    }
    public TileFactory()
    {
        p=null;
    }

    public Player producePlayer(int playerID){
        Supplier<Player> supp = playerTypes.get(playerID-1);
        this.p = supp.get();
        System.out.println("Produced player: " + this.p);
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
