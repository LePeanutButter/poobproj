package domain;
import java.util.ArrayList;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * The POOBVsZombies class manages the core functionality of the game, including
 * managing active matches, saving and loading game data, and keeping track of the player’s
 * name and high score.
 *
 * @author Botero Garcia, Santiago. Perilla Quintero, Laura Natalia.
 * @version November 20 2024
 */
public class POOBVsZombies {
    private String playerName;
    private int highScore;
    private ArrayList<Level> activeMatches;
    private static final String SAVE_FILE = "saved_games.ser";

    /**
     * Default constructor that initializes the game and loads any saved data.
     */
    public POOBVsZombies() {
        loadData();
    }

    /**
     * Starts a new match based on the given difficulty, game mode, and lawn.
     */
    public Level play(String difficulty, String gameMode, String lawn){
        Level level = null;
        switch (gameMode) {
            case "PvM":
                level = new PvM(difficulty, lawn);
                setActiveMatch(level);
                break;
            case "PvP":
                level = new PvP(difficulty, lawn);
                setActiveMatch(level);
                break;
            case "MvM":
                level = new MvM(difficulty, lawn);
                setActiveMatch(level);
                break;
            default:
                throw new IllegalArgumentException("Invalid Game Mode: " + gameMode);
        }
        return level;
    }

    /**
     * Starts a new match by selecting an existing level type ("PvM", "PvP", "MvM").
     */
    public Level play(String level){
        Level lev = null;
        for (Level l : activeMatches) {
            String nameClass = l.getClass().getSimpleName();
            if (nameClass.equals(level)) {
                switch (level) {
                    case "PvM":
                        lev = new PvM(l);
                        break;
                    case "PvP":
                        lev = new PvP(l);
                        break;
                    case "MvM":
                        lev = new MvM(l);
                        break;
                    default:
                        throw new IllegalArgumentException("Invalid Game Mode: " + gameMode);
                }
            }
        }
        return lev;
    }

    /**
     * Loads the saved game data from a file, including active matches, player name, and high score.
     */
    public void loadData() {
        File file = new File(SAVE_FILE);
        if (!file.exists()) {
            activeMatches = new ArrayList<>();
            playerName = "";
            highScore = 0;
            return;
        }
        try (ObjectInputStream out = new ObjectInputStream(new FileInputStream(SAVE_FILE))) {
            activeMatches = (ArrayList<Level>) out.readObject();
            playerName = (String) out.readObject();
            highScore = out.readInt();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Saves the current game data to a file, including active matches, player name, and high score.
     */
    public void saveData() {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(SAVE_FILE))) {
            out.writeObject(activeMatches);
            out.writeObject(playerName);
            out.writeInt(highScore);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Sets the player's name.
     *
     * @param name The name of the player.
     */
    public void setPlayerName(String name) {
        this.playerName = name;
    }

    /**
     * Gets the player's name.
     *
     * @return The name of the player.
     */
    public String getPlayerName() {
        return playerName;
    }

    /**
     * Updates the high score if the given score is higher than the current high score.
     *
     * @param score The score to compare against the current high score.
     */
    public void changeHighScore(int score){
        if (score > highScore) {
            highScore = score;
        }
    }

    /**
     * Gets the current high score.
     *
     * @return The current high score.
     */
    public int getHighScore() {
        return highScore;
    }

    /**
     * Checks if there is an active match for the specified game mode.
     *
     * @param gameMode The game mode to check ("PvM", "PvP", "MvM").
     * @return true if there is an active match for the specified game mode, false otherwise.
     */
    public boolean hasActiveMatch(String gameMode) {
        for (Level l : activeMatches) {
            String nameClass = l.getClass().getSimpleName();
            if (nameClass.equals(gameMode)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Adds a new match to the active matches list and saves the game data.
     *
     * @param level The Level object representing the match to be added.
     */
    public void setActiveMatch(Level level){
        activeMatches.add(level);
        saveData();
    }

    /**
     * Removes an active match from the list of active matches based on the specified game mode.
     *
     * @param gameMode The game mode of the match to be removed ("PvM", "PvP", "MvM").
     */
    public void removeActiveMatch(String gameMode){
        for (Level l : activeMatches) {
            String nameClass = l.getClass().getSimpleName();
            if (nameClass.equals(gameMode)) {
                activeMatches.remove(l);
                saveData();
                break;
            }
        }
    }
}