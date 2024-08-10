package model.tiles;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.Position;

import static org.junit.jupiter.api.Assertions.*;

class EmptyTest {

    private Empty empty;

    @BeforeEach
    void setUp() {
        empty = new Empty();
        empty.initialize(new Position(0, 0));
    }

    @Test
    void testEmptyInitialization() {
        assertEquals(".", empty.toString());
        assertNotNull(empty.getPosition());
    }

    @Test
    void testGetPosition() {
        Position position = new Position(1, 1);
        empty.initialize(position);
        assertEquals(position, empty.getPosition());
    }

}
