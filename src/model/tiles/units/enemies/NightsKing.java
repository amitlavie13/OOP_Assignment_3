package model.tiles.units.enemies;


import model.tiles.units.players.Player;
import model.game.Board;

public class NightsKing extends Boss {
    public NightsKing(Board board, Player player) {
        super('K', "Night's King", 5000, 300, 150, 8, 5000, board, player);
    }

    @Override
    public void castAbility(Player player) {
        if (rangeToPlayer() < visionRange) {
            int damage = attack; // Adjust damage calculation as needed
            player.health.takeDamage(damage);
        }
    }
}
