package primitives;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static primitives.Util.isZero;

/**
 * unit tests for primitives.Point class
 * @author Tomere Kalman and Yosef Kornfield
 */
class PointTests {
    /**
     * Test method for {@link primitives.Point#distanceSquared(primitives.Point)}.
     */
    @Test
    void testDistanceSquared() {
        Point p1 = new Point(1, 2, 5);  // Example initialization
        Point p2 = new Point(3, 4, 5);  // Example initialization
        // ============ Equivalence Partitions Tests ==============
        // TC01: Test that distance squared between two points is proper
        assertTrue(isZero(p1.distanceSquared(p2) - 8), "ERROR: distanceSquared() wrong result");
        // TC02: Test that distance squared between two points is proper when reversed
        assertTrue(isZero(p2.distanceSquared(p1) - 8), "ERROR: distanceSquared() wrong result");
        // =============== Boundary Values Tests ==================
        // TC11: Test that the distance of a point to itself is zero
        assertTrue(isZero(p1.distanceSquared(p1)), "ERROR: distanceSquared() does not return zero for the same point");
    }

    /**
     * Test method for {@link primitives.Point#distance(primitives.Point)}.
     */
    @Test
    void testDistance() {
        Point p1 = new Point(1, 2, 5);  // Example initialization
        Point p2 = new Point(3, 4, 5);  // Example initialization
        // ============ Equivalence Partitions Tests ==============
        // TC01: Test that distance between two points is proper
        assertTrue(isZero(p1.distance(p2) - Math.sqrt(8)), "ERROR: distance() wrong result");
        // TC02: Test that distance between two points is proper when reversed
        assertTrue(isZero(p2.distance(p1) - Math.sqrt(8)), "ERROR: distance() wrong result");
        // =============== Boundary Values Tests ==================
        // TC11: Test that the distance of a point to itself is zero
        assertTrue(isZero(p1.distance(p1)), "ERROR: distance() does not return zero for the same point");
    }

    /**
     * Test method for {@link primitives.Point#add(primitives.Vector)}.
     */
    @Test
    void testAdd() {
        Point p1 = new Point(1, 2, 5);  // Example initialization
        Point p2 = new Point(2, 4, 10);  // Example initialization
        Vector v1 = new Vector(1, 2, 5);  // Example initialization
        Vector v10 = new Vector(-1, -2, -5);  // Example initialization
        // ============ Equivalence Partitions Tests ==============
        // TC01: Test that addition of vectors is proper
        assertEquals(p2, p1.add(v1), "ERROR: (point + vector) = other point does not work correctly");
        // TC02: Test that addition of vectors is proper
        assertEquals(Point.ZERO, p1.add(v10), "ERROR: (point + vector) = center of coordinates does not work correctly");
    }

    /**
     * Test method for {@link primitives.Point#subtract(primitives.Point)}.
     */
    @Test
    void testSubtract() {
        Point p1 = new Point(1, 2, 5);  // Example initialization
        Point p2 = new Point(3, 4, 5);  // Example initialization
        Vector v1 = new Vector(2, 2, 0);  // Example initialization
        // ============ Equivalence Partitions Tests ==============
        // TC01: Test (point2 - point1) works correctly
        assertEquals(v1, p2.subtract(p1), "ERROR: (point2 - point1) does not work correctly");
        // =============== Boundary Values Tests ==================
        // TC11: Test (point - itself) throws IllegalArgumentException
        Exception exception = assertThrows(Exception.class, () -> {
            p1.subtract(p1);
        }, "ERROR: (point - itself) does not throw an exception");

        // TC12: Test (point - itself) throws IllegalArgumentException
        assertInstanceOf(IllegalArgumentException.class, exception, "ERROR: (point - itself) throws wrong exception");
    }
}