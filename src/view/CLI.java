package view;

import model.game.Board;
import model.tiles.units.Unit;
import model.tiles.units.enemies.Enemy;
import model.tiles.units.players.Player;

public class CLI extends View {

    public void display(String message) {
        System.out.println(message);
    }

    public void displayBoard(Board board) {
        System.out.println(board.toString());
    }

    public void displayPlayerStats(Player player) {
        System.out.println(player.description());
    }

    public void displayCombatInfo(Unit attacker, Unit defender, int attackRoll, int defenseRoll, int damage) {
        System.out.printf(attacker.getName()+" engaged in combat with "+defender.getName()+".\n");
        System.out.printf(attacker.description()+"\n");
        System.out.printf(defender.description()+"\n");
        System.out.printf(attacker.getName() +" rolled %d attack points.\n", attackRoll);
        System.out.printf(defender.getName()+" rolled %d defend points.\n", defenseRoll);
        System.out.printf(attacker.getName()+" dealt "+damage+" damage to "+defender.getName()+".\n");
    }
}
