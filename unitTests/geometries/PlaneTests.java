package geometries;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import primitives.Point;
import primitives.Vector;


class PlaneTests {


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
}