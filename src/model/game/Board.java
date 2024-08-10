package model.game;

import model.tiles.Empty;
import model.tiles.Tile;
import model.tiles.units.enemies.Enemy;
import model.tiles.units.players.Player;
import utils.Position;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class Board {
    private static Board instance;
    private  Map<Position, Tile> board;
    private  Player player;
    private  List<Enemy> enemies;
    private final int width;

    private Board(List<Tile> tiles, Player p, List<Enemy> enemies, int width)
    {
        player = p;
        this.enemies = enemies;
        this.width = width;
        this.board = new TreeMap<>();
        for(Tile t : tiles){
            if (t == null) {
                System.out.println("Null tile found");
            } else if (t.getPosition() == null) {
                System.out.println("Tile with null position found: " + t);
            }
            board.put(t.getPosition(), t);
        }
    }

    public static Board getInstance(List<Tile> tiles, Player p, List<Enemy> enemies, int width)
    {
        instance = new Board(tiles, p, enemies, width);
        return instance;
    }

    public Map<Position, Tile> getTreeMap()
    {
        return board;
    }
    public static Board getInstance()
    {
        if(instance == null)
        {
            return null;
        }
        return instance;
    }

    public void setPlayer(Player player) {
        this.player = player;

    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        int count = 0;

        for (Map.Entry<Position, Tile> entry : board.entrySet()) {
            sb.append(entry.getValue().toString());
            count++;

            // Check if we need to add a newline
            if (count % width == 0) {
                sb.append("\n");
            }
        }

        // Ensure there's no trailing newline if not needed
        if (count % width != 0) {
            sb.append("\n");
        }

        return sb.toString();
    }


    public Tile getTileAtPosition(Position position) {
        return board.get(position);
    }

    public void setTileAtPosition(Position position, Tile tile)
    {
        tile.initialize(position);
        board.put(position, tile);
    }

    public void removeEnemy(Enemy enemy) {
        Position enemyPosition = enemy.getPosition();
        this.setTileAtPosition(enemyPosition, new Empty());
        enemies.remove(enemy);
    }

    public void gameTick(boolean usedUlt) {
        for (Enemy enemy : enemies) {
            enemy.onGameTick(player);
        }
        if(!usedUlt)
            player.onGameTick();
    }

    public Player getPlayer() {
        return player;
    }

    public List<Enemy> getEnemies() {
        return enemies;
    }
}
