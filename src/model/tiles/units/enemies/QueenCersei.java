package model.tiles.units.enemies;

import model.tiles.units.players.Player;
import model.tiles.units.players.Player;
import model.game.Board;

import java.util.List;

public class QueenCersei extends Boss {
    public QueenCersei() {
        super('C', "Queen Cersei", 100, 10, 10, 1000,1, 8);
    }

    @Override
    public void castAbility(Player player) {
        if (rangeToPlayer() < visionRange) {
            int damage = attack; // Adjust damage calculation as needed
            player.health.takeDamage(damage);
        }
    }

    public boolean castAbility(List<Enemy> enemies){return true;}
}
