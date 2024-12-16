import domain.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class ECIPlantTest {
    private ECIPlant eciPlant;

    @BeforeEach
    public void setUp() {
        eciPlant = new ECIPlant(1, 1);
    }

    @Test
    public void testShoot() {
        assertNull(eciPlant.shoot());
    }

    @Test
    public void cannotProduceSun() {
        assertEquals(0, eciPlant.getSun());
    }

    @Test
    public void canProduceSun() {
        for (int i = 0; i < 901; i++) {
            eciPlant.getSun();
        }
        assertEquals(50, eciPlant.getSun());
    }

    @Test
    public void cannotMove() {
        int xPosition = eciPlant.getHitbox().x;
        eciPlant.move();
        assertEquals(xPosition, eciPlant.getHitbox().x);
    }

    @Test
    public void testGetSunCost() {
        assertEquals(75, eciPlant.getSunCost());
    }

    @Test
    public void testHealthPoints() {
        assertEquals(10, eciPlant.getHealthPoints());
    }

    @Test
    public void testTakeDamage() {
        eciPlant.takeDamage(2);
        assertEquals(8, eciPlant.getHealthPoints());
    }

    @Test
    public void testPreparationTime() {
        assertEquals(30000, eciPlant.getPreparationTime());
    }

    @Test
    public void testSeedRechargeTime() {
        assertEquals(7500, eciPlant.getSeedRechargeTime());
    }

    @AfterEach
    public void tearDown() {
        eciPlant = null;
    }
}
