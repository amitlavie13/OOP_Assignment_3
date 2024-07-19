package model.tiles.units.enemies;


import model.tiles.units.players.Player;
import model.tiles.units.players.Player;
import utils.Position;
import java.util.Random;
import model.tiles.Tile;
import model.tiles.Empty;
import model.tiles.Wall;
import model.game.Board;

public class Mountain extends Boss {

    public Mountain(Board board, Player player) {
        super('M', "Mountain", 1000, 60, 25, 6, 500, board, player);
    }

    @Override
    public void castAbility(Player player) {
        if (rangeToPlayer() < visionRange) {
            int damage = attack; // Adjust damage calculation as needed
            player.health.takeDamage(damage);
        }
    }
}
