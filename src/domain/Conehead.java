package domain;

public class Conehead extends Zombie {
    public Conehead(int row, int column) {
        super(row, column);
        this.name = "Conehead";
        this.brainCost = 75;
        this.slotRechargeTime = 30000;
        this.healthPoints = 28;
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
