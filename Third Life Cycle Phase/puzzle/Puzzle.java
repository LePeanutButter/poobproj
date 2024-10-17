import java.util.List;
import java.util.LinkedList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.*;
import java.util.Random;

/**
 * Tilting Tiles is a game inspired by Problem F from the 2023 International Programming Marathon, which tests a gummy glue.
 *
 * @author Botero Garcia, Santiago. Perilla Quintero, Laura Natalia.
 * @version September 15 2024
 */
public class Puzzle{
    private LinkedList<Rectangle> boards;
    private LinkedList<LinkedList<String>> matrix;
    private LinkedList<LinkedList<String>> endingMatrix;
    private LinkedList<Rectangle> tiles;
    private LinkedList<Rectangle> endingBoardTiles;
    private LinkedList<Rectangle> holes;
    private Map<String, String> colorMap;
    
    /**
     * Create a puzzle given the dimentions of the board
     * @param h     Rows of the board.
     * @param w     Columns of the board.
     */
    public Puzzle(int h, int w){
        if (h <= 0 || w <= 0 ) {
            throw new IllegalArgumentException("The dimensions of the rectangle must be greater than zero.");
        }
        
        int height = h * 40;
        int width = w * 40;
        board(height, width);
        colorMap();
        for (int i = 0; i < h; i++){
            LinkedList<String> startingRow = new LinkedList<>();
            LinkedList<String> endingRow = new LinkedList<>();
            for (int j = 0; j < w; j++){
                startingRow.add(String.valueOf("."));
                endingRow.add(String.valueOf("."));
            }
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
        printEndingTiles(rows, columns, endingMatrix);
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

        board(rows * 40, columns * 40);
        colorMap();
        for (int i=0; i < rows; i++){
            LinkedList<String> startingRow = new LinkedList<>();
            LinkedList<String> endingRow = new LinkedList<>();
            for (int j=0; j < columns; j++){
                startingRow.add(String.valueOf(starting[i][j]));
                endingRow.add(String.valueOf(ending[i][j]));
            }
            this.matrix.add(startingRow);
            this.endingMatrix.add(endingRow);
        }
        printTile(rows, columns, matrix);
        printEndingTiles(rows, columns, endingMatrix);
    }
    
    /*
     * Creates the boards
     * @param h     Board height.
     * @param w     Board width.
     */
    private void board(int h, int w){
        this.boards = new LinkedList<>();
        this.matrix = new LinkedList<>();
        this.tiles = new LinkedList<>();
        this.endingMatrix = new LinkedList<>();
        this.endingBoardTiles = new LinkedList<>();
        this.holes = new LinkedList<>();
        Rectangle board = new Rectangle(h + 20, w + 20, 40, 40, "brown", true);
        Rectangle endingBoard = new Rectangle(h + 20, w + 20, w + 70, 40, "brown", true);
        this.boards.add(board);
        this.boards.add(endingBoard);
        board.makeVisible();
        endingBoard.makeVisible();
    }
    
    /*
     * Store key-value pairs of colors
     */
    private void colorMap(){
        this.colorMap = new HashMap<>();
        this.colorMap.put("r", "red");
        this.colorMap.put("y", "yellow");
        this.colorMap.put("b", "blue");
        this.colorMap.put("g", "green");
        this.colorMap.put("m", "magenta");
    }

    /*
     * Retrieve the values from the starting matrix to create the tiles from a new board
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
                    String color = colorMap.get(value);
                    if (color == null) {
                        throw new IllegalArgumentException("Invalid color character: " + value);
                    }
                    Rectangle newTile = new Rectangle(40, 40, 50+(j * 40), 50+(i * 40), color, true);
                    this.tiles.add(newTile);
                    newTile.makeVisible();
                }
            }
        }
    }
    
    /*
     * Retrieve the values from the ending matrix to create the tiles from a new board
     * @param rows      Height of the board.
     * @param columns   Width of the board.
     * @param array     Position and color of the rectangles in the ending board (matrix).
     */
    private void printEndingTiles(int rows, int columns, LinkedList<LinkedList<String>> array){
        int width = columns * 40;
        for (int i = 0; i < rows; i++){
            LinkedList<String> row = array.get(i);
            for (int j = 0; j < columns; j++){
                String value = row.get(j);
                if (!value.equals(".")){
                    String color = colorMap.get(value);
                    if (color == null) {
                        throw new IllegalArgumentException("Invalid color character: " + value);
                    }
                    Rectangle newTile = new Rectangle(40, 40, (width + 80) + (j * 40), 50 + (i * 40), color, true);
                    this.endingBoardTiles.add(newTile);
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
        if (matrix.get(row-1).get(column-1).equals(".") || matrix.get(row-1).get(column-1).equals("H")){
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
        
        if (from[0] > 0 && from[0] <= matrixRows &&  from[1] > 0 && from[1] <= matrixCol && to[0] > 0 && to[0] <= matrixRows &&  to[1] > 0 && to[1] <= matrixCol && !matrix.get(from[0]-1).get(from[1]-1).equals(".") && !matrix.get(from[0]-1).get(from[1]-1).equals("H")){
            List<int[]> deletePositions = new ArrayList<>();
            List<String[]> addPositions = new ArrayList<>();
            int row = from[0];
            int column = from[1];
            int[] difference = new int[]{to[0] - row, to[1] - column};
            
            if (matrix.get(from[0]-1).get(from[1]-1).length()==2) {
                String[][] matrixCopy = new String[matrixRows][matrixCol];
                for (int i = 0; i < matrixRows; i++) {
                LinkedList<String> numRow = matrix.get(i);
                for (int j = 0; j < matrixCol; j++) {
                    matrixCopy[i][j] = numRow.get(j);
                    }
                }
                
                int[][] directions = { { -1, 0 }, { 1, 0 }, { 0, -1 }, { 0, 1 } };
                for (int[] dir : directions) {
                    int adjRow = row + dir[0];
                    int adjCol = column + dir[1];
                    if (adjRow > 0 && adjCol > 0 && adjRow <= matrixRows && adjCol <= matrixCol && !matrix.get(adjRow-1).get(adjCol-1).equals(".") && !matrix.get(adjRow-1).get(adjCol-1).equals("H")){
                        matrixCopy[adjRow-1][adjCol-1] = ".";
                    }
                }
                matrixCopy[row-1][column-1] = ".";
                
                if (matrixCopy[to[0]-1][to[1]-1].equals(".")){
                    deletePositions.add(new int[]{row, column});
                    addPositions.add(new String[]{Integer.toString(to[0]), Integer.toString(to[1]), matrix.get(row-1).get(column-1)});
                } else if (matrixCopy[to[0]-1][to[1]-1].equals("H")){
                    deletePositions.add(new int[]{row, column});
                }
                for (int[] dir : directions) {
                    int adjRow = row + dir[0];
                    int adjCol = column + dir[1];
                    if (adjRow > 0 && adjCol > 0 && adjRow <= matrixRows && adjCol <= matrixCol && !matrix.get(adjRow-1).get(adjCol-1).equals(".") && !matrix.get(adjRow-1).get(adjCol-1).equals("H")) {
                        int newRow = difference[0] + adjRow;
                        int newCol = difference[1] + adjCol;
                        if (newRow > 0 && newCol > 0 && newRow <= matrixRows && newCol <= matrixCol && matrixCopy[newRow-1][newCol-1].equals(".")) {
                            deletePositions.add(new int[]{adjRow, adjCol});
                            addPositions.add(new String[]{Integer.toString(newRow), Integer.toString(newCol), matrix.get(adjRow-1).get(adjCol-1)});
                        } else if (newRow > 0 && newCol > 0 && newRow <= matrixRows && newCol <= matrixCol && matrixCopy[newRow-1][newCol-1].equals("H")){
                            deletePositions.add(new int[]{adjRow, adjCol});
                        } else {
                            throw new IllegalArgumentException("The tiles cannot be moved to this position.");
                        }
                    }
                }
            } else {
                if (matrix.get(to[0]-1).get(to[1]-1).equals(".")){
                    deletePositions.add(new int[]{row, column});
                    addPositions.add(new String[]{Integer.toString(to[0]), Integer.toString(to[1]), matrix.get(row-1).get(column-1)});
                } else if (matrix.get(to[0]-1).get(to[1]-1).equals("H")){
                    deletePositions.add(new int[]{row, column});
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
        } else {
            throw new IllegalArgumentException("Glue cannot be added in this position.");
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
        } else {
            throw new IllegalArgumentException("Glue cannot be deleted from this position.");
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
    
    /**
     * Overloads the method tilt(char direction) that does an optimal tilt
     */
    public void tilt(){
        HashMap<Character, Integer> possibilities = new HashMap<>();
        char[] directions = {'u', 'd', 'l', 'r'};
        String[][] originalMatrix = getActualMatrix();
        int rows = matrix.size();
        int columns = matrix.get(0).size();
        for (char d: directions){
            tilt(d);
            possibilities.put(d, fixedTiles().length);
            clearBoard();
            for (int i = 0; i < rows; i++){
                for (int j = 0; j < columns ; j++){
                    if (!originalMatrix[i][j].equals(".")){
                        addTile(i + 1, j + 1, originalMatrix[i][j]);
                    }
                }
            }
        }
        int maxDifference = Integer.MIN_VALUE;
        for (int diff : possibilities.values()) {
            if (diff > maxDifference) {
                maxDifference = diff;
            }
        }
        List<Character> bestDirections = new ArrayList<>();
        for (Map.Entry<Character, Integer> entry : possibilities.entrySet()) {
            if (entry.getValue() == maxDifference) {
                bestDirections.add(entry.getKey());
            }
        }
        Random random = new Random();
        tilt(bestDirections.get(random.nextInt(bestDirections.size())));
    }
    
    /*
     * Gets the actual arrangement of the board including glued tiles and holes
     */
    private String[][] getActualMatrix(){
        int rows = matrix.size();
        int columns = matrix.get(0).size();
        String[][] actualArrangement = new String[rows][columns];
        for (int i = 0; i < rows; i++){
            for (int j = 0; j < columns ; j++){
                actualArrangement[i][j] = matrix.get(i).get(j);
            }
        }
        return actualArrangement;
    }
    
    /*
     * Delete all tiles from the board
     */
    private void clearBoard(){
        int rows = matrix.size();
        int columns = matrix.get(0).size();
        for (int i = 0; i < rows; i++){
            for (int j = 0; j < columns ; j++){
                if (!matrix.get(i).get(j).equals(".")){
                    deleteTile(i + 1, j + 1);
                }
            }
        }
    }
    
    /*
     * Moves the tiles horizontally
     * @param rows          Rows of the board
     * @param columns       Columns of the board
     * @param operation     Direction in which the tiles will be tilted
     */
    private void moveHorizontal(int rows, int columns, char operation){
        String[][] tempMatrix = tempMatrix();
        if (operation == 'l'){
            for (int j = 1; j < columns; j++){
                for (int i = 0; i < rows; i++){
                    if (!tempMatrix[i][j].equals(".")){
                        int count = j;
                        boolean isTempMatrix = tempMatrix[i][j].length() == 2;
                        
                        while (count > 0){
                            String condition = isTempMatrix ? tempMatrix[i][count - 1] : matrix.get(i).get(count - 1);

                            if (!condition.equals(".")){
                                if (condition.equals("H")){
                                    relocateTile(new int[] {i + 1, count + 1}, new int[] {i + 1, count});
                                    tempMatrix[i][count] = ".";
                                }
                                break;
                            }
                            try {
                                relocateTile(new int[] {i + 1, count + 1}, new int[] {i + 1, count});
                                tempMatrix[i][count-1] = tempMatrix[i][count];
                                tempMatrix[i][count] = ".";
                                count--;
                            } catch(Exception e) {
                                break;
                            }
                        }
                    }
                }
            }
        } else if (operation == 'r'){
            for (int j = (columns - 2); j >= 0; j--){
                for (int i = 0; i < rows; i++){
                    if (!tempMatrix[i][j].equals(".")){
                        int count = j;
                        boolean isTempMatrix = tempMatrix[i][j].length() == 2;
                        
                        while (count < (columns-1)){
                            String condition = isTempMatrix ? tempMatrix[i][count + 1] : matrix.get(i).get(count + 1);
                            
                            if (!condition.equals(".")){
                                if (condition.equals("H")){
                                    relocateTile(new int[] {i + 1, count + 1}, new int[] {i + 1, count + 2});
                                    tempMatrix[i][count] = ".";
                                }
                                break;
                            }
                            try {
                                relocateTile(new int[] {i + 1, count + 1}, new int[] {i + 1, count + 2});
                                tempMatrix[i][count+1] = tempMatrix[i][count];
                                tempMatrix[i][count] = ".";
                                count++;
                            } catch(Exception e) {
                                break;
                            }
                        }
                    }
                }
            }
        }
    }
    
    /*
     * Moves the tiles vertically
     * @param rows          Rows of the board
     * @param columns       Columns of the board
     * @param operation     Direction in which the tiles will be tilted
     */
    private void moveVertical(int rows, int columns, char operation){
        String[][] tempMatrix = tempMatrix();
        if (operation == 'u'){
            for (int i = 1; i < rows; i++){
                for (int j = 0; j < columns; j++){               
                    if (!tempMatrix[i][j].equals(".")){
                        int count = i;
                        boolean isTempMatrix = tempMatrix[i][j].length() == 2;
                        
                        while (count > 0){
                            String condition = isTempMatrix ? tempMatrix[count - 1][j] : matrix.get(count - 1).get(j);
                            
                            if (!condition.equals(".")){
                                if (condition.equals("H")){
                                    relocateTile(new int[] {count + 1, j + 1}, new int[] {count, j + 1});
                                    tempMatrix[count][j] = ".";
                                }
                                break;
                            }
                            try {
                                relocateTile(new int[] {count + 1, j + 1}, new int[] {count, j + 1});
                                tempMatrix[count - 1][j] = tempMatrix[count][j];
                                tempMatrix[count][j] = ".";
                                count--;
                            } catch(Exception e) {
                                break;
                            }
                        }
                    }
                }
            }
        } else if (operation == 'd'){
            for (int i = (rows - 2); i >= 0; i--){
                for (int j = 0; j < columns; j++){                    
                    if (!tempMatrix[i][j].equals(".")){
                        int count = i;
                        boolean isTempMatrix = tempMatrix[i][j].length() == 2;
                        
                        while (count < (rows - 1)){
                            String condition = isTempMatrix ? tempMatrix[count + 1][j] : matrix.get(count + 1).get(j);
                            
                            if (!condition.equals(".")){
                                if (condition.equals("H")){
                                    relocateTile(new int[] {count + 1, j + 1}, new int[] {count + 2, j + 1});
                                    tempMatrix[count][j] = ".";
                                }
                                break;
                            }
                            try {
                                relocateTile(new int[] {count + 1, j + 1}, new int[] {count + 2, j + 1});
                                tempMatrix[count + 1][j] = tempMatrix[count][j];
                                tempMatrix[count][j] = ".";
                                count++;
                            } catch(Exception e) {
                                break;
                            }
                        }
                    }
                }
            }
        }
    }

    /*
     * Gets the actual matrix without the adjacent tiles of glued tiles
     * @return  The actual matrix with the glued tiles and non-glued tiles that are not adjacent to glued tiles
     */
    private String[][] tempMatrix(){
        int rows = matrix.size();
        int columns = matrix.get(0).size();
        String[][] tempMatrix = new String[rows][columns];
        for (int i = 0; i < rows; i++){
            for (int j = 0; j < columns ; j++){
                boolean belowExists = (i + 1 < rows);
                boolean rightExists = (j + 1 < columns);
                if (!matrix.get(i).get(j).equals(".") && (belowExists ? matrix.get(i + 1).get(j).length() != 2 : true) && (rightExists ? matrix.get(i).get(j + 1).length() != 2 : true)){
                    tempMatrix[i][j] = matrix.get(i).get(j);
                } else {
                    tempMatrix[i][j] = ".";
                }
            }
        }
        return tempMatrix;
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
     * Gets the coordinates of fixed tiles
     * @return  An array of tuples/coordinates.
     */
    public int[][] fixedTiles(){
        List<int[]> coordinates = new ArrayList<>();
        for (int i = 0; i < matrix.size(); i++){
            for (int j = 0; j < matrix.get(0).size(); j++){
                if (!matrix.get(i).get(j).equals(".") && !matrix.get(i).get(j).equals("H") && String.valueOf(matrix.get(i).get(j).charAt(0)).equals(String.valueOf(endingMatrix.get(i).get(j).charAt(0)))){
                    coordinates.add(new int[] {i + 1, j + 1});
                }
            }        
        }
        int[][] result = new int[coordinates.size()][2];
        for (int i = 0; i < coordinates.size(); i++) {
            result[i] = coordinates.get(i);
        }
    
        return result;
    }
    
    /**
     * Gets the amount of misplaced tiles
     * @return  An Integer meaning the amount of misplaced tiles.
     */
    public int misplacedTiles(){
        List<int[]> coordinates = new ArrayList<>();
        for (int i = 0; i < matrix.size(); i++){
            for (int j = 0; j < matrix.get(0).size(); j++){
                if (!matrix.get(i).get(j).equals("H")  && !matrix.get(i).get(j).equals(".") && !String.valueOf(matrix.get(i).get(j).charAt(0)).equals(String.valueOf(endingMatrix.get(i).get(j).charAt(0)))){
                    coordinates.add(new int[] {i + 1, j + 1});
                }
            }        
        }    
        return coordinates.size();
    }
    
    /**
     * Make the puzzle visible
     */
    public void makeVisible(){
        for (Rectangle b : boards){
            b.makeVisible();
        }
        for (Rectangle tile : tiles){
            tile.makeVisible();
        }
        for (Rectangle tile : endingBoardTiles){
            tile.makeVisible();
        }
    }
    
    /**
     * Make the puzzle invisible
     */
    public void makeInvisible(){
        for (Rectangle b : boards){
            b.makeInvisible();
        }
        for (Rectangle tile : tiles){
            tile.makeInvisible();
        }
        for (Rectangle tile : endingBoardTiles){
            tile.makeInvisible();
        }
    }
    
    /**
     * Closes the current window
     */
    public void finish(){
        makeInvisible();
        Canvas canvas = Canvas.getCanvas();
        canvas.close();
    }
    
    /**
     * Exchanges the reference board (endingMatrix) with the editing board (matrix).
     */
    public void exchange() {
        char[][] actualMatrix = actualArrangement();
        char[][] finalMatrix = getEndingMatrix();
        for (int i = 0; i < matrix.size(); i++) {
            for (int j = 0; j < matrix.get(i).size(); j++) {
                endingMatrix.get(i).set(j, String.valueOf(actualMatrix[i][j]));
                if (!matrix.get(i).get(j).equals(".")){
                    deleteTile(i + 1, j + 1);
                }
            }
        }
        for (int i = 0; i < matrix.size(); i++) {
            for (int j = 0; j < matrix.get(i).size(); j++) {
                if (!String.valueOf(finalMatrix[i][j]).equals(".")){
                    addTile(i + 1, j + 1, String.valueOf(finalMatrix[i][j]));
                }
            }
        }
    }

    /**
     * Creates a hole at the specified empty cell in the puzzle.
     * @param row       Row of the board
     * @param column    Column of the board
     */
    public void makeHole(int row, int column) {
        if (matrix.get(row - 1).get(column - 1).equals(".")) {
            matrix.get(row - 1).set(column - 1, "H");
            Rectangle hole = new Rectangle(40, 40, (40 * column) + 10, (40 * row) + 10, "white", false);
            holes.add(hole);
            hole.makeVisible();
        } else {
            throw new IllegalArgumentException("Cannot create a hole on a non-empty tile.");
        }
    }
    
    
    // Methods made for the purpose of running unit tests
    
    /**
     * Gets if the puzzle is visible
     * @return      If the board is visible return true, if not return false.
     */
    public boolean isVisible(){
        boolean variable = true;
        for (Rectangle b : boards){
            if (b.isVisible() == false){
                variable = false;
            }
        }
        for (Rectangle r: tiles){
            if (r.isVisible() == false){
                variable = false;
            }
        }
        return variable;
    }
    
    /**
     * Changes the actual state of the board to the given arrangement.
     * @param currentState      The arrangement in which the actual matrix will be changed.
     */
    public void setArrangement(char[][] currentState){
        clearBoard();
        int row = matrix.size();
        int column = matrix.get(0).size();
        for (int i = 0; i < row; i++){
            for (int j = 0; j < column; j++){
                if (currentState[i][j] != '.'){
                    addTile(i + 1, j + 1, String.valueOf(currentState[i][j]));
                }
            }
        }
    }
}
