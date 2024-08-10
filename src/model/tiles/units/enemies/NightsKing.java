package model.tiles.units.enemies;


import model.tiles.units.HeroicUnit;
import model.tiles.units.players.Player;
import model.game.Board;

import java.util.List;

public class NightsKing extends Boss
{
    public NightsKing()
    {
        super('K', "Night's King", 5000, 300, 150, 5000,8, 3);
    }

    @Override
    public void castAbility(Player player) {
        if (rangeToPlayer() < visionRange) {
            int damage = attack; // Adjust damage calculation as needed
            player.health.takeDamage(damage);
        }
    }
    public boolean castAbility(List<Enemy> enemies) {return true;}
}
