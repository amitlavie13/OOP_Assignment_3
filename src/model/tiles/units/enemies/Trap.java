package model.tiles.units.enemies;

import model.tiles.units.players.Player;
import utils.Position;
import model.tiles.Tile;
import model.tiles.Empty;
import model.tiles.Wall;
import model.game.Board;

public class Trap extends Enemy {
    private static final int DEFAULT_VISIBILITY_TIME = 5;
    private static final int DEFAULT_INVISIBILITY_TIME = 5;

    private int visibilityTime;
    private int invisibilityTime;
    private int ticksCount;
    private boolean visible;
    private char tileCharReminder;

    public Trap(char tile, String name, int hitPoints, int attack, int defense, int experienceValue,
                int visibilityTime, int invisibilityTime) {
        super(tile, name, hitPoints, attack, defense, experienceValue);
        this.visibilityTime = visibilityTime;
        this.invisibilityTime = invisibilityTime;
        this.ticksCount = 0;
        this.visible = true;
        tileCharReminder = tile;
    }

    public void onGameTick(Player player) {
        updateVisibility();
        if (this.getPosition().range(player.getPosition()) < 2) {
            attack(player);
        }
        ticksCount++;
    }

    private void updateVisibility() {
        if (ticksCount >= (visibilityTime + invisibilityTime)) {
            ticksCount = 0;
            visible = true;
            this.tile = tileCharReminder;
        } else if (ticksCount == visibilityTime) {
            visible = false;
            this.tile = '.';
        }
    }

    private void attack(Player player) {
        if (this.getPosition().range(player.getPosition()) < 2) {
            int attackValue = this.attack();  // or some fixed attack value
            player.health.takeDamage(attackValue);
            // Optionally, send a message about the trap's attack
            if (!player.alive()) {
                player.onDeath();
            }
        }
    }

    @Override
    public String description()
    {
        return this.getName() + "\t" +
                "Health: "+this.health.getCurrent() +"/" + this.health.getCapacity() +
        "\t" + "Attack: " + this.attack + "\t" + "Defense: " + this.defense + "\t" +
                "Experience Value: " + this.experienceValue;
    }


}
