import domain.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * The test class ConeheadTest.
 *
 * @author Botero Garcia, Santiago. Perilla Quintero, Laura Natalia.
 * @version December 12 2024
 */

public class ConeheadTest {
    private Conehead coneheadZombie;

    @BeforeEach
    public void setUp() {
        coneheadZombie = new Conehead(1, 1);
    }

    @Test
    public void testShoot() {
        assertNull(coneheadZombie.shoot());
    }

    @Test
    public void testCanAttack() {
        assertTrue(coneheadZombie.attack());
    }

    @Test
    public void testCannotAttack() {
        for (int i = 0; i < 2; i++) {
            coneheadZombie.attack();
        }
        assertFalse(coneheadZombie.attack());
    }

    @AfterEach
    public void tearDown() {
        coneheadZombie = null;
    }
}