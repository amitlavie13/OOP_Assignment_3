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
            char action = getPlayerAction();
            performAction(action);
            board.gameTick();
        }
        cli.display("Game Over!");
    }

    private char getPlayerAction() {
        return scanner.next().charAt(0);
    }

    private void performAction(char action) {
        switch (action) {
            case 'w': player.moveUp(); break;
            case 's': player.moveDown(); break;
            case 'a': player.moveLeft(); break;
            case 'd': player.moveRight(); break;
            case 'e': player.castSpecialAbility(); break;
            case 'q': break; // Do nothing
            default: System.out.println("Invalid action!"); break;
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
