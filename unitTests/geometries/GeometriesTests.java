package geometries;

import org.junit.jupiter.api.Test;
import primitives.*;

import static org.junit.jupiter.api.Assertions.*;

class GeometriesTests {

    @Test
    void testAdd() {
    }

    @Test
    void testFindIntersections() {
        //Geometrical bodies
        Triangle triangle = new Triangle(new Point(-1, -1, 1), new Point(1, -1, 1), new Point(0, 1, 1));
        Sphere sphere = new Sphere(new Point(0, 0, 1), 1);
        Polygon quadPoly1 = new Polygon(new Point(5, 5, 1), new Point(5, 10, 1), new Point(10, 10, 1), new Point(10, 5, 1));
        Polygon quadPoly2 = new Polygon(new Point(1,1,1), new Point(1,-1,1), new Point(-1,-1,1), new Point(-1, 1,1));

        //geometries list

        Geometries geometries1 = new Geometries(triangle, sphere, quadPoly1);
        Geometries geometries2 = new Geometries(triangle, sphere, quadPoly2);
        Geometries geometries3 = new Geometries();
        Geometries geometries4 = new Geometries(triangle, sphere, quadPoly1, quadPoly2);

        //Ray
        Ray ray1 = new Ray(new Point(0, 0, 5), new Vector(0, 0, -1));
        Ray ray2 = new Ray(new Point(7, 7, 5), new Vector(0, 0, -1));
        Ray rei = new Ray(new Point(0, 0, 100), new Vector(0, 0, 1));
        // ============ Equivalence Partitions Tests ==============
        // TC01: The ray intersects most, but not all, of the geometries
        assertEquals(3, geometries1.findIntersections(ray1).size(), "ray intersects some of the geometries");
        //============= Boundary Values Tests ==================
        // TC11: The ray intersects all geometries
        assertEquals(4, geometries2.findIntersections(ray1).size(), "ray intersects all of the geometries");
        // TC12: The ray intersects none of the geometries
        assertNull(geometries3.findIntersections(ray1), "ray intersects none of the geometries");
        // TC13: The ray intersects only one geometry
        assertEquals(1, geometries4.findIntersections(ray2).size(), "ray intersects only one geometry");
        // TC14: The ray intersects none of the geometries
        assertNull(geometries4.findIntersections(rei), "ray intersects none of the geometries");

    }
}