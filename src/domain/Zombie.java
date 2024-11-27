package domain;
import java.awt.*;

public abstract class Zombie extends Hitbox {
    public static final Color HITBOX_COLOR = Color.RED;
    protected String name;
    protected int brainCost;
    protected int slotRechargeTime;
    protected int healthPoints;
    protected int preparationTime;
    protected int range;

    public Zombie(int row, int column) {
        super(new int[] {42,110}, Hitbox.convertZombiePosition(row, column), HITBOX_COLOR);
    }

    @Override
    public void move() {

    }

    @Override
    public boolean hitboxCollision(Rectangle other) {
        return false;
    }

    public int getBrainCost() {
        return brainCost;
    }

    public int takeDamage(int amount) {
        healthPoints -= amount;
        return healthPoints;
    }

    public int getRange() {
        return range;
    }

    public abstract POOmBas shoot();

    public abstract boolean attack();
}
