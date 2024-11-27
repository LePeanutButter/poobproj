package domain;
import java.awt.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;

public class PvM extends Level {
    private int waveProgress = 0;
    private HashMap<String,Integer> zombiesAmount;
    private int[][] tombstones;
    private int score;
    private int totalZombies = 0;

    public PvM(String lawn){
        super(lawn);
        this.tombstones = new int[5][9];
        zombiesAmount = new HashMap<>();
        randomizeZombies();
        initializeTotalZombies();
    }

    public PvM(Level level) {
        super(level);
        this.waveProgress = ((PvM) level).waveProgress;
        this.zombiesAmount = ((PvM) level).zombiesAmount;
        this.score = ((PvM) level).score;
        this.tombstones = ((PvM) level).tombstones;
        this.totalZombies = ((PvM) level).totalZombies;
    }
    
    private void initializeTotalZombies() {
        for (Integer count : zombiesAmount.values()) {
            totalZombies += count;
        }
    }

    public void randomizeZombies() {
        Random random = new Random();
        String[] zombies = {"Basic", "Conehead", "Buckethead", "ECIZombie"};
        for (String z: zombies) {
            int randomNumber = random.nextInt(5, 20);
            zombiesAmount.put(z, randomNumber);
        }
    }

    public void updateProgress() {
        int remainingZombies = 0;
        for (Integer count : zombiesAmount.values()) {
            remainingZombies  += count;
        }
        int zombiesEliminated = totalZombies - remainingZombies;
        waveProgress = 100 * zombiesEliminated / totalZombies;
    }

    public void updateWave() {
        waveProgress = 0;
        randomizeZombies();
        initializeTotalZombies();
    }

    public int getWaveProgress() {
        return waveProgress;
    }

    @Override
    public void rangesVerification() {
        for (Plant plant : plants) {
            if (plant.getRange() > 0) {
                for (Zombie zombie : zombies) {
                    int plantRow = plant.getPosition()[0];
                    int zombieRow = zombie.getPosition()[0];
                    int zombieColumn = zombie.getPosition()[1];
                    int plantColumn = plant.getPosition()[1];
                    if (zombieRow == plantRow && zombieColumn <= plantColumn + (83 * plant.getRange()) && zombieColumn >= plantColumn ) {
                        plant.shoot();
                        break;
                    }
                }
            }
        }
    }

    @Override
    public void hitboxesVerification() {
        for (Plant plant : plants) {
            for (Zombie zombie : zombies) {
                if (plant.hitboxCollision(zombie.getHitbox())) {
                    if (zombie.attack()) {
                        plant.takeDamage(1);
                        break;
                    }
                }
            }
            if (plant.healthPoints == 0) {
                plants.remove(plant);
            }
        }

        for (Projectile projectile : projectiles) {
            for (Zombie zombie : zombies) {
                if (projectile instanceof Leave && projectile.hitboxCollision(zombie.getHitbox())) {
                    zombie.takeDamage(projectile.getDamage());
                    projectiles.remove(projectile);
                    break;
                }
                if (zombie.healthPoints == 0){
                    updateScore(zombie.hitbox);
                    String name = zombie.getClass().getSimpleName();
                    zombiesAmount.compute(name, (_, amount) -> {
                                if (amount != null) {
                                    return amount - 1;
                                }
                                return 0;
                            });
                    zombies.remove(zombie);
                }
            }
        }
    }

    public void updateScore(Rectangle zombie) {
        Rectangle zone1 = new Rectangle(0, 0, 193, 630);
        Rectangle zone2 = new Rectangle(193, 0, 193, 630);
        Rectangle zone3 = new Rectangle(386, 0, 193, 630);
        Rectangle zone4 = new Rectangle(579, 0, 193, 630);
        Rectangle zone5 = new Rectangle(772, 0, 97, 630);
        int points = 0;
        if (zone1.intersects(zombie)) {
            points = 500;
        } else if (zone2.intersects(zombie)) {
            points = 1000;
        } else if (zone3.intersects(zombie)) {
            points = 1500;
        } else if (zone4.intersects(zombie)) {
            points = 2000;
        } else if (zone5.intersects(zombie)) {
            points = 2500;
        }
        this.score += points;
    }
    
    public void removeTombstone(int row, int column) {
        this.tombstones[row - 1][column - 1] = 0;
    }

    public int getScore() {
        return score;
    }

    public void removePlant(int row, int column) {
        int[] position = Hitbox.convertPlantPosition(row, column);
        boolean shovel = false;
        for (int i = 0; i < plants.size(); i++) {
            if (Arrays.equals(plants.get(i).position, position)) {
                plants.remove(i);
                shovel = true;
                break;
            }
        }
        if (!shovel) {
            throw new IllegalArgumentException("There is no plant in this position.");
        }
    }
}
