import domain.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * The test class BrainsteinTest.
 *
 * @author Botero Garcia, Santiago. Perilla Quintero, Laura Natalia.
 * @version December 07 2024
 */

public class BrainsteinTest {
    private Brainstein brainsteinZombie;

    @BeforeEach
    public void setUp() {
        brainsteinZombie = new Brainstein(1, 1);
    }

    @Test
    public void testShoot() {
        assertNull(brainsteinZombie.shoot());
    }

    @Test
    public void testCanAttack() {
        assertFalse(brainsteinZombie.attack());
    }

    @Test
    public void testCanGetBrain() {
        for (int i = 0; i < 721; i++) {
            brainsteinZombie.getBrain();
        }
        assertEquals(25, brainsteinZombie.getBrain());
    }

    @Test
    public void testCannotGetBrain() {
        assertEquals(0, brainsteinZombie.getBrain());
    }

    @AfterEach
    public void tearDown() {
        brainsteinZombie = null;
    }
}