package domain;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;

/**
 * The POOBVsZombies class manages the core functionality of the game, including
 * managing active matches, saving and loading game data, and keeping track of the player’s
 * name and high score.
 *
 * @author Botero Garcia, Santiago. Perilla Quintero, Laura Natalia.
 * @version November 20 2024
 */
public final class POOBVsZombies implements Serializable {
    private String playerName;
    private int highScore;
    private HashMap<String, Level> activeMatches;
    private static final String SAVE_FILE = "data.dat";

    /**
     * Default constructor that initializes the game and loads any saved data.
     */
    public POOBVsZombies() {
        loadData();
    }

    /**
     * Starts a new match based on the given difficulty, game mode, and lawn.
     */
    public Level play(String gameMode, String lawn, int sun, int waves, double time, String machine) throws POOBVsZombiesException {
        return switch (gameMode) {
            case "PvM" -> new PvM(lawn, sun, waves, time);
            case "PvP" -> new PvP(lawn, sun, time);
            case "MvM" -> new MvM(lawn, sun, machine ,time);
            default -> throw new POOBVsZombiesException(POOBVsZombiesException.INVALID_GAMEMODE + gameMode);
        };
    }

    /**
     * Starts a new match by selecting an existing level type ("PvM", "PvP", "MvM").
     */
    public Level play(String gameMode) {
        return activeMatches.get(gameMode);
    }

    /**
     * Loads the saved game data from a file, including active matches, player name, and high score.
     */
    public void loadData() {
        Path path = Paths.get(SAVE_FILE);
        activeMatches = new HashMap<>();
        if (!Files.exists(path)) {
            playerName = "";
            highScore = 0;
            return;
        }
        try (ObjectInputStream in = new ObjectInputStream(Files.newInputStream(path))) {
            POOBVsZombies loadedData = (POOBVsZombies) in.readObject();
            this.activeMatches = loadedData.activeMatches;
            this.playerName = loadedData.playerName;
            this.highScore = loadedData.highScore;
        } catch (IOException | ClassNotFoundException _) {
        }
    }

    /**
     * Loads the saved game data from a file, including active matches, player name, and high score.
     */
    public void loadData(File file) {
        Path path = file.toPath();
        activeMatches = new HashMap<>();
        if (!Files.exists(path)) {
            playerName = "";
            highScore = 0;
            return;
        }
        try (ObjectInputStream in = new ObjectInputStream(Files.newInputStream(path))) {
            POOBVsZombies loadedData = (POOBVsZombies) in.readObject();
            this.activeMatches = loadedData.activeMatches;
            this.playerName = loadedData.playerName;
            this.highScore = loadedData.highScore;
        } catch (IOException | ClassNotFoundException _) {
        }
    }

    /**
     * Saves the current game data to a file, including active matches, player name, and high score.
     */
    public void saveData() {
        Path path = Paths.get(SAVE_FILE);
        try (ObjectOutputStream out = new ObjectOutputStream(Files.newOutputStream(path))) {
            out.writeObject(this);
        } catch (IOException _) {
        }
    }

    /**
     * Saves the current game data to a file, including active matches, player name, and high score.
     */
    public void saveData(File file) {
        Path path = file.toPath();
        try (ObjectOutputStream out = new ObjectOutputStream(Files.newOutputStream(path))) {
            out.writeObject(this);
        } catch (IOException _) {
        }
    }

    /**
     * Sets the player's name.
     *
     * @param name The name of the player.
     */
    public void setPlayerName(String name) {
        this.playerName = name;
        saveData();
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
    public void changeHighScore(int score) {
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
        boolean isActive = false;
        Level level = activeMatches.get(gameMode);
        if (level != null) {
            isActive = true;
        }
        return isActive;
    }

    /**
     * Adds a new match to the active matches list and saves the game data.
     *
     * @param level The Level object representing the match to be added.
     */
    public void setActiveMatch(Level level) {
        activeMatches.put(level.getClass().getSimpleName(), level);
        saveData();
    }

    /**
     * Removes an active match from the list of active matches based on the specified game mode.
     *
     * @param gameMode The game mode of the match to be removed ("PvM", "PvP", "MvM").
     */
    public void removeActiveMatch(String gameMode) {
        Level levelToRemove = activeMatches.get(gameMode);
        if (levelToRemove != null) {
            activeMatches.remove(gameMode);
            saveData();
        }
    }
}