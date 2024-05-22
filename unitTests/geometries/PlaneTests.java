package geometries;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import primitives.Point;


class PlaneTests {

    private final double DELTA = 0.000001;

    @Test
    void testConstructor() {
        // ============ Equivalence Partitions Tests ==============
        //TC01: creating a plane with 3 Points
        assertDoesNotThrow(() -> new Plane(new Point(2, 3, 5),
                                           new Point(5, 3, 5),
                                           new Point(4, 5, 5)),
                              "failed to construct a plane");
        //TC02: trying to create a plane with 3 Points that lay in a line.
        assertThrows(IllegalArgumentException.class, () -> new Plane(new Point(3, 4,4),
                                                                     new Point(5, 4, 4),
                                                                     new Point(7, 4, 4)),
                                            "constructs a plane with 3 points in a line");
    }

    @Test
    void testGetNormal() {
        //============ Equivalence Partitions Tests ==============
        //TC01: 
    }

    @Test
    void testTestGetNormal() {
        //============ Equivalence Partitions Tests ==============
        //TC01:
    }
}