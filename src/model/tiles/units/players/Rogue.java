package model.tiles.units.players;

import model.game.Board;
import model.tiles.units.HeroicUnit;
import model.tiles.units.enemies.Enemy;
import model.tiles.units.Unit;
import model.tiles.Empty;
import model.tiles.Tile;
import model.tiles.Wall;
import model.tiles.units.enemies.Enemy;
import model.tiles.units.players.Player;
import utils.Health;
import utils.Position;
import utils.callbacks.DeathCallback;
import utils.callbacks.MessageCallback;
import utils.generators.Generator;

import java.util.ArrayList;
import java.util.List;

public class Rogue extends Player {
    private int abilityCost;
    private int currentEnergy;
    private static final int MAX_ENERGY = 100;

    public Rogue(String name, int hitPoints, int attack, int defense, int abilityCost) {
        super(name, hitPoints, attack, defense);
        this.abilityCost = abilityCost;
        this.currentEnergy = MAX_ENERGY;
    }

    @Override
    public void levelUp() {
        super.levelUp();
        currentEnergy = MAX_ENERGY;
        this.attack += 3 * level;
    }

    public void onGameTick() {
        currentEnergy = Math.min(MAX_ENERGY, currentEnergy + 10);
    }

    public void castAbility(Player player){}
    public boolean castAbility(List<Enemy> enemies)
    {
        List<Enemy> deadEnemies = new ArrayList<>();
        if (currentEnergy < abilityCost) {
            // Handle insufficient energy error
            messageCallback.send("Not enough energy.");
            return false;
        }
        // Cast ability
        currentEnergy -= abilityCost;
        for (Enemy enemy : enemies) {
            if (enemy.getPosition().range(this.getPosition()) < 2) {
                int defense = enemy.defend();
                enemy.health.takeDamage(attack-defense);
                if(!enemy.alive())
                {
                    deadEnemies.add(enemy);
                }
            }
        }
        for(Enemy enemy : deadEnemies)
        {
            addExperience(enemy.experienceValue());
            enemy.onDeath();
        }
        return true;
    }

    @Override
    public String description()
    {
        return this.getName() + "\t" +
                "Health: "+this.health.getCurrent() +"/" + this.health.getCapacity() +
                "\t" + "Attack: " + this.attack + "\t" + "Defense: " + this.defense + "\t" +
                "Level: " + this.level + "\t" +
                "Experience: " + this.experience + "\t" + "Energy: " + this.currentEnergy +
                "/"+MAX_ENERGY;
    }
}