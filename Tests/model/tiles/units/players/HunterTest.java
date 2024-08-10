package model.tiles.units.players;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HunterTest {

    private Hunter hunter;

    @BeforeEach
    void setUp() {
        hunter = new Hunter("Test Hunter", 80, 25, 8,2,4);
    }

    @Test
    void testHunterOnDeath() {
        hunter.health.takeDamage(80);
        assertFalse(hunter.alive());
        hunter.onDeath();
    }
}