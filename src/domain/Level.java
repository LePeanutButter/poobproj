package domain;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

public abstract class Level implements Serializable {
    private int sunCollected = 50;
    protected Lawn lawn;
    protected ArrayList<Mower> mowers;
    protected ArrayList<Plant> plants;
    protected ArrayList<Projectile> projectiles;
    protected ArrayList<Zombie> zombies;

    public Level(String lawn){
        switch (lawn) {
            case "Day":
                this.lawn = new Day();
                break;
            default:
                break;
        }
        plants = new ArrayList<>();
        mowers = new ArrayList<>();
        projectiles = new ArrayList<>();
        zombies = new ArrayList<>();
    }

    public Level(Level level){
        this.sunCollected = level.sunCollected;
        this.lawn = level.lawn;
        this.mowers = level.mowers;
        this.plants = level.plants;
        this.projectiles = level.projectiles;
        this.zombies = level.zombies;
    }

    public void loseMower(Mower mower){
        mowers.remove(mower);
    }

    public void placePlant(int row, int column, String plant) {
        for (Plant p: plants) {
            if (Arrays.equals(p.position, Hitbox.convertPlantPosition(row, column))) {
                throw new IllegalArgumentException("Cannot be planted in this position.");
            }
        }
        Plant object = getPlant(row, column, plant);
        int amount = object.getSunCost();
        if (amount > sunCollected) {
            throw new IllegalArgumentException("Not enough sun.");
        }
        sunCollected -= amount;
        plants.add(object);
    }

    private static Plant getPlant(int row, int column, String plant) {
        Plant object = null;
        try {
            switch (plant) {
                case "Sunflower":
                    object = new Sunflower(row, column);
                    break;
                case "ECIPlant":
                    object = new ECIPlant(row, column);
                    break;
                case "WallNut":
                    object = new WallNut(row, column);
                    break;
                case "Peashooter":
                    object = new Peashooter(row, column);
                    break;
                default:
                    break;
            }
        } catch (Exception _) {
        }
        assert object != null;
        return object;
    }

    public abstract void hitboxesVerification();

    public abstract void rangesVerification();

    public void collectSun(int amount) {
        sunCollected += amount;
    }

    public int getSunCollected() {
        return sunCollected;
    }

    public String getLawn() {
        return lawn.getClass().getSimpleName();
    }
}
