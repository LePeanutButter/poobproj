import domain.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;


public class ECIZombieTest {
    private ECIZombie eciZombie;

    @BeforeEach
    public void setUp() {
        eciZombie = new ECIZombie(1, 10);
    }

    @Test
    public void testCannotShoot() {
        assertNull(eciZombie.shoot());
    }

    @Test
    public void testCanShoot() {
        for (int i = 0; i < 15; i++) {
            eciZombie.shoot();
        }
        assertNotNull(eciZombie.shoot());
    }

    @Test
    public void testCanAttack() {
        assertTrue(eciZombie.attack());
    }

    @Test
    public void testCannotAttack() {
        eciZombie.attack();
        assertFalse(eciZombie.attack());
    }

    @Test
    public void testHitboxColor() {
        assertEquals(Color.RED, eciZombie.getColor());
    }

    @Test
    public void testAction() {
        assertEquals('m', eciZombie.getAction());
    }

    @Test
    public void testActionChangeToAttack() {
        eciZombie.setAction('a');
        assertEquals('a', eciZombie.getAction());
    }

    @AfterEach
    public void tearDown() {
        eciZombie = null;
    }
}
