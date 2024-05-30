package geometries;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import geometries.Polygon;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

/**
 * Testing Polygons
 * @author Dan
 */
public class PolygonTests {
   /**
    * Delta value for accuracy when comparing the numbers of type 'double' in
    * assertEquals
    */
   private final double DELTA = 0.000001;

   /** Test method for {@link geometries.Polygon#Polygon(primitives.Point...)}. */
   @Test
   public void testConstructor() {
      // ============ Equivalence Partitions Tests ==============

      // TC01: Correct concave quadrangular with vertices in correct order
      assertDoesNotThrow(() -> new Polygon(new Point(0, 0, 1),
                                           new Point(1, 0, 0),
                                           new Point(0, 1, 0),
                                           new Point(-1, 1, 1)),
                         "Failed constructing a correct polygon");

      // TC02: Wrong vertices order
      assertThrows(IllegalArgumentException.class, //
                   () -> new Polygon(new Point(0, 0, 1), new Point(0, 1, 0), new Point(1, 0, 0), new Point(-1, 1, 1)), //
                   "Constructed a polygon with wrong order of vertices");

      // TC03: Not in the same plane
      assertThrows(IllegalArgumentException.class, //
                   () -> new Polygon(new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 1, 0), new Point(0, 2, 2)), //
                   "Constructed a polygon with vertices that are not in the same plane");

      // TC04: Concave quadrangular
      assertThrows(IllegalArgumentException.class, //
                   () -> new Polygon(new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 1, 0),
                                     new Point(0.5, 0.25, 0.5)), //
                   "Constructed a concave polygon");

      // =============== Boundary Values Tests ==================

      // TC10: Vertex on a side of a quadrangular
      assertThrows(IllegalArgumentException.class, //
                   () -> new Polygon(new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 1, 0),
                                     new Point(0, 0.5, 0.5)),
                   "Constructed a polygon with vertix on a side");

      // TC11: Last point = first point
      assertThrows(IllegalArgumentException.class, //
                   () -> new Polygon(new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 1, 0), new Point(0, 0, 1)),
                   "Constructed a polygon with vertice on a side");

      // TC12: Co-located points
      assertThrows(IllegalArgumentException.class, //
                   () -> new Polygon(new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 1, 0), new Point(0, 1, 0)),
                   "Constructed a polygon with vertice on a side");

   }

   /** Test method for {@link geometries.Polygon#getNormal(primitives.Point)}. */
   @Test
   public void testGetNormal() {
      // ============ Equivalence Partitions Tests ==============
      // TC01: There is a simple single test here - using a quad
      Point[] pts =
         { new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 1, 0), new Point(-1, 1, 1) };
      Polygon pol = new Polygon(pts);
      // ensure there are no exceptions
      assertDoesNotThrow(() -> pol.getNormal(new Point(0, 0, 1)), "");
      // generate the test result
      Vector result = pol.getNormal(new Point(0, 0, 1));
      // ensure |result| = 1
      assertEquals(1, result.length(), DELTA, "Polygon's normal is not a unit vector");
      // ensure the result is orthogonal to all the edges
      for (int i = 0; i < 3; ++i)
         assertEquals(0d, result.dotProduct(pts[i].subtract(pts[i == 0 ? 3 : i - 1])), DELTA,
                      "Polygon's normal is not orthogonal to one of the edges");
   }

    /**
     * Test method for {@link Polygon#findIntersections(Ray)}.
     */
    @Test
    public void testFindIntersections() {
        Polygon polygon = new Polygon(new Point(0, 0, 0),
                new Point(4, 0, 0),
                new Point(4, 4, 0),
                new Point(0, 4, 0));
        // Expected intersection points
        final Point gp1 = new Point(2, 2, 0);

        // Vectors for rays
        final Vector v001 = new Vector(0, 0, -1);

        // Ray starting points
        //EP:
        final Point p01 = new Point(2, 2, 2);
        final Point p02 = new Point(2, 6, 1);
        final Point p03 = new Point(-1, -1, 1);
        //BVA:
        final Point p04 = new Point(2, 0, 1);
        final Point p05 = new Point(0, 0, 1);
        final Point p06 = new Point(5, 0, 1);

        // ============ Equivalence Partitions Tests ==============
        // TC01: Inside Polygon
        final var result01 = polygon.findIntersections(new Ray(p01, v001));
        assertEquals(1, result01.size(), "ray should pass only 1 point");
        assertEquals(gp1, result01.getFirst(), "Ray crosses polygon");
        // TC02: Outside against edge
        final var result02 = polygon.findIntersections(new Ray(p02, v001));
        assertNull(result02, "Ray starts outside the polygon, against an edge and doesn't intersect it");
        // TC03: Outside against vertex
        final var result03 = polygon.findIntersections(new Ray(p03, v001));
        assertNull(result03, "Ray starts outside the polygon, against a vertex and doesn't intersect it");

        // =============== Boundary Values Tests ==================
        // TC11: On edge
        final var result11 = polygon.findIntersections(new Ray(p04, v001));
        assertNull(result11, "Ray intersects on edge of polygon");
        //TC12: In vertex
        final var result12 = polygon.findIntersections(new Ray(p05, v001));
        assertNull(result12, "Ray intersects in vertex of triangle");
        // TC13: On edge's continuation
        final var result13 = polygon.findIntersections(new Ray(p06, v001));
        assertNull(result13, "Ray intersects on edge's continuation of polygon");
    }
}
