package model.tiles.units.enemies;


import model.tiles.units.players.Player;
import utils.Position;
import java.util.Random;
import model.tiles.Tile;
import model.tiles.Empty;
import model.tiles.Wall;
import model.game.Board;

public class Monster extends Enemy {
    private int visionRange;

    public Monster(char tile, String name, int hitPoints, int attack, int defense, int experienceValue, int visionRange, Board board) {
        super(tile, name, hitPoints, attack, defense, experienceValue,board);
        this.visionRange = visionRange;
    }

    public void onGameTick(Player player) {
        if (this.getPosition().range(player.getPosition()) < visionRange) {
            chasePlayer(player);
        } else {
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
        Random random=new Random();
        int direction = random.nextInt(5);
        if (direction == 0) {
            moveLeft();
        }
        else if (direction == 1) {
            moveUp();
        }
        else if (direction == 2) {
            moveRight();
        }
        else if (direction == 3) {
            moveDown();
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

    private void moveTo(Position newPos) {
        Tile newTile = board.getTileAtPosition(newPos);
        newTile.accept(this);
    }

    @Override
    public String description()
    {
        return this.getName() + "\t" +
                "Health: "+this.health.getCurrent() +"/" + this.health.getCapacity() +
                "\t" + "Attack: " + this.attack + "\t" + "Defense: " + this.defense + "\t" +
                "Experience Value: " + this.experienceValue + "\t" + "Vision Range: " + this.visionRange + "\n";
    }
}