package puzzle;

import java.util.HashSet;
import java.util.Set;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;

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
    private HashMap<Character, String> colorMap;
    
    public PuzzleContest () {
        colorMap = new HashMap<>();
        colorMap.put('r', "red");
        colorMap.put('y', "yellow");
        colorMap.put('b', "blue");
        colorMap.put('g', "green");
        colorMap.put('m', "magenta");
    }
    
    /**
     * Gets if the given puzzle is solvable.
     * @return      If the puzzle is solvable return true, otherwise return false.
     */
    public boolean solve(char[][] starting, char[][] ending) throws puzzle.TileExceptions,puzzle.PuzzleExceptions,puzzle.BoardExceptions {
        this.puzzle = new Puzzle(starting, ending);
        this.originalMatrix = starting;
        this.visitedStates = new HashSet<>();
        
        Queue<StateWithMoves> queue = new LinkedList<>();
        queue.add(new StateWithMoves(originalMatrix, ""));
        visitedStates.add(matrixToString(originalMatrix));
        
        if (puzzle.isGoal()) {
            solutionMoves = null;
            return true;
        }
        char[] directions = {'u', 'd', 'l', 'r'};
        while (!queue.isEmpty()) {
            StateWithMoves currentStateWithMoves = queue.poll();
            char[][] currentState = currentStateWithMoves.state; //
            String currentMoves = currentStateWithMoves.moves; //
            
            for (char d: directions) {
                puzzle.tilt(d);
                char[][] newState = puzzle.actualArrangement();
                String newStateString = matrixToString(newState);
                
                if (!visitedStates.contains(newStateString)) {
                    if (puzzle.isGoal()) {
                        solutionMoves = currentMoves + d;
                        updatePuzzleTiles(originalMatrix);
                        return true;
                    }
                    visitedStates.add(newStateString);
                    queue.add(new StateWithMoves(newState, currentMoves + d));
                }
                updatePuzzleTiles(currentState);
            }
        }
        updatePuzzleTiles(originalMatrix);
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
    
    private void updatePuzzleTiles(char[][] state) throws puzzle.BoardExceptions {
        HashMap<String, Tile> tiles = new HashMap<>();
        for (int i = 0; i < state.length; i++) {
            for (int j = 0; j < state[0].length; j++) {
                if (state[i][j] != '.') {
                    tiles.put(Arrays.toString(new int[] {i + 1, j + 1}), new NormalTile(i + 1, j + 1, colorMap.get(state[i][j]), 40, 40));
                }
            }
        }
        puzzle.setArrangement(state, tiles);
    }

    
    /*
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
    public void simulate(char[][] starting, char[][] ending) throws puzzle.TileExceptions,puzzle.PuzzleExceptions,puzzle.BoardExceptions {
        solve(starting, ending);
        if (solutionMoves != null) {
            for (char move : solutionMoves.toCharArray()) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                puzzle.tilt(move);
            }
        }
    }
    
    //Methods made for the purpose of running unit tests
    
    public char[][] getActualArrangement(){
        return puzzle.actualArrangement();
    }
}
