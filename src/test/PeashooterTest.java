import domain.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PeashooterTest {
    private Peashooter peashooter;

    @BeforeEach
    public void setUp() {
        peashooter = new Peashooter(1, 1);
    }

    @Test
    public void testCannotShoot() {
        assertNull(peashooter.shoot());
    }

    @Test
    public void testCanShoot() {
        for (int i = 0; i < 46; i++) {
            peashooter.shoot();
        }
        assertNotNull(peashooter.shoot());
    }

    @Test
    public void cannotMove() {
        int xPosition = peashooter.getHitbox().x;
        peashooter.move();
        assertEquals(xPosition, peashooter.getHitbox().x);
    }

    @Test
    public void testRange() {
        assertEquals(9, peashooter.getRange());
    }

    @AfterEach
    public void tearDown() {
        peashooter = null;
    }
}
