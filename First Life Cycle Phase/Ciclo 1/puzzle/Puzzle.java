import java.util.List;
import java.util.LinkedList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.*;
import javax.swing.JOptionPane;

/**
 * Tilting Tiles is a game inspired by Problem F from the 2023 International Programming Marathon, which tests a gummy glue.
 *
 * @author Botero Garcia, Santiago. Perilla Quintero, Laura Natalia.
 * @version September 15 2024
 */
public class Puzzle{
    private Rectangle board;
    private LinkedList<LinkedList<String>> startingMatrix;
    private LinkedList<LinkedList<String>> matrix;
    private LinkedList<Rectangle> tiles;
    private LinkedList<LinkedList<String>> endingMatrix;
    private Map<String, String> colorMap;
    
    /**
     * Create a puzzle given the dimentions of the board
     * @param h     Height of the board.
     * @param w     Width of the board.
     */
    public Puzzle(int h, int w){
        if (h <= 0 || w <= 0 ) {
            throw new IllegalArgumentException("The dimensions of the rectangle must be greater than zero.");
        } else if (h%40!=0 || w%40!=0){
            throw new IllegalArgumentException("The reminder of the euclidian division between the dimensions of the rectangle by forty must be zero.");
        }
        board(h, w);
        colorMap();
        for (int i = 0; i < (h/40); i++){
            LinkedList<String> startingRow = new LinkedList<>();
            LinkedList<String> endingRow = new LinkedList<>();
            for (int j = 0; j < (w/40); j++){
                startingRow.add(String.valueOf("."));
                endingRow.add(String.valueOf("."));
            }
            startingMatrix.add(startingRow);
            matrix.add(startingRow);
            endingMatrix.add(endingRow);
        }
    }

    /**
     * Stores the ending puzzle given an array
     * @param ending    Ending matrix/Goal.
     */
    public Puzzle(char[][] ending){
        if (ending == null){
            throw new IllegalArgumentException("The boards cannot be null.");
        }
        int rows = ending.length;
        int columns = ending[0].length;
        for (int i = 0; i < rows; i++){
            if (ending[i].length != columns) {
                throw new IllegalArgumentException("The number of columns in ending board does not match.");
            }
        }
        board(40 * rows, 40 * columns);
        colorMap();
        for (int i=0; i < rows; i++){
            LinkedList<String> endingRow = new LinkedList<>();
            for (int j=0; j < columns; j++){
                endingRow.add(String.valueOf(ending[i][j]));
            }
            endingMatrix.add(endingRow);
        }
    }
    
    /**
     * Create the starting puzzle and the ending puzzle given a pair of arrays
     * @param starting      Starting matrix.
     * @param ending        Ending matrix/Goal.
     */
    public Puzzle(char[][] starting,char[][] ending) {
        if (starting == null|| ending == null){
            throw new IllegalArgumentException("The boards cannot be null.");
        }
        int rows = starting.length;
        int columns = starting[0].length;
        for (int i = 0; i < rows; i++){
            if (starting[i].length != columns){
                throw new IllegalArgumentException("The number of columns does not match.");
            } else if (ending[i].length != columns) {
                throw new IllegalArgumentException("The number of columns in ending board does not match.");
            } else if (ending.length != rows){
                throw new IllegalArgumentException("The boards do not match.");
            }
        }
        board(40 * rows, 40 * columns);
        colorMap();
        for (int i=0; i < rows; i++){
            LinkedList<String> startingRow = new LinkedList<>();
            LinkedList<String> endingRow = new LinkedList<>();
            for (int j=0; j < columns; j++){
                startingRow.add(String.valueOf(starting[i][j]));
                endingRow.add(String.valueOf(ending[i][j]));
            }
            startingMatrix.add(startingRow);
            matrix.add(startingRow);
            endingMatrix.add(endingRow);
        }
        printTile(rows, columns, matrix);
    }
    
    /*
     * Creates the board
     * @param h     Board height.
     * @param w     Board width.
     */
    private void board(int h, int w){
        board = new Rectangle(h+20, w+20, 40, 40, "brown", true);
        board.makeVisible();
        startingMatrix = new LinkedList<>();
        matrix = new LinkedList<>();
        endingMatrix = new LinkedList<>();
    }
    
    /*
     * Store key-value pairs of colors
     */
    private void colorMap(){
        colorMap = new HashMap<>();
        colorMap.put("r", "red");
        colorMap.put("y", "yellow");
        colorMap.put("b", "blue");
        colorMap.put("g", "green");
        colorMap.put("m", "magenta");
    }

    /*
     * Retrive the values from the starting matrix to create the tiles from a new board
     * @param rows      Height of the board.
     * @param columns   Width of the board.
     * @param array     Position and color of the rectangles in the current board (matrix).
     */
    private void printTile(int rows, int columns, LinkedList<LinkedList<String>> array){
        for (int i = 0; i < rows; i++){
            LinkedList<String> row = array.get(i);
            for (int j = 0; j < columns; j++){
                String value = row.get(j);
                if (!value.equals(".")){
                    if (tiles == null){
                        tiles = new LinkedList<>();
                    }
                    String color = colorMap.get(value);
                    if (color == null) {
                        throw new IllegalArgumentException("Invalid color character: " + value);
                    }
                    Rectangle newTile = new Rectangle(40, 40, 50+(j * 40), 50+(i * 40), color, true);
                    tiles.add(newTile);
                    newTile.makeVisible();
                }
            }
        }
    }
    
    /**
     * Adds a tile on the board
     * @param row       Row where the tile would be added.
     * @param column    Column where the tile would be added.
     * @param color     Color of the tile (must be r(red), y(ellow), b(lue), g(reen), m(agenta) or any of the previous colours with the -G postfix).
     */
    public void addTile(int row, int column, String color){
        if (tiles == null){
            tiles = new LinkedList<>();
        }
        if (!matrix.get(row-1).get(column-1).equals(".")){
            throw new IllegalArgumentException("The tile cannot be placed in this position.");
        }
        if (!color.equals("r") && !color.equals("y") && !color.equals("b") && !color.equals("g") && !color.equals("m") && !color.equals("rG") && !color.equals("yG") && !color.equals("bG") && !color.equals("gG") && !color.equals("mG")){
            throw new IllegalArgumentException("The input must be the first letter of the color.");
        }
        matrix.get(row-1).set(column-1, color);
        String rectangleColor = colorMap.get(String.valueOf(color.charAt(0)));
        Rectangle newTile = new Rectangle(40, 40, 50+((column - 1) * 40), 50+((row - 1) * 40), rectangleColor, true);
        tiles.add(newTile);
        newTile.makeVisible();
    }
    
    /**
     * Deletes an existing tile on the board
     * @param row       Row of the tile to delete.
     * @param column    Column of the tile to delete.
     */
    public void deleteTile(int row, int column){
        if (tiles == null){
            throw new IllegalArgumentException("The board is empty, there are no tiles that can be deleted.");
        }
        if (matrix.get(row-1).get(column-1).equals(".")){
            throw new IllegalArgumentException("There is no tile place in this position.");
        }
        String value = matrix.get(row-1).get(column-1);
        String color = colorMap.get(String.valueOf(value.charAt(0)));
        int xPosition = 50 + ((column - 1) * 40);
        int yPosition = 50 + ((row - 1) * 40);
        matrix.get(row-1).set(column-1, ".");
        Rectangle toRemove = new Rectangle(40, 40, xPosition, yPosition, color, true);
        tiles.remove(toRemove);
        toRemove.makeInvisible();
    }
    
    /**
     * Changes the position of a tile
     * @param from      Ordered pair indicating the position where the tile is positioned.
     * @param to        Ordered pair indicating the position where the tile will move.
     */
    public void relocateTile(int[] from, int[] to){
        if (from.length !=2 || to.length != 2){
            throw new IllegalArgumentException("The position must be a tuple");
        }
        int matrixRows = matrix.size();
        int matrixCol = matrix.get(0).size();
        if (from[0] > 0 && from[0] <= matrixRows &&  from[1] > 0 && from[1] <= matrixCol && to[0] > 0 && to[0] <= matrixRows &&  to[1] > 0 && to[1] <= matrixCol&& !matrix.get(from[0]-1).get(from[1]-1).equals(".") && matrix.get(to[0]-1).get(to[1]-1).equals(".")){
            List<int[]> deletePositions = new ArrayList<>();
            List<String[]> addPositions = new ArrayList<>();
            int row = from[0];
            int column = from[1];
            int[] difference = new int[]{to[0] - from[0], to[1] - from[1]};
            deletePositions.add(new int[]{from[0], from[1]});
            addPositions.add(new String[]{Integer.toString(to[0]), Integer.toString(to[1]), matrix.get(row-1).get(column-1)});
            
            if (matrix.get(from[0]-1).get(from[1]-1).length()==2) {
                int[][] directions = { { -1, 0 }, { 1, 0 }, { 0, -1 }, { 0, 1 } };
                for (int[] dir : directions) {
                    int adjRow = row + dir[0];
                    int adjCol = column + dir[1];
                    if (adjRow > 0 && adjCol > 0 && adjRow <= matrixRows && adjCol <= matrixCol && !matrix.get(adjRow-1).get(adjCol-1).equals(".")) {
                        int newRow = difference[0] + adjRow;
                        int newCol = difference[1] + adjCol;
                        if (newRow > 0 && newCol > 0 && newRow <= matrixRows && newCol <= matrixCol && matrix.get(newRow-1).get(newCol-1).equals(".")) {
                            deletePositions.add(new int[]{adjRow, adjCol});
                            addPositions.add(new String[]{Integer.toString(newRow), Integer.toString(newCol), matrix.get(adjRow-1).get(adjCol-1)});
                        } else {
                            throw new IllegalArgumentException("The tiles cannot be moved to this position.");
                        }
                    }
                }
            }
            for (int[] pos : deletePositions){
                deleteTile(pos[0], pos[1]);
            }
            for (String[] pos : addPositions){
                addTile(Integer.parseInt(pos[0]), Integer.parseInt(pos[1]), pos[2]);
            }
        }
    }
    
    /**
     * Adds glue to a tile.
     * @param row       Row of the tile.
     * @parama column   Column of the tile.
     */
    public void addGlue(int row, int column){
        if (!matrix.get(row-1).get(column-1).equals(".") ){
            String glueValue = matrix.get(row-1).get(column-1);
            matrix.get(row-1).set(column-1,glueValue+"G");
        }
    }
    
    /**
     * Deletes glue from a tile
     * @param row       Row of the tile.
     * @parama column   Column of the tile.
     */
    public void deleteGlue(int row, int column){
        if (matrix.get(row-1).get(column-1).length() == 2 ){
            String value = matrix.get(row-1).get(column-1);
            matrix.get(row-1).set(column-1,String.valueOf(value.charAt(0)));
        }        
    }
    
    /**
     * Move the tiles to the given direction
     * @param direction     Must be u(p), l(eft), d(own), r(ight).
     */
    public void tilt(char direction){
        int rows = matrix.size();
        int columns = matrix.get(0).size();
        if (direction == 'l' || direction == 'r'){
            moveHorizontal(rows, columns, direction);
        } else if (direction == 'u' || direction == 'd'){
            moveVertical(rows, columns, direction);
        }
        
    }
    
    /*
     * Moves the tiles horizontally
     */
    private void moveHorizontal(int rows, int columns, char operation){
        for (int i=0; i < rows; i++){ 
            int dotCount = 0;
            if (operation == 'l'){
                for (int j=0; j < columns; j++){
                    if (matrix.get(i).get(j).equals(".")) {
                        dotCount++;
                    } else if (dotCount > 0) {
                        relocateTile(new int[]{i+1, j+1}, new int[]{i+1, (j+1) - dotCount});
                    }
                }
            } else if (operation == 'r'){
                for (int j = columns - 1; j >= 0; j--){
                    if (matrix.get(i).get(j).equals(".")) {
                        dotCount++;
                    } else if (dotCount > 0) { 
                        relocateTile(new int[]{i+1, j+1}, new int[]{i+1, (j+1) + dotCount});
                        }
                }
            }
        }
    }
    
    /*
     * Moves the tiles vertically
     */
    private void moveVertical(int rows, int columns, char operation){
        for (int j = 0; j < columns; j++) {
                int dotCount = 0;
                if (operation == 'u'){
                    for (int i = 0; i < rows; i++) {
                        if (matrix.get(i).get(j).equals(".")) {
                            dotCount++;
                        } else if (dotCount > 0) {
                            relocateTile(new int[]{i+1, j+1}, new int[]{(i+1) - dotCount, j+1});
                        }
                    }
                } else if (operation == 'd'){
                    for (int i = rows - 1; i >= 0; i--) {
                        if (matrix.get(i).get(j).equals(".")) {
                            dotCount++;
                        } else if (dotCount > 0) {
                            relocateTile(new int[]{i+1, j+1}, new int[]{(i+1) + dotCount, j+1});
                        }
                    }
                }
        }
    }
    
    /**
     * Compares the current state of the matrix with the ending matrix.
     * @return      If starting matrix equals the ending matrix, returns True.
     */
    public boolean isGoal() {
        char[][] endingMatrixArray = getEndingMatrix();
        char[][] actualArrangementArray = actualArrangement();
        return Arrays.deepEquals(endingMatrixArray, actualArrangementArray);
    }
    
    
    /*
     * Gets the ending matrix.
     * @return      Returns the ending matrix in a two-dimensional array of characters.
     */
    private char[][] getEndingMatrix(){
        int rows = endingMatrix.size();
        int columns = endingMatrix.get(0).size();
        
        char[][] arrangement = new char[rows][columns];
        for (int i = 0; i < rows; i++) {
            LinkedList<String> row = endingMatrix.get(i);
            for (int j = 0; j < columns; j++) {
                arrangement[i][j] = row.get(j).charAt(0);
                }
            }
        return arrangement;
    }
    
    /**
     * Gets the actual state of the matrix
     * @return      Returns the current matrix in a two-dimensional array of characters.
     */
    public char[][] actualArrangement(){
        int rows = matrix.size();
        int columns = matrix.get(0).size();
        
        char[][] arrangement = new char[rows][columns];
        for (int i = 0; i < rows; i++) {
            LinkedList<String> row = matrix.get(i);
            for (int j = 0; j < columns; j++) {
                arrangement[i][j] = row.get(j).charAt(0);
                }
            }
        return arrangement;
    }
    
    /**
     * Make the puzzle visible
     */
    public void makeVisible(){
        board.makeVisible();
        for (Rectangle tile : tiles){
            tile.makeVisible();
        }
    }
    
    /**
     * Make the puzzle invisible
     */
    public void makeInvisible(){
        board.makeInvisible();
        for (Rectangle tile : tiles){
            tile.makeInvisible();
        }
    }
    
    /**
     * Closes the current window
     */
    public void finish(){
        JOptionPane.showMessageDialog(null, "Thanks for playing!");
        Canvas canvas = Canvas.getCanvas();
        canvas.close();
    }
}
