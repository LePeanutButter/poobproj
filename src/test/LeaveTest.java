import domain.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

public class LeaveTest {
    private Leave leave;

    @BeforeEach
    public void setUp() {
        leave = new Leave(new int[]{50, 100});
    }

    @Test
    public void testInitialPosition() {
        assertEquals(50, leave.getHitbox().x);
        assertEquals(100, leave.getHitbox().y);
    }

    @Test
    public void testMove() {
        int initialX = leave.getHitbox().x;
        leave.move();
        assertEquals(initialX + 15, leave.getHitbox().x);
        assertEquals(100, leave.getHitbox().y);
    }

    @Test
    public void testHitboxCollision() {
        Rectangle otherRect = new Rectangle(110, 150, 10, 10);
        assertFalse(leave.hitboxCollision(otherRect));
    }
    
    @AfterEach
    public void tearDown() {
        leave = null;
    }
}
