package domain;
import java.util.*;

/**
 * The ZombiesStrategic class represents a specific implementation of zombie spawning in the game,
 * where the zombie type to be spawned depends on the passage of time during the level.
 * It extends the MachineZombies class and customizes the zombie spawning logic based on time intervals
 * in the level.
 *
 * @author Botero Garcia, Santiago. Perilla Quintero, Laura Natalia.
 * @version December 15, 2024
 */
public class ZombiesStrategic extends MachineZombies {

    /**
     * Constructs a new ZombiesStrategic instance for the specified level.
     * This constructor calls the superclass constructor with the level parameter to initialize the level
     * and set up the necessary attributes.
     *
     * @param level The level where zombies will be spawned.
     */
    public ZombiesStrategic(Level level) {
        super(level);
    }

    /**
     * Spawns a zombie in the specified row of the level, depending on the remaining time in the level.
     * The zombie type is chosen based on the percentage of the total time that has elapsed
     *
     * @param random The random number generator.
     *               This parameter is not used in this implementation.
     * @param row    The row in the level where the zombie should be spawned.
     */
    @Override
    protected void spawnRandomZombie(Random random, int row) {
        double timer = level.getOriginalTime() - level.getTime();
        if (timer < level.getOriginalTime() * 0.25) {
            try {
                level.placeZombie(row, 10, "Basic");
            } catch (POOBVsZombiesException _) {
            }
        } else if (timer >= level.getOriginalTime() * 0.25 && timer < level.getOriginalTime() * 0.50) {
            try {
                level.placeZombie(row, 10, "Conehead");
            } catch (POOBVsZombiesException _) {
            }
        } else if (timer >= level.getOriginalTime() * 0.50 && timer < level.getOriginalTime() * 0.75) {
            try {
                level.placeZombie(row, 10, "ECIZombie");
            } catch (POOBVsZombiesException _) {
            }
        } else {
            try {
                level.placeZombie(row, 10, "Buckethead");
            } catch (POOBVsZombiesException _) {
            }
        }
        zombieSpawnTime = 0;
    }
}