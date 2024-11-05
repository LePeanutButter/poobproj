import puzzle.*;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * The test class PuzzleC2Test.
 *
 * @author Botero Garcia, Santiago. Perilla Quintero, Laura Natalia.
 * @version September 21 2024
 */
public class PuzzleC2Test{
    private Puzzle puzzle;
    
    /**
     * Sets up the test fixture.
     * Called before every test case method.
     */

    @Before
    public void setUp() throws BoardExceptions, PuzzleExceptions, TileExceptions {
        char[][] startingMatrix = {
            {'.', 'y', '.', '.'},
            {'.', '.', 'g', '.'},
            {'.', '.', '.', '.'},
            {'.', '.', '.', '.'}
        };
        char[][] endingMatrix = {
            {'.', 'y', '.', '.'},
            {'.', '.', '.', '.'},
            {'.', 'g', '.', '.'},
            {'.', '.', '.', '.'}
        };
        puzzle = new Puzzle(startingMatrix, endingMatrix);
    }
    
    /**
     * Tests if the Puzzle constructor initializes with valid dimensions.
     * It should not throw any exceptions.
     */
    @Test
    public void accordingBPShouldInitializeBoardWithValidDimensions() {
        try {
            new Puzzle(5, 4);
        } catch (Exception e) {
            fail();
        }
    }
    
    /**
     * Tests if the Puzzle constructor throws an exception for invalid dimensions.
     */
    @Test
    public void accordingBPShouldThrowExceptionForInvalidDimensions() {
        try {
            new Puzzle(-100, -150);
            fail();
        } catch (Exception e) {
            assertTrue(true);
        }
    }
    
    /**
     * Tests if the Puzzle constructor initializes with a valid ending matrix.
     * It should not throw any exceptions.
     */
    @Test
    public void accordingBPShouldInitializePuzzleWithEndingMatrix() {
        char[][] ending = {
            {'r', 'y', '.'},
            {'.', 'b', 'g'}
        };
        try {
            new Puzzle(ending);
            assertTrue(true);
        } catch (Exception e) {
            fail();
        }
    }
    
    /**
     * Tests if the Puzzle constructor throws an exception for an invalid ending matrix.
     */
    @Test
    public void accordingBPShouldThrowExceptionForInvalidEndingMatrix() {
        char[][] ending = {
                {'r', 'y'},
                {'b', 'g', 'm'}
            };
        try {
            new Puzzle(ending);
            fail();
        } catch (Exception e) {
            assertTrue(true);
        }
    }
    
    /**
     * Tests if the Puzzle constructor initializes with valid starting and ending matrices.
     * It should not throw any exceptions.
     */
    @Test
    public void accordingBPShouldInitializePuzzleForValidMatrices() {
        char[][] validStarting = {
            {'.', '.', '.', '.'},
            {'.', '.', '.', '.'},
            {'.', '.', '.', '.'},
            {'.', '.', '.', '.'}
        };
        char[][] validEnding = {
            {'.', '.', '.', '.'},
            {'.', '.', '.', '.'},
            {'.', '.', '.', '.'},
            {'.', '.', '.', '.'}
        };
        try {
            new Puzzle(validStarting, validEnding);
            assertTrue(true);
        } catch (Exception e) {
            fail();
        }
    }
    
    /**
     * Tests if the Puzzle constructor throws an exception for invalid matrices.
     */
    @Test
    public void accordingBPShouldThrowExceptionForInvalidMatrices() {
        char[][] invalidStarting = {
            {'.', '.', '.'},
            {'.', '.', '.'}
        };
        char[][] invalidEnding = {
            {'.', '.'},
            {'.', '.'}
        };
        try {
            new Puzzle(invalidStarting, invalidEnding);
            fail();
        } catch (Exception e) {
            assertTrue(true);
        }
    }
    
    /**
     * Tests if a tile can be added to the puzzle at a valid position.
     */
    @Test
    public void accordingBPShouldAddTile() throws TileExceptions,PuzzleExceptions,BoardExceptions {
        puzzle.addTile(1, 1, "red");
        String actualValue = Character.toString(puzzle.actualArrangement()[0][0]);
        assertEquals("r",actualValue);
    }
    
    /**
     * Tests if an exception is thrown when trying to add a tile at an invalid position.
     */
    @Test
    public void accordingBPShouldThrowExceptionForInvalidTilePosition() {
        try {
            puzzle.addTile(5,5,"red");
            fail();
        } catch (Exception e) {
            assertTrue(true);    
        }
    }

    /**
     * Tests if a tile can be deleted from the puzzle at a valid position.
     */
    @Test
    public void accordingBPShouldDeleteTile() throws PuzzleExceptions,TileExceptions,BoardExceptions {
        puzzle.addTile(1, 1, "r");
        puzzle.deleteTile(1, 1);
        String actualValue = Character.toString(puzzle.actualArrangement()[0][0]);
        assertEquals(".",actualValue);
    }
    
    /**
     * Tests if an exception is thrown when trying to delete a tile from an invalid position.
     */
    @Test
    public void accordingBPShouldThrowExceptionForInvalidDeleteTile() {
        try {
            puzzle.deleteTile(2,1);
            fail();
        } catch (Exception e) {
            assertTrue(true);
        }
    }
    
    /**
     * Tests if a tile can be relocated from one position to another.
     */
    @Test
    public void accordingBPShouldRelocateTile() throws TileExceptions,PuzzleExceptions,BoardExceptions {
        puzzle.addTile(2, 2, "r");
        int[] from = {2, 2};
        int[] to = {3, 1};
        puzzle.relocateTile(from,to);
        String actualValue = Character.toString(puzzle.actualArrangement()[2][0]);
        assertEquals("r", actualValue);
        assertEquals(".", Character.toString(puzzle.actualArrangement()[1][1]));
    }
    
    /**
     * Tests if an exception is thrown when trying to relocate a tile to an invalid position.
     */
    @Test
    public void accordingBPShouldNotRelocateTileToInvalidPosition() {
        int[] from = {1}; 
        int[] to = {2};
        try {
            puzzle.relocateTile(from, to);
            fail();
        } catch (Exception e) {
            assertTrue(true);
        }
    }

    /**
     * Tests if glue can be added to a tile.
     */
    @Test
    public void accordingBPShouldAddGlue() throws TileExceptions,PuzzleExceptions,GlueExceptions,BoardExceptions {
        puzzle.addTile(2, 2, "r");
        puzzle.addGlue(2, 2);
        puzzle.tilt('r');
        assertArrayEquals(puzzle.actualArrangement(), new char[][] {{'.', '.', 'y', '.'},{'.', '.', 'r', 'g'},{'.', '.', '.', '.'},{'.', '.', '.', '.'}});
    }

    /**
     * Tests if glue is not added to an empty tile.
     */
    @Test
    public void accordingBPShouldNotAddGlueToEmptyTile() {
        try {
            puzzle.addGlue(2,4);
            fail();
        } catch (Exception e) {
            assertTrue(true);
        }
    }

    /**
     * Tests if glue can be deleted from a tile.
     */
    @Test
    public void accordingBPShouldDeleteGlueFromTile() throws TileExceptions,PuzzleExceptions,GlueExceptions,BoardExceptions {
        puzzle.addTile(2, 4, "red");
        puzzle.addGlue(2, 4);
        try {
            puzzle.deleteGlue(2,4);
            assertTrue(true);
        } catch (Exception e) {
            fail();
        }
    }
    
    /**
     * Tests if an exception is thrown when trying to delete glue from an empty tile.
     */
    @Test
    public void accordingBPShouldNotDeleteGlueFromEmptyTile() {
        try {
            puzzle.deleteGlue(2,4);
            fail();
        } catch (Exception e) {
            assertTrue(true);
        }
    }

    /**
     * Test that tilting the puzzle to the left ('l') works correctly.
     */
    @Test
    public void accordingBPShouldTiltLeft() throws TileExceptions,PuzzleExceptions,BoardExceptions {
        puzzle.tilt('l');
        assertArrayEquals(puzzle.actualArrangement(), new char[][] {
            {'y', '.', '.', '.'},
            {'g', '.', '.', '.'},
            {'.', '.', '.', '.'},
            {'.', '.', '.', '.'}
        });
    }
    
    /**
     * Test that tilting the puzzle to the right ('r') works correctly.
     */
    @Test
    public void accordingBPShouldTiltRight() throws TileExceptions,PuzzleExceptions,BoardExceptions {
        puzzle.tilt('r');
        assertArrayEquals(puzzle.actualArrangement(), new char[][] {
            {'.', '.', '.', 'y'},
            {'.', '.', '.', 'g'},
            {'.', '.', '.', '.'},
            {'.', '.', '.', '.'}
        });
    }
    
    /**
     * Test that tilting the puzzle up ('u') works correctly.
     */
    @Test
    public void accordingBPShouldTiltUp() throws TileExceptions,PuzzleExceptions,BoardExceptions {
        puzzle.tilt('u');
        assertArrayEquals(puzzle.actualArrangement(), new char[][] {
            {'.', 'y', 'g', '.'},
            {'.', '.', '.', '.'},
            {'.', '.', '.', '.'},
            {'.', '.', '.', '.'}
        });
    }
    
    /**
     * Test that tilting the puzzle down ('d') works correctly.
     */
    @Test
    public void accordingBPShouldTiltDown() throws TileExceptions,PuzzleExceptions,BoardExceptions  {
        puzzle.tilt('d');
        assertArrayEquals(puzzle.actualArrangement(), new char[][] {
            {'.', '.', '.', '.'},
            {'.', '.', '.', '.'},
            {'.', '.', '.', '.'},
            {'.', 'y', 'g', '.'}
        });
    }
    
    /**
     * Test that making a hole at the specified position updates the puzzle correctly.
     * A hole is represented by the character 'H'.
     */
    @Test
    public void accordingBPShouldMakeHole() {
        try {
            puzzle.makeHole(1, 1);
            assertTrue(true);
        } catch (Exception e) {
            fail();
        }
    }

    /**
     * Test that making a hole at an invalid position throws an IllegalArgumentException.
     * Invalid positions are those that do not correspond to an empty tile.
     */
    @Test
    public void accordingBPShouldThrowExceptionForInvalidMakeHole() {
        try {
            puzzle.makeHole(1,2);
            fail();
        } catch (Exception e) {
            assertTrue(true);
        }
    }
    
    /**
     * Test that exchanging tiles in valid positions updates the arrangement correctly.
     * This verifies that the current arrangement is swapped with the ending matrix.
     */
    @Test
    public void accordingBPShouldExchangeTilesInValidPositions() throws TileExceptions,PuzzleExceptions {
        puzzle.exchange();
        assertArrayEquals(puzzle.actualArrangement(), new char[][]{{'.', 'y', '.', '.'}, {'.', '.', '.', '.'}, {'.', 'g', '.', '.'},{'.', '.', '.', '.'}});
    }
    
    /**
     * Verifies that the current arrangement of the puzzle is correct.
     * This ensures that the puzzle recognizes when it has reached the desired shape.
     */
    @Test
    public void accordingBPShouldReturnTrueWhenCurrentArrangementIsGoal() {
        char[][] startingMatrix = {
            {'r', 'y', '.'},
            {'.', '.', 'g'},
            {'.', '.', '.'},
            {'.', '.', '.'}
        };
    
        char[][] endingMatrix = {
            {'r', 'y', '.'},
            {'.', '.', 'g'},
            {'.', '.', '.'},
            {'.', '.', '.'}
        };
        try {
            Puzzle puzzle1 = new Puzzle(startingMatrix, endingMatrix);
            assertTrue(puzzle1.isGoal());
        } catch (Exception e) {
            fail();
        }
    }
    
    /**
     * Verifies that the actual arrangement of the puzzle matches the saved configuration.
     * This ensures that the puzzle's current state is consistent with what is stored in actualArrangement.
     */
    @Test
    public void accordingBPShouldReturnActualArrangement() {
        char[][] startingMatrix = {
            {'.', 'y', '.', '.'},
            {'.', '.', 'g', '.'},
            {'.', '.', '.', '.'},
            {'.', '.', '.', '.'}
        };
    
        assertArrayEquals(startingMatrix, puzzle.actualArrangement());
    }
    
    /**
     * Tests that the fixedTiles() method returns the correct coordinates of the fixed tiles.
     */
    @Test
    public void accordingBPShouldReturnFixedTiles() {
        int[][] expectedCoordinates = {
            {1, 2}
        };
        int[][] actualCoordinates = puzzle.fixedTiles();
        assertArrayEquals(expectedCoordinates, actualCoordinates);
    }
    
    /**
     * Tests that the misplacedTiles() method returns the correct coordinates of the misplaced tiles.
     */
    @Test
    public void accordingBPShouldReturnMisplacedTiles() {
        int[][] expectedCoordinates = {
            {2, 3}
        };
        assertEquals(expectedCoordinates.length, puzzle.misplacedTiles());
    }
    
    /**
     * Tests that the tilt() method performs an optimal move, 
     * ensuring that the number of fixed tiles remains the same or increases 
     * and that the number of misplaced tiles remains the same or decreases.
     */
    @Test
    public void accordingBPShouldOptimalTilt() throws TileExceptions,PuzzleExceptions,BoardExceptions  {
        puzzle.tilt();
        assertArrayEquals(puzzle.actualArrangement(), new char[][]{{'.','y','g','.'},{'.','.','.','.'},{'.','.','.','.'},{'.','.','.','.'}});
    }
}
