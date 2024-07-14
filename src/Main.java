import model.tiles.units.Unit;
import model.tiles.units.players.Player;
import utils.Position;
import utils.generators.FixedGenerator;

public class Main {
    public static void main(String[] args) {
        Unit p = new Player("Player", 100, 10, 10)
                .initialize(new Position(0, 0), new FixedGenerator(), () -> System.out.println("Player died"), System.out::println);
        System.out.println("Hello world!");
    }
}