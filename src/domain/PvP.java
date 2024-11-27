package domain;

public class PvP extends Level {
    private int brainsCollected = 50;
    private int plantsDefeated = 0;
    private int zombiesDefeated = 0;

    public PvP(String lawn) {
        super(lawn);
    }

    public PvP(Level level) {
        super(level);
        brainsCollected = ((PvP) level).brainsCollected;
        plantsDefeated = ((PvP) level).plantsDefeated;
        zombiesDefeated = ((PvP) level).zombiesDefeated;
    }

    public void placeZombie(int row, int column, String zombie) {
        switch (zombie) {
            case "Basic":
                zombies.add(new Basic(row,column));
                break;
            case "ECIZombie":
                zombies.add(new ECIZombie(row,column));
                break;
            case "Conehead":
                zombies.add(new Conehead(row,column));
                break;
            case "Buckethead":
                zombies.add(new Buckethead(row,column));
                break;
            default:
                break;
        }
    }

    @Override
    public void hitboxesVerification() {

    }

    @Override
    public void rangesVerification() {

    }
}
