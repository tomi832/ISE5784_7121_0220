package geometries;

import org.junit.jupiter.api.Test;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;
import geometries.Intersectable.GeoPoint;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testing Triangles
 * @author Tomere Kalman and Yosef Kornfeld
 */
class TriangleTests {

    /**
     * test method for {@link geometries.Triangle#Triangle(primitives.Point, primitives.Point, primitives.Point)}
     */
    @Test
    void testConstructor() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: Correct vertices
        assertDoesNotThrow(() -> new Triangle(new Point(0, 0, 1),
                        new Point(1, 0, 0),
                        new Point(0, 1, 0)),
                "Failed constructing a correct triangle");

    }

    /**
     * test method for {@link geometries.Triangle#getNormal(primitives.Point)}
     */
    @Test
    void testGetNormal() {
        //============ Equivalence Partitions Tests ==============
        //TC01: checking that it brings back the right normal
        Point p1 = new Point(0,0,0);
        Triangle tri = new Triangle(new Point(1,0,0),
                                new Point(1, 1, 0),
                                p1);
        Vector normal = tri.getNormal(p1);
        Vector possibleNormal = new Vector(0,0,1);
        if (possibleNormal.dotProduct(normal) > 0) {
            assertEquals(possibleNormal, normal,
                "returns incorrect normal");
        }
        else {
            assertEquals(new Vector(0,0,-1), tri.getNormal(p1),
                "returns incorrect normal");
        }
    }

    /**
     * test method for {@link Triangle#findIntersections(Ray)}
     */
    @Test
    void testFindIntersections() {
        final Triangle triangle = new Triangle(
                new Point(-2, 0, 0),
                new Point(2, 0, 0),
                new Point(0, 4, 0));

        //expected intersection points:
        //EP:
        final Point gp01 = new Point(1, 1, 0);

        //vectors for rays:
        final Vector v001 = new Vector(0,0,-1);

        //ray starting points:
        //EP:
        final Point p01 = new Point(1, 1, 1);
        final Point p02 = new Point(4, 4,  1);
        final Point p03 = new Point(-2.5, -0.5, 1);
        //BVA:
        final Point p11 = new Point(1, 0, 1);
        final Point p12 = new Point(2, 0, 1);
        final Point p13 = new Point(3, 0, 1);
        
        // ============ Equivalence Partitions Tests ==============
        // TC01: Inside triangle
        final var result01 = triangle.findIntersections(new Ray(p01, v001));
        assertEquals(1, result01.size(), "ray should pass only 1 point");
        assertEquals(List.of(gp01), result01, "Wrong intersection with triangle");
        // TC02: Outside against edge
        final var result02 = triangle.findIntersections(new Ray(p02, v001));
        assertNull(result02, "Ray starts outside the triangle and doesn't intersect it");
        // TC03: Outside against vertex
        final var result03 = triangle.findIntersections(new Ray(p03, v001));
        assertNull(result03, "Ray starts outside the triangle and doesn't intersect it");

        // ================= Boundary Values Tests ==================
        // TC11: On edge
        final var result11 = triangle.findIntersections(new Ray(p11, v001));
        assertNull(result11, "Ray intersects on edge of triangle");
        // TC12: In vertex
        final var result12 = triangle.findIntersections(new Ray(p12, v001));
        assertNull(result12, "Ray intersects in vertex of triangle");
        // TC13: On edge's continuation
        final var result13 = triangle.findIntersections(new Ray(p13, v001));
        assertNull(result13, "Ray intersects on edge's continuation of triangle");
    }


    @Test
    void testFindGeoIntersectionsHelper() {
        final Triangle triangle = new Triangle(
                new Point(-2, 0, 0),
                new Point(2, 0, 0),
                new Point(0, 4, 0));

        //expected intersection points:
        //EP:
        final Point gp01 = new Point(1, 1, 0);

        //vectors for rays:
        final Vector v001 = new Vector(0,0,-1);

        //ray starting point:
        final Point p01 = new Point(1, 1, 1);

        // ============ Equivalence Partitions Tests ==============
        // TC01: distance greater than triangle (1 point)
        final var result01 = triangle.findGeoIntersectionsHelper(new Ray(p01, v001), 2);
        assertEquals(1, result01.size(), "ray should pass only 1 point");
        assertEquals(List.of(new GeoPoint(triangle, gp01)), result01, "Wrong intersection with triangle");
        // TC02: distance smaller than triangle (0 points)
        final var result02 = triangle.findGeoIntersectionsHelper(new Ray(p01, v001), 0.5);
        assertNull(result02, "Ray doesn't intersect the triangle");
        // ================= Boundary Values Tests ==================
        // TC11: distance equal to triangle (1 point)
        final var result11 = triangle.findGeoIntersectionsHelper(new Ray(p01, v001), 1);
        assertEquals(1, result11.size(), "ray should pass only 1 point");
        assertEquals(List.of(new GeoPoint(triangle, gp01)), result11, "Wrong intersection with triangle");
    }
}