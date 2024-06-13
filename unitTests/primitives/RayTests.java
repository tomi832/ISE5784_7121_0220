package primitives;

import org.junit.jupiter.api.Test;

import java.util.List;

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

    @Test
    void testFindClosestPoint() {
        Ray ray = new Ray(Point.ZERO, new Vector(1, 0, 0));
        // ============ Equivalence Partitions Tests ==============
        // TC01: Closest point in the middle of the list
        assertEquals(new Point(1, 0, 0), ray.findClosestPoint(List.of(new Point(2, 0, 0), new Point(1, 0, 0), new Point(3, 0, 0))));

        // ================== Boundary Values Tests ==================
        // TC11: Closest point is the first point in the list
        assertEquals(new Point(1, 0, 0), ray.findClosestPoint(List.of(new Point(1, 0, 0), new Point(2, 0, 0), new Point(3, 0, 0))));
        // TC12: Closest point is the last point in the list
        assertEquals(new Point(1, 0, 0), ray.findClosestPoint(List.of(new Point(3, 0, 0), new Point(2, 0, 0), new Point(1, 0, 0))));
        // TC13: List is empty
        assertNull(ray.findClosestPoint(List.of()));
    }
}