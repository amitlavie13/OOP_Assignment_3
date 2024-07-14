package model.tiles.units.players;

import model.tiles.units.enemies.Enemy;

import java.util.List;

public class Mage extends Player {
    private int manaPool;
    private int currentMana;
    private int manaCost;
    private int spellPower;
    private int hitsCount;
    private int abilityRange;

    public Mage(String name, int hitPoints, int attack, int defense, int manaPool, int manaCost, int spellPower, int hitsCount, int abilityRange) {
        super(name, hitPoints, attack, defense);
        this.manaPool = manaPool;
        this.currentMana = manaPool / 4;
        this.manaCost = manaCost;
        this.spellPower = spellPower;
        this.hitsCount = hitsCount;
        this.abilityRange = abilityRange;
    }

    @Override
    public void levelUp() {
        super.levelUp();
        manaPool += 25 * level;
        currentMana = Math.min(currentMana + manaPool / 4, manaPool);
        spellPower += 10 * level;
    }

    public void onGameTick() {
        currentMana = Math.min(manaPool, currentMana + 1 * level);
    }

    public void castSpecialAbility(List<Enemy> enemies) {
        if (currentMana < manaCost) {
            // Handle insufficient mana error
            messageCallback.send("Not enough mana.");
            return;
        }
        // Cast ability
        currentMana -= manaCost;
        int hits = 0;
        while (hits < hitsCount) {
            for (Enemy enemy : enemies) {
                if (enemy.getPosition().range(this.getPosition()) < abilityRange) {
                    enemy.receiveDamage(spellPower);
                    hits++;
                    if (hits >= hitsCount) break;
                }
            }
            if (hits >= hitsCount) break;
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