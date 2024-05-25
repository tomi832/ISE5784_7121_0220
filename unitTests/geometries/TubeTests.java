package geometries;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

/**
 * Testing Tubes
 * @author Tomere Kalman and Yosef Kornfield
 */
class TubeTests {

    /**test method for {@link geometries.Tube#getNormal(primitives.Point)} */
    @Test
    void testGetNormal() {
        Tube tube = new Tube(10, new Ray(new Point(0, 0, 0), new Vector(0, 0, 1)));
        Point p1 = new Point(10, 0, 1);
        Point p2 = new Point(10, 0, 0);
        //============ Equivalence Partitions Tests ==============
        assertEquals(new Vector(1, 0, 0), tube.getNormal(p1), "wrong normal to tube");
        // =============== Boundary Values Tests ==================
        // TC11: testing that it returns the right normal
        assertEquals(new Vector(1, 0, 0), tube.getNormal(p2), "wrong normal to tube");
    }
}