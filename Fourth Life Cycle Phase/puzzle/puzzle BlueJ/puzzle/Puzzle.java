package puzzle;
import shapes.*;

import java.util.*;
import java.util.List;
import java.util.LinkedList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;

/**
 * Tilting Tiles is a game inspired by Problem F from the 2023 International Programming Marathon, which tests a gummy glue.
 *
 * @author Botero Garcia, Santiago. Perilla Quintero, Laura Natalia.
 * @version October 26 2024
 */
public class Puzzle{
    public static final char STARTING_BOARD = 'S', ENDING_BOARD = 'E';
    
    public static int rows;
    public static int columns;
    private char[][] matrix;
    private char[][] endingMatrix;
    private LinkedList<Board> boards;
    private HashMap<Character, String> colorMap;
    private boolean isVisible;
    
    /**
     * Create a puzzle given the dimentions of the board
     * @param h     Rows of the board.
     * @param w     Columns of the board.
     */
    public Puzzle(int h, int w) throws BoardExceptions {
        if (h <= 0 || w <= 0) {
            throw new BoardExceptions(BoardExceptions.DIMENSION_ERROR);
        }
        this.rows = h;
        this.columns = w;
        this.boards = new LinkedList<>();
        this.boards.add(new Board(h, w, 40, 40, Board.STARTING_BOARD));
        this.boards.add(new Board(h, w, (w * 40) + 70, 40, Board.ENDING_BOARD));
        this.matrix = new char[h][w];
        this.endingMatrix = new char[h][w];
        this.isVisible = false;
        colorMap();
        for (int i = 0; i < h; i++) {
            for (int j = 0; j < w; j++) {
                this.matrix[i][j] = '.';
                this.endingMatrix[i][j] = '.';
            }
        }
    }

    /**
     * Stores the ending puzzle given an array
     * @param ending    Ending matrix/Goal.
     */
    public Puzzle(char[][] ending) throws BoardExceptions, PuzzleExceptions, TileExceptions {        
        ConstructorVerification(ending);
        this.rows = ending.length;
        this.columns = ending[0].length;
        this.boards = new LinkedList<>();
        this.boards.add(new Board(this.rows, this.columns, 40, 40, Board.STARTING_BOARD));
        this.boards.add(new Board(this.rows, this.columns, (this.columns * 40) + 70, 40, Board.ENDING_BOARD));
        this.matrix = new char[rows][columns];
        this.endingMatrix = new char[rows][columns];
        this.isVisible = false;
        colorMap();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                this.endingMatrix[i][j] = ending[i][j];
                if (ending[i][j] != '.'){
                    String color = this.colorMap.get(ending[i][j]);
                    if (color == null) {
                        throw new PuzzleExceptions(PuzzleExceptions.INVALID_COLOR + String.valueOf(ending[i][j]));
                    }
                    this.boards.get(1).addTile(i + 1, j + 1, color, "normal");
                }
            }
        }
    }
    
    /*
     * Verifies the validity of the ending matrix during the construction of the Puzzle.
     *
     * @param ending The ending matrix that needs to be verified.
     * @throws PuzzleExceptions If the ending matrix is null or if the columns in the ending matrix do not have the same length.
     */
    private void ConstructorVerification(char[][] ending) throws PuzzleExceptions {
        if (ending == null){
            throw new PuzzleExceptions(PuzzleExceptions.NULL_BOARD);
        }
        boolean sameColumnsLength = ColumnsLength(ending);
        if (!sameColumnsLength) {
            throw new PuzzleExceptions(PuzzleExceptions.ENDING_BOARD);
        }
    }
    
    /**
     * Create the starting puzzle and the ending puzzle given a pair of arrays
     * @param starting      Starting matrix.
     * @param ending        Ending matrix/Goal.
     */
    public Puzzle(char[][] starting,char[][] ending) throws BoardExceptions, PuzzleExceptions, TileExceptions {        
        ConstructorVerification(starting, ending);
        this.rows = starting.length;
        this.columns = starting[0].length;
        this.boards = new LinkedList<>();
        this.boards.add(new Board(this.rows, this.columns, 40, 40, Board.STARTING_BOARD));
        this.boards.add(new Board(this.rows, this.columns, (this.columns * 40) + 70, 40, Board.ENDING_BOARD));
        this.matrix = new char[rows][columns];
        this.endingMatrix = new char[rows][columns];
        this.isVisible = false;
        colorMap();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                this.matrix[i][j] = starting[i][j];
                this.endingMatrix[i][j] = ending[i][j];
                if (starting[i][j] != '.'){
                    String color = this.colorMap.get(starting[i][j]);
                    if (color == null) {
                        throw new PuzzleExceptions(PuzzleExceptions.INVALID_COLOR + String.valueOf(ending[i][j]));
                    }
                    this.boards.get(0).addTile(i + 1, j + 1, color, "normal");
                }
                if (ending[i][j] != '.'){
                    String color = this.colorMap.get(ending[i][j]);
                    if (color == null) {
                        throw new PuzzleExceptions(PuzzleExceptions.INVALID_COLOR + String.valueOf(ending[i][j]));
                    }
                    this.boards.get(1).addTile(i + 1, j + 1, color, "normal");
                }
            }
        }
    }
    
    /*
     * Verifies the validity of the starting and ending matrices during the construction of the Puzzle.
     * @param starting The starting matrix that needs to be verified.
     * @param ending   The ending matrix that needs to be verified.
     * @throws PuzzleExceptions If either matrix is null, if the columns in either matrix do not have the same length,
     *                          or if the starting and ending matrices have different dimensions.
     */
    private void ConstructorVerification(char[][] starting, char[][] ending) throws PuzzleExceptions {
        if (starting == null || ending == null){
            throw new PuzzleExceptions(PuzzleExceptions.NULL_BOARD);
        }
        boolean sameColumnsStarting = ColumnsLength(starting);
        boolean sameColumnsEnding = ColumnsLength(ending);
        if (!sameColumnsStarting) {
            throw new PuzzleExceptions(PuzzleExceptions.STARTING_BOARD);
        }
        if (!sameColumnsEnding) {
            throw new PuzzleExceptions(PuzzleExceptions.ENDING_BOARD);
        }
        if (starting.length != ending.length || starting[0].length != ending[0].length){
            throw new PuzzleExceptions(PuzzleExceptions.DIFFERENT_BOARDS);
        }
    }
    
    /*
     * Checks if all columns in the nested array are the same length.
     * @return  {true} if all lengths are the same; {false} if otherwise.
     */
    private boolean ColumnsLength(char[][] array) {
        int matrixRows = array.length;
        int matrixColumns = array[0].length;
        for (int i = 0; i < matrixRows; i++){
            if (array[i].length != matrixColumns) {
                return false;
            }
        }
        return true;
    }
    
    /*
     * Store key-value pairs of colors
     */
    private void colorMap(){
        this.colorMap = new HashMap<>();
        this.colorMap.put('r', "red");
        this.colorMap.put('y', "yellow");
        this.colorMap.put('b', "blue");
        this.colorMap.put('g', "green");
        this.colorMap.put('m', "magenta");
    }
    
    /**
     * Adds a tile on the board
     * @param row       Row where the tile would be added.
     * @param column    Column where the tile would be added.
     * @param color     Color of the tile (must be red, yellow, blue, green or magenta).
     */
    public void addTile(int row, int column, String color) throws BoardExceptions, PuzzleExceptions, TileExceptions {
        char colorChar = Character.toLowerCase(color.charAt(0));
        if (!this.colorMap.containsKey(colorChar)) {
            throw new PuzzleExceptions(PuzzleExceptions.INVALID_COLOR);
        }
        this.boards.get(0).addTile(row, column, this.colorMap.get(colorChar), "normal");
        this.matrix[row - 1][column - 1] = colorChar;
    }
    
    /**
     * Adds a tile of a specified type and color to the board at the given row and column.
     * @param type    The type of the tile to be added (e.g., "normal", "fixed", "rough", "freelance", "flying", "sticky").
     * @param row       Row where the tile would be added.
     * @param column    Column where the tile would be added.
     * @param color     Color of the tile (must be red, yellow, blue, green or magenta).
     * @throws BoardExceptions If the board operation fails (not specified in the method).
     * @throws PuzzleExceptions If the color is invalid (not present in the color map).
     * @throws TileExceptions If the tile type provided is not valid.
     */
    public void addTile(String type, int row, int column, String color) throws BoardExceptions, PuzzleExceptions, TileExceptions {
        char colorChar = Character.toLowerCase(color.charAt(0));
        if (!this.colorMap.containsKey(colorChar)) {
            throw new PuzzleExceptions(PuzzleExceptions.INVALID_COLOR);
        }
        switch (type.toLowerCase()) {
            case "normal":
                this.boards.get(0).addTile(row, column, this.colorMap.get(colorChar), "normal");
                break;
            case "fixed":
                this.boards.get(0).addTile(row, column, this.colorMap.get(colorChar), "fixed");
                break;
            case "rough":
                this.boards.get(0).addTile(row, column, this.colorMap.get(colorChar), "rough");
                break;
            case "freelance":
                this.boards.get(0).addTile(row, column, this.colorMap.get(colorChar), "freelance");
                break;
            case "flying":
                this.boards.get(0).addTile(row, column, this.colorMap.get(colorChar), "flying");
                break;
            case "sticky":
                this.boards.get(0).addTile(row, column, this.colorMap.get(colorChar), "sticky");
                break;
            default:
                throw new TileExceptions(TileExceptions.INVALID_TYPE);
        }
        this.matrix[row - 1][column - 1] = colorChar;
    }
    
    /**
     * Deletes an existing tile on the board
     * @param row       Row of the tile to delete.
     * @param column    Column of the tile to delete.
     */
    public void deleteTile(int row, int column)throws BoardExceptions, TileExceptions {
        this.boards.get(0).deleteTile(row, column);
        this.matrix[row - 1][column - 1] = '.';
    }
    
    /**
     * Changes the position of a tile
     * @param from      Ordered pair indicating the position where the tile is positioned.
     * @param to        Ordered pair indicating the position where the tile will move.
     */
    public void relocateTile(int[] from, int[] to) throws BoardExceptions, PuzzleExceptions, TileExceptions {
        relocateTileVerification(from, to);
        HashMap<String, int[]> setPositions = new HashMap<>();
        int row = from[0];
        int column = from[1];
        int[] difference = {to[0] - row, to[1] - column};
        
        if (this.boards.get(0).doesTileHaveGlue(row, column)) {
            HashMap<String, int[]> toMove = glueMovablePositions(row, column, difference);
            for (Map.Entry<String, int[]> entry : toMove.entrySet()) {
                setPositions.put(entry.getKey(), entry.getValue());
            }
        } else {
            if (this.matrix[to[0] - 1][to[1] - 1] != '.') {
                throw new BoardExceptions(BoardExceptions.INVALID_MOVE);
            }
            setPositions.put(Arrays.toString(from), to);
        }
        
        while (!setPositions.isEmpty()) {
            Iterator<Map.Entry<String, int[]>> iterator = setPositions.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, int[]> entry = iterator.next();
                String[] stringKeys = entry.getKey().replace("[", "").replace("]", "").split(",");
                int[] key = new int[stringKeys.length];
                
                for (int i = 0; i < stringKeys.length; i++) {
                    key[i] = Integer.parseInt(stringKeys[i].trim());
                }
                
                int[] value = entry.getValue();
                if (this.matrix[value[0] - 1][value[1] - 1] == '.') {
                    if (this.boards.get(0).doesThisPositionHaveHole(value[0], value[1]) && this.boards.get(0).canThisTileFall(key[0], key[1])) {
                        this.matrix[key[0] - 1][key[1] - 1] = '.';
                    } else {
                        this.matrix[value[0] - 1][value[1] - 1] = Character.toLowerCase(this.boards.get(0).getTileColor(key[0], key[1]).charAt(0));
                        this.matrix[key[0] - 1][key[1] - 1] = '.';
                    }
                    this.boards.get(0).moveTile(key, value);
                    iterator.remove();
                }
            }
        }
    }
    
    /*
     * Makes a temporary matrix without the tiles with glue and its adjacents.
     * @returns     The actual matrix without glued tiles and adjacents.
     */
    private HashMap<String, int[]> glueMovablePositions(int row, int column, int[] difference) throws BoardExceptions {
        int[][] directions = { { -1, 0 }, { 1, 0 }, { 0, -1 }, { 0, 1 } };
        HashMap<String, int[]> adjacentTiles = new HashMap<>();
        adjacentTiles.put(Arrays.toString(new int[] {row, column}), new int[] {row + difference[0], column + difference[1]});
        
        for (int[] dir : directions) {
            int adjRow = row + dir[0];
            int adjCol = column + dir[1];
            int[] adjacent = {adjRow, adjCol};
            if (adjRow > 0 && adjCol > 0 && adjRow <= this.rows && adjCol <= this.columns && this.matrix[adjRow - 1][adjCol - 1] != '.'){
                int newRow = adjRow + difference[0];
                int newCol = adjCol + difference[1];
                int[] adjacentMoved = {newRow, newCol};
                if (newRow <= 0 || newCol <= 0 || newRow > this.rows || newCol > this.columns) {
                    throw new BoardExceptions(BoardExceptions.OUT_OF_BOUNDS);
                }
                adjacentTiles.put(Arrays.toString(adjacent), adjacentMoved);
            }
        }
        for (int[] value : adjacentTiles.values()) {
            if (this.matrix[value[0] - 1][value[1] - 1] != '.') {
                if (!adjacentTiles.containsKey(Arrays.toString(value))) {
                    throw new BoardExceptions(BoardExceptions.INVALID_MOVE);
                }
            }
        }
        return adjacentTiles;
    }
    
    /*
     * Verifies if a tile can be relocated.
     */
    private void relocateTileVerification(int[] from, int[] to) throws BoardExceptions, PuzzleExceptions, TileExceptions {
        if (from.length !=2 || to.length != 2){
            throw new PuzzleExceptions(PuzzleExceptions.INVALID_TUPLE);
        }
        if (from[0] > this.rows || from[0] <= 0 || from[1] > this.columns || from[1] <= 0 || to[0] > this.rows || to[0] <= 0 || to[1] > this.columns || to[1] <= 0) {
            throw new BoardExceptions(BoardExceptions.OUT_OF_BOUNDS);
        }
    }
    
    /**
     * Adds glue to a tile.
     * @param row       Row of the tile.
     * @parama column   Column of the tile.
     */
    public void addGlue(int row, int column) throws BoardExceptions, GlueExceptions, TileExceptions {
        if (row > this.rows || row <= 0 || column > this.columns || column <= 0) {
            throw new BoardExceptions(BoardExceptions.OUT_OF_BOUNDS);
        }
        if (matrix[row - 1][column - 1] == '.') {
            throw new BoardExceptions(BoardExceptions.EMPTY_POSITION);
        }
        this.boards.get(0).addGlue(row, column, "normal");
    }
    
    /**
     * Adds glue of a specified type to the board at the given row and column.
     * 
     * @param type     The type of the glue to be added (e.g., "normal", "super", "fragile").
     * @param row      Row of the tile.
     * @param column   Column of the tile. 
     * @throws BoardExceptions If the specified position is out of bounds or if the position is empty.
     * @throws GlueExceptions If the glue operation fails (not specified in the method).
     * @throws TileExceptions If the operation fails due to issues with the tiles (not specified in the method).
     */
    public void addGlue(String type, int row, int column) throws BoardExceptions, GlueExceptions, TileExceptions {
        if (row > this.rows || row <= 0 || column > this.columns || column <= 0) {
            throw new BoardExceptions(BoardExceptions.OUT_OF_BOUNDS);
        }
        if (matrix[row - 1][column - 1] == '.') {
            throw new BoardExceptions(BoardExceptions.EMPTY_POSITION);
        }
        switch (type.toLowerCase()){
            case "normal":
                this.boards.get(0).addGlue(row, column, "normal");
                break;
            case "super":
                this.boards.get(0).addGlue(row, column, "super");
                break;
            case "fragil":
                this.boards.get(0).addGlue(row, column, "fragil");
                break;
        }
    }
    
    /**
     * Deletes glue from a tile
     * @param row       Row of the tile.
     * @parama column   Column of the tile.
     */
    public void deleteGlue(int row, int column) throws BoardExceptions, TileExceptions {
        if (row > this.rows || row <= 0 || column > this.columns || column <= 0) {
            throw new BoardExceptions(BoardExceptions.OUT_OF_BOUNDS);
        }
        if (matrix[row - 1][column - 1] == '.') {
            throw new BoardExceptions(BoardExceptions.EMPTY_POSITION);
        }
        this.boards.get(0).deleteGlue(row, column);
    }
    
    
    /**
     * Move the tiles to the given direction
     * @param direction     Must be u(p), l(eft), d(own), r(ight).
     */
    public void tilt(char direction) throws BoardExceptions, PuzzleExceptions, TileExceptions{
        if (direction == 'l' || direction == 'r'){
            moveHorizontal(this.rows, this.columns, direction);
        } else if (direction == 'u' || direction == 'd'){
            moveVertical(this.rows, this.columns, direction);
        }
        this.boards.get(0).deleteGlueAfterTilt();
    }
    
    /**
     * Overloads the method tilt(char direction) that does an optimal tilt
     */
    public void tilt() throws BoardExceptions, PuzzleExceptions, TileExceptions{
        HashMap<Character, Integer> possibilities = new HashMap<>();
        char[] directions = {'u', 'd', 'l', 'r'};
        char[][] originalMatrix = actualArrangement();
        if (this.isVisible) {
           this.boards.get(0).changeTilesVisibility(false);
        }
        HashMap<String, Tile> tileMap = this.boards.get(0).tilesPositions();
        for (char d: directions) {
            tilt(d);
            possibilities.put(d, fixedTiles().length);
            setArrangement(originalMatrix, tileMap);
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
        if (this.isVisible) {
            this.boards.get(0).changeTilesVisibility(true);
        }
        Random random = new Random();
        tilt(bestDirections.get(random.nextInt(bestDirections.size())));
    }
    
    /*
     * Moves the tiles horizontally
     * @param rows          Rows of the board
     * @param columns       Columns of the board
     * @param operation     Direction in which the tiles will be tilted
     */
    private void moveHorizontal(int rows, int columns, char operation) throws BoardExceptions, PuzzleExceptions, TileExceptions{
        char[][] tempMatrix = tempMatrix();
        if (operation == 'l') {
            for (int j = 1; j < this.columns; j++){
                for (int i = 0; i < this.rows; i++){
                    if (tempMatrix[i][j] != '.'){
                        int count = j;
                        boolean isGlued = this.boards.get(0).doesTileHaveGlue(i + 1, j + 1);
                        if (this.boards.get(0).canThisTileBeTilted(i + 1, j + 1)) {
                            while (count > 0){
                                char condition = (isGlued ? tempMatrix[i][count - 1] : this.matrix[i][count - 1]);
                                
                                if (condition != '.') {
                                    if (this.boards.get(0).doesThisPositionHaveHole(i + 1, count) && this.boards.get(0).canThisTileFall(i + 1, count + 1)) {
                                        try {
                                            relocateTile(new int[] {i + 1, count + 1}, new int[] {i + 1, count});
                                            tempMatrix[i][count] = '.';
                                        } catch (Exception e) {
                                        }
                                    }
                                    break;
                                }
                                try {
                                    relocateTile(new int[] {i + 1, count + 1}, new int[] {i + 1, count});
                                    tempMatrix[i][count-1] = tempMatrix[i][count];
                                    tempMatrix[i][count] = '.';
                                    count--;
                                } catch(Exception e) {
                                    break;
                                }
                            }
                        }
                    }
                }
            }
        } else if (operation == 'r') {
            for (int j = (this.columns - 2); j >= 0; j--) {
                for (int i = 0; i < this.rows; i++){
                    if (tempMatrix[i][j] != '.'){
                        int count = j;
                        boolean isGlued = this.boards.get(0).doesTileHaveGlue(i + 1, j + 1);
                        if (this.boards.get(0).canThisTileBeTilted(i + 1, j + 1)) {
                            while (count < (columns-1)){
                                char condition = (isGlued ? tempMatrix[i][count + 1] : matrix[i][count + 1]);
                                
                                if (condition != '.'){
                                    if (this.boards.get(0).doesThisPositionHaveHole(i + 1, count + 2) && this.boards.get(0).canThisTileFall(i + 1, count + 1)){
                                        try {
                                            relocateTile(new int[] {i + 1, count + 1}, new int[] {i + 1, count + 2});
                                            tempMatrix[i][count] = '.';
                                        } catch (Exception e) {
                                        }
                                    }
                                    break;
                                }
                                try {
                                    relocateTile(new int[] {i + 1, count + 1}, new int[] {i + 1, count + 2});
                                    tempMatrix[i][count+1] = tempMatrix[i][count];
                                    tempMatrix[i][count] = '.';
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
    }
    
    /*
     * Moves the tiles vertically
     * @param rows          Rows of the board
     * @param columns       Columns of the board
     * @param operation     Direction in which the tiles will be tilted
     */
    private void moveVertical(int rows, int columns, char operation) throws BoardExceptions, PuzzleExceptions, TileExceptions{
        char[][] tempMatrix = tempMatrix();
        if (operation == 'u'){
            for (int i = 1; i < rows; i++){
                for (int j = 0; j < columns; j++){               
                    if (tempMatrix[i][j] != '.'){
                        int count = i;
                        boolean isGlued = this.boards.get(0).doesTileHaveGlue(i + 1, j + 1);
                        if (this.boards.get(0).canThisTileBeTilted(i + 1, j + 1)) {
                            while (count > 0){
                                char condition = (isGlued ? tempMatrix[count - 1][j] : matrix[count - 1][j]);
                                
                                if (condition != '.'){
                                    if (this.boards.get(0).doesThisPositionHaveHole(count, j + 1) && this.boards.get(0).canThisTileFall(count + 1, j + 1)) {
                                        try {
                                            relocateTile(new int[] {count + 1, j + 1}, new int[] {count, j + 1});
                                            tempMatrix[count][j] = '.';
                                        } catch (Exception e) {
                                        } 
                                    }
                                    break;
                                }
                                try {
                                    relocateTile(new int[] {count + 1, j + 1}, new int[] {count, j + 1});
                                    tempMatrix[count - 1][j] = tempMatrix[count][j];
                                    tempMatrix[count][j] = '.';
                                    count--;
                                } catch(Exception e) {
                                    break;
                                }
                            }
                        }
                    }
                }
            }
        } else if (operation == 'd'){
            for (int i = (rows - 2); i >= 0; i--){
                for (int j = 0; j < columns; j++){                    
                    if (tempMatrix[i][j] != '.'){
                        int count = i;
                        boolean isGlued = this.boards.get(0).doesTileHaveGlue(i + 1, j + 1);
                        if (this.boards.get(0).canThisTileBeTilted(i + 1, j + 1)) {
                            while (count < (rows - 1)){
                                char condition = (isGlued ? tempMatrix[count + 1][j] : matrix[count + 1][j]);
                                
                                if (condition != '.'){
                                    if (this.boards.get(0).doesThisPositionHaveHole(count + 2, j + 1) && this.boards.get(0).canThisTileFall(count + 1, j + 1)) {
                                        try {
                                            relocateTile(new int[] {count + 1, j + 1}, new int[] {count + 2, j + 1});
                                            tempMatrix[count][j] = '.';
                                        } catch (Exception e) {
                                        }
                                    }
                                    break;
                                }
                                try {
                                    relocateTile(new int[] {count + 1, j + 1}, new int[] {count + 2, j + 1});
                                    tempMatrix[count + 1][j] = tempMatrix[count][j];
                                    tempMatrix[count][j] = '.';
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
    }

    /*
     * Gets the actual matrix without the adjacent tiles of glued tiles
     * @return  The actual matrix with the glued tiles and non-glued tiles that are not adjacent to glued tiles.
     */
    private char[][] tempMatrix() throws BoardExceptions {
        int[][] directions = { { -1, 0 }, { 1, 0 }, { 0, -1 }, { 0, 1 } };
        char[][] tempMatrix = new char[this.rows][this.columns];
        for (int i = 0; i < this.rows; i++){
            for (int j = 0; j < this.columns ; j++){
                if (tempMatrix[i][j] == '\u0000') {
                    if (this.matrix[i][j] != '.' && this.boards.get(0).doesTileHaveGlue(i + 1, j + 1)) {
                        for (int[] dir : directions) {
                            int newRow = i + dir[0];
                            int newCol = j + dir[1];
                            boolean inRange = (newRow >= 0 && newCol >= 0 && newRow < this.rows && newCol < this.columns);
                            if (inRange) {
                                tempMatrix[newRow][newCol] = '.';
                            }
                        }
                    }
                    tempMatrix[i][j] = this.matrix[i][j];
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
        char[][] ending = new char[this.rows][this.columns];
        for (int i = 0; i < rows; i++) {
            ending[i] = this.endingMatrix[i].clone();
        }
        return ending;
    }
    
    /**
     * Gets the actual state of the matrix
     * @return      Returns the current matrix in a two-dimensional array of characters.
     */
    public char[][] actualArrangement(){
        char[][] originalMatrix = new char[this.rows][this.columns];
        for (int i = 0; i < this.rows; i++) {
            for (int j = 0; j < this.columns; j++) {
                originalMatrix[i][j] = this.matrix[i][j];
            }
        }
        return originalMatrix;
    }
    
    /**
     * Gets the coordinates of fixed tiles
     * @return  An array of tuples/coordinates.
     */
    public int[][] fixedTiles(){
        List<int[]> coordinates = new ArrayList<>();
        for (int i = 0; i < this.rows; i++){
            for (int j = 0; j < this.columns; j++){
                if (this.matrix[i][j] != '.' && this.matrix[i][j] == this.endingMatrix[i][j]) {
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
        int amount = 0;
        for (int i = 0; i < this.rows; i++){
            for (int j = 0; j < this.columns; j++){
                if (this.matrix[i][j] != '.' && this.matrix[i][j] != this.endingMatrix[i][j]){
                    amount++;
                }
            }        
        }
        return amount;
    }
    
    /**
     * Make the puzzle visible
     */
    public void makeVisible(){
        for (Board b : boards){
            b.makeVisible();
        }
        this.isVisible = true;
    }
    
    /**
     * Make the puzzle invisible
     */
    public void makeInvisible(){
        for (Board b : boards){
            b.makeInvisible();
        }
        this.isVisible = false;
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
    public void exchange() throws PuzzleExceptions, TileExceptions {
        char[][] actualMatrix = actualArrangement();
        char[][] finalMatrix = getEndingMatrix();
        this.matrix = finalMatrix;
        this.endingMatrix = actualMatrix;
        boolean swapped = false;
        if (this.isVisible) {
            makeInvisible();
            swapped = true;
        }
        for (Board b: this.boards) {
            b.exchange();
        }
        Board temp = this.boards.get(0);
        this.boards.set(0, this.boards.get(1));
        this.boards.set(1, temp);
        if (swapped) {
            makeVisible();
        }
    }

    /**
     * Creates a hole at the specified empty cell in the puzzle.
     * @param row       Row of the board
     * @param column    Column of the board
     */
    public void makeHole(int row, int column) throws BoardExceptions, HoleExceptions {
        if (row > this.rows || row <= 0 || column > this.columns || column <= 0) {
            throw new BoardExceptions(BoardExceptions.OUT_OF_BOUNDS);
        }
        if (this.matrix[row - 1][column - 1] != '.') {
            throw new BoardExceptions(BoardExceptions.CANT_HOLE);
        }
        if (this.boards.get(0).doesThisPositionHaveHole(row, column)) {
            throw new BoardExceptions(BoardExceptions.EXISTING_HOLE);
        }
        this.boards.get(0).makeHole(row, column);
    }
    
    
    // Methods made public for the purpose of running unit tests and PuzzleContest

    /**
     * Changes the actual state of the board to the given arrangement.
     * @param currentState      The arrangement in which the actual matrix will be changed.
     */
    public void setArrangement(char[][] currentState, HashMap<String, Tile> tileList) {
        for (int i = 0; i < this.rows; i++) {
            for (int j = 0; j < this.columns; j++) {
                this.matrix[i][j] = currentState[i][j];
            }
        }
        this.boards.get(0).changeTiles(tileList);
    }
}
