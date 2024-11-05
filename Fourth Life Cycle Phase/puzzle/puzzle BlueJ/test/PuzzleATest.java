package test;
import puzzle.*;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * The test class PuzzleATest.
 *
 * @author Botero Garcia, Santiago. Perilla Quintero, Laura Natalia.
 * @version November 3 2024
 */
public class PuzzleATest {

    @Test
    public void PuzzleTest() throws HoleExceptions,GlueExceptions,BoardExceptions, PuzzleExceptions, TileExceptions {
        char[][] startingMatrix = {{'.', '.', '.', 'r', 'g', '.'},
                                   {'y', '.', '.', 'b', '.', '.'},
                                   {'.', '.', 'r', '.', '.', '.'},
                                   {'.', 'm', '.', '.', '.', '.'}, 
                                   {'.', '.', '.', '.', '.', '.'}};
        char[][] endingMatrix = {{'.', '.', '.', '.', '.', '.'},
                                 {'.', '.', '.', '.', '.', '.'},
                                 {'.', '.', '.', '.', 'r', '.'},
                                 {'.', '.', '.', '.', 'b', 'g'}, 
                                 {'.', '.', '.', 'y', 'r', 'g'}};
        Puzzle puzzle = new Puzzle(startingMatrix, endingMatrix);
        puzzle.addTile("flying", 3, 5, "green");
        puzzle.addGlue("fragil", 1, 4);
        puzzle.makeHole(5, 6);
        puzzle.tilt('r');
        puzzle.tilt();
        puzzle.exchange();
        assertTrue(puzzle.isGoal());
    }
    
    @Test
    public void PuzzleContestTest() throws BoardExceptions,PuzzleExceptions,TileExceptions {
        PuzzleContest puzzleContest = new PuzzleContest();
        char[][] startingMatrix = {{'.', '.', '.', 'r', 'g', '.'},
                                   {'y', '.', '.', 'b', '.', '.'},
                                   {'.', '.', 'r', '.', '.', '.'},
                                   {'.', 'm', '.', '.', '.', '.'}, 
                                   {'.', '.', '.', '.', '.', '.'}};
        char[][] endingMatrix = {{'.', 'y', 'm', 'r', 'b', 'r'},
                                 {'.', '.', '.', '.', '.', 'g'},
                                 {'.', '.', '.', '.', '.', '.'},
                                 {'.', '.', '.', '.', '.', '.'}, 
                                 {'.', '.', '.', '.', '.', '.'}};
        assertTrue(puzzleContest.solve(startingMatrix, endingMatrix));
    }
}
