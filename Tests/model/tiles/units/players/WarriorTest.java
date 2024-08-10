package model.tiles.units.players;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WarriorTest {

    private Warrior warrior;

    @BeforeEach
    void setUp() {
        warrior = new Warrior("Test Warrior", 100, 20, 10,3);
    }

    @Test
    void testWarriorInitialization() {
        assertEquals("Test Warrior", warrior.getName());
        assertEquals(100, warrior.health.getCurrent());
    }

    @Test
    void testWarriorOnDeath() {
        warrior.health.takeDamage(100);
        assertFalse(warrior.alive());
        warrior.onDeath();
    }
}