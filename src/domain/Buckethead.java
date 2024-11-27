package domain;

public class Buckethead extends Zombie {
    public Buckethead(int row, int column) {
        super(row, column);
        this.name = "Buckethead";
        this.brainCost = 25;
        this.slotRechargeTime = 7500;
        this.healthPoints = 10;
        this.preparationTime = 600;
        this.range = 0;
    }

    @Override
    public POOmBas shoot() {
        return null;
    }

    @Override
    public boolean attack() {
        boolean canAttack = false;
        if (preparationTime == 600) {
            preparationTime = 0;
            canAttack = true;
        } else {
            preparationTime += 100 / 3;
        }
        return canAttack;
    }
}
