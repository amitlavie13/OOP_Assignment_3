package model.tiles.units.players;

import model.tiles.units.enemies.Enemy;

import java.util.List;

public class Warrior extends Player {
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
        defense += 1 * level;
    }

    public void onGameTick() {
        remainingCooldown = Math.max(0, remainingCooldown - 1);
    }

    public void castSpecialAbility(List<Enemy> enemies) {
        if (remainingCooldown > 0) {
            // Handle ability cooldown error
            messageCallback.send("Ability is on cooldown.");
            return;
        }
        // Cast ability
        remainingCooldown = abilityCooldown;
        currentHealth = Math.min(currentHealth + DEFENSE_MULTIPLIER * defense, health.getMax());
        for (Enemy enemy : enemies) {
            if (enemy.getPosition().range(this.getPosition()) < ABILITY_RANGE) {
                int damage = (int) (health.getMax() * HEALTH_PERCENTAGE);
                enemy.receiveDamage(damage);
                break;
            }
        }
    }

    @Override
    public void visit(Enemy enemy) {
        battle(enemy);
        if (!enemy.alive()) {
            addExperience(enemy.experienceValue());
            enemy.onDeath();
        }
    }
}