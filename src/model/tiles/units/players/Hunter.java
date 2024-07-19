package model.tiles.units.players;

import model.game.Board;
import model.tiles.units.HeroicUnit;
import model.tiles.units.enemies.Enemy;

import java.util.ArrayList;
import java.util.List;

public class Hunter extends Player
{
    private int range;
    private int arrowCount;
    private int ticksCount;

    public Hunter(String name, int hitPoints, int attack, int defense, int range, int arrowCount, Board board)
    {
        super(name, hitPoints,attack,defense,board);
        this.range = range;
        this.arrowCount = arrowCount;
        this.ticksCount = 0;
    }

    @Override
    public void levelUp() {
        super.levelUp();
        this.arrowCount += 10 * level;
        this.attack += 2*level;
        this.defense += level;
    }

    public void onGameTick()
    {
        if(ticksCount == 10)
        {
            arrowCount += level;
            ticksCount = 0;
        }
        else
        {
            ticksCount++;
        }
    }

    public void castSpecialAbility(List<Enemy> enemies)
    {
        List<Enemy> enemyInRange = new ArrayList<>();
        for(Enemy enemy : enemies)
        {
            if(enemy.getPosition().range(this.getPosition()) < range)
            {
                enemyInRange.add(enemy);
            }
        }
        if(!enemyInRange.isEmpty())
        {
            if(arrowCount != 0)
            {
                Enemy closest = enemyInRange.getFirst();
                for(Enemy enemy : enemyInRange)
                {
                    if(enemy.getPosition().range(this.getPosition()) < closest.getPosition().range(this.getPosition()))
                    {
                        closest = enemy;
                    }
                }
                closest.health.takeDamage(this.attack -closest.defend());
                if(!closest.alive())
                {
                    addExperience(closest.experienceValue());
                    closest.onDeath();
                }
            }
            else
            {
                //Do Something
            }
        }
        else
        {
            //Do Something
        }

    }
}
