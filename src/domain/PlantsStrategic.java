package domain;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * The PlantsStrategic class represents a specific implementation of plant spawning in the game,
 * where the plant type to be spawned depends on the amount of sun collected.
 * It extends the MachinePlants class and customizes the plant spawning logic based on time intervals
 * in the level.
 *
 * @author Botero Garcia, Santiago. Perilla Quintero, Laura Natalia.
 * @version December 15, 2024
 */
public class PlantsStrategic extends MachinePlants {

    /**
     * Constructs a new PlantStrategic instance for the specified level.
     * This constructor calls the superclass constructor with the level parameter to initialize the level
     * and set up the necessary attributes.
     *
     * @param level The level where zombies will be spawned.
     */
    public PlantsStrategic(Level level) {
        super(level);
    }

    /**
     * Based on an Array of available plants, a plant spawns at a specific position on the board.
     * The plants to be placed vary depending on the latest position that was taken and the sun
     * collected.
     *
     * @param available A list of plants that can be placed depending on their sun cost.
     */
    @Override
    public void spawnRandomPlant(ArrayList<String> available) {
        int count = 0;
        HashMap<Integer, ArrayList<Plant>> plants = level.getPlants();
        for (int i = 0; i < 5; i++) {
            int y = (i * 113) + 98 + (32 / 3);
            count += plants.get(y).size();
        }
        if (count < 30) {
            boolean flag = false;
            while (!flag && count >= 0) {
                int column = count / 5;
                int row = count % 5;
                if (level.getSunCollected() <= 200 && count < 5) {
                    try {
                        placeSunProducer(available, row, column);
                        flag = true;
                    } catch (POOBVsZombiesException _) {
                        --count;
                    }
                } else if (column == 5) {
                    try {
                        placeDefenses(available, row, column);
                        flag = true;
                    } catch (POOBVsZombiesException _) {
                        --count;
                    }
                } else {
                    try {
                        placeAttack(available, row, column);
                        flag = true;
                    } catch (POOBVsZombiesException _) {
                        --count;
                    }
                }
            }
        }
    }

    /*
     * Places a sun producer plant on the first column of the board for sustainability.
     *
     * @param available A list of plants that can be placed depending on their sun cost.
     * @param row Lane from where the plant line is taken.
     * @throws POOBVsZombiesException If the plant cannot be placed.
     */
    private void placeSunProducer(ArrayList<String> available, int row, int column) throws POOBVsZombiesException {
        if (available.contains("ECIPlant")) {
            level.placePlant(row, column, "ECIPlant");
        } else if (available.contains("Sunflower")) {
            level.placePlant(row, column, "Sunflower");
        }
    }

    /*
     * Places a WallNut on the sixth column of the board if a lane is endangered.
     *
     * @param available A list of plants that can be placed depending on their sun cost.
     * @param row Lane from where the plant line is taken.
     * @throws POOBVsZombiesException If the plant cannot be placed.
     */
    private void placeDefenses(ArrayList<String> available, int row, int column) throws POOBVsZombiesException {
        if (available.contains("WallNut")) {
            level.placePlant(row, column, "WallNut");
        }
    }

    /*
     * Places a peashooter on the board if a lane needs to be defended.
     *
     * @param available A list of plants that can be placed depending on their sun cost.
     * @param row Lane from where the plant line is taken.
     * @param column Tile of the board in which the peashooter will be placed.
     * @throws POOBVsZombiesException If the plant cannot be placed.
     */
    private void placeAttack(ArrayList<String> available, int row, int column) throws POOBVsZombiesException {
        if (available.contains("Peashooter")) {
            level.placePlant(row, column, "Peashooter");
        }
    }
}
