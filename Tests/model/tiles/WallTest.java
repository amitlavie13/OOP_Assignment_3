package model.tiles;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.Position;

import static org.junit.jupiter.api.Assertions.*;

class WallTest {

    private Wall wall;

    @BeforeEach
    void setUp() {
        wall = new Wall();
        wall.initialize(new Position(1, 1));
    }

    @Test
    void testWallInitialization() {
        assertEquals("#", wall.toString());
        assertNotNull(wall.getPosition());
    }


    @Test
    void testGetPosition() {
        Position position = new Position(2, 2);
        wall.initialize(position);
        assertEquals(position, wall.getPosition());
    }
}