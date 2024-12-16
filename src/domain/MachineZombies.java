package domain;

import java.io.Serializable;
import java.util.*;

/**
 * The MachineZombies class represents an abstract machine that manages the spawning of zombies
 * on the game board at certain intervals, based on the time elapsed and the state of the game level.
 *
 * @author Botero Garcia, Santiago. Perilla Quintero, Laura Natalia.
 * @version December 15, 2024
 */
public abstract class MachineZombies implements Serializable {
    protected final Level level;
    protected double zombieSpawnTime = 0;
    private int zombiesMinimum = 0;
    
    /**
     * Constructs a MachineZombies object with the given level.
     *
     * @param level The level where the zombies will be spawned.
     */
    public MachineZombies(Level level) {
        this.level = level;
    }

    /**
     * Handles the spawning of zombies at regular intervals.
     * Zombies are spawned at random rows when the elapsed time and the current number of zombies
     * on the board meet specific conditions
     *
     */
    public void zombieSpawn() {
        double timer = level.getOriginalTime() - level.getTime();
        if (timer >= 20000) {
            Random random = new Random();
            if (zombiesMinimum == 0) {
                int randomRow = random.nextInt(5);
                try {
                    level.placeZombie(randomRow, 10, "Basic");
                } catch (POOBVsZombiesException _) {
                }
                zombiesMinimum = getZombieSize();
            } else if (zombieSpawnTime >= 20000 || getZombieSize() < zombiesMinimum) {
                int zombiesToSpawn = random.nextInt(2, 4);
                ArrayList<Integer> array = new ArrayList<>();
                for (int i = 0; i < 4; i++) {
                    array.add(i);
                }
                Collections.shuffle(array);
                List<Integer> randomRows = array.subList(0, zombiesToSpawn);
                for (Integer row : randomRows) {
                    spawnRandomZombie(random, row);
                }
                zombiesMinimum = getZombieSize() - zombiesToSpawn + 1;
            } else {
                zombieSpawnTime += (double) 100 / 3;
            }
        }
    }

    /**
     * Returns the total number of zombies currently on the board.
     *
     * @return The total number of zombies across all rows on the board.
     */
    private int getZombieSize() {
        int count = 0;
        for (ArrayList<Zombie> array : level.zombies.values()) {
            count += array.size();
        }
        return count;
    }

    /**
     * Spawns a random zombie at the specified row of the level.
     * The specific behavior of this method depends on the subclass implementation.
     * It handles the logic for selecting and placing a zombie on the board based on game conditions.
     *
     */
    protected abstract void spawnRandomZombie(Random random, int row);
}