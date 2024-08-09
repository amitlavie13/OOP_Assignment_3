package model.tiles.units.enemies;

import model.tiles.units.players.Player;
import utils.Position;
import model.tiles.Tile;
import model.tiles.Empty;
import model.tiles.Wall;
import model.game.Board;

import java.util.List;
import java.util.Random;
import model.tiles.units.HeroicUnit;

public abstract class Boss extends Enemy implements HeroicUnit {
    protected int visionRange;
    protected int abilityFrequency;
    protected int combatTicks;

    public Boss(char tile, String name, int hitPoints, int attack, int defense,int experienceValue, int visionRange, int abilityFrequency) {
        super(tile, name, hitPoints, attack, defense, experienceValue); // Experience value set to 0 as it's not used
        this.visionRange = visionRange;
        this.abilityFrequency = abilityFrequency;
        this.combatTicks = 0;
    }

    public void onGameTick()
    {
        Board board = Board.getInstance();
        if (rangeToPlayer() < visionRange) {
            if (combatTicks >= abilityFrequency) {
                combatTicks = 0;
                castAbility(board.getPlayer());
            } else {
                combatTicks++;
                chasePlayer(board.getPlayer());
            }
        } else {
            combatTicks = 0;
            randomMove();
        }
    }

    private void chasePlayer(Player player) {
        Position playerPos = player.getPosition();
        Position myPos = this.getPosition();
        int dx = myPos.getX() - playerPos.getX();
        int dy = myPos.getY() - playerPos.getY();

        if (Math.abs(dx) > Math.abs(dy)) {
            if (dx > 0) {
                moveLeft();
            } else {
                moveRight();
            }
        } else {
            if (dy > 0) {
                moveUp();
            } else {
                moveDown();
            }
        }
    }

    private void randomMove() {
        Random random = new Random();
        int direction = random.nextInt(5);
        switch (direction) {
            case 0 -> moveLeft();
            case 1 -> moveUp();
            case 2 -> moveRight();
            case 3 -> moveDown();
            default -> {
            } // Do nothing (stay in the same place)
        }
    }

    private void moveLeft() {
        Position newPos = new Position(this.getPosition().getX() - 1, this.getPosition().getY());
        moveTo(newPos);
    }

    private void moveRight() {
        Position newPos = new Position(this.getPosition().getX() + 1, this.getPosition().getY());
        moveTo(newPos);
    }

    private void moveUp() {
        Position newPos = new Position(this.getPosition().getX(), this.getPosition().getY() - 1);
        moveTo(newPos);
    }

    private void moveDown() {
        Position newPos = new Position(this.getPosition().getX(), this.getPosition().getY() + 1);
        moveTo(newPos);
    }

    private void moveTo(Position newPos)
    {
        Board board = Board.getInstance();
        Tile newTile = board.getTileAtPosition(newPos);
        if (newTile instanceof Empty) {
            swapPosition(newTile);
        } else if (newTile instanceof Player) {
            battle((Player) newTile);
        } else if (newTile instanceof Wall) {
            // Do nothing, as the wall blocks movement
        }
    }

    protected double rangeToPlayer()
    {
        Board board = Board.getInstance();
        return getPosition().range(board.getPlayer().getPosition());
    }

    @Override
    public abstract void castAbility(Player player);
    public abstract void castAbility(List<Enemy> enemy);

    @Override
    public String description()
    {
        return this.getName() + "\t" +
                "Health: "+this.health.getCurrent() +"/" + this.health.getCapacity() +
                "\t" + "Attack: " + this.attack + "\t" + "Defense: " + this.defense + "\t" +
                "Experience Value: " + this.experienceValue + "\t" + "Vision Range: " + this.visionRange + "\n";
    }
}
