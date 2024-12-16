package domain;

/**
 * The MvM (Machine vs. Machine) class represents a game level.
 *
 * @author Botero Garcia, Santiago. Perilla Quintero, Laura Natalia.
 * @version December 07 2024
 */
public final class MvM extends Level {
    private final String machine;
    private final MachineZombies machineZombies;
    private final MachinePlants machinePlants;

    /**
     * Constructs a new MvM level with the specified lawn, sun amount, and number of waves.
     * This constructor initializes the level with the given parameters, setting up the initial conditions.
     *
     * @param lawn  The layout of the lawn for the level.
     * @param sun   The initial amount of sun available in the level.
     * @param machine The name of the machine that will be used as the main strategy for the game.
     * @param time  The time duration of the level (in minutes, between 3 and 10).
     * @throws POOBVsZombiesException If an error occurs while setting up the level.
     */
    public MvM(String lawn, int sun, String machine, double time) throws POOBVsZombiesException {
        super(lawn, sun, 1, time);
        switch (machine) {
            case "original" -> {
                machineZombies = new ZombiesOriginal(this);
                machinePlants = new PlantsIntelligent(this);
            }
            case "strategic" -> {
                machineZombies = new ZombiesStrategic(this);
                machinePlants = new PlantsStrategic(this);
            }
            default -> throw new POOBVsZombiesException(POOBVsZombiesException.UNKNOWN_MACHINE);
        }
        this.machine = machine;
    }

    /**
     * This method is responsible for handling zombies in the MvM level.
     *
     * @param zombieName The name of the zombie to be handled.
     */
    @Override
    public void handleZombie(String zombieName) {
    }

    /**
     * Empty method made on porpoise.
     */
    @Override
    public void updateScore(Hitbox hitbox) {
    }

    /**
     * Updates the state of the machines in the MvM level.
     * 
     */
    public void updateMachines() {
        machineZombies.zombieSpawn();
        machinePlants.plantSpawn();
    }

    /**
     * Gets the name of the machine that is being used as the main strategy for the game.
     *
     * @return The machine attribute.
     */
    public String getMachine() {
        return machine;
    }
}
