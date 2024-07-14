package model.tiles.units.enemies;

import model.tiles.units.Unit;
import model.tiles.units.players.Player;

public class Enemy extends Unit {
    protected int experienceValue;

    public Enemy(char tile, String name, int hitPoints, int attack, int defense, int experienceValue) {
        super(tile, name, hitPoints, attack, defense);
        this.experienceValue = experienceValue;
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

}
