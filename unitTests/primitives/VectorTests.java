package primitives;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testing Vectors
 * @author Tomere Kalman and Yosef Kornfield
 */
class VectorTests {

    private static final double DELTA = 0.000001;

    /**
     * Test method for {@link primitives.Vector#Vector(double, double, double)}.
     */
    @Test
    public void testConstructor() {
        // =============== Boundary Values Tests ==================
        // TC01: Test that zero vector throws an exception
        Exception exception = assertThrows(IllegalArgumentException.class, () -> new Vector(0, 0, 0),
                "Constructing a zero vector does not throw an exception");
        // TC02: Test that zero vector throws illegal argument exception
        assertInstanceOf(IllegalArgumentException.class, exception,
                "Constructing a zero vector does not throw an IllegalArgumentException");
    }

    /**
    * Test method for
    * {@link primitives.Vector#crossProduct(primitives.Vector)}.
    */
    @Test
    void testCrossProduct() {
        // ============ Equivalence Partitions Tests ==============
        Vector v1 = new Vector(0, 0, 1);
        Vector v2 = new Vector(1, 0, 0);
        Vector v3 = new Vector(0, 0 ,25);
        Vector vr = v1.crossProduct(v2);
        // TC01: Test that length of cross-product is proper (orthogonal vectors taken
        // for simplicity)
        assertEquals(v1.length() * v2.length(), vr.length(), DELTA,
                "crossProduct() wrong result length");
        // TC02: Test cross-product result orthogonality to its operands
        assertEquals(0, vr.dotProduct(v1), "crossProduct() result is not orthogonal to 1st operand");
        assertEquals(0, vr.dotProduct(v2), "crossProduct() result is not orthogonal to 2nd operand");
        // =============== Boundary Values Tests ==================
        // TC11: test zero vector from cross-product of parallel vectors
        assertThrows(IllegalArgumentException.class, () -> v1.crossProduct(v3), //
                "crossProduct() for parallel vectors does not throw an exception");
    }

    /**
     * Test method for {@link primitives.Vector#add(primitives.Vector)}.
     */
    @Test
    void testAdd() {
        // ============ Equivalence Partitions Tests ==============
        Vector v1 = new Vector(1, 2, 3);
        Vector v2 = new Vector(-2, -4, -6);
        Vector v10 = new Vector(-1, -2, -3);
        Vector result = v1.add(v2);
        // TC01: Test that addition of vectors is proper
        assertEquals(v10, result, "add() wrong result");
        // =============== Boundary Values Tests ==================
        // TC11: Test that adding a vector to its reversed self throws an exception
        Exception exception = assertThrows(IllegalArgumentException.class, () -> v1.add(v10),
                "add() for opposite vector does not throw an exception");
        // TC12: Test that adding a vector to its reversed self throws illegal argument exception
        assertInstanceOf(IllegalArgumentException.class, exception,
                "add() for opposite vector does not throw an IllegalArgumentException");

    }

    /**
     * Test method for {@link primitives.Vector#scale(double)}.
     */
    @Test
    void testLengthSquared() {
        // ============ Equivalence Partitions Tests ==============
        Vector v = new Vector(1, 2, 2);
        // TC01: Test that length squared of the vector is proper
        assertEquals(9, v.lengthSquared(), DELTA, "lengthSquared() wrong value");
    }

    /**
     * Test method for {@link primitives.Vector#length()}.
     */
    @Test
    void testLength() {
        // ============ Equivalence Partitions Tests ==============
        Vector v = new Vector(1, 2, 2);
        // TC01: Test that length of the vector is proper
        assertEquals(3, v.length(), DELTA, "length() wrong value");

    }

    /**
     * Test method for {@link primitives.Vector#normalize()}.
     */
    @Test
    void testNormalize() {
        // ============ Equivalence Partitions Tests ==============
        Vector v = new Vector(1, 2, 2);
        Vector vNormal = v.normalize();
        // TC01: Test that normalizing the vector is proper
        assertEquals(1, vNormal.lengthSquared(), DELTA, "normalize() wrong value");
        // TC02: Test that normalizing the vector does not result in an opposite vector
        assertTrue(v.dotProduct(vNormal) > 0, "normalize() creates an opposite vector");
        // ================= Boundary Values Tests ==================
        // TC11: Test that the cross product of a vector with its normalized self throws an exception
        assertThrows(IllegalArgumentException.class, () -> v.crossProduct(vNormal),
                "crossProduct() for parallel vectors does not throw an exception");
    }

    /**
     * Test method for {@link primitives.Vector#dotProduct(primitives.Vector)}.
     */
    @Test
    void testDotProduct() {
        Vector v1 = new Vector(1, -2, 3);
        Vector v2 = new Vector(-2, -4, -6);
        Vector v3 = new Vector(0, 3, 2);
        // ============ Equivalence Partitions Tests ==============
        // TC01: Test that dot product of vectors is proper
        assertEquals(-12, v1.dotProduct(v2), DELTA, "dotProduct() wrong value");
        // TC02: Test that dot product of orthogonal vectors is proper
        assertEquals(0, v1.dotProduct(v3), DELTA, "dotProduct() for orthogonal vectors is not zero");
    }
}