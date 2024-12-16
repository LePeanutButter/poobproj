package domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * The Level class represents a game level with different types of plants, zombies, and other game objects.
 *
 * @author Botero Garcia, Santiago. Perilla Quintero, Laura Natalia.
 * @version November 22 2024
 */
public abstract class Level implements Serializable {
    private int sunCollected = 50;
    private int originalSun;
    private int originalWaves;
    protected double originalTime;
    protected Lawn lawn;
    protected ArrayList<String> chosenPlants;
    protected int waves;
    protected double time;
    protected HashMap<Integer, Mower> mowers;
    protected HashMap<Integer, ArrayList<Plant>> plants;
    protected HashMap<Integer, ArrayList<Projectile>> projectiles;
    protected HashMap<Integer, ArrayList<Zombie>> zombies;

    /**
     * Constructor for initializing a new level with a specified lawn type, sun collected, and number of waves.
     *
     * @param lawn  The type of lawn ("Day" or "Night").
     * @param sun   The initial amount of sun collected.
     * @param waves The number of waves of zombies in the level.
     * @throws POOBVsZombiesException If the lawn type is invalid or other exceptions occur.
     */
    public Level(String lawn, int sun, int waves, double time) throws POOBVsZombiesException {
        switch (lawn) {
            case "Day" -> this.lawn = new Day();
            case "Night" -> this.lawn = new Night();
            default -> throw new POOBVsZombiesException(POOBVsZombiesException.UNKNOWN_LAWN);
        }
        setSunCollected(sun);
        setWaves(waves);
        prepareTime(time);
        plants = new HashMap<>();
        zombies = new HashMap<>();
        projectiles = new HashMap<>();
        mowers = new HashMap<>();
        chosenPlants = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            int[] position = Hitbox.convertPlantPosition(i, 0);
            Mower mower = new Mower(i);
            plants.put(position[1], new ArrayList<>());
            zombies.put(position[1], new ArrayList<>());
            projectiles.put(position[1] + 20, new ArrayList<>());
            mowers.put(mower.getHitbox().y, mower);
        }
    }

    /*
     * Configures the game time for the match.
     *
     * @param time The duration of the match in minutes. Must be between 3 and 10 minutes.
     * @throws POOBVsZombiesException If the provided time is outside the valid range.
     */
    private void prepareTime(double time) throws POOBVsZombiesException {
        this.time = time;
        if (time >= 3 && time <= 10) {
            this.time = time * 60000;
        } else {
            throw new POOBVsZombiesException(POOBVsZombiesException.INVALID_TIME);
        }
        this.originalTime = this.time;
    }


    /**
     * Sets the list of chosen plants for the player.
     *
     * @param chosenPlants A list of plant names chosen by the player for the game.
     */
    public void setChosenPlants(ArrayList<String> chosenPlants) {
        this.chosenPlants.addAll(chosenPlants);
    }

    /**
     * Returns the list of plants chosen by the player for the game.
     *
     * @return An ArrayList containing the names of the chosen plants.
     */
    public ArrayList<String> getChosenPlants() {
        return chosenPlants;
    }

    /**
     * Sets the amount of sun collected for the level, ensuring it is within the valid range of 25 to 9975.
     *
     * @param sun The sun amount to set.
     */
    private void setSunCollected(int sun) {
        if (sun >= 25 && sun < 10000) {
            if (sun % 25 == 0) {
                sunCollected = sun;
            } else {
                sunCollected = Math.round(sun / 25.0f) * 25;
            }
        } else {
            if (sun < 25) {
                sunCollected = 25;
            } else {
                sunCollected = 9975;
            }
        }
        originalSun = sunCollected;
    }

    /**
     * Sets the number of waves for the level, ensuring it is within the valid range of 1 to 10.
     *
     * @param waves The number of waves to set.
     */
    private void setWaves(int waves) {
        if (waves >= 1 && waves <= 10) {
            this.waves = waves;
        } else {
            if (waves < 1) {
                this.waves = 1;
            } else {
                this.waves = 10;
            }
        }
        originalWaves = this.waves;
    }

    /**
     * Decreases the number of waves remaining by one, ensuring it does not go below zero.
     */
    public void decreaseWaves() {
        if (waves > 0) {
            waves--;
        }
    }


    /**
     * Returns the current number of waves remaining to be played in the level.
     *
     * @return The number of waves still left to play in the current level.
     */
    public int getWaves() {
        return waves;
    }

    /**
     * Updates the time by decreasing it over time. The time decreases by a fixed rate.
     *
     * @return The updated time in milliseconds.
     */
    public double updateTime() {
        time -= (double) 100 / 3;
        return time;
    }

    /**
     * Gets the current remaining time for the level.
     *
     * @return The current time remaining in milliseconds.
     */
    public double getTime() {
        return time;
    }

    /**
     * Gets the original time for the level.
     *
     * @return The original time in minutes.
     */
    public double getOriginalTime() {
        return originalTime;
    }

    /**
     * Places a specified plant on the lawn at the given position.
     * The plant type is determined by the provided name.
     *
     * @param row       The row position for the plant.
     * @param column    The column position for the plant.
     * @param plantName The type of plant to place.
     */
    public void placePlant(int row, int column, String plantName) throws POOBVsZombiesException {
        int[] position = Hitbox.convertPlantPosition(row, column);
        int xPosition = position[0];
        ArrayList<Plant> plantLine = plants.get(position[1]);
        if (plantLine != null) {
            for (Plant p : plantLine) {
                if (p.getHitbox().x == xPosition) {
                    throw new POOBVsZombiesException(POOBVsZombiesException.INVALID_POSITION);
                }
            }
        }
        Plant plant = getPlant(row, column, plantName);
        int yPosition = plant.getHitbox().y;
        plants.get(yPosition).add(plant);
    }

    /**
     * Removes a plant from the lawn at the given position.
     *
     * @param row    The row position of the plant to remove.
     * @param column The column position of the plant to remove.
     * @throws POOBVsZombiesException If the plant cannot be removed due to invalid position.
     */
    public void removePlant(int row, int column) throws POOBVsZombiesException {
        int[] position = Hitbox.convertPlantPosition(row, column);
        int xPosition = position[0];
        int yPosition = position[1];
        if (plants.containsKey(yPosition)) {
            for (Plant p : plants.get(yPosition)) {
                if (p.getHitbox().x == xPosition) {
                    plants.get(yPosition).remove(p);
                    return;
                }
            }
        }
        throw new POOBVsZombiesException(POOBVsZombiesException.INVALID_SHOVEL);
    }

    /**
     * Places a specified zombie on the lawn at the given position.
     *
     * @param row        The row position for the zombie.
     * @param column     The column position for the zombie.
     * @param zombieName The type of zombie to place.
     * @return The placed zombie.
     * @throws POOBVsZombiesException If the zombie name is unknown or invalid.
     */
    public Zombie placeZombie(int row, int column, String zombieName) throws POOBVsZombiesException {
        Zombie zombie;
        switch (zombieName) {
            case "Basic" -> zombie = new Basic(row, column);
            case "Conehead" -> zombie = new Conehead(row, column);
            case "Buckethead" -> zombie = new Buckethead(row, column);
            case "ECIZombie" -> zombie = new ECIZombie(row, column);
            default -> throw new POOBVsZombiesException(POOBVsZombiesException.UNKNOWN_ZOMBIE);
        }
        handleZombie(zombieName);
        int yPosition = zombie.getHitbox().y;
        zombies.get(yPosition).add(zombie);
        return zombie;
    }

    /*
     * Places a specified plant on the lawn at the given position.
     *
     * @param row       The row position for the zombie.
     * @param column    The column position for the zombie.
     * @param plantName The type of plant to place.
     * @return The placed zombie.
     * @throws POOBVsZombiesException If the plant name is unknown or invalid.
     */
    private Plant getPlant(int row, int column, String plantName) throws POOBVsZombiesException {
        Plant plant;
        switch (plantName) {
            case "Sunflower" -> plant = new Sunflower(row, column);
            case "ECIPlant" -> plant = new ECIPlant(row, column);
            case "WallNut" -> plant = new WallNut(row, column);
            case "Peashooter" -> plant = new Peashooter(row, column);
            case "PotatoMine" -> plant = new PotatoMine(row, column);
            case "Evolve" -> plant = new Evolve(row, column);
            default -> throw new POOBVsZombiesException(POOBVsZombiesException.UNKNOWN_PLANT);
        }
        final int amount = plant.getSunCost();
        if (amount > sunCollected) {
            throw new POOBVsZombiesException(POOBVsZombiesException.NOT_ENOUGH_SUN);
        }
        sunCollected -= amount;
        return plant;
    }


    /**
     * Retrieves the amount of sun required to plant a specified plant.
     *
     * @param plantName The name of the plant for which the sun cost is required.
     * @return The amount of sun needed to plant the specified plant.
     */
    public int getAmount(String plantName) {
        int amount = 0;
        switch (plantName) {
            case ("Sunflower") -> amount = new Sunflower(0, 0).getSunCost();
            case ("Peashooter") -> amount = new Peashooter(0, 0).getSunCost();
            case ("PotatoMine") -> amount = new PotatoMine(0, 0).getSunCost();
            case ("WallNut") -> amount = new WallNut(0, 0).getSunCost();
            case ("ECIPlant") -> amount = new ECIPlant(0, 0).getSunCost();
            case ("Evolve") -> amount = new Evolve(0, 0).getSunCost();
        }
        return amount;
    }

    /**
     * Abstract method  updates the score based on the game mode.
     */
    public abstract void updateScore(Hitbox hitbox);

    /**
     * Abstract method to handles the zombie count by updating the number of zombies of a given type.
     *
     * @param zombieName The name of the zombie type.
     */
    protected abstract void handleZombie(String zombieName);

    /**
     * Collects sun points for the level, adding to the total sun collected.
     *
     * @param amount The amount of sun to add.
     */
    public void collectSun(int amount) {
        if (sunCollected < 9975) {
            sunCollected += amount;
        }
    }

    /**
     * Returns the total amount of sun collected in the current level.
     *
     * @return The total amount of sun collected.
     */
    public int getSunCollected() {
        return sunCollected;
    }

    /**
     * Returns the original amount of sun at the beginning of the level.
     *
     * @return The original amount of sun.
     */
    public int getOriginalSun() {
        return originalSun;
    }

    /**
     * Returns the original number of waves for the level.
     *
     * @return The original number of waves.
     */
    public int getOriginalWaves() {
        return originalWaves;
    }

    /**
     * Returns the lawn object representing the game field.
     *
     * @return The lawn object.
     */
    public Lawn getLawn() {
        return lawn;
    }

    /**
     * Returns the map of plants in the game.
     *
     * @return The map of plants.
     */
    public HashMap<Integer, ArrayList<Plant>> getPlants() {
        return plants;
    }

    /**
     * Returns the map of zombies in the game.
     *
     * @return The map of zombies.
     */
    public HashMap<Integer, ArrayList<Zombie>> getZombies() {
        return zombies;
    }

    /**
     * Returns the map of projectiles in the game.
     *
     * @return The map of projectiles.
     */
    public HashMap<Integer, ArrayList<Projectile>> getProjectiles() {
        return projectiles;
    }

    /**
     * Returns the map of mowers in the game.
     *
     * @return The map of mowers.
     */
    public HashMap<Integer, Mower> getMowers() {
        return mowers;
    }


    /**
     * Removes specified plants from the plant list at the given row.
     * This method removes all plants contained in the `plantsToRemove` list from the
     * plant list at the specified row (y). The row is indexed within the `plants` collection.
     *
     * @param y              The index of the row from which the plants will be removed.
     * @param plantsToRemove The list of plants to be removed from the specified row.
     */
    public void removePlants(int y, ArrayList<Plant> plantsToRemove) {
        plants.get(y).removeAll(plantsToRemove);
    }

    /**
     * Removes specified zombies from the zombie list at the given row.
     * This method removes all zombies contained in the `zombiesToRemove` list from the
     * zombie list at the specified row (y). The row is indexed within the `zombies` collection.
     *
     * @param y               The index of the row from which the zombies will be removed.
     * @param zombiesToRemove The list of zombies to be removed from the specified row.
     */
    public void removeZombies(int y, ArrayList<Zombie> zombiesToRemove) {
        zombies.get(y).removeAll(zombiesToRemove);
    }

    /**
     * Removes specified projectiles from the projectile list at the given row.
     * This method removes all projectiles contained in the `projectilesToRemove` list from
     * the projectile list at the specified row (y + 20). The row is indexed within the `projectiles` collection.
     * The offset of 20 is added to the row index for correct targeting.
     *
     * @param y                   The index of the row from which the projectiles will be removed. The row is
     *                            adjusted by adding 20 to `y` before accessing the `projectiles` collection.
     * @param projectilesToRemove The list of projectiles to be removed from the specified row.
     */
    public void removeProjectiles(int y, ArrayList<Projectile> projectilesToRemove) {
        projectiles.get(y + 20).removeAll(projectilesToRemove);
    }

    /**
     * Removes a mower from the mower list at the given index.
     * This method removes the mower located at the index `y + 40` from the `mowers` collection.
     * The index offset of 40 is added to `y` to target the correct mower.
     *
     * @param y The index used to identify the mower to remove. The index is adjusted by adding 40
     *          before removing from the `mowers` collection.
     */
    public void removeMower(int y) {
        mowers.remove(y + 40);
    }

    /**
     * Adds a new projectile to the projectile list at the given row.
     * This method adds a `projectile` to the list of projectiles at the specified `yPosition`.
     * The projectiles are stored in a list indexed by their y position.
     *
     * @param yPosition  The row index where the projectile will be added.
     * @param projectile The projectile to be added to the specified row.
     */
    public void addProjectile(int yPosition, Projectile projectile) {
        projectiles.get(yPosition).add(projectile);
    }
}
