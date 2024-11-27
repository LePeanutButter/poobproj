package domain;

public class ECIZombie extends Zombie {
    double shootingTime = 3000;

    public ECIZombie(int row, int column) {
        super(row, column);
        this.name = "ECIZombie";
        this.brainCost = 25;
        this.slotRechargeTime = 7500;
        this.healthPoints = 10;
        this.preparationTime = 600;
        this.range = 0;
    }

    @Override
    public POOmBas shoot() {
        POOmBas projectile = null;
        if (shootingTime == 3000){
            projectile = new POOmBas(position);
            shootingTime = 0;
        } else {
            shootingTime += (double) 100 /3;
        }
        return projectile;
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
