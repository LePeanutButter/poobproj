import domain.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

public class PvMTest {
    private PvM pvm;
    private Basic basicZombie;

    @BeforeEach
    public void setUp() throws POOBVsZombiesException {
        pvm = new PvM("Day", 5000, 3, 5);
    }

    @Test
    public void testUpdateScoreFirstZone() {
        basicZombie = new Basic(1, 1);
        pvm.updateScore(basicZombie);
        assertEquals(100, pvm.getScore());
    }

    @Test
    public void testUpdateScoreSecondZone() {
        basicZombie = new Basic(1, 3);
        pvm.updateScore(basicZombie);
        assertEquals(200, pvm.getScore());
    }

    @Test
    public void testUpdateScoreThirdZone() {
        basicZombie = new Basic(1, 5);
        pvm.updateScore(basicZombie);
        assertEquals(300, pvm.getScore());
    }

    @Test
    public void testUpdateScoreFourthZone() {
        basicZombie = new Basic(1, 7);
        pvm.updateScore(basicZombie);
        assertEquals(400, pvm.getScore());
    }

    @Test
    public void testUpdateScoreFifthZone() {
        basicZombie = new Basic(1, 9);
        pvm.updateScore(basicZombie);
        assertEquals(500, pvm.getScore());
    }

    @Test
    public void testUpdateProgress() {
        pvm.handleZombie("Basic");
        pvm.updateProgress();
        assertTrue(pvm.getWaveProgress() > 0);
    }

    @Test
    public void testTimeUpdate() {
        double initialTime = pvm.getTime();
        double updatedTime = pvm.updateTime();
        assertTrue(updatedTime < initialTime);
    }

    @Test
    public void testWaveUpdate() {
        pvm.updateWave();
        assertEquals(2, pvm.getWaves());
    }

    @Test
    public void testSettingChosenPlants() {
        ArrayList<String> chosenPlants = new ArrayList<>();
        chosenPlants.add("Sunflower");
        chosenPlants.add("Peashooter");
        pvm.setChosenPlants(chosenPlants);
        assertEquals(chosenPlants, pvm.getChosenPlants());
    }

    @Test
    public void testSettingNonExactSun() throws POOBVsZombiesException {
        pvm = new PvM("Night", 5005, 3, 5);
        assertEquals(5000, pvm.getSunCollected());
    }

    @Test
    public void testSettingNegativeSun() throws POOBVsZombiesException {
        pvm = new PvM("Night", -1, 3, 5);
        assertEquals(25, pvm.getSunCollected());
    }

    @Test
    public void testSettingOverTenThousandSun() throws POOBVsZombiesException {
        pvm = new PvM("Night", 10000, 3, 5);
        assertEquals(9975, pvm.getSunCollected());
    }

    @Test
    public void testSettingNegativeWaves() throws POOBVsZombiesException {
        pvm = new PvM("Day", 5000, -1, 5);
        assertEquals(1, pvm.getOriginalWaves());
    }

    @Test
    public void testSettingOverTenWaves() throws POOBVsZombiesException {
        pvm = new PvM("Day", 5000, 11, 5);
        assertEquals(10, pvm.getOriginalWaves());
    }

    @Test
    public void testGettingOriginalTime() {
        for (int i = 0; i < 5; i++) {
            pvm.updateTime();
        }
        assertEquals(300000, pvm.getOriginalTime());
    }

    @Test
    public void testThrowsInvalidTime() {
        assertThrows(POOBVsZombiesException.class, () -> new PvM("Day", 50, 3, 2));
    }

    @Test
    public void testThrowsInvalidLevel() {
        assertThrows(POOBVsZombiesException.class, () -> new PvM("Grass", 50, 3, 4));
    }

    @Test
    public void testSunCollected() {
        pvm.collectSun(10000);
        assertEquals(15000, pvm.getSunCollected());
    }

    @Test
    public void testPlaceSunflowerWithEnoughSun() throws POOBVsZombiesException {
        pvm.placePlant(1,1,"Sunflower");
        int size = 0;
        for (ArrayList<Plant> array : pvm.getPlants().values()) {
            size += array.size();
        }
        assertEquals(1, size);
    }

    @Test
    public void testPlacePeashooterWithEnoughSun() throws POOBVsZombiesException {
        pvm.placePlant(1,1,"Peashooter");
        int size = 0;
        for (ArrayList<Plant> array : pvm.getPlants().values()) {
            size += array.size();
        }
        assertEquals(1, size);
    }

    @Test
    public void testPlaceECIPlantWithEnoughSun() throws POOBVsZombiesException {
        pvm.placePlant(1,1,"ECIPlant");
        int size = 0;
        for (ArrayList<Plant> array : pvm.getPlants().values()) {
            size += array.size();
        }
        assertEquals(1, size);
    }

    @Test
    public void testPlaceWallNutWithEnoughSun() throws POOBVsZombiesException {
        pvm.placePlant(1,1,"WallNut");
        int size = 0;
        for (ArrayList<Plant> array : pvm.getPlants().values()) {
            size += array.size();
        }
        assertEquals(1, size);
    }

    @Test
    public void testPlacePotatoMineWithEnoughSun() throws POOBVsZombiesException {
        pvm.placePlant(1,1,"PotatoMine");
        int size = 0;
        for (ArrayList<Plant> array : pvm.getPlants().values()) {
            size += array.size();
        }
        assertEquals(1, size);
    }

    @Test
    public void testPlaceEvolveWithEnoughSun() throws POOBVsZombiesException {
        pvm.placePlant(1,1,"Evolve");
        int size = 0;
        for (ArrayList<Plant> array : pvm.getPlants().values()) {
            size += array.size();
        }
        assertEquals(1, size);
    }

    @Test
    public void testThrowPlacePlantWithoutEnoughSun() throws POOBVsZombiesException {
        pvm = new PvM("Day", 25, 3, 5);
        assertThrows(POOBVsZombiesException.class, () -> pvm.placePlant(1,1, "Peashooter"));
    }

    @Test
    public void testThrowUnknownPlant() {
        assertThrows(POOBVsZombiesException.class, () -> pvm.placePlant(1,1,"CatTail"));
    }

    @Test
    public void testThrowTakenPositionToPlant() throws POOBVsZombiesException {
        pvm.placePlant(1,1,"Sunflower");
        assertThrows(POOBVsZombiesException.class, () -> pvm.placePlant(1,1, "Peashoooter"));
    }

    @Test
    public void testGetsSunflowerSunCost() {
        int sunCost = pvm.getAmount("Sunflower");
        assertEquals(50, sunCost);
    }

    @Test
    public void testGetsPeashooterSunCost() {
        int sunCost = pvm.getAmount("Peashooter");
        assertEquals(100, sunCost);
    }

    @Test
    public void testGetsWallNutSunCost() {
        int sunCost = pvm.getAmount("WallNut");
        assertEquals(50, sunCost);
    }

    @Test
    public void testGetsPotatoMineSunCost() {
        int sunCost = pvm.getAmount("PotatoMine");
        assertEquals(25, sunCost);
    }

    @Test
    public void testGetsECIPlantSunCost() {
        int sunCost = pvm.getAmount("ECIPlant");
        assertEquals(75, sunCost);
    }

    @Test
    public void testGetsEvolveSunCost() {
        int sunCost = pvm.getAmount("Evolve");
        assertEquals(200, sunCost);
    }

    @Test
    public void testRemovePlacedPlant() throws POOBVsZombiesException {
        pvm.placePlant(1,1,"Sunflower");
        pvm.removePlant(1,1);
        int size = 0;
        for (ArrayList<Plant> array : pvm.getPlants().values()) {
            for (Plant _ : array) {
                ++size;
            }
        }
        assertEquals(0, size);
    }

    @Test
    public void testThrowInvalidPositionToRemove() {
        assertThrows(POOBVsZombiesException.class, () -> pvm.removePlant(1,1));
    }

    @Test
    public void testPlaceBasic() throws POOBVsZombiesException {
        pvm.placeZombie(1,1,"Basic");
        int size = 0;
        for (ArrayList<Zombie> array : pvm.getZombies().values()) {
            for (Zombie _ : array) {
                ++size;
            }
        }
        assertEquals(1, size);
    }

    @Test
    public void testPlaceConehead() throws POOBVsZombiesException {
        pvm.placeZombie(1,1,"Conehead");
        int size = 0;
        for (ArrayList<Zombie> array : pvm.getZombies().values()) {
            for (Zombie _ : array) {
                ++size;
            }
        }
        assertEquals(1, size);
    }

    @Test
    public void testPlaceBuckethead() throws POOBVsZombiesException {
        pvm.placeZombie(1,1,"Buckethead");
        int size = 0;
        for (ArrayList<Zombie> array : pvm.getZombies().values()) {
            for (Zombie _ : array) {
                ++size;
            }
        }
        assertEquals(1, size);
    }

    @Test
    public void testPlaceECIZombie() throws POOBVsZombiesException {
        pvm.placeZombie(1,1,"ECIZombie");
        int size = 0;
        for (ArrayList<Zombie> array : pvm.getZombies().values()) {
            for (Zombie _ : array) {
                ++size;
            }
        }
        assertEquals(1, size);
    }

    @Test
    public void testThrowPlaceUnknownZombie() {
        assertThrows(POOBVsZombiesException.class, () -> pvm.placeZombie(1,1,"Zomboni"));
    }

    @Test
    public void testOriginalSun() throws POOBVsZombiesException {
        pvm.placePlant(1,1,"Sunflower");
        pvm.placePlant(1,2,"Peashooter");
        boolean sun = pvm.getOriginalSun() == 5000 && pvm.getSunCollected() == 4850;
        assertTrue(sun);
    }

    @Test
    public void testOriginalWaves() {
        pvm.decreaseWaves();
        assertEquals(3, pvm.getOriginalWaves());
    }

    @Test
    public void testChosenLawn() {
        String lawn = pvm.getLawn().getClass().getSimpleName();
        assertEquals("Day", lawn);
    }

    @Test
    public void testGetPlacedMowers() {
        assertEquals(5, pvm.getMowers().size());
    }

    @Test
    public void testGetsZombiesAmountPerWave() {
        HashMap<String, Integer> amount = pvm.getZombiesAmount();
        int total = 0;
        for (HashMap.Entry<String, Integer> set : amount.entrySet()) {
            total += set.getValue();
        }
        assertTrue(total >= 20);
    }

    @Test
    public void testSpawnZombieOriginal() {
        for (int i = 0; i < 602; i++) {
            pvm.zombieSpawn();
            pvm.updateTime();
        }
        int size = 0;
        for (ArrayList<Zombie> array : pvm.getZombies().values()) {
            size += array.size();
        }
        assertEquals(1, size);
    }

    @Test
    public void testSpawnMultipleZombies() {
        for (int i = 0; i < 1204; i++) {
            pvm.zombieSpawn();
            pvm.updateTime();
        }
        int size = 0;
        for (ArrayList<Zombie> array : pvm.getZombies().values()) {
            size += array.size();
        }
        boolean condition = size >= 3 && size <=5;
        assertTrue(condition);
    }

    @Test
    public void testWaveUpdateAfterZombiesSpawned() {
        for (int i = 0; i < 8428; i++) {
            pvm.zombieSpawn();
            pvm.updateTime();
        }
        assertTrue(pvm.getWaves() < pvm.getOriginalWaves());
    }

    @Test
    public void testAddingAProjectile() {
        Peashooter peashooter = new Peashooter(0,0);
        for (int i = 0; i < 46; i++) {
            peashooter.shoot();
        }
        int y = 128;
        Projectile projectile = peashooter.shoot();
        assertDoesNotThrow(() -> pvm.addProjectile(y, projectile));
    }

    @Test
    public void testGettingProjectiles() {
        Peashooter peashooter = new Peashooter(0,0);
        for (int i = 0; i < 46; i++) {
            peashooter.shoot();
        }
        int y = 128;
        Projectile projectile = peashooter.shoot();
        pvm.addProjectile(y, projectile);
        HashMap<Integer, ArrayList<Projectile>> projectiles = pvm.getProjectiles();
        assertEquals(1, projectiles.get(y).size());
    }

    @Test
    public void testRemovingPlantLine() throws POOBVsZombiesException {
        pvm.placePlant(2,2,"WallNut");
        pvm.placePlant(2,3,"Evolve");
        pvm.placePlant(2,0,"Sunflower");
        int y = 334;
        ArrayList<Plant> toRemove = new ArrayList<>();
        for (ArrayList<Plant> array : pvm.getPlants().values()) {
            for (Plant p: array) {
                if (!p.getClass().getSimpleName().equals("Sunflower")) {
                    toRemove.add(p);
                }
            }
        }
        pvm.removePlants(y, toRemove);
        int size = 0;
        for (ArrayList<Plant> p : pvm.getPlants().values()) {
            size += p.size();
        }
        assertEquals(1, size);
    }

    @Test
    public void testRemovingZombieLine() throws POOBVsZombiesException {
        pvm.placeZombie(1,5,"Buckethead");
        pvm.placeZombie(1,10,"ECIZombie");
        pvm.placeZombie(1,3,"Basic");
        int y = 221;
        ArrayList<Zombie> toRemove = new ArrayList<>();
        for (ArrayList<Zombie> array : pvm.getZombies().values()) {
            for (Zombie z: array) {
                if (z.getClass().getSimpleName().equals("Basic")) {
                    toRemove.add(z);
                }
            }
        }
        pvm.removeZombies(y, toRemove);
        int size = 0;
        for (ArrayList<Zombie> z : pvm.getZombies().values()) {
            size += z.size();
        }
        assertEquals(2, size);
    }

    @Test
    public void testRemovingProjectileLine() {
        Peashooter peashooter = new Peashooter(0,0);
        int y = 108;
        ArrayList<Projectile> toRemove = new ArrayList<>();
        for (int i = 0; i < 137; i++) {
            Projectile projectile = peashooter.shoot();
            if (projectile != null) {
                pvm.addProjectile(y + 20, projectile);
                toRemove.add(projectile);
            }
        }
        Projectile projectile = peashooter.shoot();
        pvm.addProjectile(y + 20, projectile);
        pvm.removeProjectiles(y, toRemove);
        int size = 0;
        for (ArrayList<Projectile> p : pvm.getProjectiles().values()) {
            size += p.size();
        }
        assertEquals(1, size);
    }

    @Test
    public void testRemovingMower() {
        int originalSize = pvm.getMowers().size();
        pvm.removeMower(334);
        int actualSize = pvm.getMowers().size();
        assertTrue(originalSize > actualSize);
    }

    @AfterEach
    public void tearDown() {
        pvm = null;
        if (basicZombie != null) {
            basicZombie = null;
        }
    }
}
