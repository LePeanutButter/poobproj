package puzzle;
import shapes.*;

/**
 * The Hole class represents a hole on the simulator board.
 *
 * @author Botero Garcia, Santiago. Perilla Quintero, Laura Natalia.
 * @version November 03 2024
 */
public class Hole {
    public static final int HOLE_WIDTH = 40, HOLE_HEIGHT = 40;
    
    protected int row;
    protected int column;
    private Rectangle rectangle;
    

    /**
     * Constructor for objects of the Hole class.
     * 
     * @param row The row where the hole is located.
     * @param column The column where the hole is located.
     * @param xPosition The x position of the hole in the interface.
     * @param yPosition The y position of the hole in the interface.
     * @throws HoleExceptions If the hole is out of the board's bounds.
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
    
    /**
     * Moves the hole horizontally.
     * 
     * @param xPosition Amount of horizontal movement.
     */
    public void moveHorizontal(int xPosition) {
        this.rectangle.moveHorizontal(xPosition);
    }
}
