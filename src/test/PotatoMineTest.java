import domain.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PotatoMineTest {
    private PotatoMine potatoMine;

    @BeforeEach
    public void setUp() {
        potatoMine = new PotatoMine(1, 1);
    }

    @Test
    public void testCannotShoot() {
        assertNull(potatoMine.shoot());
    }

    @Test
    public void testCannotExplodes() {
        potatoMine.cooldown();
        assertFalse(potatoMine.isActive());
    }

    @Test
    public void testCanExplodes() {
        for (int i = 0; i < 451; i++) {
            potatoMine.cooldown();
        }
        assertTrue(potatoMine.isActive());
    }

    @Test
    public void cannotMove() {
        int xPosition = potatoMine.getHitbox().x;
        potatoMine.move();
        assertEquals(xPosition, potatoMine.getHitbox().x);
    }

    @AfterEach
    public void tearDown() {
        potatoMine = null;
    }
}
