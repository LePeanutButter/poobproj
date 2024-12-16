import domain.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MowerTest {
    private Mower mower;

    @BeforeEach
    public void setUp() {
        mower = new Mower(2);
    }

    @Test
    public void testMoveInactive() {
        int initialX = mower.getHitbox().x;
        mower.move();
        assertEquals(initialX, mower.getHitbox().x);
    }

    @Test
    public void testMoveActive() {
        int initialX = mower.getHitbox().x;
        mower.setActive();
        mower.move();
        assertNotEquals(initialX, mower.getHitbox().x);
    }

    @Test
    public void testIsActive() {
        assertFalse(mower.isActive());
        mower.setActive();
        assertTrue(mower.isActive());
    }

    @Test
    public void testConvertMowerPosition() {
        int[] position = Hitbox.convertMowerPosition(2);
        assertEquals(230, position[0]);
        assertEquals(374, position[1]);
    }

    @AfterEach
    public void tearDown() {
        mower = null;
    }
}
