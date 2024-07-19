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
    private Board board;

    public Monster(char tile, String name, int hitPoints, int attack, int defense, int experienceValue, int visionRange, Board board) {
        super(tile, name, hitPoints, attack, defense, experienceValue);
        this.visionRange = visionRange;
        this.board = board;
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
        if (newTile instanceof Empty) {
            swapPosition(newTile);
        } else if (newTile instanceof Player) {
            battle((Player) newTile);
        } else if (newTile instanceof Wall) {
            // Do nothing, as the wall blocks movement
        }
    }


    @Override
    public void visit(Player player) {
        battle(player);
        if (!player.alive()) {
            player.onDeath();
        }
    }
}