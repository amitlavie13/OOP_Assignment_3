package model.tiles.units.players;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MageTest {

    private Mage mage;

    @BeforeEach
    void setUp() {
        mage = new Mage("Test Mage", 70, 30, 5,20,10,4,5,4);
    }

    @Test
    void testMageInitialization() {
        assertEquals("Test Mage", mage.getName());
        assertEquals(70, mage.health.getCurrent());
    }

    @Test
    void testMageOnDeath() {
        mage.health.takeDamage(70);
        assertFalse(mage.alive());
        mage.onDeath();

    }
}