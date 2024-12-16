package domain;

import java.awt.*;
import java.util.*;

/**
 * The PvM (Player vs. Machine) class represents a game level where plants fight against waves of zombies.
 *
 * @author Botero Garcia, Santiago. Perilla Quintero, Laura Natalia.
 * @version December 07 2024
 */
public final class PvM extends Level {
    private final HashMap<String, Integer> zombiesAmount;
    private final ZombiesOriginal zombiesOriginal;
    private int waveProgress = 0;
    private int totalZombies = 0;
    private Rectangle[] zones;
    private int score;

    /**
     * Constructs a PvM level with the specified lawn type, sun amount, wave count, and time duration.
     *
     * @param lawn  The type of lawn for the level.
     * @param sun   The starting sun amount for the player.
     * @param waves The number of waves in the level.
     * @param time  The time duration of the level (in minutes, between 3 and 10).
     * @throws POOBVsZombiesException If the time is not within the allowed range.
     */
    public PvM(String lawn, int sun, int waves, double time) throws POOBVsZombiesException {
        super(lawn, sun, waves, time);
        this.zombiesAmount = new HashMap<>();
        zombiesOriginal = new ZombiesOriginal(this);
        randomizeZombies();
        initializeTotalZombies();
        prepareZones();
    }

    /**
     * Initializes the total number of zombies for the wave based on the zombie count per wave.
     */
    private void initializeTotalZombies() {
        for (Integer count : zombiesAmount.values()) {
            totalZombies += count;
        }
    }

    /**
     * Prepares the zones for scoring by creating a set of predefined areas on the lawn.
     */
    public void prepareZones() {
        zones = new Rectangle[5];
        zones[0] = new Rectangle(306, 66, 193, 630);
        zones[1] = new Rectangle(499, 66, 193, 630);
        zones[2] = new Rectangle(692, 66, 193, 630);
        zones[3] = new Rectangle(885, 66, 193, 630);
        zones[4] = new Rectangle(1078, 66, 97, 630);
    }

    /**
     * Randomizes the zombie types and assigns a random amount to each type for the current wave.
     */
    public void randomizeZombies() {
        Random random = new Random();
        String[] zombieNames = {"Basic", "Conehead", "Buckethead", "ECIZombie"};
        for (String z : zombieNames) {
            int randomNumber = random.nextInt(5, 10);
            zombiesAmount.put(z, randomNumber);
        }
    }

    /**
     * Handles the decrement of the zombie count when a zombie is defeated.
     *
     * @param zombieName The name of the zombie type to decrement.
     */
    @Override
    public void handleZombie(String zombieName) {
        zombiesAmount.compute(zombieName, (_, amount) -> {
            int num = 0;
            if (amount != null) {
                num = amount - 1;
            }
            return num;
        });
    }

    /**
     * Updates the score based on the zone the zombie is currently located in.
     *
     * @param zombie The hitbox representing the zombie.
     */
    @Override
    public void updateScore(Hitbox zombie) {
        String zombieName = zombie.getClass().getSimpleName();
        String[] possibleZombies = {"Basic", "Conehead", "Buckethead", "ECIZombie"};
        if (Arrays.asList(possibleZombies).contains(zombieName)) {
            HashMap<String, Integer> multiplicationFactor = new HashMap<>();
            multiplicationFactor.put("Basic", 1);
            multiplicationFactor.put("Conehead", 2);
            multiplicationFactor.put("ECIZombie", 3);
            multiplicationFactor.put("Buckethead", 4);
            Rectangle z = zombie.getHitbox();
            int points = 0;
            if (zones[0].intersects(z)) {
                points = 100;
            } else if (zones[1].intersects(z)) {
                points = 200;
            } else if (zones[2].intersects(z)) {
                points = 300;
            } else if (zones[3].intersects(z)) {
                points = 400;
            } else if (zones[4].intersects(z)) {
                points = 500;
            }
            this.score += points * multiplicationFactor.get(zombieName);
        }
    }

    /**
     * Updates the wave progress based on the number of zombies eliminated.
     */
    public void updateProgress() {
        int remainingZombies = 0;
        for (Integer count : zombiesAmount.values()) {
            remainingZombies += count;
        }
        int zombiesEliminated = totalZombies - remainingZombies;
        waveProgress = 100 * zombiesEliminated / totalZombies;
    }

    /**
     * Resets the wave progress, randomizes the zombies, and restarts the total number of zombies.
     */
    public void updateWave() {
        if (waves > 0) {
            waveProgress = 0;
            randomizeZombies();
            initializeTotalZombies();
            --waves;
        }
    }

    /**
     * Gets the progress of the current wave as a percentage.
     *
     * @return The percentage of wave progress.
     */
    public int getWaveProgress() {
        return waveProgress;
    }

    /**
     * Gets the amount of zombies in each wave, categorized by type.
     *
     * @return A map containing the zombie types and their respective counts in the current wave.
     */
    public HashMap<String, Integer> getZombiesAmount() {
        return zombiesAmount;
    }

    /**
     * Gets the current score of the player.
     *
     * @return The current score of the player.
     */
    public int getScore() {
        return score;
    }

    /**
     * Updates the state of the machines in the PvM level.
     * 
     */
    public void zombieSpawn() {
        zombiesOriginal.zombieSpawn();
    }
}
