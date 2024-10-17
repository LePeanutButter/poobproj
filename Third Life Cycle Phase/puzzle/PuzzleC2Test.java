import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * The test class PuzzleC2Test.
 *
 * @author Botero Garcia, Santiago. Perilla Quintero, Laura Natalia.
 * @version September 21 2024
 */
public class PuzzleC2Test{
    private static Puzzle puzzle;

    /**
     * Sets up the test fixture.
     * Called before every test case method.
     */

    @BeforeEach
    public void setUp() {
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
        assertDoesNotThrow(() -> {
            new Puzzle(120, 160).makeInvisible();
        });
    }
    
    /**
     * Tests if the Puzzle constructor throws an exception for invalid dimensions.
     */
    @Test
    public void accordingBPShouldThrowExceptionForInvalidDimensions() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Puzzle(-100, -150).makeInvisible();
        });
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
        assertDoesNotThrow(() -> {
            new Puzzle(ending).makeInvisible();
        });
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
        char[][] ending1 = null;
        assertThrows(IllegalArgumentException.class, () -> {
            new Puzzle(ending).makeInvisible();
            new Puzzle(ending1).makeInvisible();
        });
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
        assertDoesNotThrow(() -> {
            new Puzzle(validStarting,validEnding).makeInvisible();
        });
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
        
        char[][] invalidStarting1 = null;
        char[][] invalidEnding1 = null;
        assertThrows(IllegalArgumentException.class, () -> {
            new Puzzle(invalidStarting, invalidEnding).makeInvisible();
            new Puzzle(invalidStarting1, invalidEnding1).makeInvisible();
        });
    }
    
    /**
     * Tests if a tile can be added to the puzzle at a valid position.
     */
    @Test
    public void accordingBPShouldAddTile() {
        puzzle.addTile(1, 1, "r");
        String actualValue = Character.toString(puzzle.actualArrangement()[0][0]);
        assertEquals("r",actualValue);
    }
    
    /**
     * Tests if an exception is thrown when trying to add a tile at an invalid position.
     */
    @Test
    public void accordingBPShouldThrowExceptionForInvalidTilePosition() {
        assertThrows(IndexOutOfBoundsException.class, () -> {
            puzzle.addTile(5, 5, "r");
            puzzle.addTile(1, 3, "f");
        });
    }

    /**
     * Tests if a tile can be deleted from the puzzle at a valid position.
     */
    @Test
    public void accordingBPShouldDeleteTile() {
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
        assertThrows(IllegalArgumentException.class, () -> {
            puzzle.deleteTile(2, 1);
            puzzle.deleteTile(2, 2);
        });
    }
    
    /**
     * Tests if a tile can be relocated from one position to another.
     */
    @Test
    public void accordingBPShouldRelocateTile() {
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
        assertThrows(IllegalArgumentException.class, () -> {
            puzzle.relocateTile(from, to);
        });
    }

    /**
     * Tests if glue can be added to a tile.
     */
    @Test
    public void accordingBPShouldAddGlue() {
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
        assertThrows(IllegalArgumentException.class, () -> puzzle.addGlue(2, 4));
    }

    /**
     * Tests if glue can be deleted from a tile.
     */
    @Test
    public void accordingBPShouldDeleteGlueFromTile() {
        puzzle.addTile(2, 4, "r");
        puzzle.addGlue(2, 4);
        assertDoesNotThrow(() -> puzzle.deleteGlue(2, 4));
    }
    
    /**
     * Tests if an exception is thrown when trying to delete glue from an empty tile.
     */
    @Test
    public void accordingBPShouldNotDeleteGlueFromEmptyTile() {
        assertThrows(IllegalArgumentException.class, () -> puzzle.deleteGlue(2, 4));
    }

    /**
     * Test that tilting the puzzle to the left ('l') works correctly.
     */
    @Test
    public void accordingBPShouldTilt() {
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
    public void accordingBPShouldTiltRight() {
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
    public void accordingBPShouldTiltUp() {
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
    public void accordingBPShouldTiltDown() {
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
        puzzle.makeHole(1, 1);
        assertEquals("H", Character.toString(puzzle.actualArrangement()[0][0]));
    }

    /**
     * Test that making a hole at an invalid position throws an IllegalArgumentException.
     * Invalid positions are those that do not correspond to an empty tile.
     */
    @Test
    public void accordingBPShouldThrowExceptionForInvalidMakeHole() {
        assertThrows(IllegalArgumentException.class, () -> {
            puzzle.makeHole(1, 2); 
        });
    }
    
    /**
     * Test that exchanging tiles in valid positions updates the arrangement correctly.
     * This verifies that the current arrangement is swapped with the ending matrix.
     */
    @Test
    public void accordingBPShouldExchangeTilesInValidPositions() {
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
    
        Puzzle puzzle1 = new Puzzle(startingMatrix, endingMatrix);
        puzzle1.makeInvisible();
        assertTrue(puzzle1.isGoal());
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
     * Test that making the puzzle visible changes its visibility status to true.
     * This ensures that the puzzle correctly updates its state when the makeVisible method is called.
     */
    @Test
    public void accordingBPShouldMakeVisible() {
        puzzle.makeVisible();
        assertTrue(puzzle.isVisible());
    }
    
    /**
     * Test that making the puzzle invisible changes its visibility status to false.
     * This ensures that the puzzle correctly updates its state when the makeInvisible method is called.
     */
    @Test
    public void accordingBPShouldMakeInvisible() {
        puzzle.makeVisible();
        puzzle.makeInvisible();
        assertFalse(puzzle.isVisible());
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
    public void accordingBPShouldOptimalTilt() {
        puzzle.tilt();
        assertArrayEquals(puzzle.actualArrangement(), new char[][]{{'.','y','g','.'},{'.','.','.','.'},{'.','.','.','.'},{'.','.','.','.'}});
    }
    
    /**
     * Cleans up resources used by the tests once all have been completed.
     * This ensures that any windows or resources created during the tests are closed properly,
     * preventing potential memory leaks or unused resources.
     */
    @AfterAll
    public static void tearDown(){
        puzzle.makeInvisible();
        puzzle.finish();
        puzzle = null;
    }
}
