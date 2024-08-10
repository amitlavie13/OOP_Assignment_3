package model.tiles.units;

import model.tiles.units.enemies.Enemy;
import model.tiles.units.players.Player;

import java.util.List;

public interface HeroicUnit {
    boolean castAbility(List<Enemy> enemies);
    void castAbility(Player player);
}
