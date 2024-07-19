package model.tiles.units.players;

import model.game.Board;
import model.tiles.units.enemies.Enemy;

import java.util.List;
import java.util.ArrayList;
import java.util.Random;

public class Mage extends Player {
    private int manaPool;
    private int currentMana;
    private int manaCost;
    private int spellPower;
    private int hitsCount;
    private int abilityRange;

    public Mage(String name, int hitPoints, int attack, int defense, int manaPool, int manaCost, int spellPower, int hitsCount, int abilityRange, Board board) {
        super(name, hitPoints, attack, defense,board);
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
        currentMana = Math.min(manaPool, currentMana + level);
    }

    public void castSpecialAbility(List<Enemy> enemies)
    {
        Random rand = new Random();
        if (currentMana < manaCost) {
            // Handle insufficient mana error
            messageCallback.send("Not enough mana.");
            return;
        }
        // Cast ability
        currentMana -= manaCost;
        int hits = 0;
        List<Enemy> enemyInRange = new ArrayList<>();
        for(Enemy enemy : enemies)
        {
            if(enemy.getPosition().range(this.getPosition()) < abilityRange)
            {
                enemyInRange.add(enemy);
            }
        }
        while (hits < hitsCount && !enemyInRange.isEmpty())
        {
            int randomIndex = rand.nextInt(enemyInRange.size());
            enemyInRange.get(randomIndex).health.takeDamage(spellPower-defend());
            if (!enemyInRange.get(randomIndex).alive())
            {
                addExperience(enemyInRange.get(randomIndex).experienceValue());
                enemyInRange.get(randomIndex).onDeath();
                enemyInRange.remove(randomIndex);
            }
            hits++;
        }
    }
}