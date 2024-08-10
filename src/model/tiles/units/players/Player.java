package model.tiles.units.players;

import model.game.Board;
import model.tiles.units.HeroicUnit;
import model.tiles.units.Unit;
import model.tiles.units.enemies.Enemy;
import utils.Position;
import model.tiles.Tile;
import model.tiles.Empty;
import model.tiles.Wall;
import utils.callbacks.DeathCallback;
import  utils.callbacks.MessageCallback;
import utils.generators.Generator;

import java.util.List;

public abstract class Player extends Unit implements HeroicUnit {
    public static final char PLAYER_TILE = '@';
    protected static final int LEVEL_REQUIREMENT = 50;
    protected static final int HEALTH_GAIN = 10;
    protected static final int ATTACK_GAIN = 4;
    protected static final int DEFENSE_GAIN = 1;

    protected int level;
    protected int experience;

    public Player(String name, int hitPoints, int attack, int defense) {
        super(PLAYER_TILE, name, hitPoints, attack, defense);
        this.level = 1;
        this.experience = 0;
    }
    public Player initialize(Position p, Generator generator, DeathCallback deathCallback, MessageCallback messageCallback) {
        super.initialize(p, generator, deathCallback, messageCallback);
        return this;
    }

    public void addExperience(int experienceValue)
    {
        messageCallback.send(String.format("%s gained %d experience.", this.getName(), experienceValue));
        this.experience += experienceValue;
        while (experience >= levelRequirement()) {
            levelUp();
        }
    }

    public void levelUp(){
        this.experience -= levelRequirement();
        this.level++;
        int healthGain = healthGain();
        int attackGain = attackGain();
        int defenseGain = defenseGain();
        messageCallback.send(String.format("%s reached level %d: +%d Health, +%d Attack, +%d Defense.",this.name,this.level,healthGain,attackGain,defenseGain));
        health.increaseMax(healthGain);
        health.heal();
        attack += attackGain;
        defense += defenseGain;
    }

    protected int levelRequirement(){
        return LEVEL_REQUIREMENT * level;
    }

    protected int healthGain(){
        return HEALTH_GAIN * level;
    }

    protected int attackGain(){
        return ATTACK_GAIN * level;
    }

    protected int defenseGain(){
        return DEFENSE_GAIN * level;
    }

    @Override
    public void accept(Unit unit)
    {
        unit.visit(this);
    }

    public void visit(Player p)
    {
        // Do nothing
    }

    public void visit(Enemy e)
    {
        Board board = Board.getInstance();
        battle(e);
        if(!e.alive()){
            Position enemyPosition = e.getPosition();
            String expGained = String.format("%s gained %d experience.", this.getName(), e.experienceValue());
            addExperience(e.experienceValue());
            e.onDeath();
            messageCallback.send(expGained);
            this.swapPosition(board.getTileAtPosition(enemyPosition));
        }
    }

    public void moveUp() {
        Position newPos = new Position(this.getPosition().getX(), this.getPosition().getY() - 1);
        moveTo(newPos);
    }

    public void moveDown() {
        Position newPos = new Position(this.getPosition().getX(), this.getPosition().getY() + 1);
        moveTo(newPos);
    }

    public void moveLeft() {
        Position newPos = new Position(this.getPosition().getX() - 1, this.getPosition().getY());
        moveTo(newPos);
    }

    public void moveRight() {
        Position newPos = new Position(this.getPosition().getX() + 1, this.getPosition().getY());
        moveTo(newPos);
    }

    private void moveTo(Position newPos)
    {
        Board board = Board.getInstance();
        Tile newTile = board.getTileAtPosition(newPos);
        newTile.accept(this);
    }

    public void onDeath()
    {
        this.tile = 'X';
    }

    public int getLevel() {
        return level;
    }

    //will be overridden
    public String description()
    {
        return "";
    }
    public abstract void onGameTick();
    public abstract boolean castAbility(List<Enemy> enemies);
    public abstract void castAbility(Player player);
}
