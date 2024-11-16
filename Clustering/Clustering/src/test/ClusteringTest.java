import domain.Clustering;

import org.junit.Test;
import  org.junit.Before;
import java.awt.*;
import java.io.File;
import java.util.Arrays;
import java.util.Objects;
import static org.junit.Assert.*;

/**
 * The ClusteringTest class purpose is to verify the functionality of the Clustering class with JUnit 4.8 tests.
 * @author Botero Garcia, Santiago. Perilla Quintero, Laura Naralia.
 * @version 2024-11-15
 */
public class ClusteringTest {
    public static Clustering clustering;

    /**
     * Method that runs before each test, should instantiate the clustering attribute with a given arrangement.
     */
    @Before
    public void setUp() {
        clustering = new Clustering(new Color[][]  {{null, Color.red, null, null},
                                                    {Color.RED, Color.GREEN, Color.YELLOW, Color.BLUE},
                                                    {null, Color.BLUE, null, null},
                                                    {null, Color.YELLOW, Color.RED, null}});
    }

    /**
     *  Should tilt the board one position above each tile.
     */
    @Test
    public void shouldTiltUp() {
        clustering.tilt('u');
        Color[][] arrangement = clustering.getTilesColors();
        Color[][] shoudlBe = new Color[][] {{Color.RED, Color.red, Color.YELLOW, Color.BLUE},
                                            {null, Color.GREEN, null, null},
                                            {null, Color.BLUE, Color.RED, null},
                                            {null, Color.YELLOW, null, null}};
        assertArrayEquals(shoudlBe, arrangement);
    }

    /**
     *  Should tilt the board one position below each tile.
     */
    @Test
    public void shouldTiltDown() {
        clustering.tilt('d');
        Color[][] arrangement = clustering.getTilesColors();
        Color[][] shoudlBe = new Color[][] {{null, Color.red, null, null},
                                            {null, Color.GREEN, null, null},
                                            {Color.RED, Color.BLUE, Color.YELLOW, Color.BLUE},
                                            {null, Color.YELLOW, Color.RED, null}};
        assertArrayEquals(shoudlBe, arrangement);
    }

    /**
     *  Should tilt the board one position to the left each tile.
     */
    @Test
    public void shouldTiltLeft() {
        clustering.tilt('l');
        Color[][] arrangement = clustering.getTilesColors();
        Color[][] shoudlBe = new Color[][] {{Color.red, null, null, null},
                                            {Color.RED, Color.GREEN, Color.YELLOW, Color.BLUE},
                                            {Color.BLUE, null, null, null},
                                            {Color.YELLOW, Color.RED, null, null}};
        assertArrayEquals(shoudlBe, arrangement);
    }

    /**
     *  Should tilt the board one position to the right each tile.
     */
    @Test
    public void shouldTiltRight() {
        clustering.tilt('r');
        Color[][] arrangement = clustering.getTilesColors();
        Color[][] shoudlBe = new Color[][] {{null, null, Color.red, null},
                                            {Color.RED, Color.GREEN, Color.YELLOW, Color.BLUE},
                                            {null, null, Color.BLUE, null},
                                            {null, null, Color.YELLOW, Color.RED}};
        assertArrayEquals(shoudlBe, arrangement);
    }

    /**
     * Tilt on multiple directions till the maximum score is achieved, clustering.getScore() should return the maximum
     * score possible in this arrangement.
     */
    @Test
    public void shouldReturnTheRightScore() {
        clustering.tilt('l');
        clustering.tilt('d');
        clustering.tilt('d');
        clustering.tilt('r');
        clustering.tilt('r');
        clustering.tilt('u');
        clustering.tilt('l');
        clustering.tilt('l');
        clustering.tilt('u');
        assertEquals(1600, clustering.getScore());
    }

    /**
     * Tilt on multiple directions till the maximum score is achieved, clustering.getMoves() should return the amount
     * of moves that were required to achieve the maximum score in this arrangement.
     */
    @Test
    public void shouldReturnTheRightMoves() {
        clustering.tilt('l');
        clustering.tilt('d');
        clustering.tilt('d');
        clustering.tilt('r');
        clustering.tilt('r');
        clustering.tilt('u');
        clustering.tilt('l');
        clustering.tilt('l');
        clustering.tilt('u');
        assertEquals(9, clustering.getMoves());
    }

    /**
     * The cluster changes the board initial settings by changing the board dimensions and amount of tiles required
     * on the board with a given percentage.
     */
    @Test
    public void shouldIncreaseBoardSize() {
        clustering.resizeBoard(8, 8, 30);
        Color[][] board = clustering.getTilesColors();
        boolean height = board.length == 8;
        boolean width = board[0].length == 8;
        boolean tiles = (int) Arrays.stream(board)
                        .flatMap(Arrays::stream)
                        .filter(Objects::nonNull)
                        .count() == 19;
        assertTrue(height && width && tiles);
    }

    /**
     * Should change the color of a tile to the one give.
     */
    @Test
    public void shouldChangeTileColor() {
        clustering.changeTileColor(1,1,Color.BLUE);
        Color[][] colors = clustering.getTilesColors();
        assertSame(Color.BLUE, colors[1][1]);
    }

    /**
     * Saves a pre-made game and loads it after starting a new game.
     */
    @Test
    public void shouldLoadSavedGame() {
        clustering.resizeBoard(8, 8, 60);
        clustering.changeTileColor(1,1, Color.BLUE);
        Color[][] originalColors = clustering.getTilesColors();
        File file = new File("src/test/board.ser");
        clustering.saveGame(file);
        clustering = new Clustering();
        clustering.loadGame(file);
        assertArrayEquals(originalColors, clustering.getTilesColors());
    }
}
