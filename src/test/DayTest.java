import domain.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DayTest {
    private Day day;

    @BeforeEach
    public void setUp() {
        day = new Day();
    }

    @Test
    public void testCanSunFall() {
        assertTrue(day.canSunFall());
    }

    @AfterEach
    public void tearDown() {
        day = null;
    }
}
