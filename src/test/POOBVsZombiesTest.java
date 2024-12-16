import domain.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import java.io.File;

public class POOBVsZombiesTest {
    private POOBVsZombies game;

    @BeforeEach
    public void setUp() {
        File[] files = new File(".").listFiles((_, name) -> name.endsWith(".dat"));
        if (files != null) {
            for (File file : files) {
                file.delete();
            }
        }
        game = new POOBVsZombies();
    }

    @Test
    public void testSetAndGetPlayerName() {
        game.setPlayerName("Player1");
        assertEquals("Player1", game.getPlayerName());
    }

    @Test
    public void testChangeHighScore() {
        game.changeHighScore(100);
        assertEquals(100, game.getHighScore());
        game.changeHighScore(50);
        assertEquals(100, game.getHighScore()
        );
    }   

    @Test
    public void testPlayWithGameMode() throws POOBVsZombiesException {
        Level match = game.play("PvM", "Day", 2000, 3, 3, "");
        game.setActiveMatch(match);
        assertTrue(game.hasActiveMatch("PvM"));
    }

    @Test
    public void testThrowsPlayWithGameMode() {
        try {
            game.play("gdrgd", "Lawn", 5000, 3, 3, "");
            fail("error");
        } catch (POOBVsZombiesException e) {
            assertEquals("Invalid Game Mode: gdrgd", e.getMessage());
        }
    }

    @Test
    public void testHasActiveMatchWhenMatchDoesNotExist() {
        assertFalse(game.hasActiveMatch("PvP"));
    }

    @Test
    public void testSaveAndLoadData() {
        game.setPlayerName("Laura");
        game.changeHighScore(200);
        game.saveData();

        POOBVsZombies newGame = new POOBVsZombies();
        assertEquals("Laura", newGame.getPlayerName());
        assertEquals(200, newGame.getHighScore());
    }

    @Test
    public void testPlayActiveMatch() throws POOBVsZombiesException {
        Level match = game.play("MvM", "Day", 2000, 3, 3, "strategic");
        game.setActiveMatch(match);
        Level altMatch = game.play("MvM");
        assertNotNull(altMatch);
    }

    @Test
    public void testRemoveActiveMatch() throws POOBVsZombiesException {
        Level match = game.play("PvP", "Day", 2000, 3, 3, "");
        game.setActiveMatch(match);
        game.removeActiveMatch("PvP");
        assertFalse(game.hasActiveMatch("PvP"));

    }

    @Test
    public void testSaveDataWithFile() {
        File testFile = new File("test_data.dat");
        game.saveData(testFile);
        assertTrue(testFile.exists());
        testFile.delete();
    }

    @Test
    public void testLoadDataWithFile() {
        game.setPlayerName("Laura");
        File testFile = new File("test_data.dat");
        game.saveData(testFile);
        game = new POOBVsZombies();
        game.loadData(testFile);
        assertEquals("Laura", game.getPlayerName());
        testFile.delete();
    }

    @Test
    public void testLoadWithoutDataFile() {
        game.loadData(new File("alsndkanskd"));
        assertEquals("", game.getPlayerName());
    }

    @AfterEach
    public void tearDown() {
        File[] files = new File(".").listFiles((dir, name) -> name.endsWith(".dat"));
        if (files != null) {
            for (File file : files) {
                file.delete();
            }
        }
        game = null;
    }
}
