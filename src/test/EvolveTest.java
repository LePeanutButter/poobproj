import domain.Evolve;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class EvolveTest {
    private Evolve evolve;

    @BeforeEach
    public void setUp() {
        evolve = new Evolve(1, 1);
    }

    @Test
    public void testCannotShoot() {
        assertNull(evolve.shoot());
    }

    @Test
    public void testCanShootFirstEvolution() {
        for (int i = 0; i < 450; i++) {
            evolve.shoot();
            evolve.updateEvolveTimer();
        }
        assertNotNull(evolve.shoot());
    }

    @Test
    public void testGetsSecondEvolution() {
        int evolutions = 0;
        for (int i = 0; i < 1052; i++) {
            if (evolve.updateEvolveTimer()){
                ++evolutions;
            }
        }
        assertEquals(2, evolutions);
    }

    @Test
    public void cannotMove() {
        int xPosition = evolve.getHitbox().x;
        evolve.move();
        assertEquals(xPosition, evolve.getHitbox().x);
    }

    @AfterEach
    public void tearDown() {
        evolve = null;
    }
}
