import domain.WallNut;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class WallNutTest {
    private WallNut wallNut;

    @BeforeEach
    public void setUp() {
        wallNut = new WallNut(1, 1);
    }

    @Test
    public void testCannotShoot() {
        assertNull(wallNut.shoot());
    }

    @Test
    public void cannotMove() {
        int xPosition = wallNut.getHitbox().x;
        wallNut.move();
        assertEquals(xPosition, wallNut.getHitbox().x);
    }

    @AfterEach
    public void tearDown() {
        wallNut = null;
    }
}
