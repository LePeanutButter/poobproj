import domain.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

public class PvPTest {
    private PvP pvp;
    private Basic basicZombie;
    private Sunflower sunflower;

    @BeforeEach
    public void setUp() throws POOBVsZombiesException {
        pvp = new PvP("Day", 3000, 5);
        basicZombie = new Basic(1, 1);
        sunflower = new Sunflower(1, 1);
    }

    @Test
    public void testCollectBrain() {
        pvp.collectBrain(50);
        assertEquals(3050, pvp.getBrainCollected());
    }

    @Test
    public void testNegativeBrain() throws POOBVsZombiesException {
        pvp = new PvP("Day", -5, 5);
        assertEquals(25, pvp.getBrainCollected());
    }

    @Test
    public void testNonExactBrain() throws POOBVsZombiesException {
        pvp = new PvP("Day", 5005, 5);
        assertEquals(5000, pvp.getBrainCollected());
    }

    @Test
    public void testOverTenThousandBrain() throws POOBVsZombiesException {
        pvp = new PvP("Day", 10000, 5);
        assertEquals(9975, pvp.getBrainCollected());
    }

    @Test
    public void testGetZombiesPlaying() {
        pvp.setZombiesPlaying(true);
        assertTrue(pvp.getZombiesPlaying());
    }

    @Test
    void testHandleZombie() {
        assertDoesNotThrow(() -> pvp.handleZombie("Basic"));
    }

    @Test
    public void testSetStrategyTimer() {
        pvp.setStrategyTimer();
        assertEquals(120000, pvp.getTime());
    }

    @Test
    public void testSetZombiesTimer() {
        pvp.setZombiesTimer();
        assertEquals(pvp.getOriginalTime() / 2, pvp.getTime());
    }

    @Test
    public void testUpdateScore() {
        pvp.updateScore(basicZombie);
        assertEquals(1, pvp.getZombiesDefeated());
        pvp.updateScore(sunflower);
        assertEquals(1, pvp.getPlantsDefeated());
    }

    @Test
    public void testPlaceBasic() throws POOBVsZombiesException {
        Zombie zombie = pvp.placeZombie(1, 1, "Basic");
        assertNotNull(zombie);
        assertEquals("Basic", zombie.getClass().getSimpleName());
    }

    @Test
    public void testPlaceConehead() throws POOBVsZombiesException {
        Zombie zombie = pvp.placeZombie(1, 1, "Conehead");
        assertNotNull(zombie);
        assertEquals("Conehead", zombie.getClass().getSimpleName());
    }

    @Test
    public void testPlaceBuckethead() throws POOBVsZombiesException {
        Zombie zombie = pvp.placeZombie(1, 1, "Buckethead");
        assertNotNull(zombie);
        assertEquals("Buckethead", zombie.getClass().getSimpleName());
    }

    @Test
    public void testPlaceECIZombie() throws POOBVsZombiesException {
        Zombie zombie = pvp.placeZombie(1, 1, "ECIZombie");
        assertNotNull(zombie);
        assertEquals("ECIZombie", zombie.getClass().getSimpleName());
    }

    @Test
    public void testPlaceBrainstein() throws POOBVsZombiesException {
        Zombie zombie = pvp.placeZombie(1, 1, "Brainstein");
        assertNotNull(zombie);
        assertEquals("Brainstein", zombie.getClass().getSimpleName());
    }

    @Test
    public void testThrowPlaceInvalidZombie() {
        assertThrows(POOBVsZombiesException.class, () -> pvp.placeZombie(1, 1, "Zomboss"));
    }

    @Test
    public void testPlaceZombieNotEnoughBrains() throws POOBVsZombiesException {
        pvp = new PvP("Day", 25, 5);
        assertThrows(POOBVsZombiesException.class, () -> pvp.placeZombie(3, 3, "Buckethead"));
    }

    @Test
    public void testSetChosenZombies() {
        ArrayList<String> zombiesList = new ArrayList<>();
        zombiesList.add("Basic");
        zombiesList.add("Conehead");
        pvp.setChosenZombies(zombiesList);
        assertEquals(zombiesList, pvp.getChosenZombies());
    }

    @Test
    public void testRemovePlantWithInvalidPosition() {
        assertThrows(POOBVsZombiesException.class, () -> pvp.removePlant(1, 1));
    }

    @Test
    public void testRemovePlantSuccessfully() throws POOBVsZombiesException {
        pvp.placePlant(1, 1, "Sunflower");
        pvp.removePlant(1, 1);
        HashMap<Integer, ArrayList<Plant>> plants = pvp.getPlants();
        assertTrue(plants.get(221).isEmpty() && pvp.getSunCollected() == 3000);
    }

    @Test
    public void testGetOriginalBrain() {
        int originalBrainValue = pvp.getOriginalBrain();
        assertEquals(3000, originalBrainValue);
    }

    @Test
    public void testGetWinnerByDefeatsPlants() {
        pvp.updateScore(basicZombie);
        assertEquals("Zombies", pvp.getWinnerByDefeats());
    }

    @Test
    public void testGetWinnerByDefeatsZombies() {
        Sunflower sunflower = new Sunflower(1,1);
        pvp.updateScore(sunflower);
        assertEquals("Plants", pvp.getWinnerByDefeats());
    }

    @Test
    public void testGetDetailedWinnerByDefeatsPlants() {
        pvp.updateScore(basicZombie);
        assertEquals("1 Zombies", pvp.getDetailedWinnerByDefeats());
    }

    @Test
    public void testGetDetailedWinnerByDefeatsZombies() {
        Sunflower sunflower = new Sunflower(1,1);
        pvp.updateScore(sunflower);
        assertEquals("1 Plants", pvp.getDetailedWinnerByDefeats());
    }

    @AfterEach
    public void tearDown() {
        pvp = null;
    }
}
