import puzzle.*;

import static org.junit.Assert.*;
import org.junit.Test;
import java.util.Arrays;

/**
 * The test class PuzzleC2Test.
 *
 * @author Botero Garcia, Santiago. Perilla Quintero, Laura Natalia.
 * @version October 05 2024
 */
public class PuzzleContestC3Test{
    /**
     * Tests if the Puzzle constructor initializes with valid dimensions.
     * It should not throw any exceptions.
     */
    @Test
    public void shouldReturnTrueWhenStartingMatrixSolveEndingMatrix() throws puzzle.TileExceptions,puzzle.PuzzleExceptions,puzzle.BoardExceptions {
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
        PuzzleContest puzzle = new PuzzleContest();
        assertTrue(puzzle.solve(startingMatrix, endingMatrix));
    }
    
    @Test
    public void shouldReturnFalseWhenStartingMatrixDoesNotSolveEndingMatrix() throws puzzle.TileExceptions,puzzle.PuzzleExceptions,puzzle.BoardExceptions {
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
        PuzzleContest puzzle = new PuzzleContest();
        assertFalse(puzzle.solve(startingMatrix, endingMatrix));
    }
    
    @Test
    public void shouldTiltLeftAndDownWhenSimulating() throws puzzle.TileExceptions,puzzle.PuzzleExceptions,puzzle.BoardExceptions {
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
        PuzzleContest puzzle = new PuzzleContest();
        puzzle.simulate(startingMatrix, endingMatrix);
        assertArrayEquals(puzzle.getActualArrangement(), endingMatrix);
    }
    
    @Test
    public void shouldntTiltWhenSimulatingSinceItCannotBeSolved() throws puzzle.TileExceptions,puzzle.PuzzleExceptions,puzzle.BoardExceptions {
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
        PuzzleContest puzzle = new PuzzleContest();
        puzzle.simulate(startingMatrix, endingMatrix);
        assertFalse(Arrays.equals(puzzle.getActualArrangement(), endingMatrix));
    }
}