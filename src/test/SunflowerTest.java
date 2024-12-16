import domain.Sunflower;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SunflowerTest {
    private Sunflower sunflower;

    @BeforeEach
    public void setUp() {
        sunflower = new Sunflower(1, 1);
    }

    @Test
    public void testCannotShoot() {
        assertNull(sunflower.shoot());
    }


    @Test
    public void testDoesNotProduceSun() {
        assertEquals(0, sunflower.getSun());
    }

    @Test
    public void testProducesSun() {
        for (int i = 0; i < 721; i++) {
            sunflower.getSun();
        }
        assertEquals(25, sunflower.getSun());
    }

    @Test
    public void cannotMove() {
        int xPosition = sunflower.getHitbox().x;
        sunflower.move();
        assertEquals(xPosition, sunflower.getHitbox().x);
    }

    @AfterEach
    public void tearDown() {
        sunflower = null;
    }
}
