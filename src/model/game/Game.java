package model.game;

import control.initializers.LevelInitializer;
import model.tiles.units.players.*;
import model.tiles.units.enemies.Enemy;
import utils.generators.RandomGenerator;
import view.CLI;
import java.util.Scanner;
import model.tiles.units.players.Player;
import model.tiles.units.Unit;
import model.game.Board;
import utils.generators.Generator;

public class Game {
    private Board board;
    private final CLI cli;
    private final Scanner scanner;
    private boolean usedUlt = false;
    private boolean flag = true;

    public Game() {
        this.board = null;
        this.cli = new CLI();
        this.scanner = new Scanner(System.in);

        // Initialize callbacks
        //player.initialize(player.getPosition(), Generator::new, this::handleDeath, this::handleMessage);
    }

    public void start(String directoryPath)
    {
        int i = 1;
        int playerID = choosePlayer();
        LevelInitializer levelInitializer = new LevelInitializer(playerID,this);
        while (flag)
        {
            levelInitializer.initLevel(directoryPath+"\\level"+i+".txt");
            this.board = Board.getInstance();
            board.getPlayer().initialize(board.getPlayer().getPosition(),new RandomGenerator(), this::handleDeath,this::handleMessage);
            while (board.getPlayer().alive() && !board.getEnemies().isEmpty()) {
                cli.display(board.toString());
                cli.displayPlayerStats(board.getPlayer());
                performAction();
                board.gameTick(usedUlt);
                usedUlt = false;
            }
            if(board.getPlayer().alive())
            {
                if(i<3)
                    i++;
                else
                {
                    cli.display("You Won!");
                    flag = false;
                }
            }
        }
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
                        board.getPlayer().moveUp();
                        validAction = true;
                        break;
                    case 's':
                        board.getPlayer().moveDown();
                        validAction = true;
                        break;
                    case 'a':
                        board.getPlayer().moveLeft();
                        validAction = true;
                        break;
                    case 'd':
                        board.getPlayer().moveRight();
                        validAction = true;
                        break;
                    case 'e':
                        usedUlt = board.getPlayer().castAbility(board.getEnemies());
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

    public void handleDeath(Unit unit) {
        if (unit == board.getPlayer())
        {
            cli.displayBoard(this.board);
            cli.display("You have died.");
            flag = false;
        }
        else {
            board.removeEnemy((Enemy) unit);
            cli.display(unit.getName() + " died");
        }
    }

    public void handleMessage(String message) {
        cli.display(message);
    }

    private int choosePlayer()
    {
        int choice = 0;
        Player[] players = new Player[7];
        players[0] = new Warrior("Jon Snow",300,30,4,3);
        players[1] = new Warrior("The Hound",400,20,6,5);
        players[2] = new Mage("Melisandre",100,5,1,300,30,15,5,6);
        players[3] = new Mage("Thoros of Myr",250,25,4,150,20,20,3,4);
        players[4] = new Rogue("Arya Stark",150,40,2,20);
        players[5] = new Rogue("Bronn",250,35,3,50);
        players[6] = new Hunter("Ygritte",220,30,2,10,6);

        this.cli.display("Select player:");
        int i = 1;
        for(Player player : players)
        {
            this.cli.display(i + ". " +player.description());
            i++;
        }

        do {
            while(!scanner.hasNextInt())
            {
                scanner.next();
            }
            choice = scanner.nextInt();
        }while(choice < 1 || choice > 7);

        return choice;
    }
}
