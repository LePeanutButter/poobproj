package domain;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * The PvP (Player vs. Player) class represents a game level where two players compete against each other.
 *
 * @author Botero Garcia, Santiago. Perilla Quintero, Laura Natalia.
 * @version December 09 2024
 */
public final class PvP extends Level {
    private int brainCollected = 50;
    private int originalBrain;
    private int plantsDefeated = 0;
    private int zombiesDefeated = 0;
    private boolean areZombiesPlaying = false;
    private ArrayList<String> chosenZombies;

    /**
     * Constructs a PvP level with the specified lawn type, sun amount, and wave count.
     *
     * @param lawn  The type of lawn for the level.
     * @param sun   The starting sun amount for the player.
     * @throws POOBVsZombiesException If there is an error initializing the level.
     */
    public PvP(String lawn, int sun, double time) throws POOBVsZombiesException {
        super(lawn, sun, 2, time);
        setBrainsCollected(sun);
        chosenZombies = new ArrayList<>();
        setStrategyTimer();
    }

    /**
     * Sets the amount of brains collected for the level, ensuring it is within the valid range of 25 to 9975.
     *
     * @param brain The sun amount to set.
     */
    private void setBrainsCollected(int brain) {
        if (brain >= 25 && brain < 10000) {
            if (brain % 25 == 0) {
                brainCollected = brain;
            } else {
                brainCollected = Math.round(brain / 25.0f) * 25;
            }
        } else {
            if (brain < 25) {
                brainCollected = 25;
            } else {
                brainCollected = 9975;
            }
        }
        originalBrain = brainCollected;
    }

   /**
     * Collects brain points for the level, adding to the total brain collected.
     *
     * @param amount The amount of brain to add.
     */
    public void collectBrain(int amount) {
        if (brainCollected < 9975) {
            brainCollected += amount;
        }
    }

    /**
     * Method made null on porpoise
     */
    @Override
    public void handleZombie(String zombieName) {
    }

    /**
     * Sets the state indicating whether zombies are actively playing in the game.
     *
     * @param state A boolean value where true means zombies are playing
     *              and false means they are not.
     */
    public void setZombiesPlaying(boolean state) {
        areZombiesPlaying = state;
    }

    /**
     * Returns the current state indicating whether zombies are actively playing in the game.
     *
     * @return true if zombies are playing, false otherwise.
     */
    public boolean getZombiesPlaying() {return areZombiesPlaying;}

    /**
     * Sets the game timer to the strategy phase duration.
     * The timer is set to 2 minutes (120,000 milliseconds).
     */
    public void setStrategyTimer() {
        time = 120000;
    }

    /**
     * Resets the game timer to its original match duration.
     */
    public void setZombiesTimer() {
        time = (double) originalTime / 2;
    }

    /**
     * Updates the score based on the entity.
     *
     * @param entity The hitbox representing a zombie or plant.
     */
    @Override
    public void updateScore(Hitbox entity) {
        String entityName = entity.getClass().getSimpleName();
        String[] possibleZombies = {"Basic", "Conehead", "Buckethead", "ECIZombie", "Brainstein"};
        String[] possiblePlants = {"Peashooter", "Sunflower", "WallNut", "PotatoMine", "ECIPlant"};
        if (Arrays.asList(possibleZombies).contains(entityName)) {
            ++zombiesDefeated;
        } else if (Arrays.asList(possiblePlants).contains(entityName)) {
            ++plantsDefeated;
        }
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
    @Override
    public Zombie placeZombie(int row, int column, String zombieName) throws POOBVsZombiesException {
        Zombie zombie;
        switch (zombieName) {
            case "Basic" -> zombie = new Basic(row, column);
            case "Conehead" -> zombie = new Conehead(row, column);
            case "Buckethead" -> zombie = new Buckethead(row, column);
            case "ECIZombie" -> zombie = new ECIZombie(row, column);
            case "Brainstein" -> zombie = new Brainstein(row, column);
            default -> throw new POOBVsZombiesException(POOBVsZombiesException.UNKNOWN_ZOMBIE);
        }
        final int amount = zombie.getBrainCost();
        if (amount > brainCollected) {
            throw new POOBVsZombiesException(POOBVsZombiesException.NOT_ENOUGH_BRAIN);
        }
        brainCollected -= amount;
        int yPosition = zombie.getHitbox().y;
        zombies.get(yPosition).add(zombie);
        return zombie;
    }

    /**
     * Sets the list of chosen zombies for the player.
     *
     * @param chosenZombies An ArrayList containing the names of the zombies chosen by the player for the game.
     */
    public void setChosenZombies(ArrayList<String> chosenZombies) {
        this.chosenZombies = chosenZombies;
    }

    /**
     * Returns the list of zombies chosen by the player for the game.
     *
     * @return An ArrayList containing the names of the zombies that the player has selected.
     */
    public ArrayList<String> getChosenZombies() {return chosenZombies;}

    /**
     * Removes a plant from the lawn at the given position.
     *
     * @param row    The row position of the plant to remove.
     * @param column The column position of the plant to remove.
     * @throws POOBVsZombiesException If the plant cannot be removed due to invalid position.
     */
    @Override
    public void removePlant(int row, int column) throws POOBVsZombiesException {
        int[] position = Hitbox.convertPlantPosition(row, column);
        int xPosition = position[0];
        int yPosition = position[1];
        if (plants.containsKey(yPosition)) {
            for (Plant p : plants.get(yPosition)) {
                collectSun(p.sunCost);
                if (p.getHitbox().x == xPosition) {
                    plants.get(yPosition).remove(p);
                    return;
                }
            }
        }
        throw new POOBVsZombiesException(POOBVsZombiesException.INVALID_SHOVEL);
    }

    /**
     * Returns the total number of brains collected during the game.
     *
     * @return The number of brains collected.
     */
    public int getBrainCollected() {
        return brainCollected;
    }

    /**
     * Returns the original number of brains available at the start of the game.
     *
     * @return The original number of brains.
     */
    public int getOriginalBrain() {
        return originalBrain;
    }

    /**
     * Returns the total number of zombies defeated during the game.
     *
     * @return The number of zombies defeated.
     */
    public int getZombiesDefeated() {
        return zombiesDefeated;
    }

    /**
     * Returns the total number of plants defeated during the game.
     *
     * @return The number of plants defeated.
     */
    public int getPlantsDefeated() {
        return plantsDefeated;
    }

    /**
     * Determines whether more zombies or plants were defeated during the game.
     *
     * @return A string "Zombies" if more zombies were defeated, or "Plants" if more plants were defeated.
     */
    public String getWinnerByDefeats() {
        if (zombiesDefeated > plantsDefeated) {
            return "Zombies";
        } else {
            return "Plants";
        }
    }

    /**
     * Returns a detailed string indicating the number of defeats for the winning group.
     *
     * @return A string in the format "X Zombies" or "X Plants", where X is the number of defeats for the winning group.
     */
    public String getDetailedWinnerByDefeats() {
        String winner = getWinnerByDefeats();
        if (winner.equals("Zombies")) {
            return zombiesDefeated + " Zombies";
        } else {
            return plantsDefeated + " Plants";
        }
    }
}
