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

    private Board(List<Tile> tiles, Player p, List<Enemy> enemies, int width){
        player = p;
            this.enemies = enemies;
            this.width = width;
            this.board = new TreeMap<>();
            for(Tile t : tiles){
                board.put(t.getPosition(), t);
        }
    }

    public static Board getInstance(List<Tile> tiles, Player p, List<Enemy> enemies, int width)
    {
        instance = new Board(tiles, p, enemies, width);
        return instance;
    }

    public static Board getInstance()
    {
        return instance;
    }

    public void setPlayer(Player player) {
        this.player = player;

    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for(Map.Entry<Position, Tile> entry : board.entrySet()){
            sb.append(entry.getValue().toString());
            if(entry.getKey().getX() == width-1){
                sb.append("\n");
            }
        }
        return sb.toString();
    }


    public Tile getTileAtPosition(Position position) {
        return board.get(position);
    }

    public void setTileAtPosition(Position position, Tile tile) {
        board.put(position, tile);
    }

    public void removeEnemy(Enemy enemy) {
        Position enemyPosition = enemy.getPosition();
        this.setTileAtPosition(enemyPosition, new Empty());
        enemies.remove(enemy);
    }

    public void gameTick() {
        for (Enemy enemy : enemies) {
            enemy.onGameTick(player);
        }
    }

    public Player getPlayer() {
        return player;
    }

    public List<Enemy> getEnemies() {
        return enemies;
    }
}
