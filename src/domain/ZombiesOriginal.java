package domain;

import java.util.*;

/**
 * The ZombiesOriginal class represents a specific implementation of zombie spawning in the game.
 * It extends the MachineZombies class and provides custom logic for spawning zombies based on the
 * current level and the game's mode (PvM or other).
 *
 * @author Botero Garcia, Santiago. Perilla Quintero, Laura Natalia.
 * @version December 15, 2024
 */
public class ZombiesOriginal extends MachineZombies {

    /**
     * Constructs a new ZombiesOriginal instance for the specified level.
     * This constructor calls the superclass constructor with the level parameter to initialize the level
     * and set up the necessary attributes.
     *
     * @param level The level where zombies will be spawned.
     */
    public ZombiesOriginal(Level level) {
        super(level);
    }

    /**
     * Spawns a random zombie in the specified row of the level.
     * If the level is of type PvM, the zombie to be spawned is chosen based on the available
     * zombies and their quantities. If there are no zombies available, the wave is updated.
     * Otherwise, a random zombie is selected and placed in the row.
     * For other types of levels, a random zombie is selected from a predefined list and placed in the row.
     *
     * @param random The random number generator used to select a zombie.
     * @param row    The row in the level where the zombie should be spawned.
     */
    @Override
    protected void spawnRandomZombie(Random random, int row) {
        if (level.getClass().getSimpleName().equals("PvM")) {
            ArrayList<String> zombiesArray = new ArrayList<>();
            HashMap<String, Integer> zombies = ((PvM)level).getZombiesAmount();
            for (HashMap.Entry<String, Integer> set : zombies.entrySet()) {
                String zombieName = set.getKey();
                int amount = set.getValue();
                if (amount > 0) {
                    zombiesArray.add(zombieName);
                }
            }
            if (!zombiesArray.isEmpty()) {
                String randomZombie = zombiesArray.get(random.nextInt(zombiesArray.size()));
                try {
                    level.placeZombie(row, 10, randomZombie);
                    zombieSpawnTime = 0;
                } catch (POOBVsZombiesException _) {
                }
            } else {
                ((PvM)level).updateWave();
            }
        } else {
            String[] zombiesArray = {"Basic", "Buckethead", "Conehead", "ECIZombie"};
            String randomZombie = zombiesArray[random.nextInt(zombiesArray.length)];
            try {
                level.placeZombie(row, 10, randomZombie);
                zombieSpawnTime = 0;
            } catch (POOBVsZombiesException _) {
                
            }

        }
    }

}