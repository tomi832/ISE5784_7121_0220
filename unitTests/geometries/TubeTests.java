package geometries;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.Comparator;
import java.util.List;

/**
 * Testing Tubes
 * @author Tomere Kalman and Yosef Kornfeld
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

    @Test
    void testFindIntersections() {
        Tube tube = new Tube(1, new Ray(Point.ZERO, new Vector(0, 0, 1)));

        //expected intersection points
        Point gp1 = new Point(1, 0, 1);
        Point gp2 = new Point(-1, 0, 1);
        Point gp3 = new Point(1, 0, 0);
        Point gp4 = new Point(-1, 0, 0);
        final var exp1 = List.of(gp1, gp2);
        final var exp2 = List.of(gp3, gp4);

        //Vectors for rays
        Vector v100 = new Vector(1, 0, 0);
        Vector v001 = new Vector(0, 0, 1);

        //Ray starting points
        Point p1 = new Point(2, 0, 1);
        Point p2 = new Point(-2, 0, 1);
        Point p3 = new Point(0, 0, 1);
        Point p4 = new Point(1, 0, 1);
        Point p5 = new Point(2, 0, 0);

        // ============ Equivalence Partitions Tests ==============
        // TC01: Ray starts after the tube (0 points)
        assertNull(tube.findIntersections(new Ray(p1, v100)), "Ray's line is after the tube");
        // TC02: Ray starts before and crosses the tube (2 points)
        final var result1 = tube.findIntersections(new Ray(p2, v100))
                .stream()
                .sorted(Comparator.comparingDouble(p -> p.distance(p1)))
                .toList();
        assertEquals(2, result1.size(), "Wrong number of points");
        assertEquals(exp1, result1, "Ray crosses the tube");
        // TC03: Ray starts inside the tube (1 point)
        final var result2 = tube.findIntersections(new Ray(p3, v100));
        assertEquals(1, result2.size(), "Wrong number of points");
        assertEquals(gp1, result2.getFirst(), "Ray starts inside the tube");
        // =============== Boundary Values Tests ==================
        // TC11: Ray is parallel to the tube (0 points)
        assertNull(tube.findIntersections(new Ray(p3, v001)), "Ray is parallel to the tube");
        // TC12: Ray starts at the tube and crosses it (1 point)
        final var result3 = tube.findIntersections(new Ray(gp2, v100));
        assertEquals(1, result3.size(), "Wrong number of points");
        assertEquals(gp1, result3.getFirst(), "Ray starts at the tube and crosses it");
        // TC13: Ray starts at the tube and goes outside (0 points)
        assertNull(tube.findIntersections(new Ray(p4, v100)), "Ray starts at the tube and goes outside");
        //TC14: Ray is parallel to the tube and starts inside it (0 points)
        assertNull(tube.findIntersections(new Ray(Point.ZERO, v001)), "Ray is parallel to the tube and starts inside it");
        //TC15: Ray is parallel to the tube at starts at it (0 points)
        assertNull(tube.findIntersections(new Ray(gp3, v001)), "ray is parallel to the tube at starts at it");
        //TC16: Ray goes through the origin point (2 points)
        final var result16 = tube.findIntersections(new Ray(p5, v100))
                .stream()
                .sorted(Comparator.comparingDouble(p -> p.distance(p5)))
                .toList();
        assertEquals(2, result16.size(), "Wrong number of points");
        assertEquals(exp2, result16, "Ray goes through the origin point");
    }
}