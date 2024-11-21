package domain;
import java.util.ArrayList;
import java.io.Serializable;

public abstract class Level implements Serializable {
    private int sunCollected = 50;
    private Lawn lawn;
    private ArrayList<Mower> mowers;
    protected ArrayList<Plant> plants;
    private ArrayList<Projectile> projectiles;
    private ArrayList<Zombie> zombies;

    public Level(String difficulty, String lawn){
        switch (lawn) {
            case "Day":
                this.lawn = new Day();
                break;
            default:
                break;
        }
    }

    public Level(Level level){
        this.sunCollected = level.sunCollected;
        this.lawn = level.lawn;
        this.mowers = level.mowers;
        this.plants = level.plants;
    }

    public void loseMower(Mower mower){
        mowers.remove(mower);
    }

    public void placePlant(int row, int column, Plant plant){
        String nameClass = plant.getClass().getSimpleName();
        switch (nameClass) {
            case "Sunflower":
                plants.add(new Sunflower(row,column));
                break;
            case "ECIPlant":
                plants.add(new ECIPlant(row,column));
                break;
            case "Wall_nut":
                plants.add(new Wall_nut(row,column));
                break;
            case "Peashooter":
                plants.add(new Peashooter(row,column));
                break;
            default:
                break;
        }
    }

    public void collectSun(int amount) {
        sunCollected += amount;
    }

    public Plant[] getPlants() {
        return plants.toArray(new Plant[plants.size()]);
    }

    public Zombie[] getZombies() {
        return zombies.toArray(new Zombie[zombies.size()]);
    }

    public Projectile[] getProjectiles() {
        return projectiles.toArray(new Projectile[projectiles.size()]);
    }

    public Mower[] getMowers() {
        return mowers.toArray(new Mower[mowers.size()]);
    }
}
