import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Arrays;

/**
 * The test class PuzzleC2Test.
 *
 * @author Botero Garcia, Santiago. Perilla Quintero, Laura Natalia.
 * @version October 05 2024
 */
public class PuzzleContestC3Test{
    private static PuzzleContest puzzle;
    
    @BeforeEach
    public void setUp(){
        if (puzzle != null){
            puzzle = null;
        }
    }
    
    /**
     * Tests if the Puzzle constructor initializes with valid dimensions.
     * It should not throw any exceptions.
     */
    @Test
    public void shouldReturnTrueWhenStartingMatrixSolveEndingMatrix() {
        char[][] startingMatrix = {
            {'r', '.', '.', '.'},
            {'.', 'y', '.', 'b'},
            {'.', 'g', '.', '.'},
            {'.', '.', '.', '.'}
        };
        char[][] endingMatrix = {
            {'.', '.', '.', '.'},
            {'r', '.', '.', '.'},
            {'y', '.', '.', '.'},
            {'g', 'b', '.', '.'}
        };
        puzzle = new PuzzleContest(startingMatrix, endingMatrix);
        assertTrue(puzzle.solve());
    }
    
    @Test
    public void shouldReturnFalseWhenStartingMatrixDoesNotSolveEndingMatrix() {
        char[][] startingMatrix = {
            {'r', '.', '.', '.'},
            {'g', 'y', '.', '.'},
            {'b', '.', '.', '.'},
            {'.', '.', '.', '.'}
        };
        char[][] endingMatrix = {
            {'r', '.', '.', '.'},
            {'.', 'y', '.', 'b'},
            {'.', 'g', '.', '.'},
            {'.', '.', '.', '.'}
        };
        PuzzleContest puzzle = new PuzzleContest(startingMatrix, endingMatrix);
        assertFalse(puzzle.solve());
    }
    
    @Test
    public void shouldTiltLeftAndDownWhenSimulating(){
        char[][] startingMatrix = {
            {'r', '.', '.', '.'},
            {'.', 'y', '.', 'b'},
            {'.', 'g', '.', '.'},
            {'.', '.', '.', '.'}
        };
        char[][] endingMatrix = {
            {'.', '.', '.', '.'},
            {'r', '.', '.', '.'},
            {'y', '.', '.', '.'},
            {'g', 'b', '.', '.'}
        };
        PuzzleContest puzzle = new PuzzleContest(startingMatrix, endingMatrix);
        puzzle.solve();
        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        puzzle.simulate();
        assertArrayEquals(puzzle.getActualArrangement(), endingMatrix);
    }
    
    @Test
    public void shouldntTiltWhenSimulatingSinceItCannotBeSolved(){
        char[][] startingMatrix = {
            {'r', '.', '.', '.'},
            {'g', 'y', '.', '.'},
            {'b', '.', '.', '.'},
            {'.', '.', '.', '.'}
        };
        char[][] endingMatrix = {
            {'r', '.', '.', '.'},
            {'.', 'y', '.', 'b'},
            {'.', 'g', '.', '.'},
            {'.', '.', '.', '.'}
        };
        PuzzleContest puzzle = new PuzzleContest(startingMatrix, endingMatrix);
        puzzle.solve();
        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        puzzle.simulate();
        assertFalse(Arrays.equals(puzzle.getActualArrangement(), endingMatrix));
    }

    
    /**
     * Cleans up resources used by the tests once all have been completed.
     * This ensures that any windows or resources created during the tests are closed properly,
     * preventing potential memory leaks or unused resources.
     */
    @AfterAll
    public static void tearDown(){
        puzzle.makeInvisible();
        puzzle.finishGame();
        puzzle = null;
    }
}