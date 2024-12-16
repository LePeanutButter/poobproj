import domain.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class MvMTest {
    private MvM mvm;
    private Basic basicZombie;

    @BeforeEach
    public void setUp() throws POOBVsZombiesException {
        mvm = new MvM("Day", 5000, "strategic", 5);
        basicZombie = new Basic(1, 1);
    }

    @Test
    public void testHandleZombie() {
        assertDoesNotThrow(() -> mvm.handleZombie("Basic"));
    }

    @Test
    public void testUpdateScore() {
        assertDoesNotThrow(() -> mvm.updateScore(basicZombie));
    }

    @Test
    public void testUpdateMachines() {
        assertDoesNotThrow(() -> mvm.updateMachines());
    }

    @Test
    public void testThrowInvalidMachine() {
        assertThrows(POOBVsZombiesException.class, () -> mvm = new MvM("Day", 5000, "glue", 5));
    }

    @Test
    public void testGettingMachine() {
        assertEquals("strategic", mvm.getMachine());
    }

    @Test
    public void testSpawnZombieBasic() {
        for (int i = 0; i < 602; i++) {
            mvm.updateMachines();
            mvm.updateTime();
        }
        boolean instance = false;
        for (ArrayList<Zombie> array : mvm.getZombies().values()) {
            if (!array.isEmpty()) {
                Zombie lastZombie = array.getLast();
                if (lastZombie.getClass().getSimpleName().equals("Basic")) {
                    instance = true;
                }
            }
        }
        assertTrue(instance);
    }

    @Test
    public void testSpawnZombieConehead() {
        for (int i = 0; i < 2408; i++) {
            mvm.updateMachines();
            mvm.updateTime();
        }
        boolean instance = false;
        for (ArrayList<Zombie> array : mvm.getZombies().values()) {
            if (!array.isEmpty()) {
                Zombie lastZombie = array.getLast();
                if (lastZombie.getClass().getSimpleName().equals("Conehead")) {
                    instance = true;
                }
            }
        }
        assertTrue(instance);
    }

    @Test
    public void testSpawnZombieECIZombie() {
        for (int i = 0; i < 4816; i++) {
            mvm.updateMachines();
            mvm.updateTime();
        }
        boolean instance = false;
        for (ArrayList<Zombie> array : mvm.getZombies().values()) {
            if (!array.isEmpty()) {
                Zombie lastZombie = array.getLast();
                if (lastZombie.getClass().getSimpleName().equals("ECIZombie")) {
                    instance = true;
                }
            }
        }
        assertTrue(instance);
    }

    @Test
    public void testSpawnZombieBuckethead() {
        for (int i = 0; i < 7224; i++) {
            mvm.updateMachines();
            mvm.updateTime();
        }
        boolean instance = false;
        for (ArrayList<Zombie> array : mvm.getZombies().values()) {
            if (!array.isEmpty()) {
                Zombie lastZombie = array.getLast();
                if (lastZombie.getClass().getSimpleName().equals("Buckethead")) {
                    instance = true;
                }
            }
        }
        assertTrue(instance);
    }

    @Test
    public void testSpawnECIPlant() throws POOBVsZombiesException {
        mvm = new MvM("Day", 75, "strategic", 5);
        for (int i = 0; i < 182; i++) {
            mvm.updateMachines();
            mvm.updateTime();
        }
        boolean instance = false;
        for (ArrayList<Plant> array : mvm.getPlants().values()) {
            if (!array.isEmpty()) {
                Plant lastPlant = array.getLast();
                if (lastPlant.getClass().getSimpleName().equals("ECIPlant")) {
                    instance = true;
                }
            }
        }
        assertTrue(instance);
    }

    @Test
    public void testSpawnSunflower() throws POOBVsZombiesException {
        mvm = new MvM("Day", 50, "strategic", 5);
        for (int i = 0; i < 182; i++) {
            mvm.updateMachines();
            mvm.updateTime();
        }
        boolean instance = false;
        for (ArrayList<Plant> array : mvm.getPlants().values()) {
            if (!array.isEmpty()) {
                Plant lastPlant = array.getLast();
                if (lastPlant.getClass().getSimpleName().equals("Sunflower")) {
                    instance = true;
                }
            }
        }
        assertTrue(instance);
    }

    @Test
    public void testSpawnRandomZombieAfterBasic() throws POOBVsZombiesException {
        mvm = new MvM("Day", 5000, "original", 5);
        for (int i = 0; i < 1204; i++) {
            mvm.updateMachines();
            mvm.updateTime();
        }
        int size = 0;
        for (ArrayList<Zombie> array : mvm.getZombies().values()) {
            size += array.size();
        }
        assertTrue(size >= 2 && size <= 5);
    }

    @Test
    public void testPlaceSunflower() throws POOBVsZombiesException {
        mvm = new MvM("Day", 50, "original", 5);
        for (int i = 0; i < 182; i++) {
            mvm.updateMachines();
            mvm.updateTime();
        }
        boolean instance = false;
        for (ArrayList<Plant> array: mvm.getPlants().values()) {
            for (Plant p: array) {
                if (p.getClass().getSimpleName().equals("Sunflower")) {
                    instance = true;
                    break;
                }
            }
        }
        assertTrue(instance);
    }

    @AfterEach
    public void tearDown() {
        mvm = null;
        basicZombie = null;
    }
}