import domain.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * The test class BucketheadTest.
 *
 * @author Botero Garcia, Santiago. Perilla Quintero, Laura Natalia.
 * @version December 12 2024
 */

public class BucketheadTest {
    private Buckethead bucketheadZombie;

    @BeforeEach
    public void setUp() {
        bucketheadZombie = new Buckethead(1, 1);
    }

    @Test
    public void testShoot() {
        assertNull(bucketheadZombie.shoot());
    }

    @Test
    public void testCanAttack() {
        assertTrue(bucketheadZombie.attack());
    }

    @Test
    public void testCannotAttack2() {
        for (int i = 0; i < 2; i++) {
            bucketheadZombie.attack();
        }
        assertFalse(bucketheadZombie.attack());
    }

    @AfterEach
    public void tearDown() {
        bucketheadZombie = null;
    }
}