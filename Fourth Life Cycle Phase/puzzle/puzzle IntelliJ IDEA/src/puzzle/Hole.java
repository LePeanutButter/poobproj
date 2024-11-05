package puzzle;
import shapes.*;

/**
 * Write a description of class Hole here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Hole {
    public static final int HOLE_WIDTH = 40, HOLE_HEIGHT = 40;
    
    protected int row;
    protected int column;
    private Rectangle rectangle;
    

    /**
     * Constructor for objects of class Hole
     */
    public Hole(int row, int column, int xPosition, int yPosition) throws HoleExceptions {
        if (row <= 0 || column <= 0 || row > Board.rows || column > Board.columns) {
            throw new HoleExceptions(HoleExceptions.OUT_OF_BOUNDS);
        }
        this.row = row;
        this.rectangle = new Rectangle(HOLE_HEIGHT, HOLE_WIDTH, xPosition + 10 + ((column - 1) * 40), yPosition + 10 + ((row - 1) * 40), "white", false);
    }
    
    /**
     * Makes the hole visible.
     */
    public void makeVisible() {
        this.rectangle.makeVisible();
    }

    /**
     * Makes the hole invisible.
     */
    public void makeInvisible() {
        this.rectangle.makeInvisible();
    }
    
    public void moveHorizontal(int xPosition) {
        this.rectangle.moveHorizontal(xPosition);
    }
}
