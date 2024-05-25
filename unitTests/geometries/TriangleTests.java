package geometries;

import org.junit.jupiter.api.Test;

import primitives.Point;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testing Triangles
 * @author Tomere Kalman and Yosef Kornfield
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
}