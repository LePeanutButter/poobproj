import domain.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class POOmBasTest {
    private POOmBas pooMBas;

    @BeforeEach
    public void setUp() {
        pooMBas = new POOmBas(new int[]{100, 200});
    }

    @Test
    public void testInitialPosition() {
        assertEquals(100, pooMBas.getHitbox().x);
        assertEquals(200, pooMBas.getHitbox().y);
    }

    @Test
    public void testGetDamage() {
        pooMBas.setDamage(25);
        assertEquals(25, pooMBas.getDamage());
    }

    @Test
    public void testMove() {
        int initialX = pooMBas.getHitbox().x;
        pooMBas.move();
        assertEquals(initialX - 15, pooMBas.getHitbox().x);
        assertEquals(200, pooMBas.getHitbox().y);
    }
}
