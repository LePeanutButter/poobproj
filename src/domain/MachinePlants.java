package domain;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * The MachinePlants class represents an abstract machine that manages the spawning of plants
 * on the game board at certain intervals, based on the time elapsed and the state of the game level.
 *
 * @author Botero Garcia, Santiago. Perilla Quintero, Laura Natalia.
 * @version December 15, 2024
 */
public abstract class MachinePlants implements Serializable {
    protected final Level level;
    protected double plantSpawnTime = 0;

    /**
     * Constructs a MachinePlant object with the given level.
     *
     * @param level The level where the plants will be spawned.
     */
    public MachinePlants(Level level) {
        this.level = level;
    }

    /**
     * Handles the spawning of plants at regular intervals.
     * Plants are spawned at specific rows based on the position of zombies
     * on the board.
     *
     */
    public void plantSpawn() {
        if (plantSpawnTime >= 6000) {
            int sunCollected = level.getSunCollected();
            ArrayList<String> available = new ArrayList<>();
            String[] plants = {"Sunflower", "Peashooter", "PotatoMine", "WallNut", "ECIPlant", "Evolve"};
            for (String p : plants) {
                int amount = level.getAmount(p);
                if (sunCollected >= amount) {
                    available.add(p);
                }
            }
            if (!available.isEmpty()) {
                spawnRandomPlant(available);
            }
            plantSpawnTime = 0;
        } else {
            plantSpawnTime += (double) 100 / 3;
        }
    }

    /**
     * Based on an Array of available plants, a plant spawns at a specific position on the board.
     * The specific behavior of this method depends on the subclass implementation.
     * It handles the logic for selecting and placing a plant on the board based on game conditions.
     *
     * @param available A list of plants that can be placed depending on their sun cost.
     */
    public abstract void spawnRandomPlant(ArrayList<String> available);
}