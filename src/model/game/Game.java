package model.game;

import model.tiles.units.players.Player;
import model.tiles.units.enemies.Enemy;
import view.CLI;
import java.util.Scanner;
import model.tiles.units.players.Player;
import model.tiles.units.Unit;
import model.game.Board;
import utils.generators.Generator;

public class Game {
    private Board board;
    private Player player;
    private CLI cli;
    private Scanner scanner;

    public Game(Board board, Player player) {
        this.board = board;
        this.player = player;
        this.cli = new CLI();
        this.scanner = new Scanner(System.in);

        // Initialize callbacks
        //player.initialize(player.getPosition(), Generator::new, this::handleDeath, this::handleMessage);
    }

    public void start() {
        while (player.alive()) {
            cli.display(board.toString());
            cli.displayPlayerStats(player);
            performAction();
            board.gameTick();
        }
        cli.display("Game Over!");
    }

    private String getPlayerAction() {
        return scanner.next();
    }

    private void performAction() {
        boolean validAction = false;

        while (!validAction) {
            String input = getPlayerAction(); // Get input from the player
            if (input.length() == 1) { // Ensure input is a single character
                char action = input.charAt(0);
                switch (action) {
                    case 'w':
                        player.moveUp();
                        validAction = true;
                        break;
                    case 's':
                        player.moveDown();
                        validAction = true;
                        break;
                    case 'a':
                        player.moveLeft();
                        validAction = true;
                        break;
                    case 'd':
                        player.moveRight();
                        validAction = true;
                        break;
                    case 'e':
                        player.castSpecialAbility();
                        validAction = true;
                        break;
                    case 'q':
                        validAction = true; // Do nothing, but exit loop
                        break;
                    default:
                        System.out.println("Invalid action! Please enter w/a/s/d/e/q.");
                        break;
                }
            } else {
                System.out.println("Please enter a single character corresponding to an action.");
            }
        }
    }

    private void handleDeath(Unit unit) {
        if (unit == player) {
            cli.display("You have died.");
        } else {
            board.removeEnemy((Enemy) unit);
            cli.display(unit.getName() + " died");
        }
    }

    private void handleMessage(String message) {
        cli.display(message);
    }
}
