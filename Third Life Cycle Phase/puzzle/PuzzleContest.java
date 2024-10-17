import java.util.HashSet;
import java.util.Set;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.List;
import java.util.ArrayList;

/**
 * Write a description of class PuzzleContest here.
 *
 * @author Botero Garcia, Santiago. Perilla Quintero, Laura Natalia.
 * @version October 04 2024
 */
public class PuzzleContest {
    private Puzzle puzzle;
    private Set<String> visitedStates;
    private char[][] originalMatrix;
    private String solutionMoves;

    /**
     * Creates a puzzle given the starting board and the ending board.
     * @param starting      Starting matrix.
     * @param ending        Ending matrix.
     */
    public PuzzleContest(char[][] starting, char[][] ending) {
        puzzle = new Puzzle(starting, ending);
        originalMatrix = starting;
    }
    
    /**
     * Gets if the given puzzle is solvable.
     * @return      If the puzzle is solvable return true, otherwise return false.
     */
    public boolean solve() {
        visitedStates = new HashSet<>();
        Queue<StateWithMoves> queue = new LinkedList<>();
        queue.add(new StateWithMoves(originalMatrix, ""));
        visitedStates.add(matrixToString(originalMatrix));
        char[] directions = {'u', 'd', 'l', 'r'};
        
        if (puzzle.isGoal()) {
            solutionMoves = null;
            return true;
        }
        while (!queue.isEmpty()) {
            StateWithMoves currentStateWithMoves = queue.poll();
            char[][] currentState = currentStateWithMoves.state;
            String currentMoves = currentStateWithMoves.moves;
            
            for (char d: directions) {
                puzzle.tilt(d);
                char[][] newState = puzzle.actualArrangement();
                String newStateString = matrixToString(newState);
                
                if (!visitedStates.contains(newStateString)) {
                    if (puzzle.isGoal()) {
                        solutionMoves = currentMoves + d;
                        puzzle.setArrangement(originalMatrix);
                        return true;
                    }
                    visitedStates.add(newStateString);
                    queue.add(new StateWithMoves(newState, currentMoves + d));
                }
                puzzle.setArrangement(currentState);
            }
        }
        
        puzzle.setArrangement(originalMatrix);
        solutionMoves = null;
        return false;
    }
    
    /**
     * Gets a String representation from the given matrix.
     * @param matrix    State of the board.
     * @return          A string representing the matrix.
     */
    private String matrixToString(char[][] matrix) {
        StringBuilder sb = new StringBuilder();
        for (char[] row : matrix) {
            sb.append(Arrays.toString(row));
        }
        return sb.toString();
    }
    
    /**
     * Class to store the puzzle state and moves taken to reach that state.
     */
    private class StateWithMoves {
        char[][] state;
        String moves;

        public StateWithMoves(char[][] state, String moves) {
            this.state = state;
            this.moves = moves;
        }
    }

    /**
     * If the puzzle is solvable, the board tilts till the current state of the board gets to the ending matrix.
     */
    public void simulate() {
        if (solutionMoves != null) {
            puzzle.setArrangement(originalMatrix);
            
            for (char move : solutionMoves.toCharArray()) {
                puzzle.tilt(move);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    
    //Methods made for the purpose of running unit tests
    
    public void makeInvisible(){
        puzzle.makeInvisible();
    }
    
    public void finishGame(){
        puzzle.finish();
    }
    
    public char[][] getActualArrangement(){
        return puzzle.actualArrangement();
    }
}
