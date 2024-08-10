package model.tiles.units.players;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RogueTest {

    private Rogue rogue;

    @BeforeEach
    void setUp() {
        rogue = new Rogue("Test Rogue", 90, 35, 7,20);
    }

    @Test
    void testRogueInitialization() {
        assertEquals("Test Rogue", rogue.getName());
        assertEquals(90, rogue.health.getCurrent());
    }
    @Test
    void testRogueOnDeath() {
        rogue.health.takeDamage(90);
        assertFalse(rogue.alive());
        rogue.onDeath();
    }
}