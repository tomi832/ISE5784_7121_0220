package geometries;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

/**
 * Testing Planes
 * @author Tomere Kalman and Yosef Kornfeld
 */
class PlaneTests {

    /** test method for {@link geometries.Plane#Plane(primitives.Point, primitives.Point, primitives.Point)} */
    @Test
    void testConstructor() {
        // ============ Equivalence Partitions Tests ==============
        //TC01: creating a plane with 3 Points
        assertDoesNotThrow(() -> new Plane(new Point(2, 3, 5),
                                           new Point(5, 3, 5),
                                           new Point(4, 5, 5)),
                              "failed to construct a plane");

        // =============== Boundary Values Tests ==================
        //TC11: trying to create a plane with 3 Points that lay in a line.
        assertThrows(IllegalArgumentException.class, () -> new Plane(new Point(3, 4,4),
                                                                     new Point(5, 4, 4),
                                                                     new Point(7, 4, 4)),
                                            "constructs a plane with 3 points in a line");

        //TC12: trying to create a plane with 2 Points that are the same.
        assertThrows(IllegalArgumentException.class, () -> new Plane(new Point(3, 4, 4),
                                                                     new Point(3, 4, 4),
                                                                     new Point(7, 4, 4)),
                                            "constructs a plane with 2 points that are the same");
    }

    /**test method for {@link geometries.Plane#getNormal(primitives.Point)} */
    @Test
    void testGetNormal() {
        //============ Equivalence Partitions Tests ==============
        //TC01: checking that it brings back the right normal
        Plane plane = new Plane(new Point(1,0,0),
                                new Point(1, 1, 0),
                                new Point(0, 0 , 0));
        Vector normal = plane.getNormal();
        Vector possibleNormal = new Vector(0,0,1);
        if (possibleNormal.dotProduct(normal) > 0) {
            assertEquals(possibleNormal, normal,
                "returns incorrect normal");
        }
        else {
            assertEquals(new Vector(0,0,-1), plane.getNormal(),
                "returns incorrect normal");
        }
    }

    /**
     *
     */
    @Test
    void testFindIntersections() {
        //TODO: don't use point Zero
        final Vector v001 = new Vector(0,0,1);
        final Plane plane = new Plane(Point.ZERO, v001);

        //expected intersection points:
        final Point gp01 = new Point(1,0,0);

        //vectors for rays:
        final Vector v101 = new Vector(1,0,1);
        final Vector v100 = new Vector(1,0,0);

        //ray starting points:
        final Point p01 = new Point(0,0,-1);
        final Point p02 = new Point(0,0,1);

        // ============ Equivalence Partitions Tests ==============
        // TC01: Ray starts outside the plane and intersects it at a sharp-angle (1 point)
        final var result01 = plane.findIntersections(new Ray(p01, v101));
        assertEquals(1, result01.size(), "ray should pass only 1 point");
        assertEquals(gp01, result01.getFirst(), "Ray starts outside the plane");
        //TC02: Ray starts outside the plane and doesn't intersect it (0 points)
        final var result02 = plane.findIntersections(new Ray(p02, v101));
        assertNull(result02, "Ray starts outside the plane and doesn't intersect it");

        // ================= Boundary Values Tests ==================
        //TC11: Ray is parallel to the plane outside of it (0 points)
        final var result11 = plane.findIntersections(new Ray(p02, v100));
        assertNull(result11, "Ray is parallel to the plane");
        //TC12: Ray is parallel to the plane and is included in it (0 points)
        final var result12 = plane.findIntersections(new Ray(gp01, v100));
        assertNull(result12, "Ray is parallel to the plane and is included in it");
        //TC13: Ray is orthogonal to the plane and starts at it (0 points)
        final var result13 = plane.findIntersections(new Ray(Point.ZERO, v001));
        assertNull(result13, "Ray is orthogonal to the plane and starts at it");
        //TC14: Ray is orthogonal to the plane and starts before the plane (1 point)
        final var result14 = plane.findIntersections(new Ray(p01, v001));
        assertEquals(1, result14.size(), "ray should pass only 1 point");
        assertEquals(Point.ZERO, result14.getFirst(), "Ray is orthogonal to the plane and starts before the plane");
        //TC15: Ray is orthogonal to the plane and starts after the plane (0 points)
        final var result15 = plane.findIntersections(new Ray(p02, v001));
        assertNull(result15, "Ray is orthogonal to the plane and starts after the plane");
        //TC16: Ray isn't orthogonal to the plane and starts at it (0 points)
        final var result16 = plane.findIntersections(new Ray(gp01, v101));
        assertNull(result16, "Ray isn't orthogonal to the plane and starts at it");
        //TC17: Ray isn't orthogonal to the plane and starts at the reference point (0 points)
        final var result17 = plane.findIntersections(new Ray(Point.ZERO, v101));
        assertNull(result17, "Ray isn't orthogonal to the plane and starts at reference point");
    }
}