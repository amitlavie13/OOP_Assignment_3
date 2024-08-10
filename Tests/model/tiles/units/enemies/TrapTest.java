package model.tiles.units.enemies;

import model.tiles.units.players.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.Position;


import static org.junit.jupiter.api.Assertions.*;

class TrapTest {

    private Trap trap;
    private Player mockPlayer;

    @BeforeEach
    void setUp() {
        trap = new Trap('T', "Spike Trap", 100, 30, 10, 3,3,3);

    }

    @Test
    void testTrapInitialization() {
        assertEquals("Spike Trap", trap.getName());
        assertEquals(100, trap.health.getCurrent());
    }

    @Test
    void testTrapDeath() {
        trap.health.takeDamage(100);
        assertFalse(trap.alive());
    }
}