package model.tiles.units.enemies;

import model.tiles.units.players.Player;
import model.tiles.units.players.Player;
import model.game.Board;

public class QueenCersei extends Boss {
    public QueenCersei(Board board, Player player) {
        super('C', "Queen Cersei", 100, 10, 10, 1, 1000, board, player);
    }

    @Override
    public void castAbility(Player player) {
        if (rangeToPlayer() < visionRange) {
            int damage = attack; // Adjust damage calculation as needed
            player.health.takeDamage(damage);
        }
    }
}
