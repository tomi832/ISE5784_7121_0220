package geometries;

import org.junit.jupiter.api.Test;
import primitives.*;

import static org.junit.jupiter.api.Assertions.*;


/**
 * Testing Cylinders
 * @author Tomer Kalman and Yosef Kornfeld
 */
class CylinderTests {

    /**test method for {@link geometries.Cylinder#getNormal(primitives.Point)} */
    @Test
    void testGetNormal() {
        Cylinder cylinder = new Cylinder(10, new Ray(new Point(0, 0, 0), new Vector(0, 0, 1)), 10);
        Point p1 = new Point(10, 0, 1);
        Point middleBottomBase = new Point(0, 0, 0);
        Point middleTopBase = new Point(0, 0, 10);
        //============ Equivalence Partitions Tests ==============
        // TC01: testing that it returns the right normal
        assertEquals(new Vector(1, 0, 0), cylinder.getNormal(p1), "wrong normal to cylinder");
        //TC02: testing that it returns the right normal from the top
        assertEquals(new Vector(0, 0, 1), cylinder.getNormal(new Point(1, 0, 10)), "wrong normal to cylinder");
        //TC03: testing that it returns the right normal from the bottom
        assertEquals(new Vector(0, 0, 1), cylinder.getNormal(new Point(1, 0, 0)), "wrong normal to cylinder");
        // =============== Boundary Values Tests ==================
        //TC11: testing point in the middle of the top base
        assertEquals(new Vector(0, 0, 1), cylinder.getNormal(middleTopBase), "wrong normal to cylinder");
        //TC12: testing point in the middle of the bottom base
        assertEquals(new Vector(0, 0, 1), cylinder.getNormal(middleBottomBase), "wrong normal to cylinder");
    }
}