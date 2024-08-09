package model.tiles.units.enemies;


import model.tiles.units.players.Player;
import model.tiles.units.players.Player;
import utils.Position;

import java.util.List;
import java.util.Random;
import model.tiles.Tile;
import model.tiles.Empty;
import model.tiles.Wall;
import model.game.Board;

public class Mountain extends Boss {

    public Mountain() {
        super('M', "The Mountain", 1000, 60, 25, 500, 6, 5);
    }

    @Override
    public void castAbility(Player player) {
        if (rangeToPlayer() < visionRange) {
            int damage = attack; // Adjust damage calculation as needed
            player.health.takeDamage(damage);
        }
    }

    public void castAbility(List<Enemy> enemies){}
}
