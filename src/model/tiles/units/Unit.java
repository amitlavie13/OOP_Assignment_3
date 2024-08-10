package model.tiles.units;

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
import view.CLI;

public abstract class Unit extends Tile {
    protected String name;
    public Health health;
    protected int attack;
    protected int defense;

    protected Generator generator;
    protected DeathCallback deathCallback;
    protected MessageCallback messageCallback;

    public Unit(char tile, String name, int hitPoints, int attack, int defense) {
        super(tile);
        this.name = name;
        this.health = new Health(hitPoints);
        this.attack = attack;
        this.defense = defense;
    }

    public Unit initialize(Position p, Generator generator, DeathCallback deathCallback, MessageCallback messageCallback){
        super.initialize(p);
        this.generator = generator;
        this.deathCallback = deathCallback;
        this.messageCallback = messageCallback;
        return this;
    }

    public Generator getGenerator()
    {
        return generator;
    }

    public int attack(){
        return generator.generate(attack);
    }

    public int defend(){
        return generator.generate(defense);
    }

    public boolean alive(){
        return health.getCurrent() > 0;
    }

    public void battle(Unit enemy)
    {
        int damage = 0;
        int attack = this.attack();
        int defense = enemy.defend();
        damage = Math.max(0, attack - defense);
        damage = Math.min(enemy.health.getCurrent(), damage);

        if (messageCallback != null) {
            messageCallback.send(String.format("%s engaged in combat with %s.\n", this.getName(), enemy.getName()));
            messageCallback.send(this.description());
            messageCallback.send(enemy.description());
            messageCallback.send(String.format("%s rolled %d attack points.\n", this.getName(), attack));
            messageCallback.send(String.format("%s rolled %d defend points.\n", enemy.getName(), defense));
            messageCallback.send(String.format("%s dealt %d damage to %s.\n", this.getName(), damage, enemy.getName()));
        }

        int damageTaken = enemy.health.takeDamage(damage);
    }

    public void interact(Tile t){
        t.accept(this);
    }

    public void visit(Empty e){
        swapPosition(e);
    }

    public void visit(Wall w){
        // Do nothing
    }

    public abstract void visit(Player p);
    public abstract void visit(Enemy e);

    public abstract void onDeath();


    public String getName()
    {
        return this.name;
    }

    public abstract String description();
}
