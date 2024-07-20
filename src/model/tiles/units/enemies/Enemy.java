package model.tiles.units.enemies;

import model.game.Board;
import model.tiles.units.Unit;
import model.tiles.units.players.Player;
import utils.Position;
import model.tiles.Empty;

public class Enemy extends Unit {
    protected int experienceValue;
    protected Board board;

    public Enemy(char tile, String name, int hitPoints, int attack, int defense, int experienceValue, Board board) {
        super(tile, name, hitPoints, attack, defense);
        this.experienceValue = experienceValue;
        this.board = board;
    }

    public int experienceValue() {
        return experienceValue;
    }

    @Override
    public void accept(Unit unit) {
        unit.visit(this);
    }

    public void visit(Enemy e){
        // Do nothing
    }

    public void visit(Player p){
        battle(p);
        if (!p.alive()){
            p.onDeath();
        }
    }

    public void onDeath()
    {
        board.removeEnemy(this);
        deathCallback.onDeath();
    }

    //will Override
    public String description()
    {
        return " ";
    }
    public void onGameTick(Player player){}


}
