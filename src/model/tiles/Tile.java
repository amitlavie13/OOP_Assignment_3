package model.tiles;

import model.tiles.units.Unit;
import utils.Position;

public abstract class Tile {
    protected char tile;
    protected Position position;

    public Tile(char tile){
        this.tile = tile;
    }

    public Tile initialize(Position p){
        this.position = p;
        return this;
    }

    public void swapPosition(Tile t) {
        Position temp = t.position;
        t.position = this.position;
        this.position = temp;
    }

    @Override
    public String toString() {
        return String.valueOf(tile);
    }

    public abstract void accept(Unit unit);

    public Position getPosition() {
        return position;
    }
}
