package model.tiles.units.players;

import model.tiles.units.enemies.Enemy;

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

    public void castSpecialAbility(List<Enemy> enemies) {
        if (currentEnergy < abilityCost) {
            // Handle insufficient energy error
            messageCallback.send("Not enough energy.");
            return;
        }
        // Cast ability
        currentEnergy -= abilityCost;
        for (Enemy enemy : enemies) {
            if (enemy.getPosition().range(this.getPosition()) < 2) {
                enemy.receiveDamage(attack);
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