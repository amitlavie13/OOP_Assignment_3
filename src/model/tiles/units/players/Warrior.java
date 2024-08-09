package model.tiles.units.players;

import model.game.Board;
import model.tiles.units.HeroicUnit;
import model.tiles.units.enemies.Enemy;

import java.util.List;

public class Warrior extends Player implements HeroicUnit {
    private static final double HEALTH_PERCENTAGE = 0.10;
    private static final int DEFENSE_MULTIPLIER = 10;
    private static final int ABILITY_RANGE = 3;

    private int abilityCooldown;
    private int remainingCooldown;

    public Warrior(String name, int hitPoints, int attack, int defense, int abilityCooldown) {
        super(name, hitPoints, attack, defense);
        this.abilityCooldown = abilityCooldown;
        this.remainingCooldown = 0;
    }

    @Override
    public void levelUp() {
        super.levelUp();
        remainingCooldown = 0;
        health.increaseMax(5 * level);
        attack += 2 * level;
        defense += level;
    }

    public void onGameTick() {
        remainingCooldown = Math.max(0, remainingCooldown - 1);
    }

    public void castAbility(Player player) {}
    public void castAbility(List<Enemy> enemies) {
        if (remainingCooldown > 0) {
            // Handle ability cooldown error
            messageCallback.send("Ability is on cooldown.");
            return;
        }
        // Cast ability

        remainingCooldown = abilityCooldown;
        health.setCurrent(Math.min(health.getCurrent() + DEFENSE_MULTIPLIER * defense, health.getCapacity()));
        for (Enemy enemy : enemies) {
            if (enemy.getPosition().range(this.getPosition()) < ABILITY_RANGE) {
                int damage = (int) (health.getCapacity() * HEALTH_PERCENTAGE);
                enemy.health.takeDamage(damage-enemy.defend());
                if(!enemy.alive())
                {
                    addExperience(enemy.experienceValue());
                    enemy.onDeath();
                }
                break;
            }
        }
    }

    @Override
    public String description()
    {
        return this.getName() + "\t" +
                "Health: "+this.health.getCurrent() +"/" + this.health.getCapacity() +
                "\t" + "Attack: " + this.attack + "\t" + "Defense: " + this.defense + "\t" +
                "Level: " + this.level + "\t" +
                "Experience: " + this.experience + "\t" + "Cooldown: " + this.remainingCooldown +
                "/"+this.abilityCooldown;
    }
}