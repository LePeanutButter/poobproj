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
     * Default constructor for test class PuzzleC2Test
     */
    public PuzzleC2Test(){
        
    }

    /**
     * Sets up the test fixture.
     *
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
        puzzle.makeInvisible();
    }
    
    /**
     * Tests if the Puzzle constructor initializes with valid dimensions.
     * It should not throw any exceptions.
     */
    @Test
    public void accordingBPShouldInitializeBoardWithValidDimensions() {
        assertDoesNotThrow(() -> {
            new Puzzle(120, 160);
            new Puzzle(80, 160);
        });
    }
    
    /**
     * Tests if the Puzzle constructor throws an exception for invalid dimensions.
     */
    @Test
    public void accordingBPShouldThrowExceptionForInvalidDimensions() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Puzzle(100, 150); 
            new Puzzle(-100, -150);
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
        char[][] ending1 = {
            {'r', '.', '.'},
            {'.', 'b', 'g'}
        };
        assertDoesNotThrow(() -> {
            new Puzzle(ending);
            new Puzzle(ending1);
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
            new Puzzle(ending);
            new Puzzle(ending1);
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
        char[][] validStarting1 = {
            {'r', '.', '.', '.'},
            {'.', '.', 'g', '.'},
            {'.', '.', '.', '.'},
            {'.', '.', '.', '.'}
        };
        char[][] validEnding1 = {
            {'.', 'r', '.', '.'},
            {'.', '.', '.', '.'},
            {'.', 'g', '.', '.'},
            {'.', '.', '.', '.'}
        };  
        assertDoesNotThrow(() -> {
            new Puzzle(validStarting,validEnding);
            new Puzzle(validStarting1,validEnding1);
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
            new Puzzle(invalidStarting, invalidEnding);
            new Puzzle(invalidStarting1, invalidEnding1);
        });
    }
    
    /**
     * Tests if a tile can be added to the puzzle at a valid position.
     */
    @Test
    public void accordingBPShouldAddTile() {
        puzzle.addTile(1, 1, "r");
        puzzle.addTile(1, 4, "g");
        String actualValue = Character.toString(puzzle.actualArrangement()[0][0]);
        String actualValue1 = Character.toString(puzzle.actualArrangement()[0][3]);
        assertEquals("r",actualValue);
        assertEquals("g",actualValue1);
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
        puzzle.addTile(1, 4, "g");
        puzzle.deleteTile(1, 1);
        puzzle.deleteTile(1, 4);
        String actualValue = Character.toString(puzzle.actualArrangement()[0][0]);
        String actualValue1 = Character.toString(puzzle.actualArrangement()[0][3]);
        assertEquals(".",actualValue);
        assertEquals(".",actualValue1);
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
        
        puzzle.addTile(2, 1, "g");
        int[] from1 = {2, 1};
        int[] to1 = {3, 3};
        puzzle.relocateTile(from1,to1);
        String actualValue1 = Character.toString(puzzle.actualArrangement()[2][2]);
        assertEquals("g", actualValue1);
        assertEquals(".", Character.toString(puzzle.actualArrangement()[1][0])); 
    }
    
    /**
     * Tests if an exception is thrown when trying to relocate a tile to an invalid position.
     */
    @Test
    public void accordingBPShouldNotRelocateTileToInvalidPosition() {
        int[] from = {1, 1};
        int[] to = {3, 3};
        int[] from1 = {1}; 
        int[] to1 = {2};
        assertThrows(IllegalArgumentException.class, () -> {
            puzzle.relocateTile(from, to);
            puzzle.relocateTile(from1, to1);
        });
    }

    /**
     * Tests if glue can be added to a tile.
     */
    @Test
    public void accordingBPShouldAddGlue() {
        puzzle.addTile(2, 4, "r");
        puzzle.addGlue(2, 4);
        String tileContent = puzzle.tempMatrix()[1][3];
        assertEquals("rG", tileContent);
        puzzle.addTile(3, 4, "g");
        puzzle.addGlue(3, 4);
        String tileContent1 = puzzle.tempMatrix()[2][3];
        assertEquals("gG", tileContent1);
    }
    
    /**
     * Tests if glue is not added to an empty tile.
     */
    @Test
    public void accordingBPShouldNotAddGlueToEmptyTile() {
        puzzle.addGlue(2, 4);
        String tileContent = puzzle.tempMatrix()[1][3];
        assertEquals(".", tileContent);
        puzzle.addGlue(3, 4);
        String tileContent1 = puzzle.tempMatrix()[2][3];
        assertEquals(".", tileContent1);
    }

    /**
     * Tests if glue can be deleted from a tile.
     */
    @Test
    public void accordingBPShouldDeleteGlueFromTile() {
        puzzle.addTile(2, 4, "r");
        puzzle.addGlue(2, 4); 
        puzzle.deleteGlue(2, 4);
        String tileContent = puzzle.tempMatrix()[1][3];
        assertEquals("r", tileContent);
        
        puzzle.addTile(3, 4, "r");
        puzzle.addGlue(3, 4); 
        puzzle.deleteGlue(3, 4);
        String tileContent1 = puzzle.tempMatrix()[2][3];
        assertEquals("r", tileContent1);
    }
    
    /**
     * Tests if an exception is thrown when trying to delete glue from an empty tile.
     */
    @Test
    public void accordingBPShouldNotDeleteGlueFromEmptyTile() {
        puzzle.deleteGlue(2, 4);
        String tileContent = puzzle.tempMatrix()[1][3];
        assertEquals(".", tileContent);
        puzzle.deleteGlue(1, 2);
        String tileContent1 = puzzle.tempMatrix()[0][1];
        assertEquals("y", tileContent1);
    }

    /**
     * Test that tilting the puzzle works correctly.
     */
    @Test
    public void accordingBPShouldTilt() {
        puzzle.tilt('l');
        String[][] result = puzzle.tempMatrix();
        assertEquals("y", result[0][0]);
        assertEquals("g", result[1][0]);
        assertEquals(".", result[2][0]);
        assertEquals(".", result[3][0]);
        
        puzzle.tilt('d');
        String[][] result1 = puzzle.tempMatrix();
        assertEquals("y", result1[2][0]);
        assertEquals("g", result1[3][0]);
        assertEquals(".", result1[3][1]);
        assertEquals(".", result1[3][2]);
        assertEquals(".", result1[3][3]);
    }
    
    /**
     * Test that making a hole at the specified position updates the puzzle correctly.
     * A hole is represented by the character 'H'.
     */
    @Test
    public void accordingBPShouldMakeHole() {
        puzzle.makeHole(1, 1);
        assertEquals("H", Character.toString(puzzle.actualArrangement()[0][0]));
        
        puzzle.makeHole(3, 3);
        assertEquals("H", Character.toString(puzzle.actualArrangement()[2][2]));
    }

    /**
     * Test that making a hole at an invalid position throws an IllegalArgumentException.
     * Invalid positions are those that do not correspond to an empty tile.
     */
    @Test
    public void accordingBPShouldThrowExceptionForInvalidMakeHole() {
        assertThrows(IllegalArgumentException.class, () -> {
            puzzle.makeHole(1, 2); 
            puzzle.makeHole(2, 3); 
        });
    }
    
    /**
     * Test that exchanging tiles in valid positions updates the arrangement correctly.
     * This verifies that the current arrangement is swapped with the ending matrix.
     */
    @Test
    public void accordingBPShouldExchangeTilesInValidPositions() {
        char[][] act = puzzle.actualArrangement();
        char[][] end = puzzle.getEndingMatrix();
        
        puzzle.exchange();

        assertArrayEquals(end, puzzle.actualArrangement());
        assertArrayEquals(act, puzzle.getEndingMatrix());
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
        assertEquals(expectedCoordinates.length, actualCoordinates.length);
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
        int[][] actualCoordinates = puzzle.misplacedTiles();
        assertArrayEquals(expectedCoordinates, actualCoordinates);
    }
    
    /**
     * Tests that the tilt() method performs an optimal move, 
     * ensuring that the number of fixed tiles remains the same or increases 
     * and that the number of misplaced tiles remains the same or decreases.
     */
    @Test
    public void accordingBPShouldOptimalTilt() {
        puzzle.tilt();
        assertArrayEquals(puzzle.getActualMatrix(), new String[][]{{".","y","g","."},{".",".",".","."},{".",".",".","."},{".",".",".","."}});
    }
    
    /**
     * Cleans up resources used by the tests once all have been completed.
     * This ensures that any windows or resources created during the tests are closed properly,
     * preventing potential memory leaks or unused resources.
     */
    @AfterAll
    public static void tearDown(){
        puzzle.finish();
    }
}
