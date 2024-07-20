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
        System.out.printf("Combat info:\n");
        System.out.printf("Attacker: %s\n", attacker.description());
        System.out.printf("Defender: %s\n", defender.description());
        System.out.printf("Attack roll: %d\n", attackRoll);
        System.out.printf("Defense roll: %d\n", defenseRoll);
        System.out.printf("Damage taken: %d\n", damage);
        if (!defender.alive()&& defender instanceof Enemy) {
            System.out.printf("%s has been defeated. %s gained %d experience.\n", defender.getName(), attacker.getName(), ((Enemy) defender).experienceValue());
        }
    }

}
