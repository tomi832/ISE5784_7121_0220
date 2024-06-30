package geometries;

import org.junit.jupiter.api.Test;
import primitives.*;

import java.util.Comparator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testing Spheres
 * @author Tomere Kalman and Yosef Kornfeld
 */
class SphereTests {

    /**
     * test method for {@link geometries.Sphere#getNormal(primitives.Point)}
     */
    @Test
    void testGetNormal() {
        // ============ Equivalence Partitions Tests ==============
        Sphere s = new Sphere(Point.ZERO, 12);
        Point pt = new Point(0, 0, 12);
        // TC01: Test that getNormal() returns the proper value
        assertEquals(new Vector(0, 0, 1), s.getNormal(pt), "getNormal() wrong result");
    }

    /**
     * Test method for {@link geometries.Sphere#findIntersections(primitives.Ray)}.
     */
    @Test
    public void testFindIntersections() {
        final Point p100 = new Point(1, 0, 0);
        Sphere sphere = new Sphere(p100, 1d);
        // Expected intersection points
        final Point gp1 = new Point(0.0651530771650466, 0.355051025721682, 0);
        final Point gp2 = new Point(1.53484692283495, 0.844948974278318, 0);
        final var exp02 = List.of(gp1, gp2);
        final Point gp3 = new Point(1, 1, 0);
        final Point gp4 = new Point(0, 0, 0);
        final Point gp5 = new Point(2, 0, 0);
        final var exp13 = List.of(gp4, gp5);
        // Vectors for rays
        final Vector v310 = new Vector(3, 1, 0);
        final Vector v110 = new Vector(1, 1, 0);
        final Vector v010 = new Vector(0, 1, 0);
        final Vector v100 = new Vector(1, 0, 0);
        // Ray starting points
        final Point p01 = new Point(-1, 0, 0);
        final Point p02 = new Point(1, 0.5, 0);
        final Point p03 = new Point(3, 0, 0);
        final Point p04 = new Point(0.5, 0, 0);
        final Point p05 = new Point(0, -1, 0);
        final Point p06 = new Point(0, 1, 0);

        // ============ Equivalence Partitions Tests ==============
        // TC01: Ray's line is outside the sphere (0 points)
        assertNull(sphere.findIntersections(new Ray(p01, v110)), "Ray's line out of sphere");
        // TC02: Ray starts before and crosses the sphere (2 points)
        final var result1 = sphere.findIntersections(new Ray(p01, v310))
                .stream()
                .sorted(Comparator.comparingDouble(p -> p.distance(p01)))
                .toList();
        assertEquals(2, result1.size(), "Wrong number of intersection points");
        assertEquals(exp02, result1, "Ray crosses sphere");
        // TC03: Ray starts inside the sphere (1 point)
        final var result2 = sphere.findIntersections(new Ray(p02, v010));
        assertEquals(1, result2.size(), "ray should pass only 1 point");
        assertEquals(gp3, result2.getFirst(), "Ray from inside the sphere");
        // TC04: Ray starts after the sphere (0 points)
        assertNull(sphere.findIntersections(new Ray(p03, v010)), "Ray's line out of sphere");
        // =============== Boundary Values Tests ==================
        // **** Group: Ray's line crosses the sphere (but not the center)
        // TC11: Ray starts at sphere and goes inside (1 point)
        final var result3 = sphere.findIntersections(new Ray(gp1, v310));
        assertEquals(1, result3.size(), "ray should pass only 1 point");
        assertEquals(gp2, result3.getFirst(), "Ray begins at the sphere itself and goes inside");
        // TC12: Ray starts at sphere and goes outside (0 points)
        assertNull(sphere.findIntersections(new Ray(gp2, v310)), "Ray's line out of sphere");
        // **** Group: Ray's line goes through the center
        // TC13: Ray starts before the sphere (2 points)
        final var result13 = sphere.findIntersections(new Ray(p01,v100))
                .stream()
                .sorted(Comparator.comparingDouble(p -> p.distance(p01)))
                .toList();
        assertEquals(2, result13.size(), "Wrong number of intersection points");
        assertEquals(exp13, result13, "Ray crosses sphere");
        // TC14: Ray starts at sphere and goes inside (1 point)
        final var result14 = sphere.findIntersections(new Ray(gp4, v100));
        assertEquals(1, result14.size(), "ray should pass only 1 point");
        assertEquals(gp5, result14.getFirst(), "ray doesn't pass through the correct intersection point");
        // TC15: Ray starts inside (1 point)
        final var result15 = sphere.findIntersections(new Ray(p04, v100));
        assertEquals(1, result15.size(), "ray should pass only 1 point");
        assertEquals(gp5, result15.getFirst(), "ray doesn't pass through the correct intersection point");
        // TC16: Ray starts at the center (1 point)
        final var result16 = sphere.findIntersections(new Ray(p100, v100));
        assertEquals(1, result16.size(), "ray should pass only 1 point");
        assertEquals(gp5, result16.getFirst(), "ray doesn't pass through the correct intersection point");
        // TC17: Ray starts at sphere and goes outside (0 points)
        assertNull(sphere.findIntersections(new Ray(gp5, v100)), "Ray's line out of sphere");
        // TC18: Ray starts after sphere (0 points)
        assertNull(sphere.findIntersections(new Ray(p03, v100)), "Ray's line out of sphere");
        // **** Group: Ray's line is tangent to the sphere (all tests 0 points)
        // TC19: Ray starts before the tangent point
        final var result19 = sphere.findIntersections(new Ray(p05, v010));
        assertNull(result19, "Ray's line only tangent to sphere");
        // TC20: Ray starts at the tangent point
        final var result20 = sphere.findIntersections(new Ray(gp4, v010));
        assertNull(result20, "Ray's line only tangent to sphere");
        // TC21: Ray starts after the tangent point
        final var result21 = sphere.findIntersections(new Ray(p06, v010));
        assertNull(result21, "Ray's line only tangent to sphere");
        // **** Group: Special cases
        // TC22: Ray's line is outside, ray is orthogonal to ray start to sphere's center line
        final var result22 = sphere.findIntersections(new Ray(p03, v010));
        assertNull(result22, "Ray's line orthogonal to sphere");

    }

    @Test
    public void testFindGeoIntersectionsHelper() {
        final Sphere sphere = new Sphere(new Point(1,0,0), 1d);
        Point p01 = new Point(-1,0,0);
        final Ray ray = new Ray(p01, new Vector(1,0,0));

        //expected intersections
        GeoPoint gp01 = new GeoPoint(sphere, Point.ZERO);
        GeoPoint gp02 = new GeoPoint(sphere, new Point(2,0,0));
        final var exp = List.of(gp01, gp02);
        // ============ Equivalence Partitions Tests ===============
        //TC01: distance smaller than the sphere (0 points)
        assertNull(sphere.findGeoIntersectionsHelper(ray, 0.5), "Ray doesn't intersect the sphere");

        //TC02: distance greater than the sphere
        final var result1 = sphere.findGeoIntersectionsHelper(ray, 5)
                .stream()
                .sorted(Comparator.comparingDouble(geoP -> geoP.point.distance(p01)))
                .toList();

        assertEquals(2, result1.size(), "Wrong number of intersection points");
        assertEquals(exp, result1, "Wrong intersection with sphere");

        // =============== Boundary Values Tests ==================
        //TC11: distance equal to the close edge of the sphere (1 point)
        final var result2 = sphere.findGeoIntersectionsHelper(ray, 1);
        assertEquals(1, result2.size(), "Ray touches the sphere");
        assertEquals(gp01, result2.getFirst(), "Wrong intersection with sphere");

        //TC12: distance equal to the far edge of the sphere (2 points)
        final var result3 = sphere.findGeoIntersectionsHelper(ray, 3)
                .stream()
                .sorted(Comparator.comparingDouble(geoP -> geoP.point.distance(p01)))
                .toList();
        assertEquals(2, result3.size(), "Ray should have 2 points");
        assertEquals(exp, result3, "Wrong intersection with sphere");
    }
}