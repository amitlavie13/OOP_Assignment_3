package model.tiles.units.enemies;

import model.tiles.units.players.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.Position;


import static org.junit.jupiter.api.Assertions.*;

class MonsterTest {

    private Monster monster;


    @BeforeEach
    void setUp() {
        monster = new Monster('M', "Orc", 200, 20, 10, 5,3);
    }

    @Test
    void testMonsterInitialization() {
        assertEquals("Orc", monster.getName());
        assertEquals(200, monster.health.getCurrent());
    }


    @Test
    void testMonsterDeath() {
        monster.health.takeDamage(200);
        assertFalse(monster.alive());
    }
}
