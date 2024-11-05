package test;
import puzzle.*;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

/**
 * The test class PuzzleC4Test.
 *
 * @author Botero Garcia, Santiago. Perilla Quintero, Laura Natalia.
 * @version November 3 2024
 */
public class PuzzleC4Test {
    private Puzzle puzzle;
    
    /**
     * Sets up the test fixture.
     *
     * Called before every test case method.
     */
    @Before
    public void setUp() throws BoardExceptions, PuzzleExceptions, TileExceptions{
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
        puzzle = new Puzzle(startingMatrix, endingMatrix);
    }
    
    @Test
    public void shouldNotBeAbleToRelocate() throws TileExceptions,PuzzleExceptions,BoardExceptions {
        puzzle.addTile("fixed", 2, 1, "magenta");
        try {
            puzzle.relocateTile(new int[] {2,1}, new int[] {3,3});
            fail();
        } catch (Exception e) {
            assertTrue(true);
        }
    }
    
    @Test
    public void shouldNotBeAbleToDelete() throws TileExceptions,PuzzleExceptions,BoardExceptions {
        puzzle.addTile("fixed", 2, 1, "magenta");
        try {
            puzzle.deleteTile(2, 1);
            fail();
        } catch (Exception e) {
            assertTrue(true);
        }
    }
    
    @Test
    public void shouldNotBeAbleToTilt() throws TileExceptions,PuzzleExceptions,BoardExceptions {
        puzzle.addTile("rough", 2, 3, "green");
        char [][] result = {
            {'r', '.', '.', '.'},
            {'y', '.', 'g', 'b'},
            {'g', '.', '.', '.'},
            {'.', '.', '.', '.'}
        };
        puzzle.tilt('l');
        assertArrayEquals(puzzle.actualArrangement(), result);
    }
    
    @Test
    public void shouldNotAbleToBeGlued() throws TileExceptions,PuzzleExceptions,BoardExceptions {
        puzzle.addTile("freelance", 3, 4, "red");
        try {
            puzzle.addGlue(3,4);
            fail();
        } catch (Exception e) {
            assertTrue(true);
        }
    }
    
    @Test
    public void shouldNotFallThroughTheHole() throws TileExceptions,PuzzleExceptions,HoleExceptions,BoardExceptions {
        puzzle.addTile("flying", 3, 4, "green");
        puzzle.makeHole(3, 3);
        puzzle.tilt('l');
        char [][] result = {
            {'r', '.', '.', '.'},
            {'y', 'b', '.', '.'},
            {'g', 'g', '.', '.'},
            {'.', '.', '.', '.'}
        };
        assertArrayEquals(puzzle.actualArrangement(), result);
    }
    
    @Test
    public void shouldDeleteFragilGlueAfterTilt() throws PuzzleExceptions,TileExceptions,GlueExceptions,BoardExceptions {
        puzzle.addGlue("fragil", 2, 2);
        puzzle.tilt('r');
        puzzle.tilt('d');
        char [][] result = {
            {'.', '.', '.', '.'},
            {'.', '.', '.', '.'},
            {'.', '.', 'y', 'r'},
            {'.', '.', 'g', 'b'}
        };
        assertArrayEquals(puzzle.actualArrangement(), result);
    }
    
    @Test
    public void shouldNotMoveTheTile() throws TileExceptions,PuzzleExceptions,BoardExceptions {
        puzzle.addTile("sticky", 2, 3, "yellow");
        try {
            puzzle.relocateTile(new int[] {2,3}, new int[] {3,3});
            fail();
        } catch (Exception e) {
            assertTrue(true);
        }
    }
    
    @Test
    public void shouldMoveTheTileIfGlued() throws GlueExceptions,TileExceptions,PuzzleExceptions,BoardExceptions {
        puzzle.addTile("sticky", 2, 3, "yellow");
        puzzle.addGlue(2,3);
        try {
            puzzle.relocateTile(new int[] {2,3}, new int[] {4,3});
            assertTrue(true);
        } catch (Exception e) {
            fail();
        }
    }

}
