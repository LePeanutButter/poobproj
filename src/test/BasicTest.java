import domain.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * The test class BasicTest.
 *
 * @author Botero Garcia, Santiago. Perilla Quintero, Laura Natalia.
 * @version December 07 2024
 */

public class BasicTest {
    private Basic basicZombie;

    @BeforeEach
    public void setUp() {
        basicZombie = new Basic(1, 1);
    }

    @Test
    public void testShoot() {
        assertNull(basicZombie.shoot());
    }

    @Test
    public void testCanAttack() {
        assertTrue(basicZombie.attack());
    }

    @Test
    public void testMove() {
        double initialXPosition = basicZombie.getHitbox().x;
        basicZombie.move();
        assertTrue(basicZombie.getHitbox().x < initialXPosition);
    }

    @Test
    public void testGetRange() {
        assertEquals(0, basicZombie.getRange());
    }

    @Test
    public void testTakeDamage() {
        basicZombie.takeDamage(3);
        assertEquals(7, basicZombie.getHealthPoints());
    }

    @Test
    public void testGetBrainCost() {
        assertEquals(25, basicZombie.getBrainCost());
    }

    @Test
    public void testCannotAttack() {
        for (int i = 0; i < 2; i++) {
            basicZombie.attack();
        }
        assertFalse(basicZombie.attack());
    }

    @Test
    public void testConvertZombiePosition() {
        int[] position = Hitbox.convertZombiePosition(1, 1);
        assertEquals(397, position[0]);
        assertEquals(221, position[1]);
    }

    @AfterEach
    public void tearDown() {
        basicZombie = null;
    }
}
