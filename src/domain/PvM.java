package domain;
import java.io.Serializable;
import java.util.HashMap;

public class PvM extends Level implements Serializable {
    private int waveProgress = 0;
    private int waves = 3;
    private HashMap<Zombie,Integer> zombiesAmount;
    private int[][] tombstones;
    private int score;

    public PvM(String difficulty, String lawn){
        super(difficulty, lawn);
        this.tombstones = new int[5][9];
    }
    public PvM(Level level){
        super(level);
        this.waveProgress = level.waveProgress;
        this.waves = level.waves;
        this.zombiesAmount = level.zombiesAmount;
        this.score = level.score;
        this.tombstones = level.tombstones;
    }

    public void updateProgress() {}
    public boolean levelBeaten() {}
    public void removeTombstone(int row, int column) {
        this.tombstones[row-1][column-1]=0;
    }
    public void updateScore(int amount) {}


    public void removePlant(int row, int column) {
        for (int i = 0; i < plants.size(); i++) {
            if (plants.get(i).getRow() == row && plants.get(i).getColumn() == column) {
                plants.remove(i);
                break;
            }
        }
    }



}
