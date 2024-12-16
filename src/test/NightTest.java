import domain.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class NightTest {
    private Night night;

    @BeforeEach
    public void setUp() {
        night = new Night();
    }

    @Test
    public void testCannotSunFall() {
        assertFalse(night.canSunFall());
    }

    @AfterEach
    public void tearDown() {
        night = null;
    }
}
