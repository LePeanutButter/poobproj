package domain;

import java.util.*;

/**
 * The PlantsIntelligent class represents a specific implementation of plant spawning in the game.
 * It extends the MachinePlants class and provides custom logic for spawning plants based on the
 * current level.
 *
 * @author Botero Garcia, Santiago. Perilla Quintero, Laura Natalia.
 * @version December 15, 2024
 */
public class PlantsIntelligent extends MachinePlants {

    /**
     * Constructs a new PlantsIntelligent instance for the specified level.
     * This constructor calls the superclass constructor with the level parameter to initialize the level
     * and set up the necessary attributes.
     *
     * @param level The level where plants will be spawned.
     */
    public PlantsIntelligent(Level level) {
        super(level);
    }

    /**
     * Based on an Array of available plants, a plant spawns at a specific position on the board.
     * The spawning of plants depends on event priorities where 1 is the highest:
     *      1. Places a WallNut when the lane requires to be defended.
     *      2. Places a Peashooter on the lane when it requires to be counter-attacked.
     *      3. Places an ECIPlant or Sunflower on the first column of the board for
     *         economy sustain.
     *      4. Places a Peashooter at a random location if no events are happening on the screen.
     *
     * @param available A list of plants that can be placed depending on their sun cost.
     */
    @Override
    public void spawnRandomPlant(ArrayList<String> available) {
        HashMap<Integer, ArrayList<Plant>> plants = level.getPlants();
        HashMap<Integer, ArrayList<Zombie>> zombies = level.getZombies();
        PriorityQueue<Action> possibilities = new PriorityQueue<>();
        for (int i = 0; i < 5; i++) {
            int y = (i * 113) + 98 + (32 / 3);
            ArrayList<Plant> plantLine = plants.get(y);
            if (plantLine.size() < 6) {
                ArrayList<Zombie> zombieLine = zombies.get(y);
                if (zombieLine.isEmpty()) {
                    queryNullZombieLine(available, plantLine, i, possibilities);
                } else {
                    queryNonNullZombieLine(available, plantLine, i, possibilities);
                }
            }
        }
        chooseHighestPriority(possibilities);
    }

    /*
     * Iterates through each row of the board. If no zombie has spawned in this lane, it handles the
     * events of the lane.
     *
     * @param available A list of plants that can be placed depending on their sun cost.
     * @param plantLine A list of plants that are currently placed in a row from the board.
     * @param row Lane from where the plant line is taken.
     * @param possibilities A priority queue that handles the possibilities of plant placement.
     */
    private void queryNullZombieLine(ArrayList<String> available, ArrayList<Plant> plantLine, int row, PriorityQueue<Action> possibilities) {
        boolean sunProducer = false;
        int column = -1;
        for (Plant p : plantLine) {
            String plantName = p.getClass().getSimpleName();
            int xPosition = (p.getHitbox().x - 360) / 97;
            if (column == -1 && !plantName.equals("WallNut")) {
                column = xPosition;
            } else if (xPosition > column && !plantName.equals("WallNut")) {
                column = xPosition;
            }
            if (plantName.equals("Sunflower") || plantName.equals("ECIPlant")) {
                sunProducer = true;
            }
        }
        if (sunProducer) {
            int finalColumn = column;
            possibilities.add(new Action(4, () -> {
                try {
                    placeAttack(available, row, finalColumn + 1);
                } catch (POOBVsZombiesException _) {
                }
            }));
        } else {
            possibilities.add(new Action(3, () -> {
                try {
                    placeSunProducer(available, row);
                } catch (POOBVsZombiesException _) {
                }
            }));
        }
    }

    /*
     * Iterates through each row of the board. If at least one zombie has spawned on this lane, it handles the
     * events of the lane.
     *
     * @param available A list of plants that can be placed depending on their sun cost.
     * @param plantLine A list of plants that are currently placed in a row from the board.
     * @param row Lane from where the plant line is taken.
     * @param possibilities A priority queue that handles the possibilities of plant placement.
     */
    private void queryNonNullZombieLine(ArrayList<String> available, ArrayList<Plant> plantLine, int row, PriorityQueue<Action> possibilities) {
        boolean wallNutDefending = false;
        int column = -1;
        for (Plant p : plantLine) {
            String plantName = p.getClass().getSimpleName();
            int xPosition = (p.getHitbox().x - 360) / 97;
            if (column == -1 && !plantName.equals("WallNut")) {
                column = xPosition;
            } else if (xPosition > column && !plantName.equals("WallNut")) {
                column = xPosition;
            }
            if (plantName.equals("WallNut")) {
                wallNutDefending = true;
            }
        }
        if (wallNutDefending) {
            int finalColumn = column;
            possibilities.add(new Action(2, () -> {
                try {
                    placeAttack(available, row, finalColumn + 1);
                } catch (POOBVsZombiesException _) {
                }
            }));
        } else {
            possibilities.add(new Action(1, () -> {
                try {
                    placeDefenses(available, row);
                } catch (POOBVsZombiesException _) {
                }
            }));
        }
    }

    /**
     * Depending on the possibilities from the priority queue, this method chooses the events that have the highest
     * priority locally, and randomized which one of them gets to be done.
     *
     * @param possibilities A priority queue that handles the possibilities of plant placement.
     */
    public static void chooseHighestPriority(PriorityQueue<Action> possibilities) {
        if (possibilities.isEmpty()) {
            return;
        }
        int maximum = possibilities.peek().priority;
        for (Action action : possibilities) {
            maximum = Math.min(maximum, action.priority);
        }
        ArrayList<Action> maximumActions = new ArrayList<>();
        for (Action action : possibilities) {
            if (action.priority == maximum) {
                maximumActions.add(action);
            }
        }
        Random rand = new Random();
        while (!maximumActions.isEmpty()) {
            Action randomAction = maximumActions.get(rand.nextInt(maximumActions.size()));
            try {
                randomAction.run();
                break;
            } catch (Exception e) {
                String message = e.getMessage();
                if (message.equals(POOBVsZombiesException.UNKNOWN_PLANT) || message.equals(POOBVsZombiesException.NOT_ENOUGH_SUN) || message.equals(POOBVsZombiesException.INVALID_POSITION)) {
                    maximumActions.remove(randomAction);
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
    private void placeSunProducer(ArrayList<String> available, int row) throws POOBVsZombiesException {
        if (available.contains("ECIPlant")) {
            level.placePlant(row, 0, "ECIPlant");
        } else if (available.contains("Sunflower")) {
            level.placePlant(row, 0, "Sunflower");
        }
    }

    /*
     * Places a WallNut on the sixth column of the board if a lane is endangered.
     *
     * @param available A list of plants that can be placed depending on their sun cost.
     * @param row Lane from where the plant line is taken.
     * @throws POOBVsZombiesException If the plant cannot be placed.
     */
    private void placeDefenses(ArrayList<String> available, int row) throws POOBVsZombiesException {
        if (available.contains("WallNut")) {
            level.placePlant(row, 5, "WallNut");
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

    /**
     * The Action class represents a specific implementation of Comparable.
     * The purpose of this class is setting priorities to action events represented
     * As a runnable from the class PlantsIntelligent.
     *
     */
    public static class Action implements Comparable<Action> {
        private final int priority;
        private final Runnable task;

        /**
         * Constructs an Action instance for the priority queue.
         *
         * @param priority Number from one to four that represents the priority in the queue (Where 1 is the highest).
         * @param task Runnable event that could be done after all events have been taken into account.
         */
        public Action(int priority, Runnable task) {
            this.priority = priority;
            this.task = task;
        }

        /**
         * Executes the runnable from the class PlantsIntelligent.
         *
         */
        public void run() {
            task.run();
        }

        /**
         * Compares multiple priorities from action instances, this is required for a correct placement on
         * the priority queue.
         *
         * @param other the object to be compared.
         * @return If both action events have the same priority;
         */
        @Override
        public int compareTo(Action other) {
            return Integer.compare(this.priority, other.priority);
        }
    }
}
