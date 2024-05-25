package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testing Spheres
 * @author Tomere Kalman and Yosef Kornfield
 */
class SphereTests {

    /** test method for {@link geometries.Sphere#getNormal(primitives.Point)} */
    @Test
    void testGetNormal() {
        // ============ Equivalence Partitions Tests ==============
        Sphere s = new Sphere(Point.ZERO, 12);
        Point pt = new Point(0, 0, 12);
        // TC01: Test that getNormal() returns the proper value
        assertEquals(new Vector(0, 0, 1), s.getNormal(pt), "getNormal() wrong result");
    }
}