package primitives;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RayTests {

    @Test
    void testGetPoint() {
// ============ Equivalence Partitions Tests ==============
        Ray ray = new Ray(Point.ZERO, new Vector(1, 0, 0));
        // TC01: Positive distance
        assertEquals(new Point(1, 0, 0), ray.getPoint(1));
        // TC02: Negative distance
        assertEquals(new Point(-1, 0, 0), ray.getPoint(-1));
        // ================== Boundary Values Tests ==================
        // TC11: zero distance
        assertThrows(IllegalArgumentException.class, () -> ray.getPoint(0));
    }
}